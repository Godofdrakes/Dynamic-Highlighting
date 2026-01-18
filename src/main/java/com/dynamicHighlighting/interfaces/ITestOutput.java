package com.dynamicHighlighting.interfaces;

import org.slf4j.Logger;

import javax.annotation.Nonnull;

public interface ITestOutput
{
	void logState( @Nonnull Logger logger );
}
