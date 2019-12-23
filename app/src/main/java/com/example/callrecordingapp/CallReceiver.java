package com.example.callrecordingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CallReceiver extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Log.d("onCreate: ", "flag2");

            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

            //Log.d("IncomingCallActivity: onCreate: ", "flagy");
            final String number = getIntent().getStringExtra(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(CallReceiver.this, MyCustomDialog.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("number",number);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

            // Log.d("IncomingCallActivity: onCreate: ", "flagz");

        }
        catch (Exception e) {
            Log.d("Exception", e.toString());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}