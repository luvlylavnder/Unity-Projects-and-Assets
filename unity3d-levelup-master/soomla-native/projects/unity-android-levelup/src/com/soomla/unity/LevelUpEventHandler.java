package com.soomla.unity;

import com.soomla.BusProvider;
import com.soomla.levelup.events.GateClosedEvent;
import com.soomla.levelup.events.GateOpenedEvent;
import com.soomla.levelup.events.LastCompletedInnerWorldChanged;
import com.soomla.levelup.events.LatestScoreChangedEvent;
import com.soomla.levelup.events.LevelEndedEvent;
import com.soomla.levelup.events.LevelStartedEvent;
import com.soomla.levelup.events.LevelUpInitializedEvent;
import com.soomla.levelup.events.MissionCompletedEvent;
import com.soomla.levelup.events.MissionCompletionRevokedEvent;
import com.soomla.levelup.events.ScoreRecordChangedEvent;
import com.soomla.levelup.events.ScoreRecordReachedEvent;
import com.soomla.levelup.events.WorldAssignedRewardEvent;
import com.soomla.levelup.events.WorldCompletedEvent;
import com.squareup.otto.Subscribe;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

public class LevelUpEventHandler {
    private static LevelUpEventHandler mLocalEventHandler;

    public static void initialize() {
        mLocalEventHandler = new LevelUpEventHandler();

    }

    public LevelUpEventHandler() {
        BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void onLevelStartedEvent(LevelStartedEvent levelStartedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onLevelStarted", levelStartedEvent.LevelId);
    }

    @Subscribe
    public void onLevelEndedEvent(LevelEndedEvent levelEndedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onLevelEnded", levelEndedEvent.LevelId);
    }

    @Subscribe
    public void onLevelUpInitializedEvent(LevelUpInitializedEvent levelUpInitializedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onLevelUpInitialized", "");
    }

    @Subscribe
    public void onWorldCompletedEvent(WorldCompletedEvent worldCompletedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onWorldCompleted", worldCompletedEvent.WorldId);
    }

    @Subscribe
    public void onGateOpenedEvent(GateOpenedEvent gateOpenedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onGateOpened", gateOpenedEvent.GateId);
    }

    @Subscribe
    public void onGateOpenedEvent(GateClosedEvent gateClosedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onGateClosed", gateClosedEvent.GateId);
    }

    @Subscribe
    public void onMissionCompletedEvent(MissionCompletedEvent missionCompletedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onMissionCompleted", missionCompletedEvent.MissionId);
    }

    @Subscribe
    public void onMissionCompletionRevokedEvent(MissionCompletionRevokedEvent missionCompletionRevokedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onMissionCompletionRevoked", missionCompletionRevokedEvent.MissionId);
    }

    @Subscribe
    public void onScoreRecordChangedEvent(ScoreRecordChangedEvent scoreRecordChangedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onScoreRecordChanged", scoreRecordChangedEvent.ScoreId);
    }

    @Subscribe
    public void onLatestScoreChangedEvent(LatestScoreChangedEvent latestScoreChangedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onLatestScoreChanged", latestScoreChangedEvent.ScoreId);
    }

    @Subscribe
    public void onScoreRecordReachedEvent(ScoreRecordReachedEvent scoreRecordReachedEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onScoreRecordReached", scoreRecordReachedEvent.ScoreId);
    }

    @Subscribe
    public void onWorldAssignedRewardEvent(WorldAssignedRewardEvent worldAssignedRewardEvent) {
        UnityPlayer.UnitySendMessage("LevelUpEvents", "onWorldAssignedReward", worldAssignedRewardEvent.WorldId);
    }

    @Subscribe
    public void onLastCompletedInnerWorldChanged(LastCompletedInnerWorldChanged lastCompletedInnerWorldChanged) {
        JSONObject eventJSON = new JSONObject();
        try {
            eventJSON.put("worldId", lastCompletedInnerWorldChanged.WorldId);
            eventJSON.put("innerWorldId", lastCompletedInnerWorldChanged.InnerWorldId);

            UnityPlayer.UnitySendMessage("LevelUpEvents", "onLastCompletedInnerWorldChanged", eventJSON.toString());
        } catch (JSONException e) {
            throw new IllegalStateException(e);
        }
    }
}
