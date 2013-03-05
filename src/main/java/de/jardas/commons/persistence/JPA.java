package de.jardas.commons.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public final class JPA {
	private JPA() {
		// utility class
	}

	@SafeVarargs
	public static Predicate like(final CriteriaBuilder cb, final String search, final Expression<String>... paths) {
		Predicate p = null;

		if (search != null && search.trim().length() > 0) {
			final Expression<String> like = like(search, cb);

			for (final Expression<String> path : paths) {
				p = or(cb, p, cb.like(cb.lower(path), like));
			}
		}

		return p;
	}

	public static Expression<String> like(final String search, final CriteriaBuilder cb) {
		return cb.lower(cb.literal('%' + search + '%'));
	}

	public static Predicate and(final CriteriaBuilder cb, final Predicate p1, final Predicate p2) {
		if (p1 == null) {
			return p2;
		}

		if (p2 == null) {
			return p1;
		}

		return cb.and(p1, p2);
	}

	public static Predicate or(final CriteriaBuilder cb, final Predicate p1, final Predicate p2) {
		if (p1 == null) {
			return p2;
		}

		if (p2 == null) {
			return p1;
		}

		return cb.or(p1, p2);
	}
}
