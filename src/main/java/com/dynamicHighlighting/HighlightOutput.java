package com.dynamicHighlighting;

import com.dynamicHighlighting.interfaces.ITestOutput;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

@Slf4j
public final class HighlightOutput implements ITestOutput
{
	@Nullable
	public Color OverheadTextColor;

	@Nullable
	public Color OutlineColor;

	public int OutlineWidth = 0;
	public int OutlineFeather = 0;

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logColor( logger, OverheadTextColor, "OverheadTextColor" );
		logColor( logger, OutlineColor, "OutlineColor" );
	}

	private static void logColor( @Nonnull Logger logger, @Nullable Color color, @Nonnull String variableName )
	{
		if ( color != null )
		{
			logger.info( "{}: #{}", variableName, Integer.toHexString( color.getRGB() ) );
		}
		else
		{
			logger.info( "{}: none", variableName );
		}
	}
}
