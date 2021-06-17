package com.example.androidview.bdtts.listener;

import android.content.Context;
import android.os.Handler;

import com.baidu.tts.client.SpeechSynthesizer;
import com.example.androidview.bdtts.IOfflineResourceConst;
import com.example.androidview.bdtts.MySynthesizer;
import com.example.androidview.bdtts.OfflineResource;

import java.io.IOException;
import java.util.HashMap;

public class SwitchSpeakerListener extends UiMessageListener implements IOfflineResourceConst {

    private MySynthesizer synthesizer;

    private Context context;

    public SwitchSpeakerListener(Handler mainHandler, Context context, MySynthesizer synthesizer) {
        super(mainHandler);
        this.synthesizer = synthesizer;
        this.context = context;
    }

    /**
     * 合成完切换发音人，一般用于合成队列，即最后一句话的结束，切换在线和离线发音人。
     *
     * @param utteranceId
     */
    @Override
    public void onSynthesizeFinish(String utteranceId) {
        super.onSynthesizeFinish(utteranceId);

        if (utteranceId.equals("0")) { // speak 或 synthesis的方法的第二个参数，用户自行指定
            // 这里判断引擎空闲即合成队列为空
            switchOnlineSpeaker(1);
            switchOfflineSpeaker(VOICE_FEMALE);
            synthesizer.speak("合成队列切换发音人成功", "abcdefg");
        }
    }

    /**
     * 一句话播放结束，切换发音人。
     * 需要调用stop方法清空原有队列。
     *
     * @param utteranceId
     */
    @Override
    public void onSpeechFinish(String utteranceId) {
        super.onSpeechFinish(utteranceId);
        switchOnlineSpeaker(3);

        if (utteranceId.equals("abcdefg")) {
            synthesizer.stop(); // 停止播放并清空合成队列及播放队列
            switchOfflineSpeaker(VOICE_DUXY);
            synthesizer.speak("队列清空，播放结束切换发音人成功", "cc");
        }
    }


    private void switchOnlineSpeaker(int speakerIndex) {
        HashMap<String, String> params = new HashMap<>();
        params.put(SpeechSynthesizer.PARAM_SPEAKER, speakerIndex + "");
        synthesizer.setParams(params);
    }

    private void switchOfflineSpeaker(String modelName) {
        try {
            OfflineResource offlineResource = new OfflineResource(context, modelName);
            synthesizer.loadModel(offlineResource.getTextFilename(), offlineResource.getModelFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
