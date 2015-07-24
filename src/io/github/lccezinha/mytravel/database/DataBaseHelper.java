package io.github.lccezinha.mytravel.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE = "boaviagem";
	private static int VERSION = 1;
	
	public DataBaseHelper(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableTravels(db);
		createTableSpents(db);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	
	private void createTableTravels(SQLiteDatabase db){
		db.execSQL("CREATE TABLE TRAVELS (_ID INTEGER PRIMARY KEY," +
				" DESTINY TEXT, TRAVEL_KIND INTEGER, DATE_FINISH DATE," +
				" DATE_START DATE, BUDGET DOUBLE, PEOPLE_QUANTITY INTEGER);");
	}
	
	private void createTableSpents(SQLiteDatabase db){
		db.execSQL("CREATE TABLE SPENTS (_ID INTEGER PRIMARY KEY," +
				" CATEGORGY TEXT, SPENT_DATE DATE, VALUE DOUBLE, DESCRIPTION TEXT," +
				" PLACE TEXT, TRAVEL_ID INTEGER," +
				" FOREIGN KEY(TRAVEL_ID) REFERENCES TRAVELS(_ID));");		
	}
	
}
