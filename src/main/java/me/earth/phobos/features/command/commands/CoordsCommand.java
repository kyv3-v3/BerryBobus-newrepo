package me.earth.phobos.features.command.commands;

import me.earth.phobos.features.command.Command;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;

/**
 * @author ligmaballz
 */
public
class CoordsCommand
        extends Command {
    public
    CoordsCommand ( ) {
        super ( "coords" , new String[0] );
    }

    @Override
    public
    void execute ( String[] commands ) {
        final DecimalFormat format = new DecimalFormat ( "#" );
        final StringSelection contents = new StringSelection ( format.format ( mc.player.posX ) + ", " + format.format ( mc.player.posY ) + ", " + format.format ( mc.player.posZ ) );
        final Clipboard clipboard = Toolkit.getDefaultToolkit ( ).getSystemClipboard ( );
        clipboard.setContents ( contents , null );
        Command.sendMessage ( "Saved Coordinates To Clipboard." , false );
    }
}