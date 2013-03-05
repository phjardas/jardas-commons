package de.jardas.commons.init;

import org.springframework.core.Ordered;

public interface Initializer extends Ordered {
	void initialize();
}
