package io.github.lccezinha.mytravel.provider;

import static io.github.lccezinha.mytravel.provider.MyTravelContract.AUTHORITY;
import static io.github.lccezinha.mytravel.provider.MyTravelContract.SPENT_PATH;
import static io.github.lccezinha.mytravel.provider.MyTravelContract.TRAVEL_PATH;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import io.github.lccezinha.mytravel.database.DataBaseHelper;
import io.github.lccezinha.mytravel.provider.MyTravelContract.Travel;
import io.github.lccezinha.mytravel.provider.MyTravelContract.Spent;

public class MyTravelProvider extends ContentProvider {

	private static final int TRAVELS = 1;
	private static final int TRAVEL_ID = 2;
	private static final int SPENTS = 3;
	private static final int SPENT_ID = 4;
	private static final int SPENT_TRAVEL_ID = 5;
	
	private DataBaseHelper dataBaseHelper;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static{
		uriMatcher.addURI(AUTHORITY, TRAVEL_PATH, TRAVELS);
		uriMatcher.addURI(AUTHORITY, TRAVEL_PATH + "/#", TRAVEL_ID);
		uriMatcher.addURI(AUTHORITY, SPENT_PATH, SPENTS);
		uriMatcher.addURI(AUTHORITY, SPENT_PATH + "/#", SPENT_ID);
		uriMatcher.addURI(AUTHORITY, SPENT_PATH + "/" + TRAVEL_PATH + "/#", SPENT_TRAVEL_ID);
	}
	
	@Override
	public boolean onCreate() {
		dataBaseHelper = new DataBaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		
		switch (uriMatcher.match(uri)) {
		case TRAVELS:
			return db.query(TRAVEL_PATH, projection, selection, selectionArgs, null, null, sortOrder);
		
		case TRAVEL_ID:
			selection = Travel._ID + " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() };
			return db.query(TRAVEL_PATH, projection, selection, selectionArgs, null, null, sortOrder);
		
		case SPENTS:
			return db.query(SPENT_PATH, projection, selection, selectionArgs, null, null, sortOrder);
			
		case SPENT_ID:
			selection = Spent._ID + " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() };
			return db.query(SPENT_PATH, projection, selection, selectionArgs, null, null, sortOrder);
			
		case SPENT_TRAVEL_ID:
			selection = Spent.TRAVEL_ID = " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() }; 
			return db.query(SPENT_PATH, projection, selection, selectionArgs, null, null, sortOrder);
			
		default:
			throw new IllegalArgumentException("Uri desconhecida!");
			
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case TRAVELS:
			return Travel.CONTENT_TYPE;
		case TRAVEL_ID:
			return Travel.CONTENT_ITEM_TYPE;
		case SPENTS:
		case SPENT_TRAVEL_ID:
			return Spent.CONTENT_TYPE;
		case SPENT_ID:
			return Spent.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Uri desconhecida!");
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		long id;
		
		switch (uriMatcher.match(uri)) {
		case TRAVELS:
			id = db.insert(TRAVEL_PATH, null, values);
			return Uri.withAppendedPath(Travel.CONTENT_URI, String.valueOf(id));
			
		case SPENTS:
			id = db.insert(SPENT_PATH, null, values);
			return Uri.withAppendedPath(Spent.CONTENT_URI, String.valueOf(id));
		
		default:
			throw new IllegalArgumentException("Uri desconhecida!");
		
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
		
		switch (uriMatcher.match(uri)) {
		case TRAVEL_ID:
			selection = Travel._ID + " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() };
			return database.delete(TRAVEL_PATH, selection, selectionArgs);
			
		case SPENT_ID:
			selection = Spent._ID + " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() };
			return database.delete(SPENT_PATH, selection, selectionArgs);
			
		default:
			throw new IllegalArgumentException("Uri desconhecida");
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
		
		switch (uriMatcher.match(uri)) {
		case TRAVEL_ID:
			selection = Travel._ID + " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() };
			return database.update(TRAVEL_PATH, values, selection, selectionArgs);
		case SPENT_ID:
			selection = Spent._ID + " = ?";
			selectionArgs = new String[] { uri.getLastPathSegment() };
			return database.update(SPENT_PATH, values, selection, selectionArgs);
		
		default:
			throw new IllegalArgumentException("Uri desconhecida");
		}
	}

}
