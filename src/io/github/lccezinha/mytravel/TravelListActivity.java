package io.github.lccezinha.mytravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

public class TravelListActivity extends ListActivity implements
		OnItemClickListener, OnClickListener {
	
	private List<Map<String, Object>> travels;
	private AlertDialog alertDialog;
	private int selectedTravel;
	private AlertDialog confirmationDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		String[] from = { "image", "destiny", "date", "total", "valuesProgressBar" };
		int[] to = { R.id.travelKind, R.id.destiny, R.id.date, R.id.value, R.id.valuesProgressBar };
		
		SimpleAdapter adapter = new SimpleAdapter(this, listTravels(), R.layout.list_travel, from, to);
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
		travels = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("image", R.drawable.negocios);
		item.put("destiny", "São Paulo");
		item.put("date", "02/02/2012 a 04/02/2012");
		item.put("total", "Gasto total de R$: 313.21");
		item.put("valuesProgressBar", new Double[]{ 500.0, 450.0, 313.21 });
		travels.add(item);
		
		item = new HashMap<String, Object>();
		item.put("image", R.drawable.lazer);
		item.put("destiny", "Joinville");
		item.put("date", "03/03/2013 a 07/03/2013");
		item.put("total", "Gasto total de R$: 555.12");
		item.put("valuesProgressBar", new Double[]{ 750.0, 250.0, 555.12 });
		travels.add(item);
		
		return travels;
	}
	
	/*public boolean setViewValue(View view, Object data, String textRepresentation) {
		if (view.getId() == R.id.valuesProgressBar) {
			Double valores[] = (Double[]) data;
			ProgressBar progressBar = (ProgressBar) view;
			progressBar.setMax(valores[0].intValue());
			progressBar.setSecondaryProgress(valores[1].intValue());
			progressBar.setProgress(valores[2].intValue());
			return true;
		}
		return false;
	}*/

	@Override
	public void onClick(DialogInterface dialog, int item) {
		switch (item) {
		
		case 0:
			startActivity(new Intent(this, TravelActivity.class));
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

}
