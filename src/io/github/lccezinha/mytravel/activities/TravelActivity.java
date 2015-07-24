package io.github.lccezinha.mytravel.activities;

import java.util.Calendar;
import java.util.Date;

import io.github.lccezinha.mytravel.R;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class TravelActivity extends Activity {
	
	private Date dateFinish, dateStart;
	private int year, month, day;
	private Button dateFinishButton, dateStartButton;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_travel);
		
		Calendar calendar = Calendar.getInstance();
		year  = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day   = calendar.get(Calendar.DAY_OF_MONTH);
		
		dateFinishButton = (Button) findViewById(R.id.dateFinish);
		dateStartButton = (Button) findViewById(R.id.dateStart);
	}
	
	@SuppressWarnings("deprecation")
	public void selectDate(View view){
		showDialog(view.getId());
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
	
}
