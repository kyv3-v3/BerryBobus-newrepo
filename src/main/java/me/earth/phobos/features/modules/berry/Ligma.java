package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.Timer;

public class Ligma extends Module {

    private Setting <String> message = this.register(new Setting<>("message", "LigmaBallz on tope!"));
    public Setting <Boolean> enabled = this.register(new Setting<>("enabled", true));
    public Setting <Integer> delay = this.register(new Setting<>("delay", 50));
    public Setting <Integer> amount = this.register(new Setting<>("amount", 1));
    public Setting <Integer> radius = this.register(new Setting<>("radius", 50));
    private Timer timer = new Timer();

    public Ligma() {
        super("Ligma", "ballz", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        if (enabled.getValue()) {
            if (timer.passedMs((long) delay.getValue())) {
                if (amount.getValue() > 0) {
                    if (radius.getValue() > 0) {
                        new Command.ChatMessage(message.getValue());
                        String s = " amount:" + amount.getValue() + " radius: " + radius.getValue() + "delay: " + delay.getValue();
                        Command.sendMessage("Ligma enabled with: " + s);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        Command.sendMessage("ligma off!");
    }

    public static class Message {
        public final int amount;

        public Message(int amount) {
            this.amount = amount;
        }
    }
}
