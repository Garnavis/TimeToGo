package com.example.fragmentpractice;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDbHelper extends SQLiteOpenHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
		    "CREATE TABLE " + FeedEventEntry.TABLE_NAME + "(" +
		    		FeedEventEntry.EVENT_ID + " INTEGER PRIMARY KEY," +
		    		FeedEventEntry.EVENT_NAME + TEXT_TYPE + COMMA_SEP +
		    		FeedEventEntry.EVENT_NOTE + TEXT_TYPE + COMMA_SEP +
		    		FeedEventEntry.EVENT_TIME + TEXT_TYPE + COMMA_SEP +
		    		FeedEventEntry.EVENT_DATE + TEXT_TYPE + COMMA_SEP +
		    		FeedEventEntry.EVENT_ADDRESS + TEXT_TYPE + COMMA_SEP +
		    		FeedEventEntry.EVENT_CONTACTS + TEXT_TYPE + ")";
	
	private static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + FeedEventEntry.TABLE_NAME;

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Events.db";
	
	public EventDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	
	public void onDownGrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	//add new contact to the db
	public void addEvent(Event event) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(FeedEventEntry.EVENT_NAME, event.getName());
		values.put(FeedEventEntry.EVENT_NOTE, event.getNotes());
		values.put(FeedEventEntry.EVENT_TIME, event.getTime());
		values.put(FeedEventEntry.EVENT_DATE, event.getDate());
		values.put(FeedEventEntry.EVENT_ADDRESS, event.getAddressName());
		values.put(FeedEventEntry.EVENT_CONTACTS, event.getContactsString());
		
		
		db.insert(FeedEventEntry.TABLE_NAME, null, values);
		db.close();
	}
	
	public Event getEvent(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(FeedEventEntry.TABLE_NAME, 
				new String[] {FeedEventEntry.EVENT_ID, FeedEventEntry.EVENT_NAME, FeedEventEntry.EVENT_NOTE, FeedEventEntry.EVENT_TIME, 
				FeedEventEntry.EVENT_DATE, FeedEventEntry.EVENT_ADDRESS, FeedEventEntry.EVENT_CONTACTS},
				FeedEventEntry.EVENT_ID	+ "=?", new String[] { String.valueOf(id) }, null, null, null, null); 
		
		if (cursor != null)
	        cursor.moveToFirst();
		
		Event event = new Event(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), null, null);
		db.close();
		return event;
	}
	
	public ArrayList<Event> getAllEvents() {
		ArrayList<Event> events = new ArrayList<Event>();

	    String selectQuery = "SELECT  * FROM " + FeedEventEntry.TABLE_NAME;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    if (cursor.moveToFirst()) {
	        do {
	        	Event event = new Event(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), null, null);
	            events.add(event);
	        } while (cursor.moveToNext());
	    }
	    db.close();	 
	    return events;
	}
	
	public int updateEvent(Event event) {
	    SQLiteDatabase db = this.getWritableDatabase();
	 
	    ContentValues values = new ContentValues();
	    
		values.put(FeedEventEntry.EVENT_NAME, event.getName());
		values.put(FeedEventEntry.EVENT_NOTE, event.getNotes());
		values.put(FeedEventEntry.EVENT_TIME, event.getTime());
		values.put(FeedEventEntry.EVENT_DATE, event.getDate());
		values.put(FeedEventEntry.EVENT_ADDRESS, event.getAddressName());
		values.put(FeedEventEntry.EVENT_CONTACTS, event.getContactsString());
	    
	    return db.update(FeedEventEntry.TABLE_NAME, values, FeedEventEntry._ID + " = ?",
	            new String[] { String.valueOf(event.getID()) });
	}
	
	public void deleteEvent(Event event) {
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(FeedEventEntry.TABLE_NAME, FeedEventEntry.EVENT_ID + " = ?",
	            new String[] { String.valueOf(event.getID()) });
	    db.close();
	}
	

}