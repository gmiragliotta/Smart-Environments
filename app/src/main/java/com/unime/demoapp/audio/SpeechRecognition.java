package com.unime.demoapp.audio;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import com.unime.demoapp.utilities.CommandTrigger;

import java.util.ArrayList;

public class SpeechRecognition {

    private static final String TAG = "SpeechActivity";

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private String smartObjectName;
    private String command;
    private Context context;

    public SpeechRecognition(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public String getSmartObjectName() {
        return smartObjectName;
    }

    public void setSmartObjectName(String smartObjectName) {
        this.smartObjectName = smartObjectName;
    }

    public void createSpeechRecognizer() {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getContext().getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        }

        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);
    }

    public void startListening(String smartObjectName) {
        setSmartObjectName(smartObjectName);
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
    }


    public void destroySpeechRecognizer() {
        if (mSpeechRecognizer != null)
        {
            mSpeechRecognizer.destroy();
        }
    }

    public void useCommand() {
        Toast.makeText(getContext(), getCommand(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "useCommand: " + getCommand());

        CommandTrigger commandTrigger = new CommandTrigger(getSmartObjectName(), getCommand(), getContext());
        commandTrigger.tryCommand();
    }

    protected class SpeechRecognitionListener implements RecognitionListener
    {

        @Override
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error)
        {
            Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }

        @Override
        public void onResults(Bundle results)
        {
            Log.d(TAG, "onResults");
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            setCommand(matches.get(0).toLowerCase());
            useCommand();
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }

}
