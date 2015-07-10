package io.github.lccezinha.mytravel;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class SpentActivity extends Activity {
	
	private int year, month, day;
	private Button spentDate;
	private Spinner category;
	
	@Override
	public void onCreate(Bundle savedInstaceState){
		super.onCreate(savedInstaceState);
		setContentView(R.layout.spent);
		
		Calendar calendar = Calendar.getInstance();
		year  = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day   = calendar.get(Calendar.DAY_OF_MONTH);
		
		spentDate = (Button) findViewById(R.id.spent_date);
		spentDate.setText(day + "/" + month + "/" + year);
		
		ArrayAdapter<CharSequence> adapter = 
				ArrayAdapter.createFromResource(
					this, 
					R.array.spent_categories, 
					android.R.layout.simple_spinner_item
				);
		
		category = (Spinner) findViewById(R.id.category);
		category.setAdapter(adapter);
	}
	
	@SuppressWarnings("deprecation")
	public void selectDate(View view){
		showDialog(view.getId());
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		if(R.id.spent_date == id){
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
		}		
	};
	
}
