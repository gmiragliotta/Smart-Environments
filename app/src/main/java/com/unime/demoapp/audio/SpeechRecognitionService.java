package com.unime.demoapp.audio;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class SpeechRecognitionService extends Service {
    private static final String SPEECH_RECOGNITION_SERVICE_TAG = "SpeechRecognitionService";
    public static final String SMART_OBJ_NAME = "SmartObjectName";
    private SpeechRecognition mSpeechRecognitionManager;

    @Override
    public void onCreate() {

        mSpeechRecognitionManager = new SpeechRecognition(this);
        mSpeechRecognitionManager.createSpeechRecognizer();
    }

    @SuppressLint("LongLogTag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(SPEECH_RECOGNITION_SERVICE_TAG, "onStartCommand");
        String smartObjectName = intent.getStringExtra(SMART_OBJ_NAME);
        mSpeechRecognitionManager.startListening(smartObjectName);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDestroy() {
        Log.d(SPEECH_RECOGNITION_SERVICE_TAG, "onDestroy");
        mSpeechRecognitionManager.destroySpeechRecognizer();
    }
}
