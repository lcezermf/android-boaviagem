package io.github.lccezinha.mytravel.dao;

import io.github.lccezinha.mytravel.database.DataBaseHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAO {

	private DataBaseHelper dataBaseHelper;
	private SQLiteDatabase db;
	
	public DAO(Context context){
		dataBaseHelper = new DataBaseHelper(context);
	}
	
	private SQLiteDatabase getDb(){
		if(db == null){
			db = dataBaseHelper.getWritableDatabase();
		}
		
		return db;
	}
	
	public void close(){
		dataBaseHelper.close();
	}
	
}
