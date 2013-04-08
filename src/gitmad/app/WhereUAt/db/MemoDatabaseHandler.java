package gitmad.app.WhereUAt.db;

import gitmad.app.WhereUAt.model.Memo;
import gitmad.app.WhereUAt.model.Place;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemoDatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "memoManager";

	// places table name
	private static final String TABLE_MEMOS = "memos";

	// places Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_FILEPATH = "filepath";

	public MemoDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createMemoTable = "CREATE TABLE " + TABLE_MEMOS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_LOCATION + " TEXT,"
				+ KEY_FILEPATH + " TEXT)";
		db.execSQL(createMemoTable);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public void addMemo(Memo memo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, memo.getName());
		values.put(KEY_LOCATION, memo.getLocation());
        values.put(KEY_FILEPATH, memo.getFilePath());

		// Inserting Row
		long id = db.insert(TABLE_MEMOS, null, values);
		if (id < 0) {
		    throw new RuntimeException("An error has occurred when inserting a new memo into the database");
		}
		memo.setId(id);
		db.close(); // Closing database connection
	}

//	g getPlace(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE_MEMOS, new String[] { KEY_ID,
//				KEY_NAME, KEY_LAT, KEY_LON }, KEY_ID + "=?",
//				new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		Place place = new Place(Integer.parseInt(cursor.getString(0)),
//				cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)));
//		// return place
//		return place;
//	}
//	
	// Getting All places
	public List<Memo> getAllMemos() {
		List<Memo> memoList = new ArrayList<Memo>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_MEMOS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Memo place = new Memo(
				                    cursor.getLong(0),
				                    cursor.getString(1),
				                    cursor.getString(2),
                                    cursor.getString(3));
				// Adding place to list
				memoList.add(place);
			} while (cursor.moveToNext());
		}

		// return place list
		return memoList;
	}
	
//	// Updating single place
//	public int updateplace(Place place) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, place.getName());
//		values.put(KEY_LAT, place.getLat());
//		values.put(KEY_LON, place.getLon());
//
//		// updating row
//		return db.update(TABLE_MEMOS, values, KEY_ID + " = ?",
//				new String[] { String.valueOf(place.getID()) });
//	}
//
	public void deleteMemo(Memo memo) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMOS, KEY_ID + " = ?",
				new String[] { String.valueOf(memo.getId()) });
		db.close();
	}


//	// Getting places Count
//	public int getplacesCount() {
//		String countQuery = "SELECT  * FROM " + TABLE_MEMOS;
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor cursor = db.rawQuery(countQuery, null);
//		cursor.close();
//
//		// return count
//		return cursor.getCount();
//	}
//
}
