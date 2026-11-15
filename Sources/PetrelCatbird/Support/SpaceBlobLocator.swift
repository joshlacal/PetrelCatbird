import Foundation
import Petrel

public extension SpaceRef {
    /// Parses a canonical `at://{did}/space/{spaceType}/{skey}` reference or normalizes
    /// legacy `at:space/{did}/{spaceType}/{skey}`, `at://space/{did}/{spaceType}/{skey}`,
    /// and `at://{did}/com.atproto.space.space/{rkey}` spellings on untrusted read boundaries
    /// before validating with `SpaceRef(uriString:)`.
    init?(hedgedURIString raw: String) {
        if let canonical = try? SpaceRef(uriString: raw) {
            self = canonical
            return
        }

        if raw.hasPrefix("at://") {
            let atParts = raw.dropFirst("at://".count)
                .split(separator: "/", omittingEmptySubsequences: false)
            if atParts.count == 4, atParts[1] == "com.atproto.space.space" {
                let rewritten = "at://\(atParts[0])/space/\(atParts[2])/\(atParts[3])"
                if let normalized = try? SpaceRef(uriString: rewritten) {
                    self = normalized
                    return
                }
            } else if atParts.count == 3, atParts[1] == "com.atproto.space.space" {
                let rkey = String(atParts[2])
                if let colon = rkey.firstIndex(of: ":") {
                    let st = String(rkey[..<colon])
                    let sk = String(rkey[rkey.index(after: colon)...])
                    if let normalized = try? SpaceRef(uriString: "at://\(atParts[0])/space/\(st)/\(sk)") {
                        self = normalized
                        return
                    }
                }
                if let normalized = try? SpaceRef(uriString: "at://\(atParts[0])/space/com.atproto.space.space/\(rkey)") {
                    self = normalized
                    return
                }
            }
        }

        let remainder: Substring
        if raw.hasPrefix("at:space/") {
            remainder = raw.dropFirst("at:space/".count)
        } else if raw.hasPrefix("at://space/") {
            remainder = raw.dropFirst("at://space/".count)
        } else {
            return nil
        }

        let parts = remainder.split(separator: "/", omittingEmptySubsequences: false)
        guard parts.count == 3 else {
            return nil
        }
        let rewritten = "at://\(parts[0])/space/\(parts[1])/\(parts[2])"
        guard let normalized = try? SpaceRef(uriString: rewritten) else {
            return nil
        }
        self = normalized
    }

    /// Emits the hedged `at:space/{spaceDID}/{spaceType}/{skey}` spelling.
    var hedgedURIString: String {
        "at:space/\(spaceDID)/\(spaceType)/\(skey)"
    }
}

/// Canonical client-local locator for an encrypted blob stored in an ATProto Space:
/// `{spaceURI}|{repoDID}|{cid}`.
///
/// Decodes both the canonical pipe-delimited spelling and the legacy
/// `{spaceURI}/{repoDID}#{cid}` transitional spelling, and always encodes to the
/// canonical pipe-delimited spelling.
public struct SpaceBlobLocator: Sendable, Equatable, Hashable, Codable, CustomStringConvertible {
    public let space: SpaceRef
    public let repo: DID
    public let cid: CID

    public init(space: SpaceRef, repo: DID, cid: CID) {
        self.space = space
        self.repo = repo
        self.cid = cid
    }

    /// Canonical `{spaceURI}|{repoDID}|{cid}` wire/storage representation.
    public var encoded: String {
        "\(space.uriString())|\(repo.description)|\(cid.string)"
    }

    public var description: String {
        encoded
    }

    public init?(parsing raw: String) {
        if raw.contains("|") {
            guard !raw.contains("#") else {
                return nil
            }
            let parts = raw.split(separator: "|", omittingEmptySubsequences: false)
            guard parts.count == 3,
                  let space = SpaceRef(hedgedURIString: String(parts[0])),
                  let repo = try? DID(didString: String(parts[1])),
                  repo.atprotoMethod != nil,
                  let cid = try? CID.parse(String(parts[2]))
            else {
                return nil
            }
            self.init(space: space, repo: repo, cid: cid)
            return
        }

        // Transitional compatibility: `{spaceURI}/{repoDID}#{cid}`
        let hashParts = raw.split(separator: "#", omittingEmptySubsequences: false)
        guard hashParts.count == 2 else {
            return nil
        }
        let prefix = hashParts[0]
        guard let slashIndex = prefix.lastIndex(of: "/") else {
            return nil
        }
        let rawSpace = String(prefix[..<slashIndex])
        let rawRepo = String(prefix[prefix.index(after: slashIndex)...])
        let rawCid = String(hashParts[1])
        guard let space = SpaceRef(hedgedURIString: rawSpace),
              let repo = try? DID(didString: rawRepo),
              repo.atprotoMethod != nil,
              let cid = try? CID.parse(rawCid)
        else {
            return nil
        }
        self.init(space: space, repo: repo, cid: cid)
    }

    public init(from decoder: Decoder) throws {
        let container = try decoder.singleValueContainer()
        let raw = try container.decode(String.self)
        guard let parsed = SpaceBlobLocator(parsing: raw) else {
            throw DecodingError.dataCorruptedError(
                in: container,
                debugDescription: "Invalid SpaceBlobLocator: \(raw)"
            )
        }
        self = parsed
    }

    public func encode(to encoder: Encoder) throws {
        var container = encoder.singleValueContainer()
        try container.encode(encoded)
    }
}
