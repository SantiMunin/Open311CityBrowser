package org.codeforamerica.open311.util;

import java.util.Comparator;

import org.codeforamerica.open311.facade.City;

public class CityComparator implements Comparator<City> {

	@Override
	public int compare(City arg0, City arg1) {
		return arg0.getCityName().compareTo(arg1.getCityName());
	}

}
