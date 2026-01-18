package com.dynamicHighlighting.services;

import com.dynamicHighlighting.PlayerInfo;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.coords.WorldPoint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public final class ClientService
{
	@Nullable
	private PlayerInfo localPlayerInfo;

	public boolean isLocalPlayer( @Nonnull PlayerInfo playerInfo )
	{
		if ( localPlayerInfo == null )
		{
			return false;
		}

		return playerInfo.getPlayerId() == localPlayerInfo.getPlayerId();
	}

	public int getClientDistance( @Nonnull WorldPoint worldLocation )
	{
		if ( localPlayerInfo == null )
		{
			return Integer.MAX_VALUE;
		}

		return localPlayerInfo.getWorldLocation().distanceTo( worldLocation );
	}
}
