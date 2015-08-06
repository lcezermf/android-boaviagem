package io.github.lccezinha.mytravel.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE = "boaviagem";
	private static int VERSION = 1;
	
	public static class Travel {
		public static final String TABLE = "TRAVELS";
		public static final String _ID = "_ID";
		public static final String DESTINY = "DESTINY";
		public static final String DATE_START = "DATE_START";
		public static final String DATE_FINISH = "DATE_FINISH";
		public static final String BUDGET = "BUDGET";
		public static final String PEOPLE_QUANTITY = "PEOPLE_QUANTITY";
		public static final String TRAVEL_KIND = "TRAVEL_KIND";
		
		public static final String[] COLUMNS = new String[]{
							_ID, DESTINY, DATE_START, DATE_FINISH,  
							TRAVEL_KIND, BUDGET, PEOPLE_QUANTITY };
	}
	
	public static class Spent {
		public static final String TABLE = "SPENTS";
		public static final String _ID = "_ID";
		public static final String TRAVEL_ID = "TRAVEL_ID";
		public static final String CATEGORY = "CATEGORY";
		public static final String DATE = "DATE";
		public static final String DESCRIPTION = "DESCRIPTION";
		public static final String VALUE = "VALUE";
		public static final String PLACE = "PLACE";
		
		public static final String[] COLUMNS = new String[]{
			_ID, TRAVEL_ID, CATEGORY, DATE, DESCRIPTION, VALUE, PLACE
		};
	}
	
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
