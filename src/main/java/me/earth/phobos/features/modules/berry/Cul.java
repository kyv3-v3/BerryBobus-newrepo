package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * @author ligmaballz
 */
public class Cul
        extends Module {
    private static Cul cul;

    public Cul() {
        super("Damage", "die without the /kill or /suicide", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        if (Cul.fullNullCheck()) {
            return;
        }
        mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(Cul.mc.player.posX, Cul.mc.player.posY, Cul.mc.player.posZ, false));
        Cul.mc.player.setLocationAndAngles(Cul.mc.player.posX, Cul.mc.player.posY, Cul.mc.player.posZ, Cul.mc.player.rotationYaw, Cul.mc.player.rotationPitch);
        mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(Cul.mc.player.posX, Cul.mc.player.posY - 1339.2, Cul.mc.player.posZ, true));
        this.disable();
    }
}