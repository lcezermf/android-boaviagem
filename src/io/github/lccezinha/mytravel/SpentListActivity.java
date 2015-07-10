package io.github.lccezinha.mytravel;

import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SpentListActivity extends ListActivity implements
		OnItemClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setListAdapter(
				new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSpents())
		);
		ListView listView = getListView();
		listView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		TextView textView = (TextView) view;
		Toast.makeText(this,"Gasto selecionado: " + textView.getText(), Toast.LENGTH_SHORT).show();
	}
	
	private List<String> listSpents(){
		return Arrays.asList("Dogão: R$ 20.00", "Taxi - Volta: R$ 35.00", "Coisas: R$ 50.00");
	}

}
