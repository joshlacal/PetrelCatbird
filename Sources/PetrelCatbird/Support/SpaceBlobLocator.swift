import Foundation
import Petrel

public extension SpaceRef {
    /// Parses a canonical `at:space/{did}/{spaceType}/{skey}` reference or normalizes
    /// the Spaces alpha form `at://{did}/space/{spaceType}/{skey}` and the older
    /// `at://space/{did}/{spaceType}/{skey}` and `at://{did}/com.atproto.space.space/{rkey}`
    /// spellings on untrusted read boundaries (values read back from storage or records),
    /// validating each through `SpaceRef`. Never use it on request input, which takes
    /// only the canonical form.
    ///
    /// TODO: remove the alpha-form reading before beta.
    init?(hedgedURIString raw: String) {
        if let canonical = try? SpaceRef(uriString: raw) {
            self = canonical
            return
        }

        let parts: [String]
        if raw.hasPrefix("at://space/") {
            parts = raw.dropFirst("at://space/".count)
                .split(separator: "/", omittingEmptySubsequences: false).map(String.init)
            guard parts.count == 3 else {
                return nil
            }
        } else if raw.hasPrefix("at://") {
            let atParts = raw.dropFirst("at://".count)
                .split(separator: "/", omittingEmptySubsequences: false).map(String.init)
            if atParts.count == 4, atParts[1] == "space" || atParts[1] == "com.atproto.space.space" {
                // `at://{did}/space/{type}/{skey}` (alpha) or `.../com.atproto.space.space/{type}/{skey}`.
                parts = [atParts[0], atParts[2], atParts[3]]
            } else if atParts.count == 3, atParts[1] == "com.atproto.space.space" {
                let rkey = atParts[2]
                if let colon = rkey.firstIndex(of: ":"),
                   let normalized = try? SpaceRef(
                       spaceDID: atParts[0],
                       spaceType: String(rkey[..<colon]),
                       skey: String(rkey[rkey.index(after: colon)...])
                   ) {
                    self = normalized
                    return
                }
                parts = [atParts[0], "com.atproto.space.space", rkey]
            } else {
                return nil
            }
        } else {
            return nil
        }

        guard let normalized = try? SpaceRef(spaceDID: parts[0], spaceType: parts[1], skey: parts[2]) else {
            return nil
        }
        self = normalized
    }

    /// The `at:space/{spaceDID}/{spaceType}/{skey}` spelling, which is now the
    /// canonical form (the same string as `uriString()`).
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
