package me.earth.phobos.util;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import me.earth.phobos.features.command.Command;
import net.minecraft.world.World;
import java.util.Iterator;
import net.minecraft.network.Packet;
import me.earth.phobos.util.MinecraftInstance;

public class WorldUtil implements MinecraftInstance {
    public static void disconnectFromWorld(final Command command) {
        WorldUtil.mc.world.sendQuittingDisconnectingPacket();
        WorldUtil.mc.loadWorld((WorldClient)null);
        WorldUtil.mc.displayGuiScreen((GuiScreen)new GuiMainMenu());
    }
}
