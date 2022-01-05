package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class IP extends Module {
    public IP() {
        super("IP", "Copy Ip to Clipboard", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {

        final Minecraft mc = Minecraft.getMinecraft();

        if (mc.getCurrentServerData() != null) {
            final StringSelection contents = new StringSelection(mc.getCurrentServerData().serverIP);
            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(contents, null);
            Command.sendMessage("Copied IP to clipboard");
            Command.sendMessage("IP: " + mc.getCurrentServerData().serverIP);
        } else {
            Command.sendMessage("Error, Join a server");
        }
    }
}
