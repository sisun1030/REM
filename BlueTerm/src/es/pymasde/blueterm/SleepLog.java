package es.pymasde.blueterm;

import es.pymasde.blueterm.sqlite.DatabaseHandler;
import es.pymasde.blueterm.data.Sleep;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;




public class SleepLog extends Activity {
	
	private TextView tvDate, tvSleepTime, tvAwakeTime, tvMood;
	private Button next, previous;
	private int count, sleep_id;
	
	DatabaseHandler db = new DatabaseHandler(this);
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalog);
        addListenerOnButton();
        
        
        /*
        // Inserting sample data first
        Log.d("Insert: ", "Inserting ..");
        db.addSleep(new Sleep("10pm","8am","Friday","happy"));
        db.addSleep(new Sleep("11pm","8am","Saturday","sad"));
        db.addSleep(new Sleep("12am","6am","Sunday","meh"));
        db.addSleep(new Sleep("12am","7am","Sunday","happy"));
        
        */
        Sleep latestLog = db.getLatestSleep();
        count = db.getSleepsCount();
        sleep_id = latestLog.getID();
        
        
        tvDate = (TextView) findViewById(R.id.textDate);
        tvSleepTime = (TextView) findViewById(R.id.textSleepTime);
        tvAwakeTime = (TextView) findViewById(R.id.textAwakeTime);
        tvMood = (TextView) findViewById(R.id.textMood);
        
        tvDate.setText(latestLog.getDate());
        tvSleepTime.setText(latestLog.getSleepTime());
        tvAwakeTime.setText(latestLog.getAwakeTime());
        tvMood.setText(latestLog.getMood());
        
        next = (Button)findViewById(R.id.next);
        previous = (Button)findViewById(R.id.previous);
        
        /*
        if (sleep_id == 1 && (count > 1)){
        	next.setVisibility(View.VISIBLE);
        }
        */
        if (sleep_id > 1){
        	previous.setVisibility(View.VISIBLE);
        }       
        
 
        // Reading all sleep logs
        Log.d("Reading: ", "Reading all contacts..");
        List<Sleep> sleepLogs = db.getAllContacts();       
 
        for (Sleep cn : sleepLogs) {
            String log = "Id: "+cn.getID()+" ,SleepTime: " + cn.getSleepTime() + " ,AwakeTime: " + cn.getAwakeTime() 
            		+ " ,Date: " + cn.getDate() +" ,Mood: " + cn.getMood();
                // Writing Contacts to log
            Log.d("Name: ", log);
        
        }
    }
	
	
	public void addListenerOnButton() {
	
		next = (Button)findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				sleep_id = sleep_id + 1;
				System.out.println(Integer.toString(sleep_id));
				Sleep Log = db.getSleep(sleep_id);
				
				tvDate = (TextView) findViewById(R.id.textDate);
		        tvSleepTime = (TextView) findViewById(R.id.textSleepTime);
		        tvAwakeTime = (TextView) findViewById(R.id.textAwakeTime);
		        tvMood = (TextView) findViewById(R.id.textMood);
		        
		        tvDate.setText(Log.getDate());
		        tvSleepTime.setText(Log.getSleepTime());
		        tvAwakeTime.setText(Log.getAwakeTime());
		        tvMood.setText(Log.getMood());
		        		        
		        if (sleep_id < count){
		        	next.setVisibility(View.VISIBLE);
		        }
		        else{
		        	next.setVisibility(View.INVISIBLE);
		        }
		        
		        if (sleep_id > 1){
		        	previous.setVisibility(View.VISIBLE);
		        } 
		        else{
		        	previous.setVisibility(View.INVISIBLE);
		        }
			}
		});
		
        previous = (Button)findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				sleep_id = sleep_id - 1;
				System.out.println(Integer.toString(sleep_id));
				Sleep Log = db.getSleep(sleep_id);
				
				tvDate = (TextView) findViewById(R.id.textDate);
		        tvSleepTime = (TextView) findViewById(R.id.textSleepTime);
		        tvAwakeTime = (TextView) findViewById(R.id.textAwakeTime);
		        tvMood = (TextView) findViewById(R.id.textMood);
		        
		        tvDate.setText(Log.getDate());
		        tvSleepTime.setText(Log.getSleepTime());
		        tvAwakeTime.setText(Log.getAwakeTime());
		        tvMood.setText(Log.getMood());
		        		        
		        if (sleep_id < count){
		        	next.setVisibility(View.VISIBLE);
		        }
		        else{
		        	next.setVisibility(View.INVISIBLE);
		        }
		        
		        if (sleep_id > 1){
		        	previous.setVisibility(View.VISIBLE);
		        } 
		        else{
		        	previous.setVisibility(View.INVISIBLE);
		        }
		        
			}
		});
		
	}
	
	
}