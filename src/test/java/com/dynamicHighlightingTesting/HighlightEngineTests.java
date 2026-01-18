package com.dynamicHighlightingTesting;

import com.dynamicHighlighting.*;
import com.dynamicHighlighting.effects.OverheadNameEffect;
import com.dynamicHighlighting.services.ClientService;
import com.dynamicHighlighting.targets.FriendTarget;
import com.dynamicHighlighting.targets.RemotePlayerTarget;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.NpcID;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static org.junit.Assert.*;

public final class HighlightEngineTests
{
	private final Logger logger = LoggerFactory.getLogger( HighlightEngineTests.class );

	private final ClientService clientService = new ClientService();

	@Before
	public void beforeTest()
	{
		clientService.setLocalPlayerInfo( CreateLocalPlayer() );
	}

	@Test
	public void testPlayerNameColor()
	{
		final var engine = new HighlightEngine();

		{
			final var rule = new HighlightRule();

			{
				final var target = new RemotePlayerTarget( clientService );

				final var effect = new OverheadNameEffect();

				effect.color = Color.RED;

				rule.targets.add( target );
				rule.effects.add( effect );
			}

			engine.rules.add( rule );
		}

		{
			final var rule = new HighlightRule();

			{
				final var target = new FriendTarget();

				final var effect = new OverheadNameEffect();

				effect.color = Color.BLUE;

				rule.targets.add( target );
				rule.effects.add( effect );
			}

			engine.rules.add( rule );
		}

		{
			final var actorInfo = CreateFriend();

			final var output = new HighlightOutput();

			engine.logState( this.logger );

			logger.info( "" );

			engine.evaluate( this.logger, actorInfo, output );

			logger.info( "" );

			output.logState( this.logger );

			assertEquals( Color.BLUE, output.OverheadTextColor );
			assertNull( output.OutlineColor );
		}

		{
			final var actorInfo = CreateRemotePlayer();

			final var output = new HighlightOutput();

			engine.logState( this.logger );

			logger.info( "" );

			engine.evaluate( this.logger, actorInfo, output );

			logger.info( "" );

			output.logState( this.logger );

			assertEquals( Color.RED, output.OverheadTextColor );
			assertNull( output.OutlineColor );
		}
	}

	private static PlayerInfo CreateLocalPlayer()
	{
		final var player = new PlayerInfo();

		player.setPlayerId( 1 );
		player.setName( "You" );
		player.setWorldLocation( new WorldPoint( 0, 0, 0 ) );

		return player;
	}

	private static PlayerInfo CreateFriend()
	{
		final var player = new PlayerInfo();

		player.setPlayerId( 31337 );
		player.setName( "Cow31337Killer" );
		player.setFriend( true );
		player.setWorldLocation( new WorldPoint( 10, 10, 0 ) );

		return player;
	}

	private static PlayerInfo CreateRemotePlayer()
	{
		final var player = new PlayerInfo();

		player.setPlayerId( 321 );
		player.setName( "Durial321" );
		player.setWorldLocation( new WorldPoint( -5, -5, 0 ) );

		return player;
	}

	private static NpcInfo CreateCow()
	{
		final var npc = new NpcInfo();

		npc.setNpcId( NpcID.COW );
		npc.setName( "Cow" );
		npc.setWorldLocation( new WorldPoint( 5, 5, 0 ) );

		return npc;
	}
}
