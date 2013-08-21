package org.codeforamerica.open311;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.codeforamerica.open311.facade.data.ServiceRequest;
import org.codeforamerica.open311.facade.data.operations.GETServiceRequestsFilter;
import org.codeforamerica.open311.facade.exceptions.APIWrapperException;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ServiceRequestListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_list_activity);
		((TextView) findViewById(android.R.id.empty))
				.setText(getString(R.string.loading));
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			new FetchServiceRequests()
					.execute(extras.getString("service_code"));
		}
	}

	private class FetchServiceRequests extends
			AsyncTask<String, Void, List<ServiceRequest>> {

		protected List<ServiceRequest> doInBackground(String... params) {
			try {
				return WrapperStore
						.getInstance()
						.getWrapper()
						.getServiceRequests(
								new GETServiceRequestsFilter()
										.setServiceCode(params[0]));
			} catch (APIWrapperException e) {
				return new LinkedList<ServiceRequest>();
			}
		}

		@Override
		protected void onPostExecute(List<ServiceRequest> result) {
			super.onPostExecute(result);
			((TextView) findViewById(android.R.id.empty))
					.setText(getString(R.string.empty));
			String[] data = new String[result.size()];
			int i = 0;
			for (ServiceRequest serv : result) {
				data[i++] = serv.getServiceRequestId() + " "
						+ serv.getDescription();
			}
			Arrays.sort(data);
			setListAdapter(new ArrayAdapter<String>(getBaseContext(),
					android.R.layout.simple_list_item_1, android.R.id.text1,
					data));
		}

	}

}
