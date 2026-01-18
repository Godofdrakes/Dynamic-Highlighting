package com.dynamicHighlighting;

public enum Comparator
{
	EqualTo,
	NotEqualTo,
	LessThan,
	LessThanOrEqualTo,
	GreaterThan,
	GreaterThanOrEqualTo;

	public final <T extends Comparable<? super T>> boolean compare( T lhs, T rhs )
	{
		final var result = lhs.compareTo( rhs );

		switch (this)
		{
			case EqualTo:
				return result == 0;
			case NotEqualTo:
				return result != 0;
			case LessThan:
				return result < 0;
			case LessThanOrEqualTo:
				return result <= 0;
			case GreaterThan:
				return result > 0;
			case GreaterThanOrEqualTo:
				return result >= 0;
			default:
				throw new IndexOutOfBoundsException();
		}
	}
}
