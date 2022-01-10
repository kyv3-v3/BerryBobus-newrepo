package me.earth.phobos.features.modules.berry;

import me.earth.phobos.event.events.MoveEvent;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DontDrown extends Module {
    public DontDrown() {
        super("DontDrown", "keep afloat above water", Category.BERRY, true, false, false);
    }
    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (mc.player.isCreative()) {
            Command.sendMessage("&cYou are in creative mode! You can't drown!");
            toggle();
        } else if (mc.player.isSpectator()) {
            Command.sendMessage("&cYou are spectating! You can't do that!");
            toggle();
        } else if (mc.player.inWater) {
            mc.player.jump();
        }
    }
}
