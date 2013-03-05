package de.jardas.commons.init;

import java.util.Comparator;

import org.springframework.core.Ordered;

public class OrderedComparator implements Comparator<Ordered> {
	@Override
	public int compare(final Ordered o1, final Ordered o2) {
		return o2.getOrder() - o1.getOrder();
	}
}
