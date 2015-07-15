package io.github.lccezinha.mytravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class SpentListActivity extends ListActivity implements
		OnItemClickListener {

	private List<Map<String, Object>> spents;
	private String lastDate;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		String[] from = { "date", "description", "value", "category" };
		int[] to = { R.id.date, R.id.description, R.id.value, R.id.category };
		
		SimpleAdapter adapter = new SimpleAdapter(this, listSpents(), R.layout.list_spent, from, to);
		adapter.setViewBinder(new SpentViewBinder());
		
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Map<String, Object> map = spents.get(position);
		String description = (String) map.get("description");
		String message = "Gasto Selecionado: " + description;
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	private List<Map<String, Object>> listSpents(){
		spents = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("date", "03/03/2013");
		item.put("description", "Hotel");
		item.put("value", "R$ 41.00");
		item.put("category", R.color.categoria_hospedagem);
		spents.add(item);
		
		item = new HashMap<String, Object>();
		item.put("date", "03/03/2013");
		item.put("description", "Lanche");
		item.put("value", "R$ 30.00");
		item.put("category", R.color.categoria_alimentacao);
		spents.add(item);
		
		return spents;
	}
	
	private class SpentViewBinder implements ViewBinder{

		@Override
		public boolean setViewValue(View view, Object object, String textRepresentation) {
			if(view.getId() == R.id.date){
				if(!lastDate.equals(object)){
					TextView textView = (TextView) view;
					textView.setText(textRepresentation);
					lastDate = textRepresentation;
					view.setVisibility(View.VISIBLE);
				}
			
				return true;
			}
			
			if(view.getId() == R.id.category){
				Integer id = (Integer) object;
				view.setBackgroundColor(getResources().getColor(id));
			}
			
			return false;			
		}
	}
}
