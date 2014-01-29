package com.example.rem;

import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.Intent;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageButton;

 
public class MainActivity extends Activity {
 
	private TextView tvDisplayTime;
	private TimePicker timePicker1;
	private ImageButton btnChangeTime, btnStartAlarm;
	private CheckBox smartAlarm, heartRate, motion;
	private int hour, minute, am_pm;
	private String bpm_check, motion_check, smart_check;
 
	static final int TIME_DIALOG_ID = 999;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		setCurrentTimeOnView();
		addListenerOnButton();
 
	}
 
	
	// display current time
	public void setCurrentTimeOnView() {
 
		tvDisplayTime = (TextView) findViewById(R.id.tvTime);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
 
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		am_pm = c.get(Calendar.AM_PM);
 
		// set current time into textview
		tvDisplayTime.setText(
                    new StringBuilder().append(pad(hour))
                                       .append(":").append(pad(minute)));
 
		// set current time into timepicker
		timePicker1.setCurrentHour(hour);
		timePicker1.setCurrentMinute(minute);
 
	}
 
	
	public void addListenerOnButton() {
		
		final Context context = this;
		smartAlarm = (CheckBox) findViewById(R.id.smartalarm);
		heartRate = (CheckBox) findViewById(R.id.heartrate);
		motion = (CheckBox) findViewById(R.id.motion);
				
		Boolean a = smartAlarm.isChecked();
		smart_check = a.toString();
		Boolean b = heartRate.isChecked();
		bpm_check = b.toString();
		Boolean c = motion.isChecked();
		motion_check = c.toString();
 
		btnChangeTime = (ImageButton) findViewById(R.id.btnChangeTime);
		btnChangeTime.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		btnStartAlarm = (ImageButton) findViewById(R.id.btnStart);
		btnStartAlarm.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, App2Activity.class);
			    intent.putExtra("hour", hour);
			    intent.putExtra("minute", minute);
			    intent.putExtra("am_pm", am_pm);

			    intent.putExtra("smart_alarm", smart_check);
			    intent.putExtra("bpm", bpm_check);
			    intent.putExtra("motion", motion_check);
                startActivity(intent); 
			}
		});
 
	}
 
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 
                                        timePickerListener, hour, minute,false);
 
		}
		return null;
	}
 
	
	private TimePickerDialog.OnTimeSetListener timePickerListener = 
            new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;
 
			// set current time into textview
			tvDisplayTime.setText(new StringBuilder().append(pad(hour))
					.append(":").append(pad(minute)));
 
			// set current time into timepicker
			timePicker1.setCurrentHour(hour);
			timePicker1.setCurrentMinute(minute);
 
		}
	};
 
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	
}