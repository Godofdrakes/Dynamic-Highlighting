package com.dynamicHighlighting;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class DynamicHighlightingPluginTest
{
	public static void main( String[] args ) throws Exception
	{
		ExternalPluginManager.loadBuiltin( DynamicHighlightingPlugin.class );
		RuneLite.main( args );
	}
}