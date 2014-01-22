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
 	
	String button1;
	String button2;
	String button3;
	static PendingIntent pendingIntent;
	static AlarmManager alarmManager;	
	Button btnStopAlarm;
	Long alarm_long;
	String alarm;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		
		btnStopAlarm=(Button)findViewById(R.id.button1);		
		btnStopAlarm.setOnClickListener(this);
		
		Bundle b = getIntent().getExtras();
		alarm = b.getString("ringAlarm").toString();
	    TextView textView2 = (TextView) findViewById(R.id.textView2);
	    textView2.setText("Alarm set to: " + alarm);
	    
	    button1 = b.getString("button1");
	    button2 = b.getString("button2");
	    button3 = b.getString("button3");
	    
	    StringBuffer result = new StringBuffer();
		result.append("Smart Alarm : ").append(button1);
		result.append("\nHear Rate : ").append(button2);
		result.append("\nMotion :").append(button3);
 
		Toast.makeText(App2Activity.this, result.toString(), Toast.LENGTH_LONG).show();
		
		//Changing the alarm variable from string to Date
		SimpleDateFormat formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy");	 
		try {	 
			Date date_test = formatter.parse(alarm);
			System.out.println(date_test);
			alarm_long = date_test.getTime();
			System.out.println(alarm_long);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	
	@Override
	public void onClick(View v) {
		final Context context = this;
		if(v==btnStopAlarm){
			System.out.println("Ringing alarm");
			Intent intent = new Intent(context, FinalPage.class);
		    intent.putExtra("alarm", alarm);
            startActivity(intent);  
		}
	}
	
}