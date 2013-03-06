package de.jardas.commons.spring;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.core.Ordered;

public final class OrderedComparator implements Comparator<Ordered> {
	private static final OrderedComparator INTANCE = new OrderedComparator();

	private OrderedComparator() {
	}

	@Override
	public int compare(final Ordered o1, final Ordered o2) {
		return o2.getOrder() - o1.getOrder();
	}

	public static OrderedComparator getIntance() {
		return INTANCE;
	}

	public static void sort(final List<? extends Ordered> list) {
		Collections.sort(list, getIntance());
	}
}
