package de.jardas.commons.init;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.collect.Ordering;

import de.jardas.commons.spring.OrderedComparator;

public class Initialization implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired(required = false)
	private final List<Initializer> initializers = new LinkedList<Initializer>();

	private void initialize() {
		final List<Initializer> orderedInitializers = Ordering.from(OrderedComparator.getIntance()).sortedCopy(
				initializers);

		for (final Initializer initializer : orderedInitializers) {
			initializer.initialize();
		}
	}

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		final ApplicationContext context = event.getApplicationContext();
		final ApplicationContext parent = context.getParent();

		if (parent == null) {
			initialize();
		}
	}
}
