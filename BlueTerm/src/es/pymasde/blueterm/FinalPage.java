package es.pymasde.blueterm;

import java.util.ArrayList;
import java.util.Calendar;

import es.pymasde.blueterm.sqlite.DatabaseHandler;
import es.pymasde.blueterm.data.Sleep;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.util.Log;


public class FinalPage extends Activity {
	
	private static ArrayList<String> sleepData = new ArrayList<String>();

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main3);

        //Create an offset from the current time in which the alarm will go off.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 3);
        
        //Pass sleep data when alarm rings
        Intent mIntent = getIntent();
        ArrayList<String[]> sleepData =(ArrayList<String[]>) mIntent.getSerializableExtra("sleepData");
        
        //This will get you the first sleep time - sleepData.get(0 to number of data points)[0 for time, 1 for data]
        //Example:
        //System.out.println(sleepData.get(0)[0]);
        //This will get you the first sleep data
        //System.out.println(sleepData.get(0)[1]);
        
        
        // Inserting sleep data to SQLite
        DatabaseHandler db = new DatabaseHandler(this);
        String date = sleepData.get(0)[0];
        String sleep_time = sleepData.get(0)[0];
        String wake_time = sleepData.get(sleepData.size()-1)[0];
        
        
        //Create a new PendingIntent and add it to the AlarmManager
        Intent intent = new Intent(this, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
            12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = 
            (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent);
    }

}