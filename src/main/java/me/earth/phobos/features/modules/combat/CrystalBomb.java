package me.earth.phobos.features.modules.combat;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.EntityUtil;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;

/**
 * @author ligmaballz
 */
public class CrystalBomb extends Module {

    public Setting<Boolean> offhand = this.register(new Setting<>( "OffhandSwing", false));
    public Setting<Boolean> mainhand = this.register(new Setting<>( "MainhandSwing", true));

    private static double yaw;
    private static double pitch;
    int rangeCap = 3;

    public CrystalBomb() {
        super("CrystalBomb", "breaks crystals", Category.COMBAT, true, false, false);
    }
    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityEnderCrystal)
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistance(c)))
                .orElse(null);
        if(crystal != null && mc.player.getDistance(crystal) <= rangeCap) {
            lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
            mc.playerController.attackEntity(mc.player, crystal);
            if(offhand.getValue()) {
                mc.player.swingArm(EnumHand.OFF_HAND);
            } else if (mainhand.getValue()) {
            mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        registries((float) v[0], (float) v[1]);
    }
    private void registries(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
    }
}