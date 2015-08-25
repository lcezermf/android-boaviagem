package io.github.lccezinha.mytravel.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import io.github.lccezinha.mytravel.database.DataBaseHelper;
import io.github.lccezinha.mytravel.domain.Spent;
import io.github.lccezinha.mytravel.domain.Travel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	
	public List<Travel> listTravels(){
		Cursor cursor = getDb().query(DataBaseHelper.Travel.TABLE, 
				DataBaseHelper.Travel.COLUMNS, null, null, null, null, null);
		
		List<Travel> travels = new ArrayList<Travel>();
		while(cursor.moveToNext()){
			Travel travel = createTravel(cursor);
			travels.add(travel);
		}
		cursor.close();
		return travels;
	}
	
	public Travel findTravelById(Integer id){
		Cursor cursor = getDb().query(DataBaseHelper.Travel.TABLE, 
				DataBaseHelper.Travel.COLUMNS,
				DataBaseHelper.Travel._ID + " = ?",
				new String[]{ id.toString() },
				null, null, null);
		
		
		if(cursor.moveToNext()){
			Travel travel = createTravel(cursor);
			cursor.close();
			
			return travel;
		}
		
		return null;
	}
	
	public long insertTravel(Travel travel){
		ContentValues values = new ContentValues();
		
		values.put(DataBaseHelper.Travel.DESTINY, travel.getDestiny());
		values.put(DataBaseHelper.Travel.TRAVEL_KIND, travel.getTravelKind());
		values.put(DataBaseHelper.Travel.DATE_START, travel.getDateStart().getTime());
		values.put(DataBaseHelper.Travel.DATE_FINISH, travel.getDateFinish().getTime());
		values.put(DataBaseHelper.Travel.BUDGET, travel.getBudget());
		values.put(DataBaseHelper.Travel.PEOPLE_QUANTITY, travel.getPeopleQuantity());
		
		return getDb().insert(DataBaseHelper.Travel.TABLE, null, values);
	}
		
	public boolean removeTravel(Long id){
		String whereClause = DataBaseHelper.Travel._ID + " = ?";
		String[] whereArgs = new String[]{ id.toString() };
		int removed = getDb().delete(DataBaseHelper.Travel.TABLE, whereClause, whereArgs);
		
		if(removed > 0){
			String whereClause_2 = DataBaseHelper.Spent.TRAVEL_ID + " = ?";
			String[] whereArgs_2 = new String[]{ id.toString() };
			getDb().delete(DataBaseHelper.Spent.TABLE, whereClause_2, whereArgs_2);
		}
		
		return removed > 0;
	}
	
	public List<Spent> listSpents(Travel travel){
		String selection = DataBaseHelper.Spent.TRAVEL_ID + " = ?";
		String[] selectionArgs = new String[] { travel.getId().toString() };
		
		Cursor cursor = getDb().query(DataBaseHelper.Spent.TABLE,
				DataBaseHelper.Spent.COLUMNS,
				selection, selectionArgs, null, null, null);
		
		List<Spent> spents = new ArrayList<Spent>();
		
		while(cursor.moveToNext()){
			Spent spent = createSpent(cursor);
			spents.add(spent);
		}
		
		cursor.close();
		
		return spents;
	}
	
	public Spent findSpentById(Integer id){
		Cursor cursor = getDb().query(DataBaseHelper.Spent.TABLE, 
				DataBaseHelper.Spent.COLUMNS,
				DataBaseHelper.Spent._ID + " = ?",
				new String[]{ id.toString() },
				null, null, null);
		
		
		if(cursor.moveToNext()){
			Spent spent = createSpent(cursor);
			cursor.close();
			
			return spent;
		}
		
		return null;
	}
	
	public long insertSpent(Spent spent){
		ContentValues values = new ContentValues();
		
		values.put(DataBaseHelper.Spent.CATEGORY, spent.getCategory());
		values.put(DataBaseHelper.Spent.DATE, spent.getDate().getTime());
		values.put(DataBaseHelper.Spent.DESCRIPTION, spent.getDescription());
		values.put(DataBaseHelper.Spent.PLACE, spent.getPlace());
		values.put(DataBaseHelper.Spent.VALUE, spent.getValue());
		values.put(DataBaseHelper.Spent.TRAVEL_ID, spent.getTravelId());
		
		return getDb().insert(DataBaseHelper.Spent.TABLE, null, values);
	}
	
	public boolean removeSpent(Long id){
		String whereClause = DataBaseHelper.Spent._ID + " = ?";
		String[] whereArgs = new String[]{ id.toString() };
		int removed = getDb().delete(DataBaseHelper.Spent.TABLE, whereClause, whereArgs);
		
		return removed > 0;
	}
	
	public double calculateTotalSpent(Travel travel){
		Cursor cursor = getDb().rawQuery(
				"SELECT SUM(" + DataBaseHelper.Spent.VALUE + ") FROM " +
						DataBaseHelper.Spent.TABLE + " WHERE " +
						DataBaseHelper.Spent.TRAVEL_ID + " = ?",
						new String[] { travel.getId().toString() });
		
		cursor.moveToFirst();
		double total = cursor.getDouble(0);
		cursor.close();
		
		return total;
	}
	
	private Travel createTravel(Cursor cursor){
		Travel travel = new Travel(
				cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Travel._ID)),
				cursor.getString(cursor.getColumnIndex(DataBaseHelper.Travel.DESTINY)),
				cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Travel.TRAVEL_KIND)),
				new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Travel.DATE_FINISH))),
				new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Travel.DATE_START))),
				cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.Travel.BUDGET)),
				cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Travel.PEOPLE_QUANTITY))
		);
		
		return travel;
	}
	
	private Spent createSpent(Cursor cursor){
		Spent spent = new Spent(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Spent._ID)),
				new Date(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.Spent.DATE))),
				cursor.getString(cursor.getColumnIndex(DataBaseHelper.Spent.CATEGORY)),
				cursor.getString(cursor.getColumnIndex(DataBaseHelper.Spent.DESCRIPTION)),
				cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.Spent.VALUE)),
				cursor.getString(cursor.getColumnIndex(DataBaseHelper.Spent.PLACE)),
				cursor.getInt(cursor.getColumnIndex(DataBaseHelper.Spent.TRAVEL_ID))
		);
		
		return spent;
	}
}
