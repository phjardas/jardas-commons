package de.jardas.commons.persistence;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Predicate;

public class Predicates {
	private final AbstractQuery<?> query;
	private final List<Predicate> predicates = new LinkedList<Predicate>();

	public Predicates(final AbstractQuery<?> query) {
		this.query = query;
	}

	public void and(final Predicate added) {
		predicates.add(added);
	}

	public void apply() {
		if (!predicates.isEmpty()) {
			query.where(predicates.toArray(new Predicate[predicates.size()]));
		}
	}

	public AbstractQuery<?> getQuery() {
		return query;
	}
}
