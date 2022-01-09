package me.earth.phobos.features.modules.berry;

import me.earth.phobos.Phobos;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;

/**
 * @author ligmaballz
 */
public class Stuff extends Module {

    private Setting<Boolean> a = this.register(new Setting<>("a", true));

    public Stuff() {
        super("Test", "idk", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        if (a.getValue()) {
            if (mc.getSession().getUsername().equals("CReb0rn")) {
                Command.sendMessage("Welcome ligmaballz!");
                Phobos.commandManager.executeCommand(".bind clickgui delete");
                Command.sendMessage("gui binded!");
            }
        }
    }
}
