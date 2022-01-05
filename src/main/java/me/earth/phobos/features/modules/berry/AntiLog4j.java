package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.util.TextUtil;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiLog4j extends Module {

    public AntiLog4j() {
        super("AntiLog4j", "Makes log4j exploit disappear", Category.BERRY, true, false, false);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        String text = ((SPacketChat) event.getMessage()).getChatComponent().getUnformattedText();
        if (text.contains("$")) {
            event.setCanceled(true);
            Command.sendMessage(TextUtil.DARK_RED + text.replace("$", "Message with $ replaced: "));
            Command.sendMessage(TextUtil.DARK_RED + "Someone prolly tried to log4j your ass");
        }
    }
}
