
#import "UnityLevelUpEventDispatcher.h"
#import "LevelUpEventHandling.h"
#import "SoomlaUtils.h"

extern "C" {
    void soomlaLevelup_Init() {
        [UnityLevelUpEventDispatcher initialize];
    }
}

@implementation UnityLevelUpEventDispatcher

+ (void)initialize {
    static UnityLevelUpEventDispatcher* instance = nil;
    if (!instance) {
        instance = [[UnityLevelUpEventDispatcher alloc] init];
    }
}

- (id) init {
    if (self = [super init]) {
        [LevelUpEventHandling observeAllEventsWithObserver:self withSelector:@selector(handleEvent:)];
    }

    return self;
}

- (void)handleEvent:(NSNotification*)notification{

	if ([notification.name isEqualToString:EVENT_GATE_OPENED]) {
        NSString* gateId = [[notification userInfo] objectForKey:DICT_ELEMENT_GATE];
        UnitySendMessage("LevelUpEvents", "onGateOpened", [gateId UTF8String]);
	}
    else if ([notification.name isEqualToString:EVENT_GATE_CLOSED]) {
        NSString* gateId = [[notification userInfo] objectForKey:DICT_ELEMENT_GATE];
        UnitySendMessage("LevelUpEvents", "onGateClosed", [gateId UTF8String]);
    }
	else if ([notification.name isEqualToString:EVENT_LEVEL_ENDED]) {
        NSString* levelId = [[notification userInfo] objectForKey:DICT_ELEMENT_LEVEL];
        UnitySendMessage("LevelUpEvents", "onLevelEnded", [levelId UTF8String]);
	}
	else if ([notification.name isEqualToString:EVENT_LEVEL_STARTED]) {
	    NSString* levelId = [[notification userInfo] objectForKey:DICT_ELEMENT_LEVEL];
        UnitySendMessage("LevelUpEvents", "onLevelStarted", [levelId UTF8String]);
	}
	else if ([notification.name isEqualToString:EVENT_LEVEL_UP_INITIALIZED]) {
        UnitySendMessage("LevelUpEvents", "onLevelUpInitialized", "");
	}
	else if ([notification.name isEqualToString:EVENT_MISSION_COMPLETED]) {
	    NSString* missionId = [[notification userInfo] objectForKey:DICT_ELEMENT_MISSION];
        UnitySendMessage("LevelUpEvents", "onMissionCompleted", [missionId UTF8String]);
    }
	else if ([notification.name isEqualToString:EVENT_MISSION_COMPLETION_REVOKED]) {
        NSString* missionId = [[notification userInfo] objectForKey:DICT_ELEMENT_MISSION];
        UnitySendMessage("LevelUpEvents", "onMissionCompletionRevoked", [missionId UTF8String]);
    }
	else if ([notification.name isEqualToString:EVENT_SCORE_RECORD_CHANGED]) {
        NSString* scoreId = [[notification userInfo] objectForKey:DICT_ELEMENT_SCORE];
        UnitySendMessage("LevelUpEvents", "onScoreRecordChanged", [scoreId UTF8String]);
    }
	else if ([notification.name isEqualToString:EVENT_SCORE_LATEST_CHANGED]) {
        NSString* scoreId = [[notification userInfo] objectForKey:DICT_ELEMENT_SCORE];
        UnitySendMessage("LevelUpEvents", "onLatestScoreChanged", [scoreId UTF8String]);
    }
	else if ([notification.name isEqualToString:EVENT_WORLD_COMPLETED]) {
        NSString* worldId = [[notification userInfo] objectForKey:DICT_ELEMENT_WORLD];
        UnitySendMessage("LevelUpEvents", "onWorldCompleted", [worldId UTF8String]);
    }
    else if ([notification.name isEqualToString:EVENT_WORLD_REWARD_ASSIGNED]) {
        NSString* worldId = [[notification userInfo] objectForKey:DICT_ELEMENT_WORLD];
        UnitySendMessage("LevelUpEvents", "onWorldAssignedReward", [worldId UTF8String]);
    }
    else if ([notification.name isEqualToString:EVENT_LAST_COMPLETED_INNER_WORLD_CHANGED]) {
        NSDictionary* userInfo = [notification userInfo];
        NSString* jsonStr = [SoomlaUtils dictToJsonString:@{
                                                            @"worldId": [userInfo objectForKey:DICT_ELEMENT_WORLD],
                                                            @"innerWorldId": [userInfo objectForKey:DICT_ELEMENT_INNER_WORLD] ?: @""
                                                            }];
        UnitySendMessage("LevelUpEvents", "onLastCompletedInnerWorldChanged", [jsonStr UTF8String]);
    }
}

@end
