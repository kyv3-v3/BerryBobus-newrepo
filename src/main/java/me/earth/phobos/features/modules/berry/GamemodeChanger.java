package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GamemodeChanger extends Module {
    public GamemodeChanger() {
        super("GamemodeChanger", "yes", Category.BERRY, true, false, false);
    }


    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (GamemodeChanger.mc.player == null) {
            return;
        }
        Minecraft.getMinecraft();
        GamemodeChanger.mc.playerController.setGameType(GameType.CREATIVE);
    }

    @Override
    public void onDisable() {
        if (GamemodeChanger.mc.player == null) {
            return;
        }
        GamemodeChanger.mc.playerController.setGameType(GameType.SURVIVAL);
    }

}