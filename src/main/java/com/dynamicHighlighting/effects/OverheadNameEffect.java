package com.dynamicHighlighting.effects;

import com.dynamicHighlighting.HighlightOutput;
import com.dynamicHighlighting.interfaces.IEffect;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.awt.*;

public final class OverheadNameEffect implements IEffect
{
	@Nonnull
	public Color color = Color.MAGENTA;

	@Override
	public void execute( @Nonnull HighlightOutput output, @Nonnull Logger logger )
	{
		logger.info( "set name color to #{}", Integer.toHexString( color.getRGB() ) );

		output.OverheadTextColor = color;
	}

	@Override
	public void logState( @Nonnull Logger logger )
	{
		logger.info( "{}", OverheadNameEffect.class );
		logger.info( "color: #{}", Integer.toHexString( color.getRGB() ) );
	}
}
