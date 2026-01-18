package com.dynamicHighlighting.services;

import com.dynamicHighlighting.ActorInfo;
import com.dynamicHighlighting.NpcInfo;
import com.dynamicHighlighting.PlayerInfo;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

// Handles bucketing of updates
public final class HighlightService
{
	private final HashMap<Integer,PlayerInfo> playerLookup = new HashMap<>();
	private final HashMap<Integer,NpcInfo> npcLookup = new HashMap<>();
	private final List<ActorInfo> actorInfoList = new ArrayList<>();

	private boolean isDirty = false;

	public Consumer<ActorInfo> actorUpdated;

	@Nonnull
	private final ClientService clientService;

	@Inject
	public HighlightService( @Nonnull ClientService clientService )
	{
		this.clientService = clientService;
	}

	public void update( int maxUpdates )
	{
		final var updateTime = Instant.now();

		if ( isDirty )
		{
			actorInfoList.sort( Comparator.comparingInt( this::getActorUpdatePriority ) );
		}

		final var updateMax = 10;

		var updateCount = 0;

		for ( final var actorInfo : actorInfoList )
		{
			var nextUpdateTime = actorInfo.getNextUpdateTime();

			if ( updateTime.isBefore( nextUpdateTime ) )
			{
				continue;
			}

			updateCount++;

			nextUpdateTime = updateTime.plus( getNextUpdateDelay( actorInfo ) );

			actorInfo.setNextUpdateTime( nextUpdateTime );

			actorUpdated.accept( actorInfo );

			if ( updateCount >= updateMax )
			{
				break;
			}
		}
	}

	private int getActorUpdatePriority( @Nonnull ActorInfo actorInfo )
	{
		final var playerInfo = actorInfo.getPlayerInfo();

		if ( playerInfo != null )
		{
			if ( clientService.isLocalPlayer( playerInfo ) )
			{
				// Always put the local player at the front
				return Integer.MIN_VALUE;
			}
		}

		return clientService.getClientDistance( actorInfo.getWorldLocation() );
	}

	private Duration getNextUpdateDelay( @Nonnull ActorInfo actorInfo )
	{
		final var playerInfo = actorInfo.getPlayerInfo();

		if ( playerInfo != null )
		{
			if ( clientService.isLocalPlayer( playerInfo ) )
			{
				// Update the player every frame
				return Duration.ZERO;
			}
		}

		return Duration.ofMillis( 60 );
	}

	public void addPlayer( @Nonnull Player player )
	{
		final var playerInfo = PlayerInfo.create( player );

		actorInfoList.add( playerInfo );
		playerLookup.put( player.getId(), playerInfo );

		playerInfo.setWorldLocation( player.getWorldLocation() );

		isDirty = true;
	}

	public void addNpc( @Nonnull NPC npc )
	{
		final var npcInfo = NpcInfo.create( npc );

		actorInfoList.add( npcInfo );
		npcLookup.put( npc.getId(), npcInfo );

		npcInfo.setWorldLocation( npc.getWorldLocation() );

		isDirty = true;
	}

	public void refreshPlayer( @Nonnull Player player )
	{
		final var playerInfo = playerLookup.get( player.getId() );

		if ( playerInfo == null )
		{
			return;
		}

		playerInfo.setWorldLocation( player.getWorldLocation() );

		// todo

		isDirty = true;
	}

	public void refreshNpc( @Nonnull NPC npc )
	{
		final var npcInfo = npcLookup.get( npc.getId() );

		if ( npcInfo == null )
		{
			return;
		}

		npcInfo.setWorldLocation( npc.getWorldLocation() );

		// todo

		isDirty = true;
	}

	public void removePlayer( @Nonnull Player player )
	{
		final var playerInfo = playerLookup.get( player.getId() );

		if ( playerInfo != null )
		{
			actorInfoList.remove( playerInfo );

			isDirty = true;
		}
	}

	public void removeNpc( @Nonnull NPC npc )
	{
		final var npcInfo = npcLookup.remove( npc.getId() );

		if ( npcInfo != null )
		{
			actorInfoList.remove( npcInfo );

			isDirty = true;
		}
	}
}
