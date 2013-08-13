package org.codeforamerica.open311;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ServiceDefinitionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_definition);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.service_definition, menu);
		return true;
	}

}
