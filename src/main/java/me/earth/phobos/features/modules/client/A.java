package me.earth.phobos.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.earth.phobos.event.events.ModuleToggleEvent;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class A extends Module {

    public Setting <Boolean> a = this.register(new Setting<>("Colors", true));

    public A() {
        super("ModuleMessage", "notifies u when a module is enabled or disabled", Category.CLIENT, true, false, false);
    }

    @SubscribeEvent
    public void onModuleEnabled(final ModuleToggleEvent.Enable event) {
        if (a.getValue()) {
            Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "Module " + event.getModule().getName() + " is enabled!");
        } else {
        Command.sendMessage("Module " + event.getModule().getName() + " is enabled!");
    }}

    @SubscribeEvent
    public void onModuleDisabled(final ModuleToggleEvent.Disable event) {
        if (a.getValue()) {
            Command.sendMessage(ChatFormatting.LIGHT_PURPLE + "Module " + event.getModule().getName() + " is disabled!");
        } else {
        Command.sendMessage("Module " + event.getModule().getName() + " is disabled!");
    }}
}
