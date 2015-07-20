package io.github.lccezinha.mytravel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TravelListActivity extends ListActivity implements
		OnItemClickListener {
	
	private List<Map<String, Object>> travels;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		String[] from = { "image", "destiny", "date", "total" };
		int[] to = { R.id.travelKind, R.id.destiny, R.id.date, R.id.value };
		
		SimpleAdapter adapter = new SimpleAdapter(this, listTravels(), R.layout.list_travel, from, to);
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Map<String, Object> map = travels.get(position);
		String destiny = (String) map.get("destiny");
		String message = "Viagem Selecionada: " + destiny;
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, SpentListActivity.class));
	}
	
	private List<Map<String, Object>> listTravels(){
		travels = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("image", R.drawable.negocios);
		item.put("destiny", "São Paulo");
		item.put("date", "02/02/2012 a 04/02/2012");
		item.put("total", "Gasto total de R$: 313.21");
		travels.add(item);
		
		item = new HashMap<String, Object>();
		item.put("image", R.drawable.lazer);
		item.put("destiny", "Joinville");
		item.put("date", "03/03/2013 a 07/03/2013");
		item.put("total", "Gasto total de R$: 555.12");
		travels.add(item);
		
		return travels;
	}

}
