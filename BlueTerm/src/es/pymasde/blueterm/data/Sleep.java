package es.pymasde.blueterm.data;

public class Sleep {
	
	//private variables
	int _id;
	String _sleep_time;
	String _awake_time;
	String _date;
	String _mood;
	
	// Empty constructor
	public Sleep(){
		
	}
	// constructor
	public Sleep(int id, String sleep_time, String awake_time, String date, String mood){
		this._id = id;
		this._sleep_time = sleep_time;
		this._awake_time = awake_time;
		this._date = date;
		this._mood = mood;
	}
	
	// constructor
	public Sleep(String sleep_time, String awake_time, String date, String mood){
		this._sleep_time = sleep_time;
		this._awake_time = awake_time;
		this._date = date;
		this._mood = mood;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting sleep time
	public String getSleepTime(){
		return this._sleep_time;
	}
	
	// setting sleep time
	public void setSleepTime(String sleep_time){
		this._sleep_time = sleep_time;
	}
	
	// getting awake time
	public String getAwakeTime(){
		return this._awake_time;
	}
		
	// setting awake time
	public void setAwakeTime(String awake_time){
		this._awake_time = awake_time;
	}
	
	// getting awake time
	public String getDate(){
		return this._date;
	}
			
	// setting awake time
	public void setDate(String date){
		this._date = date;
	}
	
	// getting mood
	public String getMood(){
		return this._mood;
	}
	
	// setting mood
	public void setMood(String mood){
		this._mood = mood;
	}
}
