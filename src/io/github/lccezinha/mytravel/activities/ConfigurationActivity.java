package io.github.lccezinha.mytravel.activities;

import io.github.lccezinha.mytravel.R;
import io.github.lccezinha.mytravel.R.xml;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfigurationActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
