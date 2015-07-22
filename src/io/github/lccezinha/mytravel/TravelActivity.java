package io.github.lccezinha.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TravelActivity extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_travel);
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
		case R.id.new_spent:
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
