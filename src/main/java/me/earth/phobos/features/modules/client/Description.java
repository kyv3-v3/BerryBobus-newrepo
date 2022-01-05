package me.earth.phobos.features.modules.client;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.util.TextUtil;

public class Description extends Module {
    public Description() {
        super("Description", "shows module's descriptions in the gui", Category.CLIENT, true, false, false);
    }
    @Override
    public void onEnable() {
        Command.sendMessage(TextUtil.LIGHT_PURPLE + "Now showing module's descriptions in the gui");
    }

    @Override
    public void onDisable() {
        Command.sendMessage(TextUtil.LIGHT_PURPLE + "Now hiding module's descriptions in the gui");
    }
}
