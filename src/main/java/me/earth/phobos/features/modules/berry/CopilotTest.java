package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;

public class CopilotTest extends Module {

    public CopilotTest() {
        super("ActionNotifs", "idk some random testing with github copilot", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        Command.sendMessage("Enabled");
    }

    @Override
    public void onDisable() {
        Command.sendMessage("Disabled");
    }

    public void onCommand(Command command) {
        Command.sendMessage("Command");
    }

    public void onClientCommand(Command command) {
        Command.sendMessage("Client command");
    }

    public void onServerCommand(Command command) {
        Command.sendMessage("Server command");
    }

    public void onChat(String message) {
        Command.sendMessage("Chat");
    }

    public void onClientChat(String message) {
        Command.sendMessage("Client chat");
    }

    public void onServerChat(String message) {
        Command.sendMessage("Server chat");
    }

    public void onChat(String message, String username) {
        Command.sendMessage("Chat with username");
    }

    public void onClientChat(String message, String username) {
        Command.sendMessage("Client chat with username");
    }

    public void onServerChat(String message, String username) {
        Command.sendMessage("Server chat with username");
    }

    public void onChat(String message, String username, String displayName) {
        Command.sendMessage("Chat with username and display name");
    }

    public void onClientChat(String message, String username, String displayName) {
        Command.sendMessage("Client chat with username and display name");
    }

    public void onServerChat(String message, String username, String displayName) {
        Command.sendMessage("Server chat with username and display name");
    }

    public void onChat(String message, String username, String displayName, String prefix, String suffix) {
        Command.sendMessage("Chat with username, display name, prefix and suffix");
    }

    public void onClientChat(String message, String username, String displayName, String prefix, String suffix) {
        Command.sendMessage("Client chat with username, display name, prefix and suffix");
    }

    public void onServerChat(String message, String username, String displayName, String prefix, String suffix) {
        Command.sendMessage("Server chat with username, display name, prefix and suffix");
    }

    public void onChat(String message, String username, String displayName, String prefix, String suffix, String group) {
        Command.sendMessage("Chat with username, display name, prefix, suffix and group");
    }

    public void onClientChat(String message, String username, String displayName, String prefix, String suffix, String group) {
        Command.sendMessage("Client chat with username");
    }
}
