package me.earth.phobos.event.events;

import me.earth.phobos.features.modules.Module;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleToggleEvent extends Event
{
    public Module module;

    public ModuleToggleEvent(final Module module) {
        this.module = module;
    }

    public static class Enable extends ModuleToggleEvent
    {
        public Enable(final Module module) {
            super(module);
        }

        public Module getModule() {
            return this.module;
        }
    }

    public static class Disable extends ModuleToggleEvent
    {
        public Disable(final Module module) {
            super(module);
        }

        public Module getModule() {
            return this.module;
        }
    }
}
