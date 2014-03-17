package es.pymasde.blueterm;

import es.pymasde.blueterm.sqlite.DatabaseHandler;
import es.pymasde.blueterm.data.Sleep;
import es.pymasde.blueterm.data.Data;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import android.widget.LinearLayout;


import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.LinkedList;


public class SleepLog extends Activity {
	
	private TextView tvDate, tvSleepTime, tvAwakeTime, tvMood;
	private Button next, previous;
	private int count, sleep_id;
	
	DatabaseHandler db = new DatabaseHandler(this);
	//GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {});
	//GraphView graphView = new LineGraphView(this, "BPM Data");

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalog);
        addListenerOnButton();
        
        Sleep latestLog = db.getLatestSleep();
        count = db.getSleepsCount();
        sleep_id = latestLog.getID();
        
        
        tvDate = (TextView) findViewById(R.id.textDate);
        tvSleepTime = (TextView) findViewById(R.id.textSleepTime);
        tvAwakeTime = (TextView) findViewById(R.id.textAwakeTime);
        tvMood = (TextView) findViewById(R.id.textMood);
        
        tvDate.setText(latestLog.getDate());
        tvSleepTime.setText(latestLog.getSleepTime() + " - " + latestLog.getAwakeTime());
        //tvAwakeTime.setText(latestLog.getAwakeTime());
        tvMood.setText(latestLog.getMood());
        
        previous = (Button)findViewById(R.id.previous);
        
        if (sleep_id > 1){
        	previous.setVisibility(View.VISIBLE);
        }       
        
        
        // Creating Graphs:        
        List<Data> dataLogs = db.getSleepData(sleep_id);
        List<String> seconds = new LinkedList<String>();
        
        // REM Graph
        GraphViewSeries remGraph = new GraphViewSeries(new GraphViewData[] {});
                
        int m = 1;
        for (Data cn : dataLogs) {
        	remGraph.appendData(new GraphViewData(m, Integer.parseInt(cn.getRem())), true, 10);
        	m = m + 1;
        	seconds.add(cn.getTime());
        }
        
        GraphView remView = new LineGraphView(this, "Sleep Cycles");
        remView.addSeries(remGraph);
        remView.setHorizontalLabels(new String[] {seconds.get(0), seconds.get(seconds.size()-1)});
        
        //remView.setViewPort(2, 10);
        //remView.setScrollable(true);
        LinearLayout remLayout = (LinearLayout) findViewById(R.id.rem_graph);
        remLayout.addView(remView);
        
        // Acceleration Graph
        GraphViewSeries accelGraph = new GraphViewSeries(new GraphViewData[] {});
                
        int k = 1;
        for (Data cn : dataLogs) {
        	accelGraph.appendData(new GraphViewData(k, Double.parseDouble(cn.getAccel())), true, 10);
        	k = k + 1;
        }
        
        GraphView graphView2 = new LineGraphView(this, "Acceleration Data");
        graphView2.addSeries(accelGraph);
        graphView2.setHorizontalLabels(new String[] {seconds.get(0), seconds.get(seconds.size()-1)});
        
        //graphView2.setViewPort(2, 10);
        //graphView2.setScrollable(true);
        LinearLayout accelLayout = (LinearLayout) findViewById(R.id.xyz_graph);
        accelLayout.addView(graphView2);
        
        // BPM Graph
        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {});
        
        int j = 1;
        for (Data cn : dataLogs) {
        	exampleSeries.appendData(new GraphViewData(j, Integer.parseInt(cn.getBpm())), true, 10);
        	j = j + 1;
        }
        
    	GraphView graphView = new LineGraphView(this, "BPM Data");
        graphView.addSeries(exampleSeries);
        graphView.setHorizontalLabels(new String[] {seconds.get(0), seconds.get(seconds.size()-1)});
        
        //graphView.setViewPort(2, 10);
        //graphView.setScrollable(true);        
        LinearLayout layout = (LinearLayout) findViewById(R.id.bpm_graph);
        layout.addView(graphView);
        

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
	
	public void changeGraphs(){
		// Creating Graphs   
		List<String> seconds = new LinkedList<String>();
        List<Data> dataLogs = db.getSleepData(sleep_id);
        
        // REM Graph
        GraphViewSeries remGraph = new GraphViewSeries(new GraphViewData[] {});
                
        int m = 1;
        for (Data cn : dataLogs) {
        	remGraph.appendData(new GraphViewData(m, Integer.parseInt(cn.getRem())), true, 10);
        	m = m + 1;
        	seconds.add(cn.getTime());
        }
        
        GraphView remView = new LineGraphView(this, "Sleep Cycles");
        remView.addSeries(remGraph);
        remView.setHorizontalLabels(new String[] {seconds.get(0), seconds.get(seconds.size()-1)});
        
        LinearLayout layout3 = (LinearLayout) findViewById(R.id.rem_graph);
        layout3.removeAllViews();
        
        layout3 = (LinearLayout) findViewById(R.id.rem_graph);
        layout3.addView(remView);
        
        //Acceleration Graph
        GraphViewSeries accelGraph = new GraphViewSeries(new GraphViewData[] {});
        
        int k = 1;
        for (Data cn : dataLogs) {
        	accelGraph.appendData(new GraphViewData(k, Double.parseDouble(cn.getAccel())), true, 10);
        	k = k + 1;
        }
        
        GraphView graphView3 = new LineGraphView(this, "Acceleration Data");
        graphView3.addSeries(accelGraph);
        graphView3.setHorizontalLabels(new String[] {seconds.get(0), seconds.get(seconds.size()-1)});
        
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.xyz_graph);
        layout2.removeAllViews();
        
        layout2 = (LinearLayout) findViewById(R.id.xyz_graph);
        layout2.addView(graphView3);
        
        // BPM Graph
        GraphViewSeries exampleSeries2 = new GraphViewSeries(new GraphViewData[] {});
        
        int j = 1;
        for (Data cn : dataLogs) {
        	exampleSeries2.appendData(new GraphViewData(j, Integer.parseInt(cn.getBpm())), true, 10);
        	j = j + 1;
        }
        
    	GraphView graphView2 = new LineGraphView(this, "BPM Data");
        graphView2.addSeries(exampleSeries2);
        graphView2.setHorizontalLabels(new String[] {seconds.get(0), seconds.get(seconds.size()-1)});
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.bpm_graph);
        layout.removeAllViews();
        
        layout = (LinearLayout) findViewById(R.id.bpm_graph);
        layout.addView(graphView2);
        
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
		        tvSleepTime.setText(Log.getSleepTime() + " - " + Log.getAwakeTime());
		        //tvAwakeTime.setText(Log.getAwakeTime());
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
		        
		        changeGraphs();
		        
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
		        tvSleepTime.setText(Log.getSleepTime() + " - " + Log.getAwakeTime());
		        //tvAwakeTime.setText(Log.getAwakeTime());
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
		        
		        changeGraphs();
			}
		});
		
	}
	
	
}