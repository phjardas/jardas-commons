package de.jardas.commons.event;

public interface Events {
	void post(ApplicationEvent event);

	<T> T register(T object);

	<T> T unregister(T object);
}
