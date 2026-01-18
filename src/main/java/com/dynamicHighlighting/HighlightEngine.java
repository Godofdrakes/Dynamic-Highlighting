package com.dynamicHighlighting;

import com.dynamicHighlighting.interfaces.ITestOutput;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class HighlightEngine implements ITestOutput
{
	public final List<HighlightRule> rules = new ArrayList<>();

	public boolean evaluate(
		@Nonnull Logger logger,
		@Nonnull ActorInfo actorInfo,
		@Nonnull HighlightOutput output )
	{
		boolean anyExecute = false;

		for ( int i = 0, count = rules.size(); i < count; i++ )
		{
			var rule = rules.get( i );

			logger.info( "evaluating rule {} of {}", i + 1, count );

			if ( rule.evaluate( actorInfo, logger ) )
			{
				logger.info( "executing rule {}", i + 1 );

				rule.execute( output, logger );

				anyExecute = true;
			}
		}

		return anyExecute;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "{}", HighlightEngine.class );

		for ( int i = 0, count = rules.size(); i < count; i++ )
		{
			logger.info( "rule {} of {}", i + 1, count );

			rules.get( i ).logState( logger );
		}
	}
}
