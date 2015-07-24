package io.github.lccezinha.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// seta campos informados na view/xml
	private static final String KEEP_CONNECTED = "keep_connected";
	private EditText username;
	private EditText password;
	private CheckBox keepConnected;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		// recupera a referência para os campos da view/xml
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		keepConnected = (CheckBox) findViewById(R.id.keepConnected);
		
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean connected = preferences.getBoolean(KEEP_CONNECTED, false);
		
		if(connected){
			startActivity(new Intent(this, DashboardActivity.class));
		}
	}
	
	public void login(View v) {
		// obtem os textos informados nos campos
		String usernameInformed = username.getText().toString();
		String passwordInformed = password.getText().toString();
						
		if(usernameInformed.equals("usuario") && passwordInformed.equals("123123")){
			keepLogged();			
			startDashboard();
		}else{
			showErrorMessage();
		}
	}
	
	private void keepLogged(){
		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean(KEEP_CONNECTED, keepConnected.isChecked());
		editor.commit();
	}
	
	private void startDashboard(){
		Intent intent = new Intent(this, DashboardActivity.class); 
		startActivity(intent);
	}
	
	private void showErrorMessage(){
		String errorMessage = getString(R.string.auth_error);
		Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
