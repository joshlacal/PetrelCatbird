import Foundation
import Petrel
import PetrelCatbird
import Testing

@Suite("Intel & Onboarding Lexicon Wire Contract Tests")
struct IntelOnboardingLexiconTests {
    @Test func communitySummaryRoundtripsSkeletonOnlyFields() throws {
        let json = """
        {
          "communityId": 497,
          "title": "Swift & Apple Platform Craft",
          "description": "iOS, macOS, and SwiftUI builders sharing architecture notes and developer tools.",
          "sceneId": 12,
          "sceneSlug": "apple-swift-craft",
          "sceneTitle": "Apple & Swift Craft",
          "macroCategory": "software_ai_tech",
          "symbolName": "swift",
          "eligibleCount": 84,
          "postCoherenceBps": 6120,
          "socialCoherenceBps": 6840,
          "curatedListTags": ["swiftui", "iosdev"],
          "neighborCommunityIds": [2294, 1510],
          "samplePostUris": ["at://did:plc:synthauthor000000000001/app.bsky.feed.post/3l7revaaaaaaa"],
          "sampleAccountDids": ["did:plc:synthauthor000000000001"],
          "feedGeneratorUris": ["at://did:plc:synthfeed00000000000001/app.bsky.feed.generator/iosdev"],
          "starterPackUris": []
        }
        """.data(using: .utf8)!

        let summary = try JSONDecoder().decode(BlueCatbirdIntelDefs.CommunitySummary.self, from: json)
        #expect(summary.communityId == 497)
        #expect(summary.sceneSlug == "apple-swift-craft")
        #expect(summary.postCoherenceBps == 6120)
        #expect(summary.samplePostUris.count == 1)

        let reencoded = try JSONEncoder().encode(summary)
        let roundtripped = try JSONDecoder().decode(BlueCatbirdIntelDefs.CommunitySummary.self, from: reencoded)
        #expect(roundtripped.title == summary.title)
        #expect(roundtripped.sampleAccountDids.first?.description == "did:plc:synthauthor000000000001")
    }

    @Test func solveBundleInputAndOutputRoundtrip() throws {
        let input = BlueCatbirdOnboardingSolveBundle.Input(
            selections: [
                BlueCatbirdOnboardingDefs.CommunitySelection(communityId: 497, weight: 2),
                BlueCatbirdOnboardingDefs.CommunitySelection(communityId: 2294, weight: 1)
            ],
            swipes: [
                BlueCatbirdOnboardingDefs.CalibrationSwipeAnswer(
                    swipeId: "trap_swift",
                    communityA: 497,
                    communityB: 0,
                    choice: .value_a
                )
            ],
            controls: BlueCatbirdOnboardingDefs.BundleControls(
                pace: .value_balanced,
                originalsOnlyForHeavyReposters: true
            ),
            targetSize: 24,
            pinnedDids: [try DID(didString: "did:plc:synthpinned000000000001")],
            excludedDids: nil,
            seedFollowDids: nil
        )

        let inputBytes = try JSONEncoder().encode(input)
        let decodedInput = try JSONDecoder().decode(BlueCatbirdOnboardingSolveBundle.Input.self, from: inputBytes)
        #expect(decodedInput.selections.count == 2)
        #expect(decodedInput.selections.first?.weight == 2)
        #expect(decodedInput.controls?.originalsOnlyForHeavyReposters == true)

        let outputJson = """
        {
          "sessionId": "sess_synth_01",
          "catalogVersion": "s17_rev6_intel_v2_20261031",
          "solveMs": 3,
          "summary": {
            "totalFollows": 1,
            "anchorCount": 1,
            "makerCount": 0,
            "conversationalCount": 0,
            "curatorCount": 0,
            "originalsOnlyCount": 1,
            "estimatedDailyPosts": 9,
            "coveredCommunities": 1
          },
          "follows": [
            {
              "did": "did:plc:synthpinned000000000001",
              "communityId": 497,
              "communityTitle": "Swift & Apple Platform Craft",
              "role": "anchor_creator",
              "deliveryMode": "originals_only",
              "hideReposts": true,
              "whyPicked": "Core anchor voice in Swift & Apple Platform Craft",
              "estimatedPostsPerWeek": 14,
              "samplePostUri": "at://did:plc:synthpinned000000000001/app.bsky.feed.post/3l7revaaaaaaa"
            }
          ],
          "feeds": [
            {
              "uri": "at://did:plc:synthfeed00000000000001/app.bsky.feed.generator/iosdev",
              "communityId": 497,
              "communityTitle": "Swift & Apple Platform Craft"
            }
          ],
          "starterPacks": [],
          "harmoniousNeighbors": []
        }
        """.data(using: .utf8)!

        let output = try JSONDecoder().decode(BlueCatbirdOnboardingSolveBundle.Output.self, from: outputJson)
        #expect(output.summary.totalFollows == 1)
        #expect(output.follows.first?.hideReposts == true)
        #expect(output.follows.first?.role == "anchor_creator")
    }

    @Test func clientExposesIntelAndOnboardingNamespaces() {
        let _: ATProtoClient.Blue.Catbird.Intel.Type = ATProtoClient.Blue.Catbird.Intel.self
        let _: ATProtoClient.Blue.Catbird.Onboarding.Type = ATProtoClient.Blue.Catbird.Onboarding.self
    }
}
