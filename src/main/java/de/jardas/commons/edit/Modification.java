package de.jardas.commons.edit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.Objects;

import de.jardas.commons.persistence.Identifiable;

@Entity
public class Modification implements Serializable, Identifiable<String> {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private final String path;
	private final String oldValue;
	private final String newValue;

	@SuppressWarnings("unused")
	private Modification() {
		path = null;
		oldValue = null;
		newValue = null;
	}

	public Modification(final String path, final String oldValue, final String newValue) {
		this.path = path;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public String getOldValue() {
		return oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("id", id).add("path", path).add("old", oldValue).add("new", newValue)
				.toString();
	}
}
