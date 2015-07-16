package io.github.lccezinha.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.dashboard, menu);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		finish();
		
		return true;
	}
	
}
