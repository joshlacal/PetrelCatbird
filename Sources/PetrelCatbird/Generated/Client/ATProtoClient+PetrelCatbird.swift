import Foundation
import Petrel

// Generated namespace extensions for the PetrelCatbird overlay package.

public extension ATProtoClient {
    var blue: Blue { Blue(networkService: networkService) }

    public final class Blue: @unchecked Sendable {
        public let networkService: NetworkService
        public init(networkService: NetworkService) {
            self.networkService = networkService
        }

        public lazy var catbird: Catbird = .init(networkService: networkService)

        public final class Catbird: @unchecked Sendable {
            public let networkService: NetworkService
            public init(networkService: NetworkService) {
                self.networkService = networkService
            }

            public lazy var bskychat: Bskychat = .init(networkService: networkService)

            public final class Bskychat: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var mlsChat: MlsChat = .init(networkService: networkService)

            public final class MlsChat: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var mlsDS: MlsDS = .init(networkService: networkService)

            public final class MlsDS: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

        }

    }

}

public extension ATProtoClient {
    var place: Place { Place(networkService: networkService) }

    public final class Place: @unchecked Sendable {
        public let networkService: NetworkService
        public init(networkService: NetworkService) {
            self.networkService = networkService
        }

        public lazy var stream: Stream = .init(networkService: networkService)

        public final class Stream: @unchecked Sendable {
            public let networkService: NetworkService
            public init(networkService: NetworkService) {
                self.networkService = networkService
            }

            public lazy var defs: Defs = .init(networkService: networkService)

            public final class Defs: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var key: Key = .init(networkService: networkService)

            public final class Key: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var livestream: Livestream = .init(networkService: networkService)

            public final class Livestream: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var segment: Segment = .init(networkService: networkService)

            public final class Segment: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var video: Video = .init(networkService: networkService)

            public final class Video: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var badge: Badge = .init(networkService: networkService)

            public final class Badge: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var branding: Branding = .init(networkService: networkService)

            public final class Branding: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var broadcast: Broadcast = .init(networkService: networkService)

            public final class Broadcast: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var chat: Chat = .init(networkService: networkService)

            public final class Chat: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var config: Config = .init(networkService: networkService)

            public final class Config: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var graph: Graph = .init(networkService: networkService)

            public final class Graph: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var ingest: Ingest = .init(networkService: networkService)

            public final class Ingest: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var live: Live = .init(networkService: networkService)

            public final class Live: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var metadata: Metadata = .init(networkService: networkService)

            public final class Metadata: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var moderation: Moderation = .init(networkService: networkService)

            public final class Moderation: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var multistream: Multistream = .init(networkService: networkService)

            public final class Multistream: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var muxl: Muxl = .init(networkService: networkService)

            public final class Muxl: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var playback: Playback = .init(networkService: networkService)

            public final class Playback: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var richtext: Richtext = .init(networkService: networkService)

            public final class Richtext: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

            public lazy var server: Server = .init(networkService: networkService)

            public final class Server: @unchecked Sendable {
                public let networkService: NetworkService
                public init(networkService: NetworkService) {
                    self.networkService = networkService
                }

            }

        }

    }

}

