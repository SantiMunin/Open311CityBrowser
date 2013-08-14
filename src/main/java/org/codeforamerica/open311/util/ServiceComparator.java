package org.codeforamerica.open311.util;

import java.util.Comparator;

import org.codeforamerica.open311.facade.data.Service;

public class ServiceComparator implements Comparator<Service> {

	@Override
	public int compare(Service lhs, Service rhs) {
		try {
			int i1 = Integer.valueOf(lhs.getServiceCode());
			int i2 = Integer.valueOf(rhs.getServiceCode());
			return i1 - i2;
		} catch (NumberFormatException nfe) {
			return lhs.getServiceCode().compareTo(rhs.getServiceCode());
		}

	}

}
