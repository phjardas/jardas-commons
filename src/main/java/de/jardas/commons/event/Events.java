package de.jardas.commons.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

public class Events {
	private final Logger LOG = LoggerFactory.getLogger(Events.class);
	private final EventBus eventBus;

	public Events(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void register(final Object object) {
		LOG.debug("Registering event listener: {}", object);
		eventBus.register(object);
	}

	public void unregister(final Object object) {
		LOG.debug("Unregistering event listener: {}", object);
		eventBus.unregister(object);
	}

	public void post(final ApplicationEvent event) {
		LOG.debug("Dispatching event: {}", event);
		eventBus.post(event);
	}
}
