package me.earth.phobos.features.modules.berry;

import me.earth.phobos.event.events.PacketEvent;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiLog4j extends Module {

    public AntiLog4j() {
        super("AntiLog4j", "Makes log4j exploit disappear", Category.BERRY, true, false, false);
    }

    @SubscribeEvent(priority= EventPriority.HIGHEST)
    public void onPacketRecieve(PacketEvent.Receive event) {
        String text;
        if (event.getPacket() instanceof SPacketChat && ((text = ((SPacketChat)event.getPacket()).getChatComponent().getUnformattedText()).contains("${") || text.contains("$<") || text.contains("$:-") || text.contains("jndi:ldap"))) {
            Command.sendMessage("&c[AntiLog4j] &cBlocked message: " + text);
            event.setCanceled(true);
        }
    }
}
