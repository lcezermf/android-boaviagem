package io.github.lccezinha.mytravel.activities;

import io.github.lccezinha.mytravel.R;
import io.github.lccezinha.mytravel.database.DataBaseHelper;
import io.github.lccezinha.mytravel.utils.ConstantHelpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class TravelListActivity extends ListActivity implements
		OnItemClickListener, OnClickListener, ViewBinder{
	
	private List<Map<String, Object>> travels;
	private AlertDialog alertDialog;
	private int selectedTravel;
	private AlertDialog confirmationDialog;
	private DataBaseHelper dataBaseHelper;
	private SimpleDateFormat dateFormat;
	private Double limitValue;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		initializeDataBase();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		loadPreferences();
			
		String[] from = { "image", "destiny", "date", "total", "valuesProgressBar" };
		int[] to = { R.id.travelKind, R.id.destiny, R.id.date, R.id.value, R.id.valuesProgressBar };
		
		SimpleAdapter adapter = new SimpleAdapter(this, listTravels(), R.layout.list_travel, from, to);
		adapter.setViewBinder((ViewBinder) this);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		
		this.alertDialog = createAlertDialogs();
		this.confirmationDialog = createConfirmationDialog();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectedTravel = position;
		alertDialog.show();
	}
	
	private List<Map<String, Object>> listTravels(){
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT _id, travel_kind, destiny, " + 
							"date_finish, date_start, budget FROM travels", null);
		
		cursor.moveToFirst();
				
		travels = new ArrayList<Map<String, Object>>();

		for(int i = 0; i < cursor.getCount(); i++){
			Map<String, Object> item = new HashMap<String, Object>();
			
			String id = cursor.getString(0);
			int travelKind = cursor.getInt(1);
			String destiny = cursor.getString(2);
			long dateFinish = cursor.getLong(3);
			long dateStart = cursor.getLong(4);
			double budget = cursor.getDouble(5);
			
			item.put("id", id);
			
			if(travelKind == ConstantHelpers.LEISURE_TRAVEL){
				item.put("image", R.drawable.lazer);
			}else{
				item.put("image", R.drawable.negocios);
			}
			
			item.put("destiny", destiny);
			
			Date dateFinishDate = new Date(dateFinish);
			Date dateStartDate = new Date(dateStart);
		
			String period = dateFormat.format(dateStartDate) + " a " + dateFormat.format(dateFinishDate);
			
			item.put("date", period);
			
			double totalSpent = calculateTotalSpent(db, id);
			item.put("total", "Gasto Total R$ " + totalSpent);
			
			double alert = budget * limitValue / 100;
			Double [] values = new Double[] { budget, alert, totalSpent };
			item.put("valuesProgressBar", values);
			
			travels.add(item);
			
			cursor.moveToNext();			
		}
		cursor.close();
		dataBaseHelper.close();
		
		return travels;
	}

	@Override
	public void onClick(DialogInterface dialog, int item) {
		Intent intent;
		String id = (String) travels.get(selectedTravel).get("id");
		
		switch (item) {
		case 0:
			intent = new Intent(this, TravelActivity.class);
			intent.putExtra(ConstantHelpers.TRAVEL_ID, id);
			startActivity(intent);
			break;

		case 1:
			startActivity(new Intent(this, SpentActivity.class));
			break;
			
		case 2:
			startActivity(new Intent(this, SpentListActivity.class));
			break;
			
		case 3:
			confirmationDialog.show();
			break;
		case DialogInterface.BUTTON_POSITIVE:
			travels.remove(this.selectedTravel);
			deleteTravel(id);
			getListView().invalidateViews();
			break;
		case DialogInterface.BUTTON_NEGATIVE:
			confirmationDialog.dismiss();
			break;
		}		
	}
	
	private AlertDialog createAlertDialogs(){
		final CharSequence[] items = {
				getString(R.string.edit),
				getString(R.string.new_spent),
				getString(R.string.spents_realized),
				getString(R.string.remove)
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.options);
		builder.setItems(items, this);
		
		return builder.create();
	}
	
	private AlertDialog createConfirmationDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.confirm_travel_delete);
		builder.setPositiveButton(getString(R.string.yes), this);
		builder.setNegativeButton(getString(R.string.no), this);
		
		return builder.create();
	}

	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		if (view.getId() == R.id.valuesProgressBar) {
			Double valores[] = (Double[]) data;
			ProgressBar progressBar = (ProgressBar) view;
			progressBar.setMax(valores[0].intValue());
			progressBar.setSecondaryProgress(valores[1].intValue());
			progressBar.setProgress(valores[2].intValue());
			return true;
		}
		return false;
	}
	
	private void initializeDataBase(){
		dataBaseHelper = new DataBaseHelper(this);
	}
	
	private void loadPreferences(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String value = preferences.getString("limitValue", "-1");
		limitValue = Double.valueOf(value);
	}
	
	private double calculateTotalSpent(SQLiteDatabase db, String id){
		Cursor cursor = db.rawQuery("SELECT SUM(value) FROM spents WHERE travel_id = ?", new String[]{ id });
		cursor.moveToFirst();
		double total = cursor.getDouble(0);
		cursor.close();
		
		return total;
	}
	
	private void deleteTravel(String id){
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		
		String where [] = new String[] { id };
		db.delete("spents", "travel_id = ?", where);
		db.delete("spents", "_id = ?", where);
	}

}
