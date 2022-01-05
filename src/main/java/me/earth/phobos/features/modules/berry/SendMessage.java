package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;

/**
 * @author ligmaballz
 */
public class SendMessage extends Module {
    public Setting<String> Message = this.register(new Setting<String>("Message", "Ligmaballz on tope!"));

    public SendMessage() {
        super("Message", "yes does what u think", Category.BERRY, true, false, false);
    }

    @Override
    public void onTick() {
        mc.player.sendChatMessage(Message.getValueAsString());
        toggle();
    }
}
