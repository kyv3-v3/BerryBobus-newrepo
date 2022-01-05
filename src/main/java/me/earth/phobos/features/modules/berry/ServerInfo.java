package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.util.MessageUtil;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * @author ligmaballz
 */
public class ServerInfo extends Module {
    public ServerInfo() {
        super("ServerInfo", "Cul module", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {

        final Minecraft mc = Minecraft.getMinecraft();

        if (mc.getCurrentServerData() != null) {
            Command.sendMessage("Name: " + mc.getCurrentServerData().serverName +
                                "IP: " + mc.getCurrentServerData().serverIP +
                                "MOTD: " + mc.getCurrentServerData().serverMOTD +
                                "VERSION: " + mc.getCurrentServerData().version +
                                "Players: " + mc.getCurrentServerData().playerList +
                                "PINGED: " + mc.getCurrentServerData().pinged +
                                "POPULATIONINFO: " + mc.getCurrentServerData().populationInfo +
                                "PINGTOSERVER: " + mc.getCurrentServerData().pingToServer);
        } else {
            MessageUtil.sendError("Maybe Join a server first? (No Server Data Found)");
        }
    }

    @Override
    public void onDisable() {
        if (mc.getCurrentServerData() != null) {
        final StringSelection contents = new StringSelection("Name: " + mc.getCurrentServerData().serverName +
                "IP: " + mc.getCurrentServerData().serverIP +
                "MOTD: " + mc.getCurrentServerData().serverMOTD +
                "VERSION: " + mc.getCurrentServerData().version +
                "Players: " + mc.getCurrentServerData().playerList +
                "PINGED: " + mc.getCurrentServerData().pinged +
                "POPULATIONINFO: " + mc.getCurrentServerData().populationInfo +
                "PINGTOSERVER: " + mc.getCurrentServerData().pingToServer);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, null);
        Command.sendMessage("Copied Server Info to Clipboard");
    } else {
        Command.sendMessage("Couldnt find server data, so didnt copy anything");
    }
}}
