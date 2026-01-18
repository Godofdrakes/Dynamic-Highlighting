package com.dynamicHighlighting;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.NPC;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
@Setter
public final class NpcInfo extends ActorInfo
{
	private int npcId;

	public static NpcInfo create( @Nonnull NPC npc )
	{
		final var npcInfo = new NpcInfo();

		npcInfo.setName( npc.getName() );
		npcInfo.setWorldLocation( npc.getWorldLocation() );
		npcInfo.setNpcId( npc.getId() );

		return npcInfo;
	}

	@Override
	@Nullable
	public PlayerInfo getPlayerInfo()
	{
		return null;
	}

	@Override
	@Nonnull
	public NpcInfo getNpcInfo()
	{
		return this;
	}

	@Nonnull
	@Override
	public String toString()
	{
		return String.format( "NpcInfo[%d, %s]", npcId, getName() );
	}
}
