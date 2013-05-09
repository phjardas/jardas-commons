package de.jardas.commons.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

public class EventsImpl implements Events {
	private final Logger LOG = LoggerFactory.getLogger(EventsImpl.class);
	private final EventBus eventBus;

	public EventsImpl(final EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public <T> T register(final T object) {
		LOG.debug("Registering event listener: {}", object);
		eventBus.register(object);
		return object;
	}

	@Override
	public <T> T unregister(final T object) {
		LOG.debug("Unregistering event listener: {}", object);
		eventBus.unregister(object);
		return object;
	}

	@Override
	public void post(final ApplicationEvent event) {
		LOG.debug("Dispatching event: {}", event);
		eventBus.post(event);
	}
}
