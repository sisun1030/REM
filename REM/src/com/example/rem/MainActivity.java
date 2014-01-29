package com.example.rem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import java.util.Calendar;
import android.widget.CheckBox;
import android.widget.Toast;
 
public class MainActivity extends Activity {
 
	
	Button button;
	TimePicker myTimePicker;
	Button buttonstartSetDialog;	
	String prompt;
	TextView textAlarmPrompt;
	TimePickerDialog timePickerDialog;
	final static int RQS_1 = 1;
	private CheckBox smartAlarm, heartRate, motion;
	String button1; String button2; String button3;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		 
	    textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
	    addListenerOnChkIos();
	    addListenerOnButton();
	}
	
	
	public void addListenerOnChkIos() {
		smartAlarm = (CheckBox) findViewById(R.id.smartalarm);
		smartAlarm.setOnClickListener(new OnClickListener() {
	 
			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					Toast.makeText(MainActivity.this, "We will wake you up at an optimal time BRO!", Toast.LENGTH_LONG).show();
				}
	 
			}
		});
	}
	
 
	public void addListenerOnButton() {
 
		final Context context = this;
		smartAlarm = (CheckBox) findViewById(R.id.smartalarm);
		heartRate = (CheckBox) findViewById(R.id.heartrate);
		motion = (CheckBox) findViewById(R.id.motion);
				
		Boolean a = smartAlarm.isChecked();
		button1 = a.toString();
		Boolean b = heartRate.isChecked();
		button2 = b.toString();
		Boolean c = motion.isChecked();
		button3 = c.toString();
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			    Intent intent = new Intent(context, App2Activity.class);
			    intent.putExtra("ringAlarm", prompt);
			    intent.putExtra("button1", button1);
			    intent.putExtra("button2", button2);
			    intent.putExtra("button3", button3);
                startActivity(intent);   
			}
		});
		
		buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new OnClickListener(){
        	
        	@Override
			public void onClick(View arg0) {
        		textAlarmPrompt.setText("");
        	    openTimePickerDialog(false);  
			}
 
		});
 
	}
	
	
	private void openTimePickerDialog(boolean is24r){
		Calendar calendar = Calendar.getInstance();
		  
		timePickerDialog = new TimePickerDialog(
			MainActivity.this, 
		    onTimeSetListener, 
		    calendar.get(Calendar.HOUR_OF_DAY), 
		    calendar.get(Calendar.MINUTE), 
		    is24r);
		timePickerDialog.setTitle("Set Alarm Time");  
		timePickerDialog.show();
	}
	
	
	OnTimeSetListener onTimeSetListener= new OnTimeSetListener(){
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			Calendar calNow = Calendar.getInstance();
			Calendar calSet = (Calendar) calNow.clone();

			calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calSet.set(Calendar.MINUTE, minute);
			calSet.set(Calendar.SECOND, 0);
			calSet.set(Calendar.MILLISECOND, 0);
   
			if(calSet.compareTo(calNow) <= 0){
				calSet.add(Calendar.DATE, 1);
			}
   
			setAlarm(calSet);
		}
	};


	private void setAlarm(Calendar targetCal){
		prompt = targetCal.getTime().toString();

		textAlarmPrompt.setText(
		    "\n\n***\n"
		    + "Alarm is set@ " + prompt + "\n"
		    + "***\n");
  
		Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
  
	}
 
	
}