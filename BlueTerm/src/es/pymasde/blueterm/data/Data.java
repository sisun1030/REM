package es.pymasde.blueterm.data;

public class Data {
	
	//private variables
	int _id;
	String _time;
	String _accel;
	String _bpm;
	String _rem;
	
	// Empty constructor
	public Data(){
		
	}
	// constructor
	public Data(int id, String sleep_time, String accel, String bpm, String rem){
		this._id = id;
		this._time = sleep_time;
		this._accel = accel;
		this._bpm = bpm;
		this._rem = rem;
	}
	
	// constructor
	public Data(String sleep_time, String accel, String bpm, String rem){
		this._time = sleep_time;
		this._accel = accel;
		this._bpm = bpm;
		this._rem = rem;
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
	public String getTime(){
		return this._time;
	}
	
	// setting sleep time
	public void setTime(String time){
		this._time = time;
	}
	
	// getting awake time
	public String getAccel(){
		return this._accel;
	}
		
	// setting awake time
	public void setAccel(String accel){
		this._accel = accel;
	}
	
	// getting awake time
	public String getBpm(){
		return this._bpm;
	}
			
	// setting awake time
	public void setBpm(String bpm){
		this._bpm = bpm;
	}
	
	// getting rem
	public String getRem(){
		return this._rem;
	}
	
	// setting rem
	public void setRem(String rem){
		this._rem = rem;
	}
}
