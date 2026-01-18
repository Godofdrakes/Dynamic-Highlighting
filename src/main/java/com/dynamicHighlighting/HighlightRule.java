package com.dynamicHighlighting;

import com.dynamicHighlighting.interfaces.ICondition;
import com.dynamicHighlighting.interfaces.IEffect;
import com.dynamicHighlighting.interfaces.ITarget;
import com.dynamicHighlighting.interfaces.ITestOutput;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class HighlightRule implements ITestOutput
{
	public final List<ITarget> targets = new ArrayList<>();
	public final List<ICondition> conditions = new ArrayList<>();
	public final List<IEffect> effects = new ArrayList<>();

	public boolean evaluate( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger )
	{
		if ( !evaluateTargets( actorInfo, logger ) )
		{
			logger.info( "not a valid target for this rule" );

			return false;
		}

		if ( !evaluateConditions( actorInfo, logger ) )
		{
			logger.info( "rule condition(s) not met" );

			return false;
		}

		return true;
	}

	public void execute( @Nonnull HighlightOutput output, @Nonnull Logger logger )
	{
		for ( int i = 0, count = effects.size(); i < count; i++ )
		{
			var effect = effects.get( i );

			logger.debug( "executing effect {} of {}", i + 1, count );

			effect.execute( output, logger );
		}
	}

	private boolean evaluateTargets( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger )
	{
		for ( int i = 0, count = targets.size(); i < count; i++ )
		{
			var target = targets.get( i );

			logger.debug( "evaluating target {} of {}", i + 1, count );

			if ( target.evaluate( actorInfo, logger ) )
			{
				return true;
			}
		}

		return false;
	}

	private boolean evaluateConditions( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger )
	{
		for ( int i = 0, count = conditions.size(); i < count; i++ )
		{
			var condition = conditions.get( i );

			logger.debug( "evaluating condition {} of {}", i + 1, count );

			if ( !condition.evaluate( actorInfo, logger ) )
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "{}", HighlightRule.class );

		logTargets( logger );
		logConditions( logger );
		logEffects( logger );
	}

	private void logTargets( @Nonnull Logger logger )
	{
		for ( int i = 0, count = targets.size(); i < count; i++ )
		{
			logger.info( "target {} of {}", i + 1, count );

			targets.get( i ).logState( logger );
		}
	}

	private void logConditions( @Nonnull Logger logger )
	{
		for ( int i = 0, count = conditions.size(); i < count; i++ )
		{
			logger.info( "condition {} of {}", i + 1, count );

			conditions.get( i ).logState( logger );
		}
	}

	private void logEffects( @Nonnull Logger logger )
	{
		for ( int i = 0, count = effects.size(); i < count; i++ )
		{
			logger.info( "effect {} of {}", i + 1, count );

			effects.get( i ).logState( logger );
		}
	}
}
