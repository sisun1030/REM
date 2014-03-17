package es.pymasde.blueterm;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageButton;

 
public class MainActivity extends Activity {
 
	private TextView tvDisplayTime;
	//private TimePicker timePicker1;
	private ImageButton btnChangeTime, btnStartAlarm;
	private CheckBox smartAlarm, heartRate, motion;
	private int hour, minute, hour_d, minute_d;
	private String am_pm, am_pm_d;
	private String bpm_check, motion_check, smart_check;
	//temp variable to hold alarm range of 30 mins
	private int alarmRange = 30;
 
	static final int TIME_DIALOG_ID = 999;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		setCurrentTimeOnView();
		addListenerOnButton();
 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.top_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		final Context context = this;
		
		switch (item.getItemId()) {
		case R.id.setting:
			//Toast.makeText(getApplicationContext(), "Setting Page", Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent(context, Setting.class);
            startActivity(intent);
			
			return true;
		
		case R.id.sleep_log:
			//Toast.makeText(getApplicationContext(), "SleepLog Page", Toast.LENGTH_SHORT).show();
			
			Intent intent2 = new Intent(context, SleepLog.class);
            startActivity(intent2);
            
			return true;
		
		default:
			return false;
		}
	}
	
	// display current time
	public void setCurrentTimeOnView() {
 
		tvDisplayTime = (TextView) findViewById(R.id.tvTime);
		//timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
 
		final Calendar c = Calendar.getInstance();
		final Calendar d = c;
		hour = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		switch (c.get(Calendar.AM_PM)) {
		case 0:
			am_pm="AM";
		case 1:
			am_pm="PM";
		}
		
		d.add(Calendar.MINUTE, -alarmRange);
		hour_d = d.get(Calendar.HOUR);
		minute_d = d.get(Calendar.MINUTE);
		switch (c.get(Calendar.AM_PM)) {
		case 0:
			am_pm_d="AM";
		case 1:
			am_pm_d="PM";
		}
 
		// set current time into textview
		tvDisplayTime.setText(
                    new StringBuilder().append(pad(hour_d)).append(":")
 				   					   .append(pad(minute_d)).append(am_pm_d)
 				   					   .append(" - ")
 				   					   .append(pad(hour)).append(":")
                    				   .append(pad(minute)).append(am_pm));
 
		// set current time into timepicker
		//timePicker1.setCurrentHour(hour);
		//timePicker1.setCurrentMinute(minute);
 
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
				// TODO Auto-generated method stub
			}
		});
		
		btnStartAlarm = (ImageButton) findViewById(R.id.btnStart);
		btnStartAlarm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("Time selected and going to sleep");
				
				Intent intent = new Intent(context, BlueTerm.class);
			    intent.putExtra("hour", hour);
			    intent.putExtra("minute", minute);
			    intent.putExtra("am_pm", am_pm);

			    intent.putExtra("smart_alarm", smart_check);
			    intent.putExtra("bpm", bpm_check);
			    intent.putExtra("motion", motion_check);
                startActivity(intent); 
			}
		});
		
		RelativeLayout btnNavLog = (RelativeLayout) findViewById(R.id.alarmNavRelLog);
		btnNavLog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(context, SleepLog.class);
	            startActivity(intent2);
			}
		});
 
	}
 
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, 1, 
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
			
			//System.out.println("mark" + hour + minute);
			//pm hours, result is from 12pm to 11pm
			if (hour >= 12) {
				am_pm="PM";
				if (hour > 12)
					hour = hour - 12;
			//am hours, result from 12am to 11am
			} else {
				am_pm="AM";
				if (hour == 0)
					hour = 12;
			}
			
			if (minute < alarmRange) {
				hour_d = hour - 1;
				if (hour_d == 11) {
					if (am_pm=="AM")
						am_pm_d="PM";
					else
						am_pm_d="AM";
				} else {
					am_pm_d = am_pm;
					if (hour_d == 0)
						hour_d = 12;
				}
				minute_d = minute + (60-alarmRange);
			} else {
				hour_d = hour;
				minute_d = minute - alarmRange;
			}
			
			// set current time into textview
			tvDisplayTime.setText(
                    new StringBuilder().append(pad(hour_d)).append(":")
 				   					   .append(pad(minute_d)).append(am_pm_d)
 				   					   .append(" - ")
 				   					   .append(pad(hour)).append(":")
                    				   .append(pad(minute)).append(am_pm));
 
			// set current time into timepicker
			//timePicker1.setCurrentHour(hour);
			//timePicker1.setCurrentMinute(minute);
 
		}
	};
 
	
	private static String pad(int c) {
		if (c >= 10)
		   return String.valueOf(c);
		else
		   return "0" + String.valueOf(c);
	}
	
	
}