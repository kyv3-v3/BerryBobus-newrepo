package me.earth.phobos.features.modules.berry;

import me.earth.phobos.event.events.MoveEvent;
import me.earth.phobos.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DontDrown extends Module {
    public DontDrown() {
        super("DontDrown", "keep afloat above water", Category.BERRY, true, false, false);
    }
    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (mc.player.inWater) {
            mc.player.jump();
        }
    }
}
