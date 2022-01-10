package me.earth.phobos.features.modules.bot;

import me.earth.phobos.event.events.PacketEvent;
import me.earth.phobos.features.modules.Module;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Objects;

public class Bot extends Module {

    public Bot() {
        super("BerryBobusBot", "BerryBobusBot(a chat bot)", Category.BOT, true, false, false);
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onPacketRecieve(PacketEvent.Receive event) {
            String text;
        if (event.getPacket() instanceof SPacketChat && (text = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText()).contains("^")) {
            mc.player.sendChatMessage("try ^help or ^commands");
            if (text.equalsIgnoreCase("^say")) {
                mc.player.sendChatMessage("usage: ^say <message>");
            } else if (text.startsWith("^say ")) {
                if (text.length() >= 256) {
                    mc.player.sendChatMessage("ERROR! Only 256 characters allowed.");
                    return;
                }
                mc.player.sendChatMessage(text);
            } else if (text.startsWith("^say /")) {
                mc.player.sendChatMessage("not allowed");
            } else if (text.startsWith("^say .")) {
                mc.player.sendChatMessage("not allowed");
            } else if (text.equalsIgnoreCase("^commands")) {
                mc.player.sendChatMessage("commands: ^commands, ^help, ^serverinfo, ^say, ^whisper, ^info, ^ping, ^time, ^players, ^jump, ^kill, ^move");
            } else if (text.equalsIgnoreCase("^info")) {
                mc.player.sendChatMessage("BerryBobusBot made by ligmaballz with the help of perry, join our discord: https://discord.gg/msyqrUQ2pc");
            } else if (text.equalsIgnoreCase("^help")) {
                mc.player.sendChatMessage("do ^commands for a list of commands");
            } else if (text.equalsIgnoreCase("^serverinfo")) {
                mc.player.sendChatMessage(
                        "servername: " + Objects.requireNonNull(mc.getCurrentServerData()).serverName +
                                " ip: " + mc.getCurrentServerData().serverIP +
                                " port: " + mc.serverPort +
                                " motd: " + mc.getCurrentServerData().serverMOTD +
                                " version: " + mc.getCurrentServerData().version +
                                " pingtoserver: " + mc.getCurrentServerData().pingToServer
                );
            } else if (text.equalsIgnoreCase("^ping")) {
                mc.player.sendChatMessage("pong");
            } else if (text.equalsIgnoreCase("^time")) {
                mc.player.sendChatMessage("time: " + mc.world.getWorldTime());
            } else if (text.equalsIgnoreCase("^players")) {
                mc.player.sendChatMessage("players: " + Objects.requireNonNull(mc.getCurrentServerData()).playerList);
            } else if (text.equalsIgnoreCase("^jump")) {
                mc.player.jump();
            } else if (text.equalsIgnoreCase("^whisper")) {
                mc.player.sendChatMessage("usage: ^whisper <player> <message>");
            } else if (text.startsWith("^whisper ")) {
                if (text.length() >= 256) {
                    mc.player.sendChatMessage("ERROR! Only 256 characters allowed.");
                    return;
                }
                String[] split = text.split(" ");
                mc.player.sendChatMessage("/tell " + split[1] + " " + text.substring(split[0].length() + split[1].length() + 2));

            } else if (text.startsWith("^whisper /")) {
                mc.player.sendChatMessage("not allowed");
            } else if (text.startsWith("^whisper .")) {
                mc.player.sendChatMessage("not allowed");
            } else if (text.equalsIgnoreCase("^discord")) {
                mc.player.sendChatMessage("BerryBobus discord: https://discord.gg/msyqrUQ2pc");
            } else if (text.equalsIgnoreCase("^move")) {
                mc.player.sendChatMessage("usage: ^move forward");
            } else if (text.equalsIgnoreCase("^move forward")) {
                mc.player.sendChatMessage("moving forward");
                mc.player.moveForward = 1;
            } else if (text.equalsIgnoreCase("^kill")) {
                mc.player.sendChatMessage("fuck u nigga i aint gonna do that lmfao");
            } else if (text.equalsIgnoreCase("^secret")) {
                mc.player.sendChatMessage("super secret command");
            }
        }
    }
}