package com.dynamicHighlighting.conditions;

import com.dynamicHighlighting.ActorInfo;
import com.dynamicHighlighting.Comparator;
import com.dynamicHighlighting.interfaces.ICondition;
import com.dynamicHighlighting.services.ClientService;
import com.google.inject.Inject;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public final class DistanceCondition implements ICondition
{
	@Nonnull
	private final ClientService clientService;

	public int Distance;

	public Comparator Comparator;

	@Inject
	public DistanceCondition( @Nonnull ClientService clientService )
	{
		this.clientService = clientService;
	}

	@Override
	public boolean evaluate( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger )
	{
		if ( Distance < 0 )
		{
			throw new RuntimeException( "Distance < 0" );
		}

		final var actorLocation = actorInfo.getWorldLocation();
		final var clientDistance = clientService.getClientDistance( actorLocation );

		if ( Comparator.compare( clientDistance, this.Distance ) )
		{
			logger.info( "actor distance is {} {}", Comparator, Distance );

			return true;
		}

		logger.debug( "actor distance is not {} {}", Comparator, Distance );

		return false;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "{}", DistanceCondition.class );
		logger.info( "distance: {}", Distance );
		logger.info( "comparator: {}", Comparator );
	}
}
