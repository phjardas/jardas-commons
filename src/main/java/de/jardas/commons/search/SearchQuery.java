package de.jardas.commons.search;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

import de.jardas.commons.search.SearchQuery.Order.Direction;

public abstract class SearchQuery<E> implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Class<E> entityType;
	private int pageIndex;
	private int itemsPerPage = 20;
	private List<Order> orders = new LinkedList<Order>();

	public SearchQuery(final Class<E> entityType) {
		this.entityType = entityType;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(final int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(final int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public Class<E> getEntityType() {
		return entityType;
	}

	public int getOffset() {
		return pageIndex * itemsPerPage;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void addOrder(final String column, final Direction direction) {
		orders.add(new Order(column, direction));
	}

	public void setOrders(final List<Order> orders) {
		this.orders = orders;
	}

	public void setOrdersSerialized(final String serialized) {
		orders.clear();
		final String[] tokens = serialized.split(",");

		for (final String token : tokens) {
			orders.add(Order.deserialize(token));
		}
	}

	public String getOrdersSerialized() {
		final StringBuilder ret = new StringBuilder();

		for (final Order order : orders) {
			if (ret.length() > 0) {
				ret.append(",");
			}

			ret.append(order.serialize());
		}

		return ret.toString();
	}

	@Override
	public String toString() {
		final ToStringHelper helper = Objects.toStringHelper(this);
		toStringInternal(helper);

		return helper.add("pageIndex", pageIndex).add("itemsPerPage", itemsPerPage)
				.add("orders", getOrdersSerialized()).toString();
	}

	protected abstract void toStringInternal(ToStringHelper helper);

	public static final class Order {
		public static enum Direction {
			asc, desc,
		}

		private final String column;
		private final Direction direction;

		@JsonCreator
		public Order(@JsonProperty("column") final String column, @JsonProperty("direction") final Direction direction) {
			this.column = column;
			this.direction = direction;
		}

		public String getColumn() {
			return column;
		}

		public Direction getDirection() {
			return direction;
		}

		private String serialize() {
			return column + "|" + direction;
		}

		private static Order deserialize(final String serialized) {
			final String[] tokens = serialized.split("\\|");

			return new Order(tokens[0], Direction.valueOf(tokens[1]));
		}

		@Override
		public String toString() {
			return serialize();
		}
	}
}
