package es.pymasde.blueterm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.pymasde.blueterm.sqlite.DatabaseHandler;
import es.pymasde.blueterm.data.Sleep;
import es.pymasde.blueterm.data.Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.abs;


public class AlarmReceiverActivity extends Activity {
    private MediaPlayer mMediaPlayer; 
    private String date, sleep_time, awake_time;
    private ArrayList<String[]> sleepData;
    
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.alarm);
        
        //Pass sleep data when alarm rings
        Intent mIntent = getIntent();
        sleepData =(ArrayList<String[]>) mIntent.getSerializableExtra("sleepData");
        
        //This will get you the first sleep time - sleepData.get(0 to number of data points)[0 for time, 1 for data]
        //Example:
        //System.out.println(sleepData.get(0)[0]);
        //This will get you the first sleep data
        //System.out.println(sleepData.get(0)[1]);
        
        String date1 = sleepData.get(0)[0];
        String[] array = date1.split(" ");
        date = array[0];
        sleep_time = array[1];
        
        String date2 = sleepData.get(sleepData.size()-1)[0];
        String [] array2 = date2.split(" ");
        awake_time = array2[1];
        

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
				
				db.addSleep(new Sleep(sleep_time, awake_time, date,"happy"));
				
				Sleep latestLog = db.getLatestSleep();
				int sleep_id = latestLog.getID();       
				
				int k = 0;
				List<Double> x = new LinkedList<Double>();
				List<Double> y = new LinkedList<Double>();
				List<Double> z = new LinkedList<Double>();
				
		        for (String[] cn : sleepData) {
		        	String times = cn[0];
		        	String[] array = times.split(" ");
		        	String time = array[1];
		        	
		        	String datas = cn[1];
		        	String[] array2 = datas.split(",");
		        	String accel = "0";
		        	String bpm = array2[3];
		        	String rem = "1";
		        	
		        	x.add(Double.parseDouble(array2[0]));
		        	y.add(Double.parseDouble(array2[1]));
		        	z.add(Double.parseDouble(array2[2]));
		        	
		        	if (k>2){
		        		double v1 = sqrt(pow((x.get(x.size()-2) - x.get(x.size()-3)),2) +
		        				pow((y.get(y.size()-2) - z.get(z.size()-3)),2) +
		        				pow((y.get(y.size()-2) - z.get(z.size()-3)),2)) * 0.5;
		        		double v2 = sqrt(pow((x.get(x.size()-1) - x.get(x.size()-2)),2) +
		        				pow((y.get(y.size()-1) - z.get(z.size()-2)),2) +
		        				pow((y.get(y.size()-1) - z.get(z.size()-2)),2)) * 0.5;
		        		double ac = abs(v2 - v1) * 0.5;
		        		accel = String.valueOf(ac);
		        	}
		        	
		        	db.addData(new Data(sleep_id, time, accel, bpm, rem)); 
		        	k = k + 1;
		        }
				
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
				
				db.addSleep(new Sleep(sleep_time, awake_time, date,"meh"));
				
				Sleep latestLog = db.getLatestSleep();
				int sleep_id = latestLog.getID();    
				
				int k = 0;
				List<Double> x = new LinkedList<Double>();
				List<Double> y = new LinkedList<Double>();
				List<Double> z = new LinkedList<Double>();
				 
		        for (String[] cn : sleepData) {
		        	String times = cn[0];
		        	String[] array = times.split(" ");
		        	String time = array[1];
		        	
		        	String datas = cn[1];
		        	String[] array2 = datas.split(",");
		        	String accel = "0";
		        	String bpm = array2[3];
		        	String rem = "1";
		        	
		        	x.add(Double.parseDouble(array2[0]));
		        	y.add(Double.parseDouble(array2[1]));
		        	z.add(Double.parseDouble(array2[2]));
		        	
		        	if (k>2){
		        		double v1 = sqrt(pow((x.get(x.size()-2) - x.get(x.size()-3)),2) +
		        				pow((y.get(y.size()-2) - z.get(z.size()-3)),2) +
		        				pow((y.get(y.size()-2) - z.get(z.size()-3)),2)) * 0.5;
		        		double v2 = sqrt(pow((x.get(x.size()-1) - x.get(x.size()-2)),2) +
		        				pow((y.get(y.size()-1) - z.get(z.size()-2)),2) +
		        				pow((y.get(y.size()-1) - z.get(z.size()-2)),2)) * 0.5;
		        		double ac = abs(v2 - v1) * 0.5;
		        		accel = String.valueOf(ac);
		        	}
		        	
		        	db.addData(new Data(sleep_id, time, accel, bpm, rem));
		        	k = k + 1;
		        }
		        
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
				
				db.addSleep(new Sleep(sleep_time, awake_time, date,"sad"));
				
				Sleep latestLog = db.getLatestSleep();
				int sleep_id = latestLog.getID(); 
				
				int k = 0;
				List<Double> x = new LinkedList<Double>();
				List<Double> y = new LinkedList<Double>();
				List<Double> z = new LinkedList<Double>();
				 
		        for (String[] cn : sleepData) {
		        	String times = cn[0];
		        	String[] array = times.split(" ");
		        	String time = array[1];
		        	
		        	String datas = cn[1];
		        	String[] array2 = datas.split(",");
		        	String accel = "0";
		        	String bpm = array2[3];
		        	String rem = "1";
		        	
		        	x.add(Double.parseDouble(array2[0]));
		        	y.add(Double.parseDouble(array2[1]));
		        	z.add(Double.parseDouble(array2[2]));
		        	
		        	if (k>2){
		        		double v1 = sqrt(pow((x.get(x.size()-2) - x.get(x.size()-3)),2) +
		        				pow((y.get(y.size()-2) - z.get(z.size()-3)),2) +
		        				pow((y.get(y.size()-2) - z.get(z.size()-3)),2)) * 0.5;
		        		double v2 = sqrt(pow((x.get(x.size()-1) - x.get(x.size()-2)),2) +
		        				pow((y.get(y.size()-1) - z.get(z.size()-2)),2) +
		        				pow((y.get(y.size()-1) - z.get(z.size()-2)),2)) * 0.5;
		        		double ac = abs(v2 - v1) * 0.5;
		        		accel = String.valueOf(ac);
		        	}
		        	
		        	db.addData(new Data(sleep_id, time, accel, bpm, rem)); 
		        	k = k + 1;
		        }
		        
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