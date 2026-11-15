import Foundation
import Petrel
import PetrelCatbird
import Testing

@Suite("Wave 1 Convergence Contracts")
struct Wave1ConvergenceContractTests {
    private static let sampleSpace = "at://did:web:ds.catbird.blue/space/blue.catbird.labyrinth.conversation/conv1-0"
    private static let sampleRepo = "did:plc:ewvi7nxzyoun6zhxrhs64oiz"
    private static let sampleCid = "bafkreihdwdcefgh4dqkjv67uzcmw7ojee6xedzdetojuzjevtenxquvyku"

    private static func resolveRepoRoot() throws -> URL {
        var candidate = URL(fileURLWithPath: #filePath)
        for _ in 0 ..< 5 {
            candidate = candidate.deletingLastPathComponent()
            let probe = candidate.appendingPathComponent("lexicons/blue/catbird/chat/blue.catbird.chat.authFull.json")
            if FileManager.default.fileExists(atPath: probe.path) {
                return candidate
            }
        }
        let cwd = URL(fileURLWithPath: FileManager.default.currentDirectoryPath)
        if FileManager.default.fileExists(
            atPath: cwd.appendingPathComponent("lexicons/blue/catbird/chat/blue.catbird.chat.authFull.json").path
        ) {
            return cwd
        }
        throw CocoaError(.fileNoSuchFile)
    }

    @Test func spaceRefHedgedNormalizationAndRejections() throws {
        let canonical = SpaceRef(hedgedURIString: Self.sampleSpace)
        #expect(canonical?.uriString() == Self.sampleSpace)

        let atColon = SpaceRef(
            hedgedURIString: "at:space/did:web:ds.catbird.blue/blue.catbird.labyrinth.conversation/conv1-0"
        )
        #expect(atColon?.uriString() == Self.sampleSpace)

        let atDoubleSlash = SpaceRef(
            hedgedURIString: "at://space/did:web:ds.catbird.blue/blue.catbird.labyrinth.conversation/conv1-0"
        )
        #expect(atDoubleSlash?.uriString() == Self.sampleSpace)
        #expect(atDoubleSlash?.hedgedURIString == "at:space/did:web:ds.catbird.blue/blue.catbird.labyrinth.conversation/conv1-0")

        let recordForm = SpaceRef(
            hedgedURIString: "at://did:web:ds.catbird.blue/com.atproto.space.space/blue.catbird.labyrinth.conversation:conv1-0"
        )
        #expect(recordForm?.uriString() == Self.sampleSpace)

        let bareSpaceRecordForm = SpaceRef(
            hedgedURIString: "at://did:web:ds.catbird.blue/com.atproto.space.space/conv1-0"
        )
        #expect(bareSpaceRecordForm?.uriString() == "at://did:web:ds.catbird.blue/space/com.atproto.space.space/conv1-0")

        #expect(SpaceRef(hedgedURIString: "at://did:plc:ewvi7nxzyoun6zhxrhs64oiz/app.bsky.feed.post/3l2aaaa") == nil)
        #expect(SpaceRef(hedgedURIString: "\(Self.sampleSpace)/\(Self.sampleRepo)/blue.catbird.labyrinth.batch/3l2aaaa") == nil)
    }

    @Test func spaceBlobLocatorRoundTripsAndNormalizesLegacyFormat() throws {
        let canonicalRaw = "\(Self.sampleSpace)|\(Self.sampleRepo)|\(Self.sampleCid)"
        let parsed = try #require(SpaceBlobLocator(parsing: canonicalRaw))
        #expect(parsed.encoded == canonicalRaw)
        #expect(parsed.space.skey == "conv1-0")
        #expect(parsed.repo.description == Self.sampleRepo)
        #expect(parsed.cid.string == Self.sampleCid)

        let legacyRaw = "\(Self.sampleSpace)/\(Self.sampleRepo)#\(Self.sampleCid)"
        let legacyParsed = try #require(SpaceBlobLocator(parsing: legacyRaw))
        #expect(legacyParsed.encoded == canonicalRaw)

        let hedgedLegacy = "at:space/did:web:ds.catbird.blue/blue.catbird.labyrinth.conversation/conv1-0/\(Self.sampleRepo)#\(Self.sampleCid)"
        let hedgedParsed = try #require(SpaceBlobLocator(parsing: hedgedLegacy))
        #expect(hedgedParsed.encoded == canonicalRaw)

        let json = try JSONEncoder().encode(legacyParsed)
        let decoded = try JSONDecoder().decode(SpaceBlobLocator.self, from: json)
        #expect(decoded == parsed)

        #expect(SpaceBlobLocator(parsing: "\(Self.sampleSpace)|not-a-did|\(Self.sampleCid)") == nil)
        #expect(SpaceBlobLocator(parsing: "\(Self.sampleSpace)|\(Self.sampleRepo)|not-a-cid") == nil)
        #expect(SpaceBlobLocator(parsing: "\(Self.sampleSpace)|\(Self.sampleRepo)|\(Self.sampleCid)|extra") == nil)
    }

    @Test func spaceDeclarationsUseTitleAndRegisterAuthorRedaction() throws {
        let repoRoot = try Self.resolveRepoRoot()

        let declarations = [
            "lexicons/blue/catbird/blue.catbird.circle.json",
            "lexicons/blue/catbird/blue.catbird.circle.encrypted.json",
            "lexicons/blue/catbird/labyrinth/blue.catbird.labyrinth.mailbox.json",
            "lexicons/blue/catbird/labyrinth/blue.catbird.labyrinth.conversation.json",
        ]

        for rel in declarations {
            let data = try Data(contentsOf: repoRoot.appendingPathComponent(rel))
            let doc = try #require(JSONSerialization.jsonObject(with: data) as? [String: Any])
            let defs = try #require(doc["defs"] as? [String: Any])
            let main = try #require(defs["main"] as? [String: Any])
            #expect(main["type"] as? String == "space")
            #expect((main["title"] as? String)?.isEmpty == false)
            #expect(main["name"] == nil)
            if rel.hasSuffix("blue.catbird.labyrinth.conversation.json") {
                let collections = try #require(main["collections"] as? [String])
                #expect(collections.contains("blue.catbird.labyrinth.authorRedaction"))
            }
            if rel.hasSuffix("blue.catbird.labyrinth.mailbox.json") {
                let collections = try #require(main["collections"] as? [String])
                #expect(collections.contains("blue.catbird.labyrinth.readState"))
                #expect(collections.contains("blue.catbird.labyrinth.circleOwnerState"))
            }
        }
        #expect(BlueCatbirdLabyrinthMailbox.spaceDeclaration.collections.contains("blue.catbird.labyrinth.readState"))
        #expect(BlueCatbirdLabyrinthMailbox.spaceDeclaration.collections.contains("blue.catbird.labyrinth.circleOwnerState"))
    }

    @Test func authFullMatchesLiveChatCorpusAndCreateOnlyRepoPermissions() throws {
        let repoRoot = try Self.resolveRepoRoot()
        let chatDir = repoRoot.appendingPathComponent("lexicons/blue/catbird/chat")

        let authData = try Data(contentsOf: chatDir.appendingPathComponent("blue.catbird.chat.authFull.json"))
        let authDoc = try #require(JSONSerialization.jsonObject(with: authData) as? [String: Any])
        let defs = try #require(authDoc["defs"] as? [String: Any])
        let main = try #require(defs["main"] as? [String: Any])
        let permissions = try #require(main["permissions"] as? [[String: Any]])
        #expect(permissions.count == 2)

        let rpcPerm = try #require(permissions.first(where: { $0["resource"] as? String == "rpc" }))
        let lxms = try #require(rpcPerm["lxm"] as? [String])
        #expect(lxms.count == 36)

        let retired = [
            "blue.catbird.chat.deleteBlob",
            "blue.catbird.chat.getBlob",
            "blue.catbird.chat.getBlobUsage",
            "blue.catbird.chat.prepareBlobUpload",
            "blue.catbird.chat.updatePushToken",
            "blue.catbird.chat.uploadBlob",
        ]
        for nsid in retired {
            #expect(!lxms.contains(nsid))
        }
        for requiredLive in [
            "blue.catbird.chat.acknowledgeEntries",
            "blue.catbird.chat.approveDevice",
            "blue.catbird.chat.registerPushGateway",
            "blue.catbird.chat.relaySpaceCredential",
            "blue.catbird.chat.relaySpaceRead",
        ] {
            #expect(lxms.contains(requiredLive))
        }

        let repoPerm = try #require(permissions.first(where: { $0["resource"] as? String == "repo" }))
        #expect(repoPerm["action"] as? [String] == ["create"])
        #expect(
            repoPerm["collection"] as? [String] == [
                "blue.catbird.chat.device",
                "blue.catbird.chat.deviceRevocation",
                "blue.catbird.labyrinth.mailboxKeys",
            ]
        )
    }

    @Test func deviceRevocationRecordRoundTrips() throws {
        let keyB64 = Data(repeating: 0x33, count: 32).base64EncodedString()
        let sigB64 = Data(repeating: 0x44, count: 64).base64EncodedString()
        let revocationJson = """
        {
            "$type": "blue.catbird.chat.deviceRevocation",
            "targetDeviceId": "00000000-0000-4000-8000-000000000002",
            "actorDeviceId": "00000000-0000-4000-8000-000000000001",
            "signerPublicKey": {"$bytes": "\(keyB64)"},
            "signature": {"$bytes": "\(sigB64)"},
            "algorithm": "ed25519",
            "reason": "revoked_by_sibling",
            "createdAt": "2026-11-15T12:00:00Z"
        }
        """.data(using: .utf8)!
        let rev = try JSONDecoder().decode(BlueCatbirdChatDeviceRevocation.self, from: revocationJson)
        #expect(rev.targetDeviceId == "00000000-0000-4000-8000-000000000002")
        #expect(rev.actorDeviceId == "00000000-0000-4000-8000-000000000001")
        #expect(rev.signerPublicKey.data.count == 32)
        #expect(rev.signature.data.count == 64)
        #expect(rev.algorithm == "ed25519")
    }

    @Test func authorRedactionAndNov12RelayTypesDecode() throws {
        let pubkeyB64 = Data(repeating: 0x11, count: 32).base64EncodedString()
        let sigB64 = Data(repeating: 0x22, count: 64).base64EncodedString()
        let redactionJson = """
        {
            "$type": "blue.catbird.labyrinth.authorRedaction",
            "threadId": "conv-001",
            "messageId": "msg-001",
            "redaction": {
                "transportPubkey": {"$bytes": "\(pubkeyB64)"},
                "sig": {"$bytes": "\(sigB64)"},
                "redactedAt": "2026-11-13T12:00:00Z"
            },
            "createdAt": "2026-11-13T12:00:00Z"
        }
        """.data(using: .utf8)!
        let redaction = try JSONDecoder().decode(BlueCatbirdLabyrinthAuthorRedaction.self, from: redactionJson)
        #expect(redaction.threadId == "conv-001")
        #expect(redaction.messageId == "msg-001")
        #expect(redaction.redaction.transportPubkey?.data.count == 32)

        let readInputJson = """
        {
            "actorDeviceId": "00000000-0000-4000-8000-000000000001",
            "targetDid": "did:web:ds.catbird.blue",
            "nsid": "com.atproto.space.listWriters",
            "credential": "jwt.payload.sig",
            "spaceAudience": "did:web:ds.catbird.blue",
            "acceptSignature": "mac=(\\"atproto-space-commit\\";sf);keyid=\\"did:key:zDnaex62me84JZnkEzmeYRa8FCLNe7y1asoSwBMK26GBYpL7c\\";tag=\\"atproto-space-commit\\"",
            "signatureInput": "atproto-space-credential=(\\"authorization\\" \\"atproto-space-audience\\");created=1793462400;nonce=\\"n1\\";tag=\\"atproto-space-credential\\"",
            "signature": "atproto-space-credential=:AAEC:"
        }
        """.data(using: .utf8)!
        let readInput = try JSONDecoder().decode(BlueCatbirdChatRelaySpaceRead.Input.self, from: readInputJson)
        #expect(readInput.nsid == "com.atproto.space.listWriters")
        #expect(readInput.acceptSignature?.contains("atproto-space-commit") == true)

        let credInputJson = """
        {
            "actorDeviceId": "00000000-0000-4000-8000-000000000001",
            "authorityDid": "did:web:ds.catbird.blue",
            "space": "at://did:web:ds.catbird.blue/space/blue.catbird.labyrinth.conversation/conv1-0",
            "delegationToken": "eyJhbGciOiJFUzI1NiJ9.eyJzdWIiOiJzcGFjZSJ9.c2ln",
            "cnfKey": "did:key:zDnaeWM8zmBiwzf8n42vMCPdXAWPvt8T13XQMBXZmZoLB3fCF",
            "spaceRev": "3l2aaaaaaaaaa",
            "notify": {
                "endpoint": "https://ds.catbird.blue/xrpc/com.atproto.space.notifyRepoUpdate",
                "token": "0123456789abcdef0123456789abcdef"
            }
        }
        """.data(using: .utf8)!
        let credInput = try JSONDecoder().decode(BlueCatbirdChatRelaySpaceCredential.Input.self, from: credInputJson)
        #expect(credInput.spaceRev?.description == "3l2aaaaaaaaaa")
        #expect(credInput.notify?.endpoint.uriString() == "https://ds.catbird.blue/xrpc/com.atproto.space.notifyRepoUpdate")
    }

    @Test func wave1bMultiDeviceAndMailboxStateContractsDecode() throws {
        let keyB64 = Data(repeating: 0x33, count: 32).base64EncodedString()
        let sigB64 = Data(repeating: 0x44, count: 64).base64EncodedString()
        let ctB64 = Data(repeating: 0x55, count: 48).base64EncodedString()

        let deviceJson = """
        {
            "deviceId": "00000000-0000-4000-8000-000000000002",
            "keyId": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
            "signaturePublicKey": {"$bytes": "\(keyB64)"},
            "authGeneration": 1,
            "status": "pending_approval",
            "createdAt": "2026-11-13T12:00:00.000Z",
            "updatedAt": "2026-11-13T12:00:00.000Z",
            "availablePackageCount": 10,
            "reservedPackageCount": 0,
            "deviceName": "Josh MacBook Pro",
            "lastActiveAt": "2026-11-13T12:05:00.000Z",
            "dormantAt": "2026-11-15T12:05:00.000Z"
        }
        """.data(using: .utf8)!
        let deviceView = try JSONDecoder().decode(BlueCatbirdChatDefs.DeviceView.self, from: deviceJson)
        #expect(deviceView.status.rawValue == "pending_approval")
        #expect(deviceView.deviceName == "Josh MacBook Pro")
        #expect(deviceView.lastActiveAt != nil)
        #expect(deviceView.dormantAt != nil)

        let readStateJson = """
        {
            "$type": "blue.catbird.labyrinth.readState",
            "ciphertext": {"$bytes": "\(ctB64)"},
            "epoch": 7,
            "updatedAt": "2026-11-13T12:10:00Z"
        }
        """.data(using: .utf8)!
        let readState = try JSONDecoder().decode(BlueCatbirdLabyrinthReadState.self, from: readStateJson)
        #expect(readState.epoch == 7)
        #expect(readState.ciphertext.data.count == 48)

        let circleStateJson = """
        {
            "$type": "blue.catbird.labyrinth.circleOwnerState",
            "ciphertext": {"$bytes": "\(ctB64)"},
            "epoch": 9,
            "updatedAt": "2026-11-13T12:15:00Z"
        }
        """.data(using: .utf8)!
        let circleState = try JSONDecoder().decode(BlueCatbirdLabyrinthCircleOwnerState.self, from: circleStateJson)
        #expect(circleState.epoch == 9)
        #expect(circleState.ciphertext.data.count == 48)

        let revJson = """
        {
            "revokedBy": {"$bytes": "\(keyB64)"},
            "sig": {"$bytes": "\(sigB64)"}
        }
        """.data(using: .utf8)!
        let revocation = try JSONDecoder().decode(BlueCatbirdLabyrinthDefs.DeviceRevocation.self, from: revJson)
        #expect(revocation.revokedBy.data.count == 32)
        #expect(revocation.sig.data.count == 64)
    }
}
