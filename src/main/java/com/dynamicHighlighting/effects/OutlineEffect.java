package com.dynamicHighlighting.effects;

import com.dynamicHighlighting.HighlightOutput;
import com.dynamicHighlighting.interfaces.IEffect;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.awt.*;

public final class OutlineEffect implements IEffect
{
	@Nonnull
	public Color color = Color.MAGENTA;

	public int width = 2;

	public int feather = 1;

	@Override
	public void execute( @Nonnull HighlightOutput output, @Nonnull Logger logger )
	{
		logger.info( "set outline color to #{}", Integer.toHexString( color.getRGB() ) );

		output.OutlineColor = color;
		output.OutlineWidth = width;
		output.OutlineFeather = feather;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "{}", OutlineEffect.class );
		logger.info( "color: #{}", Integer.toHexString( color.getRGB() ) );
		logger.info( "width: {}", width );
		logger.info( "feather: {}", feather );
	}
}
