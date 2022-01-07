package me.earth.phobos.features.modules.berry;

import me.earth.phobos.Phobos;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundEvent;

public class LigmaStuff extends Module {

    public Setting<Boolean> moduleenable = this.register(new Setting<>("EnableModule", true));
    public Setting<String> module = this.register(new Setting<>("Module", "Surround", "The Module to enable"));
    public Setting<Boolean> commandenable = this.register(new Setting<>("EnableCommand", false));
    public Setting<String> command = this.register(new Setting<>("Command", "", "The Command to enable"));
    public Setting<Boolean> message = this.register(new Setting<>("Message", false));
    public Setting<String> chatmessage = this.register(new Setting<>("ChatMessage", "/home home", "The Message to send"));
    public Setting<Boolean> sound = this.register(new Setting<>("Sound", false));
    public Setting<SoundEvent> soundevent = this.register(new Setting<>("SoundEvent", "minecraft:entity.enderdragon.growl", "The Sound to play"));
    public Setting<Boolean> soundpitchmin = this.register(new Setting<>("SoundPitchMin", false));
    public Setting<Float> soundpitchminvalue = this.register(new Setting<>("SoundPitchMinValue", 1.0F));
    public Setting<Boolean> soundpitchmax = this.register(new Setting<>("SoundPitchMax", false));
    public Setting<Float> soundpitchmaxvalue = this.register(new Setting<>("SoundPitchMaxValue", 1.0F));
    public Setting<Boolean> soundvolumemin = this.register(new Setting<>("SoundVolumeMin", false));
    public Setting<Float> soundvolumeminvalue = this.register(new Setting<>("SoundVolumeMinValue", 1.0F));
    public Setting<Boolean> soundvolumemax = this.register(new Setting<>("SoundVolumeMax", false));
    public Setting<Float> soundvolumemaxvalue = this.register(new Setting<>("SoundVolumeMaxValue", 1.0F));
    public Setting<SoundEvent> soundname = this.register(new Setting<>("SoundName", "enderdragon_growl"));
    public Setting<Boolean> nigga = this.register(new Setting<>("nigger", "false"));

    public LigmaStuff() {
        super("LigmaStuff", "yes", Category.BERRY, true, false, false);
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
        if (message.getValue()) {
            if (mc.player.isSneaking()) {
                mc.player.sendChatMessage(chatmessage.getValue());
            }
        }
        if (sound.getValue()) {
            if (mc.player.isSneaking()) {
                mc.player.playSound(soundname.getValue(), soundpitchmin.getValue() ? soundpitchminvalue.getValue() : soundpitchmax.getValue() ? soundpitchmaxvalue.getValue() : 1.0F, soundvolumemin.getValue() ? soundvolumeminvalue.getValue() : soundvolumemax.getValue() ? soundvolumemaxvalue.getValue() : 1.0F);
            }
        }
    }
}
