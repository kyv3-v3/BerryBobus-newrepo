package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GamemodeChanger extends Module {

    public Setting<Boolean> survival = this.register(new Setting<>("survival", false));
    public Setting<Boolean> creative = this.register(new Setting<>("creative", true));
    public Setting<Boolean> adventure = this.register(new Setting<>("adventure", false));
    public Setting<Boolean> spectator = this.register(new Setting<>("spectator", false));

    public GamemodeChanger() {
        super("GamemodeChanger", "Changes the gamemode of the player.", Category.BERRY, true, false, false);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player != null) {
            if (survival.getValue()) {
                Minecraft.getMinecraft().player.setGameType(GameType.SURVIVAL);
            }
            if (creative.getValue()) {
                Minecraft.getMinecraft().player.setGameType(GameType.CREATIVE);
            }
            if (adventure.getValue()) {
                Minecraft.getMinecraft().player.setGameType(GameType.ADVENTURE);
            }
            if (spectator.getValue()) {
                Minecraft.getMinecraft().player.setGameType(GameType.SPECTATOR);
            }
        }
    }

    @Override
    public void onDisable() {
        if (GamemodeChanger.mc.player == null) {
            return;
        }
        GamemodeChanger.mc.playerController.setGameType(GameType.SURVIVAL);
    }
}