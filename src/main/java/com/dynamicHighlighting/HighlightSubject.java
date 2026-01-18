package com.dynamicHighlighting;

import com.dynamicHighlighting.interfaces.ITestOutput;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public class HighlightSubject implements ITestOutput
{
	public int Distance;

	public int PlayerId;

	public boolean IsGroup;
	public boolean IsFriend;
	public boolean IsClan;

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "Distance : {}", this.Distance );
		logger.info( "PlayerId : {}", this.PlayerId );
		logger.info( "IsGroup  : {}", this.IsGroup );
		logger.info( "IsFriend : {}", this.IsFriend );
		logger.info( "IsClan   : {}", this.IsClan );
	}
}
