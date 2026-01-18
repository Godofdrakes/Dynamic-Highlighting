package com.dynamicHighlighting;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.coords.WorldPoint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;

@Getter
@Setter
public abstract class ActorInfo
{
	@Nonnull
	private Instant nextUpdateTime = Instant.MIN;

	@Nullable
	private String name;

	@Nonnull
	private WorldPoint worldLocation = new WorldPoint( 0, 0, 0 );

	@Nullable
	public abstract PlayerInfo getPlayerInfo();

	@Nullable
	public abstract NpcInfo getNpcInfo();

	@Nullable
	@Override
	public abstract String toString();
}
