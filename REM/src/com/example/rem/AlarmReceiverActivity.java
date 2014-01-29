package com.example.rem;

import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.alarm);

        Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
        stopAlarm.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                mMediaPlayer.stop();
                View b = findViewById(R.id.stopAlarm);
                b.setVisibility(View.GONE);
                
                View smile = findViewById(R.id.smile);
                smile.setVisibility(View.VISIBLE);
                View neutral = findViewById(R.id.neutral);
                neutral.setVisibility(View.VISIBLE);
                View sad = findViewById(R.id.sad);
                sad.setVisibility(View.VISIBLE);
                //finish();
                return false;
            }
        });
        
        //Clicked smiley face bitches:
        Button smile = (Button) findViewById(R.id.smile);
        smile.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				System.out.println("User selected they were feeling happy");
				Intent i = new Intent(AlarmReceiverActivity.this, MainActivity.class);
                startActivity(i);
			}
		});
        
        //Clicked neutral face bitches:
        Button neutral = (Button) findViewById(R.id.neutral);
        neutral.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				System.out.println("User selected they were feeling meh");
				Intent i = new Intent(AlarmReceiverActivity.this, MainActivity.class);
                startActivity(i);
			}
		});
        
        //Clicked sad face bitches:
        Button sad = (Button) findViewById(R.id.sad);
        sad.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				System.out.println("User selected they were frowning");
				Intent i = new Intent(AlarmReceiverActivity.this, MainActivity.class);
                startActivity(i);
			}
		});
        

        playSound(this, getAlarmUri());
    }
    

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    
    //Get an alarm sound. Try for an alarm. If none set, try notification, 
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }
    
    
}