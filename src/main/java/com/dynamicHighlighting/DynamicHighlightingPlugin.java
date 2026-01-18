package com.dynamicHighlighting;

import com.dynamicHighlighting.effects.OutlineEffect;
import com.dynamicHighlighting.services.ClientService;
import com.dynamicHighlighting.services.HighlightService;
import com.dynamicHighlighting.targets.LocalPlayerTarget;
import com.google.inject.Provides;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.slayer.SlayerPlugin;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Dynamic Highlighting",
	description = "A highly configurable plugin to enable highlighting players and NPCs based on various conditions",
	tags = {"highlight", "player", "npc"}
)
@PluginDependency(SlayerPlugin.class)
public class DynamicHighlightingPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private DynamicHighlightingConfig config;

	@Inject
	private HighlightService highlightService;

	@Inject
	private HighlightEngine highlightEngine;

	@Inject
	private DynamicHighlightingOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ClientService clientService;

	@Override
	protected void startUp() throws Exception
	{
		log.debug( "Example started!" );

		{
			final var rule = new HighlightRule();

			rule.targets.add( new LocalPlayerTarget( clientService ) );
			rule.effects.add( new OutlineEffect() );

			highlightEngine.rules.add( rule );
		}

		overlayManager.add( overlay );

		highlightService.actorUpdated = this::onActorUpdated;
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug( "Example stopped!" );

		overlayManager.remove( overlay );
	}

	private void onActorUpdated( @Nonnull ActorInfo actorInfo )
	{
		final var output = new HighlightOutput();

		log.debug( "updating {}", actorInfo );

		if ( highlightEngine.evaluate( log, actorInfo, output ) )
		{
			final var playerInfo = actorInfo.getPlayerInfo();

			if ( playerInfo != null )
			{
				overlay.put( playerInfo, output );
				return;
			}

			final var npcInfo = actorInfo.getNpcInfo();

			if ( npcInfo != null )
			{
				overlay.put( npcInfo, output );
				return;
			}
		}
		else
		{
			final var playerInfo = actorInfo.getPlayerInfo();

			if ( playerInfo != null )
			{
				overlay.remove( playerInfo );
				return;
			}

			final var npcInfo = actorInfo.getNpcInfo();

			if ( npcInfo != null )
			{
				overlay.remove( npcInfo );
				return;
			}
		}
	}

	@Subscribe
	public void onGameStateChanged( GameStateChanged gameStateChanged )
	{
		if ( gameStateChanged.getGameState() == GameState.LOGGED_IN )
		{
			client.addChatMessage( ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null );
		}
	}

	@Subscribe
	public void onNpcSpawned( @Nonnull NpcSpawned event )
	{
		highlightService.addNpc( event.getNpc() );
	}

	@Subscribe
	public void onNpcDespawned( @Nonnull NpcDespawned event )
	{
		highlightService.removeNpc( event.getNpc() );
	}

	@Subscribe
	public void onNpcChanged( @Nonnull NpcChanged event )
	{
		highlightService.refreshNpc( event.getNpc() );
	}

	@Subscribe
	public void onPlayerSpawned( @Nonnull PlayerSpawned event )
	{
		final var localPlayer = client.getLocalPlayer();

		if ( event.getPlayer() == localPlayer )
		{
			clientService.setLocalPlayerInfo( PlayerInfo.create( localPlayer ) );
		}

		highlightService.addPlayer( event.getPlayer() );
	}

	@Subscribe
	public void onPlayerDespawned( @Nonnull PlayerDespawned event )
	{
		final var localPlayer = client.getLocalPlayer();

		if ( event.getPlayer() == localPlayer )
		{
			clientService.setLocalPlayerInfo( null );
		}

		highlightService.removePlayer( event.getPlayer() );
	}

	@Subscribe
	public void onPlayerChanged( @Nonnull PlayerChanged event )
	{
		final var localPlayer = client.getLocalPlayer();

		if ( event.getPlayer() == localPlayer )
		{
			clientService.setLocalPlayerInfo( PlayerInfo.create( localPlayer ) );
		}

		highlightService.refreshPlayer( event.getPlayer() );
	}

	@Subscribe
	public void onGameTick( @Nonnull GameTick event )
	{

	}

	@Subscribe
	public void onPostClientTick( @Nonnull PostClientTick event )
	{
		final var maxUpdates = 10;

		highlightService.update( maxUpdates );
	}

	@Provides
	DynamicHighlightingConfig provideConfig( ConfigManager configManager )
	{
		return configManager.getConfig( DynamicHighlightingConfig.class );
	}
}
