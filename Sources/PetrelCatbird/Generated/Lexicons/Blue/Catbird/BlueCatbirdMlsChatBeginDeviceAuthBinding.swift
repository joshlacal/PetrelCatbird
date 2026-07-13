import Foundation
import Petrel



// lexicon: 1, id: blue.catbird.mlsChat.beginDeviceAuthBinding


public struct BlueCatbirdMlsChatBeginDeviceAuthBinding { 

    public static let typeIdentifier = "blue.catbird.mlsChat.beginDeviceAuthBinding"
public struct Input: ATProtocolCodable {
        public let deviceId: String

        /// Standard public initializer
        public init(deviceId: String) {
            self.deviceId = deviceId
        }
        

        public init(from decoder: Decoder) throws {
            let container = try decoder.container(keyedBy: CodingKeys.self)
            self.deviceId = try container.decode(String.self, forKey: .deviceId)
        }

        public func encode(to encoder: Encoder) throws {
            var container = encoder.container(keyedBy: CodingKeys.self)
            try container.encode(deviceId, forKey: .deviceId)
        }

        public func toCBORValue() throws -> Any {
            var map = OrderedCBORMap()
            let deviceIdValue = try deviceId.toCBORValue()
            map = map.adding(key: "deviceId", value: deviceIdValue)
            return map
        }

        private enum CodingKeys: String, CodingKey {
            case deviceId
        }
    }
    
public struct Output: ATProtocolCodable {
        
        
        public let challengeId: String
        
        public let challenge: Bytes
        
        public let expiresAt: ATProtocolDate
        
        public let bindingVersion: Int
        
        
        
        // Standard public initializer
        public init(
            
            
            challengeId: String,
            
            challenge: Bytes,
            
            expiresAt: ATProtocolDate,
            
            bindingVersion: Int
            
            
        ) {
            
            
            self.challengeId = challengeId
            
            self.challenge = challenge
            
            self.expiresAt = expiresAt
            
            self.bindingVersion = bindingVersion
            
            
        }
        
        public init(from decoder: Decoder) throws {
            
            let container = try decoder.container(keyedBy: CodingKeys.self)
            
            self.challengeId = try container.decode(String.self, forKey: .challengeId)
            
            
            self.challenge = try container.decode(Bytes.self, forKey: .challenge)
            
            
            self.expiresAt = try container.decode(ATProtocolDate.self, forKey: .expiresAt)
            
            
            self.bindingVersion = try container.decode(Int.self, forKey: .bindingVersion)
            
            
        }
        
        public func encode(to encoder: Encoder) throws {
            
            var container = encoder.container(keyedBy: CodingKeys.self)
            
            try container.encode(challengeId, forKey: .challengeId)
            
            
            try container.encode(challenge, forKey: .challenge)
            
            
            try container.encode(expiresAt, forKey: .expiresAt)
            
            
            try container.encode(bindingVersion, forKey: .bindingVersion)
            
            
        }

        public func toCBORValue() throws -> Any {
            
            var map = OrderedCBORMap()

            
            
            let challengeIdValue = try challengeId.toCBORValue()
            map = map.adding(key: "challengeId", value: challengeIdValue)
            
            
            
            let challengeValue = try challenge.toCBORValue()
            map = map.adding(key: "challenge", value: challengeValue)
            
            
            
            let expiresAtValue = try expiresAt.toCBORValue()
            map = map.adding(key: "expiresAt", value: expiresAtValue)
            
            
            
            let bindingVersionValue = try bindingVersion.toCBORValue()
            map = map.adding(key: "bindingVersion", value: bindingVersionValue)
            
            

            return map
            
        }
        
        
        private enum CodingKeys: String, CodingKey {
            case challengeId
            case challenge
            case expiresAt
            case bindingVersion
        }
        
    }
        
public enum Error: String, Swift.Error, ATProtoErrorType, CustomStringConvertible {
                case dpopRequired = "DpopRequired."
                case deviceNotFound = "DeviceNotFound."
                case unauthorized = "Unauthorized."
                case invalidDeviceId = "InvalidDeviceId."
                case bindingUnavailable = "BindingUnavailable."
            public var description: String {
                return self.rawValue
            }

            public var errorName: String {
                // Extract just the error name from the raw value
                let parts = self.rawValue.split(separator: ".")
                return String(parts.first ?? "")
            }
        }



}

extension ATProtoClient.Blue.Catbird.MlsChat {
    // MARK: - beginDeviceAuthBinding

    /// Begin proof-of-possession binding between an authenticated session and an owned MLS device registration. Returns a short-lived single-use canonical challenge. The caller signs the exact challenge bytes with the registered MLS device identity key and completes the binding under the same DPoP key.
    /// 
    /// - Parameter input: The input parameters for the request
    
    /// 
    /// - Returns: A tuple containing the HTTP response code and the decoded response data
    /// - Throws: NetworkError if the request fails or the response cannot be processed
    public func beginDeviceAuthBinding(
        
        input: BlueCatbirdMlsChatBeginDeviceAuthBinding.Input
        
    ) async throws -> (responseCode: Int, data: BlueCatbirdMlsChatBeginDeviceAuthBinding.Output?) {
        let endpoint = "blue.catbird.mlsChat.beginDeviceAuthBinding"
        
        var headers: [String: String] = [:]
        
        headers["Content-Type"] = "application/json"
        
        
        
        headers["Accept"] = "application/json"
        

        
        let requestData: Data? = try JSONEncoder().encode(input)
        
        
        let queryItems: [URLQueryItem]? = nil
        
        let urlRequest = try await networkService.createURLRequest(
            endpoint: endpoint,
            method: "POST",
            headers: headers,
            body: requestData,
            queryItems: queryItems
        )

        // Determine service DID for this endpoint
        let serviceDID = await networkService.getServiceDID(for: "blue.catbird.mlsChat.beginDeviceAuthBinding")
        let proxyHeaders = serviceDID.map { ["atproto-proxy": $0] }
        let (responseData, response) = try await networkService.performRequest(urlRequest, skipTokenRefresh: false, additionalHeaders: proxyHeaders)
        let responseCode = response.statusCode

        
        // Only validate Content-Type and decode on success. Error responses
        // (4xx/5xx) may have missing or different Content-Type headers and
        // are handled by the caller via the status code.
        if (200...299).contains(responseCode) {
            
            guard let contentType = response.allHeaderFields["Content-Type"] as? String else {
                throw NetworkError.invalidContentType(expected: "application/json", actual: "nil")
            }

            if !contentType.lowercased().contains("application/json") {
                throw NetworkError.invalidContentType(expected: "application/json", actual: contentType)
            }
            

            do {
                
                let decoder = JSONDecoder()
                let decodedData = try decoder.decode(BlueCatbirdMlsChatBeginDeviceAuthBinding.Output.self, from: responseData)
                
                return (responseCode, decodedData)
            } catch {
                // Log the decoding error for debugging but still return the response code
                LogManager.logError("Failed to decode successful response for blue.catbird.mlsChat.beginDeviceAuthBinding: \(error)")
                return (responseCode, nil)
            }
        } else {
            // Don't try to decode error responses as success types
            return (responseCode, nil)
        }
        
    }
    
}
                           

