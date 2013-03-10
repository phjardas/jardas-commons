package de.jardas.commons.search;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import de.jardas.commons.search.SearchQuery.Order.Direction;

@Repository
public abstract class SearchServiceSupport<E, Q extends SearchQuery<E>> implements SearchService<E, Q> {
	private static final Logger LOG = LoggerFactory.getLogger(SearchServiceSupport.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public SearchResult<E> search(final Q search) {
		final int totalCount = getTotalResultsCount(search);
		final List<E> pageItems = getPageItems(search);

		return createSearchResult(search, totalCount, pageItems);
	}

	protected SearchResult<E> createSearchResult(final Q search, final int totalCount, final List<E> pageItems) {
		return new SearchResult<E>(search, pageItems, totalCount);
	}

	protected List<E> getPageItems(final Q search) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<E> crit = cb.createQuery(search.getEntityType());
		final Root<E> root = crit.from(search.getEntityType());
		applySearchQuery(crit, root, search, cb, false);
		applyOrder(crit, root, search, cb);

		final TypedQuery<E> query = entityManager.createQuery(crit);
		query.setFirstResult(search.getOffset());

		if (search.getItemsPerPage() > 0) {
			query.setMaxResults(search.getItemsPerPage());
		}

		return query.getResultList();
	}

	private void applyOrder(final CriteriaQuery<?> crit, final Root<E> root, final Q search, final CriteriaBuilder cb) {
		final List<Order> orderBy = new LinkedList<Order>();

		for (final SearchQuery.Order order : search.getOrders()) {
			final Path<?> sortPath = getOrderPath(crit, root, order.getColumn());
			orderBy.add(order.getDirection() == Direction.asc ? cb.asc(sortPath) : cb.desc(sortPath));
		}

		crit.orderBy(orderBy);
	}

	protected Path<?> getOrderPath(final CriteriaQuery<?> crit, final Root<E> root, final String column) {
		Path<?> node = root;
		final String[] path = column.split("\\.");

		for (final String property : path) {
			node = node.get(property);
		}

		return node;
	}

	private int getTotalResultsCount(final Q search) {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		final CriteriaQuery<Long> crit = cb.createQuery(Long.class);
		final Root<E> root = crit.from(search.getEntityType());
		applySearchQuery(crit, root, search, cb, true);
		crit.select(cb.count(getCountExpression(root)));

		final int count = entityManager.createQuery(crit).getSingleResult().intValue();
		LOG.debug("Total count for {}: {}", search, count);

		return count;
	}

	protected Expression<?> getCountExpression(final Root<E> root) {
		return root;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	protected abstract void applySearchQuery(CriteriaQuery<?> crit, Root<E> root, final Q search,
			final CriteriaBuilder cb, boolean count);
}
