package de.jardas.commons.persistence;

import java.io.Serializable;

public interface Identifiable<I extends Serializable> {
	I getId();
}
