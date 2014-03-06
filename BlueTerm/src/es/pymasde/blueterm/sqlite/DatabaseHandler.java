package es.pymasde.blueterm.sqlite;

import java.util.ArrayList;
import java.util.List;

import es.pymasde.blueterm.data.Sleep;
import es.pymasde.blueterm.data.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "sleepManager";

	// Tables
	private static final String TABLE_SLEEPLOG = "sleepLog";
	private static final String TABLE_DATALOG = "dataLog";

	// Sleep Log Table Columns names
	private static final String KEY_ID = "id";
	private static final String SLEEP_TIME = "sleep_time";
	private static final String AWAKE_TIME = "awake_time";
	private static final String DATE = "date";
	private static final String MOOD = "mood";
	
	// Data Log Table Column names
	private static final String TIME = "time";
	private static final String ACCEL = "accel";
	private static final String BPM = "bpm";
	private static final String REM = "rem";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_SLEEPLOG_TABLE = "CREATE TABLE " + TABLE_SLEEPLOG + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + SLEEP_TIME + " LONG,"
				+ AWAKE_TIME + " LONG," + DATE + " TEXT," + MOOD + " TEXT" + ")";
		db.execSQL(CREATE_SLEEPLOG_TABLE);
		
		String CREATE_DATALOG_TABLE = "CREATE TABLE " + TABLE_DATALOG + "("
				+ KEY_ID + " INTEGER," + TIME + " INT,"
				+ ACCEL + " INT," + BPM + " INT," + REM + " INT" + ")";
		db.execSQL(CREATE_DATALOG_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older tables if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEPLOG);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATALOG);

		// Create tables again
		onCreate(db);
	}

	
	/** Below are methods for the Sleep Log Table
	 */
	

	// Adding new contact
	public void addSleep(Sleep contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SLEEP_TIME, contact.getSleepTime()); 
		values.put(AWAKE_TIME, contact.getAwakeTime()); 
		values.put(DATE, contact.getDate());
		values.put(MOOD, contact.getMood()); 

		// Inserting Row
		db.insert(TABLE_SLEEPLOG, null, values);
		db.close(); // Closing database connection
	}

	// Getting single contact
	public Sleep getSleep(int id) {
		SQLiteDatabase db = this.getReadableDatabase();		
		//String selectQuery = "SELECT * FROM " + TABLE_SLEEPLOG + " WHERE " + KEY_ID + " = ?", new String[] {Integer.toString(id)};
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SLEEPLOG + " WHERE " + KEY_ID + " = ?", new String[] { Integer.toString(id) });
		//Cursor cursor = db.rawQuery(selectQuery, null);
		
		Sleep latestLog = new Sleep();
		if (cursor.moveToFirst()) {
			do {
				latestLog.setID(Integer.parseInt(cursor.getString(0)));
				latestLog.setSleepTime(cursor.getString(1));
				latestLog.setAwakeTime(cursor.getString(2));
				latestLog.setDate(cursor.getString(3));
				latestLog.setMood(cursor.getString(4));
			} while (cursor.moveToNext());
		}
		
		return latestLog; 
	}
	
	// Getting All Contacts
	public List<Sleep> getAllContacts() {
		List<Sleep> contactList = new ArrayList<Sleep>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SLEEPLOG + " ORDER BY id DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Sleep contact = new Sleep();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setSleepTime(cursor.getString(1));
				contact.setAwakeTime(cursor.getString(2));
				contact.setDate(cursor.getString(3));
				contact.setMood(cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}
	
	// Get the latest sleep log saved
	public Sleep getLatestSleep() {
		String selectQuery = "SELECT  * FROM " + TABLE_SLEEPLOG + " ORDER BY id DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		Sleep latestLog = new Sleep();
		if (cursor.moveToFirst()) {
			do {
				latestLog.setID(Integer.parseInt(cursor.getString(0)));
				latestLog.setSleepTime(cursor.getString(1));
				latestLog.setAwakeTime(cursor.getString(2));
				latestLog.setDate(cursor.getString(3));
				latestLog.setMood(cursor.getString(4));
			} while (cursor.moveToNext());
		}
		
		return latestLog; 
	}

	// Updating single contact
	public int updateSleep(Sleep contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(SLEEP_TIME, contact.getSleepTime());
		values.put(AWAKE_TIME, contact.getAwakeTime());
		values.put(DATE, contact.getDate());
		values.put(MOOD, contact.getMood());

		// updating row
		return db.update(TABLE_SLEEPLOG, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteSleep(Sleep contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SLEEPLOG, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}


	// Getting sleep log Count
	public int getSleepsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SLEEPLOG;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		// return count
		return cursor.getCount();
	}
	

	/** Below are methods for the Data Log Table
	 */
	
	// Adding new contact
	public void addData(Data contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, contact.getID()); 
		values.put(TIME, contact.getTime()); 
		values.put(ACCEL, contact.getAccel()); 
		values.put(BPM, contact.getBpm());
		values.put(REM, contact.getRem()); 

		// Inserting Row
		db.insert(TABLE_DATALOG, null, values);
		db.close(); // Closing database connection
	}	
	
	
	// Getting all data for particular sleep ID
	public List<Data> getSleepData(int id) {
		List<Data> contactList = new ArrayList<Data>();
		// Select All Query
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DATALOG + " WHERE " + KEY_ID + " = ? ORDER BY "
				+ TIME + " ASC", new String[] { Integer.toString(id) });

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Data contact = new Data();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setTime(cursor.getString(1));
				contact.setAccel(cursor.getString(2));
				contact.setBpm(cursor.getString(3));
				contact.setRem(cursor.getString(4));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}
	

}
