package com.example.callrecordingapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.Handler;


public class TService extends Service {
    MediaRecorder recorder;
    File audiofile;
    String name, phonenumber;
    String audio_format;
    private ArrayList myList;
    private ArrayAdapter adapter;
    public String Audio_Type;
    WindowManager wm;
    int audioSource;
    String path = "/sdcard/alarm/";
    private MediaRecorder rec;
    Context context;
    private Handler handler;
    Timer timer;
    private File file;
    Boolean offHook = false, ringing = false;
    Toast toast;
    Boolean isOffHook = false;
    private AudioManager audioManager;
    private boolean recordstarted = false;

    private static final String ACTION_IN = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUT = "android.intent.action.NEW_OUTGOING_CALL";


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("service", "destroy");

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        if(!file.exists()){
            file.mkdir();
        }

        CharSequence sdf = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss").format(new Date());
        rec = new MediaRecorder();
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        rec.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        rec.setOutputFile(file.getAbsolutePath() + "/" + sdf + "rec.3gp");
        rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        audioManager.setMode(AudioManager.MODE_IN_CALL);



        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);

                if (TelephonyManager.CALL_STATE_IDLE == state && rec == null) {
                    rec.stop();
                   audioManager.setMode(AudioManager.MODE_NORMAL);
                    rec.reset();
                    rec.release();
                    stopSelf();
                } else if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0);

                    try {
                        rec.prepare();
                        rec.start();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
        return START_STICKY;
    }



}