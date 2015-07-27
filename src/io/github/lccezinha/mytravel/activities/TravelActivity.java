package io.github.lccezinha.mytravel.activities;

import io.github.lccezinha.mytravel.R;
import io.github.lccezinha.mytravel.database.DataBaseHelper;
import io.github.lccezinha.mytravel.utils.ContantHelpers;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class TravelActivity extends Activity {
	
	private Date dateFinish, dateStart;
	private int year, month, day;
	private Button dateFinishButton, dateStartButton;
	private DataBaseHelper dataBaseHelper;
	private EditText destiny, peopleQuantity, budget;
	private RadioGroup radioGroup;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_travel);
		
		initializeCalendar();		
		initializeViewComponents();
		initializeDataBase();
	}
	
	@SuppressWarnings("deprecation")
	public void selectDate(View view){
		showDialog(view.getId());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.travel_menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.newSpent:
			startActivity(new Intent(this, SpentActivity.class));
			return true;
		case R.id.remove:
			//remover viagem do banco de dados
			return true;
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}
	
	public void createTravel(View view){
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		
		//Chave = Coluna no BD
		//Value = Valor da coluna
		ContentValues values = new ContentValues();
		values.put("destiny", destiny.getText().toString());
		values.put("date_finish", dateFinish.getTime());
		values.put("date_start", dateStart.getTime());
		values.put("budget", budget.getText().toString());
		values.put("people_quantity", peopleQuantity.getText().toString());
		
		int kind = radioGroup.getCheckedRadioButtonId();
		
		if(kind == R.id.leisure){
			values.put("travel_kind", ContantHelpers.LEISURE_TRAVEL);
		}else{
			values.put("travel_kind", ContantHelpers.BUSINESS_TRAVEL);
		}
		
		long result = db.insert("travels", null, values);
		
		if(result != -1){
			Toast.makeText(this, getString(R.string.travel_create), Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, getString(R.string.travel_create_error), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy(){
		dataBaseHelper.close();
		super.onDestroy();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case R.id.dateFinish:
			return new DatePickerDialog(this, dateFinishListener, year, month, day);

		case R.id.dateStart:
			return new DatePickerDialog(this, dateStartListener, year, month, day);
		}
		return null;
	}
	
	private OnDateSetListener dateFinishListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int yearSelected, int monthSelected, int daySelected) {			
			dateFinish = createDate(yearSelected, monthSelected, daySelected);
			dateFinishButton.setText(daySelected + "/" + (monthSelected + 1) + "/" + yearSelected);
		}
	};

	private OnDateSetListener dateStartListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int yearSelected, int monthSelected, int daySelected) {
			dateStart = createDate(yearSelected, monthSelected, daySelected);
			dateStartButton.setText(daySelected + "/" + (monthSelected + 1) + "/" + yearSelected);
		}
	};

	private Date createDate(int yearSelected, int monthSelected, int daySelected) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(yearSelected, monthSelected, daySelected);
		return calendar.getTime();
	}	
	
	private void initializeCalendar(){
		Calendar calendar = Calendar.getInstance();
		year  = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day   = calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	private void initializeViewComponents(){
		dateFinishButton = (Button) findViewById(R.id.dateFinish);
		dateStartButton = (Button) findViewById(R.id.dateStart);
		destiny = (EditText) findViewById(R.id.destiny);
		budget = (EditText) findViewById(R.id.budget);
		peopleQuantity = (EditText) findViewById(R.id.peopleQuantity);
		radioGroup = (RadioGroup) findViewById(R.id.travelKind);
	}
	
	private void initializeDataBase(){
		dataBaseHelper = new DataBaseHelper(this);
	}
	
}
