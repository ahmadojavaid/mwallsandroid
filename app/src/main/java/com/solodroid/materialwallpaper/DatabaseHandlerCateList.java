package com.solodroid.materialwallpaper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandlerCateList extends SQLiteOpenHelper{
	
private static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_NAME = "CateListDB";
	
	private static final String TABLE_NAME = "CategoryList";
	private static final String KEY_ID = "id";
	private static final String KEY_CATELIST_NAME = "catelistname";
	private static final String KEY_CATELIST_IMAGEURL = "catelistimage";
	private static final String KEY_CATELIST_CATID = "catelistid";
	 
 	public DatabaseHandlerCateList(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
					+ KEY_ID + " INTEGER PRIMARY KEY," 
					+ KEY_CATELIST_NAME + " TEXT,"
					+ KEY_CATELIST_IMAGEURL + " TEXT,"
					+ KEY_CATELIST_CATID + " TEXT,"
					+"UNIQUE("+KEY_CATELIST_IMAGEURL+")"
					+ ")";
			db.execSQL(CREATE_CONTACTS_TABLE);		
			
		}
		
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

				// Create tables again
				onCreate(db);
	}
	
	//Adding Record in Database
	
	public	void AddtoFavoriteCateList(Item_All_ByCategory pj)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(KEY_CATELIST_NAME, pj.getItemCategoryName());
		values.put(KEY_CATELIST_IMAGEURL, pj.getItemImageurl());
		values.put(KEY_CATELIST_CATID, pj.getItemCatId());
		
		// Inserting Row
		db.insert(TABLE_NAME, null, values);
		db.close(); // Closing database connection
		
	}
	
	// Getting All Data
		public List<Item_All_ByCategory> getAllData() 
		{
			List<Item_All_ByCategory> dataList = new ArrayList<Item_All_ByCategory>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_NAME;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) 
			{
				do {
					Item_All_ByCategory contact = new Item_All_ByCategory();
				//	contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setItemCategoryName(cursor.getString(1));
					contact.setItemImageurl(cursor.getString(2));
					contact.setItemCatId(cursor.getString(3));
				 
					// Adding contact to list
					dataList.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			return dataList;
		}
		
	//getting single row
		
		public List<Item_All_ByCategory> getFavRow(String id) 
		{
			List<Item_All_ByCategory> dataList = new ArrayList<Item_All_ByCategory>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_NAME +" WHERE catelistid="+id;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) 
			{
				do {
					Item_All_ByCategory contact = new Item_All_ByCategory();
					//contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setItemCategoryName(cursor.getString(1));
					contact.setItemImageurl(cursor.getString(2));
					contact.setItemCatId(cursor.getString(3));
				 
					// Adding contact to list
					dataList.add(contact);
				} while (cursor.moveToNext());
			}

			// return contact list
			return dataList;
		}
 	
	//for remove favorite
		
		public void RemoveFav(Item_All_ByCategory contact)
		{
		    SQLiteDatabase db = this.getWritableDatabase();
		    db.delete(TABLE_NAME, KEY_CATELIST_CATID + " = ?",
		            new String[] { String.valueOf(contact.getItemCatId()) });
		    db.close();
		}
		
		public enum DatabaseManager {
			INSTANCE;
			private SQLiteDatabase db;
			private boolean isDbClosed =true;
			DatabaseHandlerCateList dbHelper;
			public void init(Context context) {
				dbHelper = new DatabaseHandlerCateList(context);
			if(isDbClosed){
			isDbClosed =false;
			this.db = dbHelper.getWritableDatabase();
			}

			}
 
			public boolean isDatabaseClosed(){
			return isDbClosed;
			}

			public void closeDatabase(){
			if(!isDbClosed && db != null){
			isDbClosed =true;
			db.close();
			dbHelper.close();
			}
			}
		}
}
