package io.github.lccezinha.mytravel.activities;

import io.github.lccezinha.mytravel.R;
import io.github.lccezinha.mytravel.database.DataBaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class SpentListActivity extends ListActivity implements
		OnItemClickListener {

	private List<Map<String, Object>> spents;
	private String lastDate = "";
	private DataBaseHelper dataBaseHelper;
	private SimpleDateFormat dateFormat;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		String[] from = { "date", "description", "value", "category" };
		int[] to = { R.id.date, R.id.description, R.id.value, R.id.category };
		
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		SimpleAdapter adapter = new SimpleAdapter(this, listSpents(), R.layout.list_spent, from, to);
		adapter.setViewBinder(new SpentViewBinder());
		
		setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
		
		registerForContextMenu(getListView());
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Map<String, Object> map = spents.get(position);
		String description = (String) map.get("description");
		String message = "Gasto Selecionado: " + description;
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	private List<Map<String, Object>> listSpents(){
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT _id, travel_id, category, " +
				"date, description, value, place FROM spents", null);
		
		cursor.moveToNext();
		
		spents = new ArrayList<Map<String, Object>>();
		
		for(int i = 0; i > cursor.getCount(); i++){
			Map<String, Object> item = new HashMap<String, Object>();
			
			String id = cursor.getString(0);
			String travel_id = cursor.getString(1);
			String category = cursor.getString(2);
			long date = cursor.getLong(3);
			String description = cursor.getString(4);
			String value = cursor.getString(5);
			String place = cursor.getString(6);
			
			item.put("id", id);
			item.put("travel_id", travel_id);
			item.put("category",  category);
			
			Date spentDate = new Date(date);
			String formatedDate = dateFormat.format(spentDate);
			
			item.put("date", formatedDate);
			item.put("description", description);
			item.put("value", value);
			item.put("place", place);
			
			spents.add(item);
		}
		
		cursor.close();
		dataBaseHelper.close();
		
		return spents;
	}
	
	private class SpentViewBinder implements ViewBinder{

		@Override
		public boolean setViewValue(View view, Object date, String textRepresentation) {
			if(view.getId() == R.id.date){
				if(!lastDate.equals(date)){
					TextView textView = (TextView) view;
					textView.setText(textRepresentation);
					lastDate = textRepresentation;
					view.setVisibility(View.VISIBLE);
				}else{
					view.setVisibility(View.GONE);
				}			
				return true;
			}
			
			if(view.getId() == R.id.category){
				Integer id = (Integer) date;
				view.setBackgroundColor(getResources().getColor(id));
				return true;
			}
			
			return false;			
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.spent_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.remove) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			spents.remove(info.position);
			getListView().invalidateViews();
			lastDate = ""; 
			// remover do banco de dados
			return true;
		}
		return super.onContextItemSelected(item);
	}
}
