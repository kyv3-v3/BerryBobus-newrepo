package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author ligmaballz
 */
public class GodMode extends Module {
    public GodMode() {
        super("GodMode", "idk if it works", Category.BERRY, true, false, false);
    }

    public void onTick(TickEvent.PlayerTickEvent event) {
        if (mc.player.inPortal) {
            mc.player.motionX = 0;
            mc.player.motionY = 0;
            mc.player.motionZ = 0;
        }
    }
    @Override
    public void onDisable() {
        mc.player.capabilities.disableDamage = false;
    }
    @Override
    public void onEnable() {
        if (mc.player.inPortal) {
            mc.player.capabilities.disableDamage = true;
        }
    }
    public void onMove() {
        if (mc.player.inPortal) {
            mc.player.movementInput.sneak = true;
        }
    }
}
