package gitmad.app.WhereUAt;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "placesManager";

	// places table name
	private static final String TABLE_PLACES = "places";

	// places Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	//private static final String KEY_PH_NO = "phone_number";
	private static final String KEY_LAT = "latitude";
	private static final String KEY_LON = "longitude";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_placeS_TABLE = "CREATE TABLE " + TABLE_PLACES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_LAT + " TEXT, " + KEY_LON + " TEXT" + ")";
		db.execSQL(CREATE_placeS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new place
	void addplace(Place place) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, place.getID()); //place id
		values.put(KEY_NAME, place.getName()); // place Name
		values.put(KEY_LAT, place.getLat()); // place latitude
		values.put(KEY_LON, place.getLon()); //place longitude

		// Inserting Row
		db.insert(TABLE_PLACES, null, values);
		db.close(); // Closing database connection
	}

	// Getting single place
	Place getPlace(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PLACES, new String[] { KEY_ID,
				KEY_NAME, KEY_LAT, KEY_LON }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Place place = new Place(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
		// return place
		return place;
	}
	
	// Getting All places
	public List<Place> getAllPlaces() {
		List<Place> placeList = new ArrayList<Place>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PLACES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Place place = new Place(Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
				// Adding place to list
				placeList.add(place);
			} while (cursor.moveToNext());
		}

		// return place list
		return placeList;
	}
	
	//We need to have a unique id for each place. Since a database is persistent, we need to find where to start the id
	//This method should be called in onCreate of the ListView fragment
	public int getCurrentID()
	{
		ArrayList<Place> places = (ArrayList<Place>) getAllPlaces();
		return places.size() +1;
	}
	
	void logPlaces()
	{
		Log.d("DatabaseHandler: ", "Inside Log Places()");
		ArrayList<Place> places = (ArrayList<Place>) getAllPlaces();
		for(Place p: places)
		{
			Log.d("DatabaseHandler: ", p.toString());
		}
		
	}

	// Updating single place
	public int updateplace(Place place) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, place.getName());
		values.put(KEY_LAT, place.getLat());
		values.put(KEY_LON, place.getLon());

		// updating row
		return db.update(TABLE_PLACES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(place.getID()) });
	}

	// Deleting single place
	public void deleteplace(Place place) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PLACES, KEY_ID + " = ?",
				new String[] { String.valueOf(place.getID()) });
		db.close();
	}


	// Getting places Count
	public int getplacesCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PLACES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
