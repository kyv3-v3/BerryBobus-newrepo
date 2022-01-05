package me.earth.phobos.features.modules.berry;

import me.earth.phobos.Phobos;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;

public class Move extends Module {

    public Setting<Boolean> moduleenable = this.register(new Setting<Boolean>("EnableModule", true));
    public Setting<String> module = this.register(new Setting<>("Module", "Surround", "The Module to enable"));
    public Setting<Boolean> commandenable = this.register(new Setting<>("EnableCommand", false));
    public Setting<String> command = this.register(new Setting<>("Command", "", "The Command to enable"));

    public Move() {
        super("SneakEnable", "yes", Category.BERRY, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (moduleenable.getValue()) {
            if (mc.player.isSneaking()) {
                Phobos.moduleManager.enableModule(module.getValue());
            }
        }
        if (commandenable.getValue()) {
            if (mc.player.isSneaking()) {
                Phobos.commandManager.executeCommand(command.getValue());
            }
        }
    }
}
