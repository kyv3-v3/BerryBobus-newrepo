package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.util.TextUtil;

/**
 * @author ligmaballz
 */
public class AutoRonaldo extends Module {

    public AutoRonaldo() {
        super("AutoRonaldo", "siuuuuu", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        Command.sendMessage(TextUtil.LIGHT_PURPLE + "SIUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
    }
}
