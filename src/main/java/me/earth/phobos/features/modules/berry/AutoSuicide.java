package me.earth.phobos.features.modules.berry;

import me.earth.phobos.Phobos;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;

/**
 * @author ligmaballz
 */
public class AutoSuicide extends Module {

    public Setting<Boolean> kill = this.register(new Setting<>("Kill", true));
    public Setting<Boolean> suicide = this.register(new Setting<>("Suicide", false));
    public Setting<Boolean> legit = this.register(new Setting<>("DamageModule", false));

    public AutoSuicide() {
        super("AutoSuicide", "kill urself", Module.Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        if (kill.getValue()) {
            mc.player.sendChatMessage("/kill");
        } else if (suicide.getValue()) {
            mc.player.sendChatMessage("/suicide");
        } else if (legit.getValue()) {
            Phobos.moduleManager.enableModule(Cul.class);
        }
    }
}
