package es.pymasde.blueterm;

import es.pymasde.blueterm.sqlite.DatabaseHandler;
import es.pymasde.blueterm.data.Sleep;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class SleepLog extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_page);
        
        DatabaseHandler db = new DatabaseHandler(this);
        
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addSleep(new Sleep("10pm","8am","happy"));
        db.addSleep(new Sleep("11pm","8am","sad"));
        db.addSleep(new Sleep("12am","6am","meh"));
        db.addSleep(new Sleep("12am","7am","happy"));
 
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Sleep> sleepLogs = db.getAllContacts();       
 
        for (Sleep cn : sleepLogs) {
            String log = "Id: "+cn.getID()+" ,SleepTime: " + cn.getSleepTime() + " ,AwakeTime: " + cn.getAwakeTime() +" ,Mood: " + cn.getMood();
                // Writing Contacts to log
            Log.d("Name: ", log);
        
        }
    }
}