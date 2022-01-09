package me.earth.phobos.features.modules.client;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;

public class Particles extends Module {
    public Particles() {
        super("Particles", "draws particles on ur gui", Category.CLIENT, true, false, false);
    }

    @Override
    public void onEnable() {
        Command.sendMessage("&aNow you can see particles on ur gui");
    }

    @Override
    public void onDisable() {
        Command.sendMessage("&cNow you can't see particles on ur gui");
    }
}
