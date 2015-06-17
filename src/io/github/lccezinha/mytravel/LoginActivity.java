package io.github.lccezinha.mytravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// seta campos informados na view/xml
	private EditText username;
	private EditText password;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		// recupera a referência para os campos da view/xml
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		
		// obtem os textos informados nos campos
		String usernameInformed = username.getText().toString();
		String passwordInformed = password.getText().toString();
				
		if(usernameInformed.equals("usuario") && passwordInformed.equals("123123")){
			startDashboard();
		}else{
			showErrorMessage();
		}
	}
	
	public void login(View v) {
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
