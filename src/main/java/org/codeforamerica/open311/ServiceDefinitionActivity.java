package org.codeforamerica.open311;

import org.codeforamerica.open311.facade.exceptions.APIWrapperException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceDefinitionActivity extends Activity {

	String serviceCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_screen);
		((TextView) findViewById(R.id.loading_message))
				.setText(getString(R.string.service_definition_loading));
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
			setContentView(R.layout.activity_service_definition);
			if (result != null) {
				((TextView) findViewById(R.id.serviceCodeValue)).setText(result
						.getServiceCode());
				ListView list = (ListView) findViewById(R.id.attribute_list);
				String[] values = new String[result.getAttributes().size()];
				for (int i = 0; i < values.length; i++) {
					values[i] = result.getAttributes().get(i).getCode();
				}
				list.setAdapter(new ArrayAdapter<String>(getBaseContext(),
						R.layout.attribute_text_view, values));
			} else {
				Toast.makeText(getBaseContext(),
						"This service doesn't have a definition",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}

	}

}
