package me.earth.phobos.features.modules.client;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;

/**
 * @author ligmaballz
 */
public class CloseMC extends Module {

    public Setting <Boolean> close = this.register(new Setting<>("close", false));

    public CloseMC() {
        super("CloseMC", "close block game", Module.Category.CLIENT, true, false, false);
    }

    @Override
    public void onEnable() {
        if (close.getValue()) {
            mc.shutdown();
        } else {
            Command.sendMessage("&c&l[&c&lNigger&c&l] &c&lYou have to enable the &c&l'close' &c&lsetting to actually use this module.");
        }
    }

    @Override
    public void onDisable() {
        Command.sendMessage("lmfao");
    }
}
