package com.dynamicHighlightingTesting;

import com.dynamicHighlighting.Comparator;
import org.junit.Test;

import static org.junit.Assert.*;

public final class ComparatorTests
{
	@Test
	public void isEqualTo()
	{
		final var comparator = Comparator.EqualTo;

		assertTrue( comparator.compare( 1, 1 ) );
		assertFalse( comparator.compare( 1, 0 ) );
		assertFalse( comparator.compare( 0, 1 ) );
		assertTrue( comparator.compare( 0, 0 ) );
	}

	@Test
	public void isNotEqualTo()
	{
		final var comparator = Comparator.NotEqualTo;

		assertFalse( comparator.compare( 1, 1 ) );
		assertTrue( comparator.compare( 1, 0 ) );
		assertTrue( comparator.compare( 0, 1 ) );
		assertFalse( comparator.compare( 0, 0 ) );
	}

	@Test
	public void isLessThan()
	{
		final var comparator = Comparator.LessThan;

		assertFalse( comparator.compare( 1, 1 ) );
		assertFalse( comparator.compare( 1, 0 ) );
		assertTrue( comparator.compare( 0, 1 ) );
		assertFalse( comparator.compare( 0, 0 ) );
	}

	@Test
	public void isLessThanOrEqualTo()
	{
		final var comparator = Comparator.LessThanOrEqualTo;

		assertTrue( comparator.compare( 1, 1 ) );
		assertFalse( comparator.compare( 1, 0 ) );
		assertTrue( comparator.compare( 0, 1 ) );
		assertTrue( comparator.compare( 0, 0 ) );
	}

	@Test
	public void isGreaterThan()
	{
		final var comparator = Comparator.GreaterThan;

		assertFalse( comparator.compare( 1, 1 ) );
		assertTrue( comparator.compare( 1, 0 ) );
		assertFalse( comparator.compare( 0, 1 ) );
		assertFalse( comparator.compare( 0, 0 ) );
	}

	@Test
	public void isGreaterThanOrEqualTo()
	{
		final var comparator = Comparator.GreaterThanOrEqualTo;

		assertTrue( comparator.compare( 1, 1 ) );
		assertTrue( comparator.compare( 1, 0 ) );
		assertFalse( comparator.compare( 0, 1 ) );
		assertTrue( comparator.compare( 0, 0 ) );
	}
}
