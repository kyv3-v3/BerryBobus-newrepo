package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;

/**
 * @author ligmaballz
 */
public class GodMode extends Module {
    public GodMode() {
        super("GodMode", "idk if it works", Category.BERRY, true, false, false);
    }

    @Override
    public void onTick() {
        if (mc.player.inPortal) {
            mc.player.motionX = 0;
            mc.player.motionY = 0;
            mc.player.motionZ = 0;
        }
    }
}
