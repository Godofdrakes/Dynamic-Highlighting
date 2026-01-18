package com.dynamicHighlighting.interfaces;

import com.dynamicHighlighting.ActorInfo;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public interface ICondition extends ITestOutput
{
	boolean evaluate( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger );
}
