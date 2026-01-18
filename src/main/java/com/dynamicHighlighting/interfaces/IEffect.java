package com.dynamicHighlighting.interfaces;

import com.dynamicHighlighting.HighlightOutput;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public interface IEffect extends ITestOutput
{
	void execute( @Nonnull HighlightOutput output, @Nonnull Logger logger );
}
