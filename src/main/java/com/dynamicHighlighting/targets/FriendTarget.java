package com.dynamicHighlighting.targets;

import com.dynamicHighlighting.ActorInfo;
import com.dynamicHighlighting.interfaces.ITarget;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

public final class FriendTarget implements ITarget
{
	public boolean evaluate( @Nonnull ActorInfo actorInfo, @Nonnull Logger logger )
	{
		final var playerInfo = actorInfo.getPlayerInfo();
		if ( playerInfo == null )
		{
			logger.debug( "not a player" );

			return false;
		}

		if ( !playerInfo.isFriend() )
		{
			logger.debug( "is not friend" );

			return false;
		}

		logger.info( "is friend" );

		return true;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "{}", FriendTarget.class );
	}
}
