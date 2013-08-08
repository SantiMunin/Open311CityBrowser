package org.codeforamerica.open311;

import java.util.LinkedList;
import java.util.List;

import org.codeforamerica.open311.facade.data.Service;
import org.codeforamerica.open311.facade.exceptions.APIWrapperException;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ServiceListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_list_activity);
		new FetchServiceList().execute();
	}

	private class FetchServiceList extends AsyncTask<Void, Void, List<Service>> {

		protected List<Service> doInBackground(Void... params) {
			try {
				return WrapperStore.getInstance().getWrapper().getServiceList();
			} catch (APIWrapperException e) {
				return new LinkedList<Service>();
			}
		}

		@Override
		protected void onPostExecute(List<Service> result) {
			super.onPostExecute(result);
			String[] data = new String[result.size()];
			int i = 0;
			for (Service serv : result) {
				data[i++] = serv.getServiceCode();
			}
			setListAdapter(new ArrayAdapter<String>(getBaseContext(),
					android.R.layout.simple_list_item_single_choice,
					android.R.id.text1,
					data));
		}

	}

}
