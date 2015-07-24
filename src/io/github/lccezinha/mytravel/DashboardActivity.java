package io.github.lccezinha.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class DashboardActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
	}
	
	public void selectOption(View view){
		
		switch(view.getId()){
		
		case R.id.new_travel:
			startActivity(new Intent(this, TravelActivity.class));
			break;
			
		case R.id.new_spent:
			startActivity(new Intent(this, SpentActivity.class));
			break;
			
		case R.id.my_travels:
			startActivity(new Intent(this, TravelListActivity.class));
			break;
			
		case R.id.configurations:
			startActivity(new Intent(this, ConfigurationActivity.class));
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.dashboard_menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		finish();
		
		return true;
	}
	
}
