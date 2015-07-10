package io.github.lccezinha.mytravel;

import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TravelListActivity extends ListActivity implements
		OnItemClickListener {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setListAdapter(
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTravels())
		);
		ListView listView = getListView();
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		TextView textView = (TextView) view;
		String message = "Viagem selecionada: " + textView.getText().toString();
		
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, SpentListActivity.class));
	}
	
	private List<String> listTravels(){
		return Arrays.asList("São Paulo", "Joinville", "Floarinópolis");
	}

}
