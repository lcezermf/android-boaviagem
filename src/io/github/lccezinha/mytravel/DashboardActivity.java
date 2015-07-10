package io.github.lccezinha.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
}
