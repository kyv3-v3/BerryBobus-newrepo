package me.earth.phobos.features.command.commands;

import me.earth.phobos.util.WorldUtil;
import me.earth.phobos.features.command.Command;

/**
 * @author ligmaballz
 */
public class DisconnectCommand extends Command
{
    public DisconnectCommand() {
        super("Disconnect");
    }

    @Override
    public void execute(final String[] commands) {
        WorldUtil.disconnectFromWorld(this);
    }
}