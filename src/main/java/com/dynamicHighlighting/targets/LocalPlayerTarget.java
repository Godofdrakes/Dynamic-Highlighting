package com.dynamicHighlighting.targets;

import com.dynamicHighlighting.ActorInfo;
import com.dynamicHighlighting.interfaces.ITarget;
import com.dynamicHighlighting.services.ClientService;
import com.google.inject.Inject;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public final class LocalPlayerTarget implements ITarget
{
	@Nonnull
	private final ClientService clientService;

	@Inject
	public LocalPlayerTarget( @Nonnull ClientService clientService )
	{
		this.clientService = clientService;
	}

	@Override
	public boolean evaluate( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger )
	{
		final var playerInfo = actorInfo.getPlayerInfo();
		if ( playerInfo == null )
		{
			logger.debug( "not a player" );
			return false;
		}

		if ( !clientService.isLocalPlayer( playerInfo ) )
		{
			logger.debug( "not local player" );
			return false;
		}

		logger.debug( "is local player" );
		return true;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{

	}
}
