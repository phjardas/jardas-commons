package de.jardas.commons.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

@Configuration
@Profile("!test")
public class EventConfig {
	@Bean
	public Events events() {
		final ExecutorService executor = Executors.newCachedThreadPool();
		final EventBus eventBus = new AsyncEventBus(executor);

		return new Events(eventBus);
	}
}
