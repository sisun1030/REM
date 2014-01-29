package com.example.rem;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import android.view.View.OnClickListener;

public class App2Activity extends Activity implements OnClickListener  {
 	
	static PendingIntent pendingIntent;
	static AlarmManager alarmManager;	
	private Button btnStopAlarm;
	static int hour, minute, am_pm;
	static String smartAlarm, bpm, motion;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		
		System.out.println("Got here");
		
		btnStopAlarm=(Button)findViewById(R.id.button1);		
		btnStopAlarm.setOnClickListener(this);
		
		Intent mIntent = getIntent();
		hour = mIntent.getIntExtra("hour", 0);
		minute = mIntent.getIntExtra("minute", 0);
	    TextView textView2 = (TextView) findViewById(R.id.textView2);
	    textView2.setText("Alarm is set to: " + new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));
	    
	    smartAlarm = mIntent.getStringExtra("smart_alarm");
	    bpm = mIntent.getStringExtra("bpm");
	    motion = mIntent.getStringExtra("motion");
	    
	    StringBuffer result = new StringBuffer();
		result.append("Smart Alarm : ").append(smartAlarm);
		result.append("\nHear Rate : ").append(bpm);
		result.append("\nMotion :").append(motion);
		Toast.makeText(App2Activity.this, result.toString(), Toast.LENGTH_LONG).show();
	    
	}
	
	
	@Override
	public void onClick(View v) {
		final Context context = this;
		if(v==btnStopAlarm){
			System.out.println("Ringing alarm");
			Intent intent = new Intent(context, FinalPage.class);
		    intent.putExtra("alarm", hour);
            startActivity(intent);  
		}
	}
	
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	
}