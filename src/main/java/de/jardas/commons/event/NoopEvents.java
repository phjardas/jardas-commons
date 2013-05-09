package de.jardas.commons.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoopEvents implements Events {
	private final Logger LOG = LoggerFactory.getLogger(NoopEvents.class);

	@Override
	public <T> T register(final T object) {
		LOG.debug("Ignoring registration of event listener: {}", object);
		return object;
	}

	@Override
	public <T> T unregister(final T object) {
		LOG.debug("Ignoring unregistration of event listener: {}", object);
		return object;
	}

	@Override
	public void post(final ApplicationEvent event) {
		LOG.debug("Ignoring event: {}", event);
	}

	public static Events defaultEvents(final Events events) {
		if (events != null) {
			return events;
		}

		return new NoopEvents();
	}
}
