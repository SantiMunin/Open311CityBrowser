package org.codeforamerica.open311;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by santi on 07/08/2013.
 */
public class AndroidUtils {

	public static void fillSpinner(Context ctx, Spinner spinner, String[] data) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_spinner_item, data);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
}
