package com.dynamicHighlighting;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public final class PlayerInfo extends ActorInfo
{
	private int playerId;
	private boolean isFriend;

	public static PlayerInfo create( @Nonnull Player player )
	{
		final var playerInfo = new PlayerInfo();

		playerInfo.setName( player.getName() );
		playerInfo.setWorldLocation( player.getWorldLocation() );
		playerInfo.setPlayerId( player.getId() );
		playerInfo.setFriend( player.isFriend() );

		return playerInfo;
	}

	@Override
	@Nonnull
	public PlayerInfo getPlayerInfo()
	{
		return this;
	}

	@Override
	@Nullable
	public NpcInfo getNpcInfo()
	{
		return null;
	}

	@Nonnull
	@Override
	public String toString()
	{
		return String.format( "PlayerInfo[%d, %s]", playerId, getName() );
	}
}
