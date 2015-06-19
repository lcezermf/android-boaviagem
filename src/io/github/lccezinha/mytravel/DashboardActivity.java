package io.github.lccezinha.mytravel;

import android.app.Activity;
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
		TextView textView = (TextView) view;
		String option = "Opção: " + textView.getText().toString();
		Toast.makeText(this, option, Toast.LENGTH_LONG).show();
	}
	
}
