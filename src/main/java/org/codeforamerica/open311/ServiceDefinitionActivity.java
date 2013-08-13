package org.codeforamerica.open311;

import org.codeforamerica.open311.facade.exceptions.APIWrapperException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceDefinitionActivity extends Activity {

	String serviceCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_definition);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			serviceCode = extras.getString("service_code");
			new GetServiceDefinitionTask().execute();
		}
	}

	private class GetServiceDefinitionTask
			extends
			AsyncTask<Void, Void, org.codeforamerica.open311.facade.data.ServiceDefinition> {

		@Override
		protected org.codeforamerica.open311.facade.data.ServiceDefinition doInBackground(
				Void... arg0) {
			try {
				return WrapperStore.getInstance().getWrapper()
						.getServiceDefinition(serviceCode);
			} catch (APIWrapperException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(
				org.codeforamerica.open311.facade.data.ServiceDefinition result) {
			super.onPostExecute(result);

			if (result != null) {
				((TextView) findViewById(R.id.serviceCodeValue)).setText(result
						.getServiceCode());
			} else {
				Toast.makeText(getBaseContext(),
						"This service doesn't have a definition",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

	}

}
