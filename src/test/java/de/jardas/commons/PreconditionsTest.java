package de.jardas.commons;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Lists;

import de.jardas.commons.Preconditions.ContractViolationException;

public class PreconditionsTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void notNullNotNull() {
		final Object obj = new Object();
		assertThat(Preconditions.notNull(obj, "value"), sameInstance(obj));
	}

	@Test
	public void notNullNull() {
		expectNull();
		Preconditions.notNull(null, "value");
	}

	@Test
	public void notEmptyCollectionNotEmpty() {
		final Collection<Object> coll = Lists.newArrayList(new Object());
		assertThat(Preconditions.notEmpty(coll, "value"), sameInstance(coll));
	}

	@Test
	public void notEmptyCollectionEmpty() {
		thrown.expect(ContractViolationException.class);
		thrown.expectMessage(equalTo("value can not be empty"));
		Preconditions.notEmpty(Lists.newArrayList(), "value");
	}

	@Test
	public void notEmptyCollectionNull() {
		expectNull();
		Preconditions.notEmpty((Collection<?>) null, "value");
	}

	@Test
	public void notEmptyArrayNotEmpty() {
		final Object[] coll = { new Object() };
		assertThat(Preconditions.notEmpty(coll, "value"), sameInstance(coll));
	}

	@Test
	public void notEmptyArrayEmpty() {
		thrown.expect(ContractViolationException.class);
		thrown.expectMessage(equalTo("value can not be empty"));
		Preconditions.notEmpty(Lists.newArrayList(), "value");
	}

	@Test
	public void notEmptyArrayNull() {
		expectNull();
		Preconditions.notEmpty((Object[]) null, "value");
	}

	private void expectNull() {
		thrown.expect(ContractViolationException.class);
		thrown.expectMessage(equalTo("value can not be null"));
	}
}
