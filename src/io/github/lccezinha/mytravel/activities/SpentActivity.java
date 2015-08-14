package io.github.lccezinha.mytravel.activities;

import io.github.lccezinha.mytravel.R;
import io.github.lccezinha.mytravel.database.DataBaseHelper;
import io.github.lccezinha.mytravel.utils.ConstantHelpers;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SpentActivity extends Activity {
	
	private int year, month, day;
	private Button spentDate;
	private Spinner category;
	private TextView destiny;
	private EditText value;
	private EditText description;
	private EditText place;
	private Date data;
	private String travel_id;
	
	private DataBaseHelper dataBaseHelper;
	@Override
	public void onCreate(Bundle savedInstaceState){
		super.onCreate(savedInstaceState);
		setContentView(R.layout.spent);
		
		initializeCalendar();
		
		spentDate = (Button) findViewById(R.id.date);
		spentDate.setText(day + "/" + (month + 1) + "/" + year);
		
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(
					this, 
					R.array.spent_categories, 
					android.R.layout.simple_spinner_item
				);
		category = (Spinner) findViewById(R.id.category);
		category.setAdapter(adapter);
		
		travel_id = getIntent().getStringExtra(ConstantHelpers.TRAVEL_DESTINY);
		destiny = (TextView) findViewById(R.id.destiny);
		destiny.setText(travel_id);
		
		value = (EditText) findViewById(R.id.value);
		description = (EditText) findViewById(R.id.description);
		place = (EditText) findViewById(R.id.place);
		
		dataBaseHelper = new DataBaseHelper(this);
	}
	
	@SuppressWarnings("deprecation")
	public void selectDate(View view){
		showDialog(view.getId());
	}
	
	public void createSpent(View view) {
		ContentValues values = new ContentValues();
		values.put("value", value.getText().toString());
		values.put("description", description.getText().toString());
		values.put("place", place.getText().toString());
		values.put("category", category.getSelectedItem().toString());
		
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		db.insert("spents", null, values);
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		if(R.id.spentDate == id){
			return new DatePickerDialog(this, listener, year, month, day);
		}
		return null;
	}
	
	private OnDateSetListener listener = new OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth){
			year  = yearSelected;
			month = monthOfYear;
			day   = dayOfMonth;
			spentDate.setText(day + "/" + (month + 1) + "/" + year);
			data = createDate(day, month, year);
		}		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.spent_menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		finish();
		return true;
	}
	
	private void initializeCalendar(){
		Calendar calendar = Calendar.getInstance();
		year  = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day   = calendar.get(Calendar.DAY_OF_MONTH);
		
		spentDate = (Button) findViewById(R.id.spentDate);
		spentDate.setText(day + "/" + month + "/" + year);
	}
	
	private Date createDate(int day, int month, int year){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime(); 
	}
	
}
