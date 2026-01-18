package com.dynamicHighlighting;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;

public final class DynamicHighlightingOverlay extends Overlay
{
	@Nonnull
	private final Client client;

	@Nonnull
	private final ModelOutlineRenderer outlineRenderer;

	@Nonnull
	private final HashMap<Integer,HighlightOutput> playerHighlights = new HashMap<>();

	@Nonnull
	private final HashMap<Integer,HighlightOutput> npcHighlights = new HashMap<>();

	@Inject
	public DynamicHighlightingOverlay( @Nonnull Client client, @Nonnull ModelOutlineRenderer outlineRenderer )
	{
		this.client = client;
		this.outlineRenderer = outlineRenderer;

		setPosition( OverlayPosition.DYNAMIC );
		setLayer( OverlayLayer.ABOVE_SCENE );
	}

	public void put( @Nonnull PlayerInfo playerInfo, @Nonnull HighlightOutput highlightOutput )
	{
		playerHighlights.put( playerInfo.getPlayerId(), highlightOutput );
	}

	public void put( @Nonnull NpcInfo npcInfo, @Nonnull HighlightOutput highlightOutput )
	{
		npcHighlights.put( npcInfo.getNpcId(), highlightOutput );
	}

	public void remove( @Nonnull PlayerInfo playerInfo )
	{
		playerHighlights.remove( playerInfo.getPlayerId() );
	}

	public void remove( @Nonnull NpcInfo npcInfo )
	{
		npcHighlights.remove( npcInfo.getNpcId() );
	}

	@Override
	public Dimension render( Graphics2D graphics2D )
	{
		final var worldView = client.getTopLevelWorldView();

		for ( final var player : worldView.players() )
		{
			final var highlight = playerHighlights.get( player.getId() );

			if ( highlight == null )
			{
				continue;
			}

			if ( highlight.OutlineColor != null )
			{
				outlineRenderer.drawOutline(
					player,
					highlight.OutlineWidth,
					highlight.OutlineColor,
					highlight.OutlineFeather );
			}
		}

		for ( final var npc : worldView.npcs() )
		{
			final var highlight = npcHighlights.get( npc.getId() );

			if ( highlight == null )
			{
				continue;
			}

			if ( highlight.OutlineColor != null )
			{
				outlineRenderer.drawOutline(
					npc,
					highlight.OutlineWidth,
					highlight.OutlineColor,
					highlight.OutlineFeather );
			}
		}

		return null;
	}
}
