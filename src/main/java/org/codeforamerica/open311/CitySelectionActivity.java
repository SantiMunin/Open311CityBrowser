package org.codeforamerica.open311;

import org.codeforamerica.open311.facade.APIWrapper;
import org.codeforamerica.open311.facade.APIWrapperFactory;
import org.codeforamerica.open311.facade.City;
import org.codeforamerica.open311.facade.EndpointType;
import org.codeforamerica.open311.facade.exceptions.APIWrapperException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CitySelectionActivity extends Activity {

	// UI references.
	private EditText apiKeyView;
	private Spinner citySpinner;
	private Spinner endpointTypeSpinner;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private City[] cities = City.values();
	private int cityIndex = 0;
	private EndpointType[] types = EndpointType.values();
	private int endpointTypeIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_selection);

		citySpinner = (Spinner) findViewById(R.id.city_selection_spinner);
		endpointTypeSpinner = (Spinner) findViewById(R.id.type_selection_spinner);
		configureSpinners();
		apiKeyView = (EditText) findViewById(R.id.apikey_view);

		mLoginStatusView = findViewById(R.id.wrapper_creation_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.wrapper_creation_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	private void configureSpinners() {
		String[] values = new String[cities.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = cities[i].getCityName();
		}
		AndroidUtils.fillSpinner(this, citySpinner, values);
		citySpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long l) {
						cityIndex = position;
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						cityIndex = -1;
					}
				});

		values = new String[types.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = types[i].toString();
		}
		AndroidUtils.fillSpinner(this, endpointTypeSpinner, values);
		endpointTypeSpinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> adapterView,
							View view, int position, long l) {
						endpointTypeIndex = position;
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						endpointTypeIndex = -1;
					}
				});
	}

	public void attemptLogin() {
		new CreateWrapperTask().execute();
	}

	private class CreateWrapperTask extends AsyncTask<Void, Void, APIWrapper> {

		@Override
		protected APIWrapper doInBackground(Void... voids) {
			try {
				String apiKey = apiKeyView.getText().toString();
				return new APIWrapperFactory(cities[cityIndex],
						types[endpointTypeIndex]).setApiKey(apiKey).withLogs()
						.build();
			} catch (APIWrapperException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(APIWrapper wrapper) {
			super.onPostExecute(wrapper);
			if (wrapper == null) {
				Toast.makeText(getBaseContext(),
						"Couldn't instantiate the wrapper", Toast.LENGTH_SHORT)
						.show();
			} else {
				WrapperStore.getInstance().storeWrapper(wrapper);
				startActivity(new Intent(getBaseContext(),
						ServiceListActivity.class));
			}
		}
	}
}
