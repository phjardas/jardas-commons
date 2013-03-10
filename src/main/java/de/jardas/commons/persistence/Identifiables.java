package de.jardas.commons.persistence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

public final class Identifiables {
	private Identifiables() {
		// utility class
	}

	/**
	 * Konvertiert eine Menge von {@link Identifiable Identifiables} in eine Menge gleicher Größe mit ihren IDs. Das
	 * Iterationsverhalten der IDs ist identisch zu demjenigen der Entitäten.
	 * <p>
	 * <strong>ACHTUNG:</strong> die zurückgegebene Liste ist lazy, liest die IDs der Entitäten also erst beim
	 * tatsächlichen Zugriff aus. Um eine wirkliche Kopie zu erhalten muss die Collection noch kopiert werden:
	 * <p>
	 * <code>Lists.newArrayList(Collections.ids(...))</code>
	 */
	public static <I extends Serializable> Collection<I> ids(final Collection<? extends Identifiable<I>> entities) {
		final Function<Identifiable<I>, I> idFunction = toIdFunction();
		return Collections2.transform(entities, idFunction);
	}

	public static <I extends Serializable> List<I> ids(final List<? extends Identifiable<I>> entities) {
		final Function<Identifiable<I>, I> idFunction = toIdFunction();
		return Lists.transform(entities, idFunction);
	}

	@SafeVarargs
	public static <I extends Serializable> Collection<I> ids(final Identifiable<I>... entities) {
		return ids(Arrays.asList(entities));
	}

	private static <I extends Serializable> Function<Identifiable<I>, I> toIdFunction() {
		return new Function<Identifiable<I>, I>() {
			@Override
			public I apply(final Identifiable<I> entity) {
				return entity != null ? entity.getId() : null;
			}
		};
	}

	/**
	 * Führt eine Identitätsbetrachtung von zwei Entitäten anhand ihrer ID durch. Die zwei Objekte werden dann als
	 * gleich betrachtet, wenn sie vom gleichen Typ sind und die gleiche ID haben.
	 */
	public static <I extends Serializable, E extends Identifiable<I>> boolean equal(final E me,
			final Class<? super E> type, final Object obj) {
		if (obj == me) {
			return true;
		}

		if (!type.isInstance(obj)) {
			return false;
		}

		final I myId = me.getId();
		final Object objId = ((Identifiable<?>) obj).getId();

		return Objects.equal(myId, objId);
	}

	/**
	 * Ermittelt den Hashcode einer Entität anhand ihrer ID. Der Hash-Code von zwei Entitäten mit der gleichen ID ist
	 * identisch, unabhängig aller anderer Attribute.
	 */
	public static <I extends Serializable> int hashCode(final Identifiable<I> obj) {
		final I id = obj.getId();

		return id != null ? id.hashCode() : 0;
	}

	/**
	 * Erzeugt einen {@link ToStringHelper}, in dem die ID der Entität bereits enthalten ist.
	 */
	public static <I extends Serializable> ToStringHelper toStringHelper(final Identifiable<I> obj) {
		final String name = obj.getClass().getSimpleName() + "#" + obj.getId();

		return Objects.toStringHelper(name);
	}
}
