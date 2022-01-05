package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;

/**
 * @author ligmaballz
 */
public class Coords extends Module{
    public Coords() {
        super("Coords", "mhm", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        final DecimalFormat format = new DecimalFormat ( "#" );
        final StringSelection contents = new StringSelection ( format.format ( mc.player.posX ) + ", " + format.format ( mc.player.posY ) + ", " + format.format ( mc.player.posZ ) );
        final Clipboard clipboard = Toolkit.getDefaultToolkit ( ).getSystemClipboard ( );
        clipboard.setContents ( contents , null );
        Command.sendMessage ( "Saved Coordinates To Clipboard." , false );
        Command.sendMessage ( "Coords: " + mc.player.posX + ", " + mc.player.posY + ", " + mc.player.posZ , false );
    }
}

