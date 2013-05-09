package de.jardas.commons.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

@Configuration
@Profile("!test")
public class EventConfig {
	@Bean
	public Events events() {
		final ThreadFactory threadFactory = new CustomizableThreadFactory("events-");
		final ExecutorService executor = Executors.newCachedThreadPool(threadFactory);
		final EventBus eventBus = new AsyncEventBus(executor);

		return new EventsImpl(eventBus);
	}
}
