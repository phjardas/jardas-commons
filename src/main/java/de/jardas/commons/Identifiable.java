package de.jardas.commons;

import java.io.Serializable;

public interface Identifiable<I extends Serializable> {
	I getId();
}
