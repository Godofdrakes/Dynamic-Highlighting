package com.dynamicHighlighting.interfaces;

import com.dynamicHighlighting.ActorInfo;
import com.dynamicHighlighting.HighlightSubject;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public interface ITarget extends ITestOutput
{
	boolean evaluate( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger );
}
