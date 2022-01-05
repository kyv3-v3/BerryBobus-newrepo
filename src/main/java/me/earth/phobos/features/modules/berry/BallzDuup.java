package me.earth.phobos.features.modules.berry;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;

import java.util.concurrent.Callable;

/**
* @author ligmaballz
* Cleaned up by perry.
*/
public class BallzDuup extends Module {

    public Setting <Boolean> join2l2c = this.register(new Setting<>("Join2L", false));
    public Setting <String> login = this.register(new Setting<>("LoginMessage", "ur password"));
    public Setting <Boolean> register = this.register(new Setting<>("Register", false));
    public Setting <String> registerMessage = this.register(new Setting<>("RegisterMessage", "put 'password password'"));

   public BallzDuup() {
       super("2Ldupe", "leave ur first hotbar slot empty(this dupe is only for 2l2c.org)", Category.BERRY, true, false, false);
   }
   @Override
   public void onEnable() {
       if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equals("2l2c.org")) {
           mc.player.sendChatMessage("$dupe");
           Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "Duped! (leave ur first hotbar slot empty nigga).");
       } else {
           Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "nigga join 2l2c.org or u wont be able to use this");
       }

       if (join2l2c.getValue()) {
           mc.displayGuiScreen(new GuiConnecting(null, mc, "2l2c.org", 25565));
           mc.player.sendChatMessage("/login " + login.getValue());
           Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "Logged in!");
       } else {
           Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "turn on join2L setting to join 2l2c");
       }

       if (register.getValue()) {
           if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.equals("2l2c.org")) {
               mc.player.sendChatMessage("/register " + registerMessage.getValue());
               Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "Registered!");
           }
       }
   }
}