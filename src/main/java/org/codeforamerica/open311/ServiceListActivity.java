package org.codeforamerica.open311;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.codeforamerica.open311.facade.data.Service;
import org.codeforamerica.open311.facade.exceptions.APIWrapperException;
import org.codeforamerica.open311.util.ServiceComparator;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class ServiceListActivity extends ListActivity {

	private static final int CONTEXT_MENU_GET_DEFINITION = 0;
	private static final int CONTEXT_MENU_GET_SERVICE_REQUESTS = 1;
	private List<Service> services;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_list_activity);
		registerForContextMenu(getListView());
		new FetchServiceList().execute();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, CONTEXT_MENU_GET_DEFINITION, Menu.NONE,
				R.string.get_service_definition);
		menu.add(Menu.NONE, CONTEXT_MENU_GET_SERVICE_REQUESTS, Menu.NONE,
				R.string.get_service_requests);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Intent i;
		switch (item.getItemId()) {
		case CONTEXT_MENU_GET_DEFINITION:
			i = new Intent(this, ServiceDefinitionActivity.class);
			i.putExtra("service_code", services.get(info.position)
					.getServiceCode());
			startActivity(i);
			break;
		case CONTEXT_MENU_GET_SERVICE_REQUESTS:
			i = new Intent(this, ServiceRequestListActivity.class);
			i.putExtra("service_code", services.get(info.position)
					.getServiceCode());
			startActivity(i);
			break;
		}
		return false;

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
			services = result;
			String[] data = new String[result.size()];
			int i = 0;
			Collections.sort(result, new ServiceComparator());
			for (Service serv : result) {
				data[i++] = "[" + serv.getServiceCode() + "] "
						+ serv.getServiceName();
			}
			setListAdapter(new ArrayAdapter<String>(getBaseContext(),
					android.R.layout.simple_list_item_1, android.R.id.text1,
					data));
			getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					arg0.showContextMenuForChild(arg1);
				}
			});

		}
	}

}
