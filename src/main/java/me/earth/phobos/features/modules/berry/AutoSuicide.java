package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;

/**
 * @author ligmaballz
 */
public class AutoSuicide extends Module {

    public AutoSuicide() {
        super("AutoSuicide", "kill urself", Module.Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        mc.player.sendChatMessage("/kill");
        mc.player.sendChatMessage("/suicide");
        this.toggle();
    }
}
