package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.*;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSign;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoSignKick extends Module {
    Setting<Integer> switchDelay = this.register(new Setting<Integer>("SwitchDelay", 100, 0, 5000));
    Setting<Integer>  placeDelay = this.register(new Setting<Integer>("PlaceDelay", 100, 0, 5000));
    Setting<Integer>  mineDelay = this.register(new Setting<Integer>("MineDelay", 100, 0, 5000));
    Setting<Integer> range = this.register(new Setting<Integer>("Range", 2, 1, 20));
    Timer placeTimer = new Timer();
    Timer switchTimer = new Timer();
    Timer mineTimer = new Timer();
    public AutoSignKick() {
        super("AutoSignKick", "stop being lazy lmao", Category.BERRY, true, false, false);
    }

    private boolean hadBreak;

    @Override
    public void onEnable() {
        hadBreak = false;
    }

    @Override
    public void onDisable() {
        hadBreak = false;
    }


    public void onUpdate() {
        for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
            if (!(tileEntity instanceof TileEntitySign)) continue;
            if (!(mc.player.getDistanceSq(tileEntity.getPos()) <= MathUtil.square(range.getValue()) )) continue;
            Command.sendMessage("Sign located at X: " + tileEntity.getPos().getX() + ", Y: " + tileEntity.getPos().getY() + ", Z: " + tileEntity.getPos().getZ());
            BlockPos posTile = tileEntity.getPos();
            if (!hadBreak) {
                axeSwitch();
                mineBlock(posTile);
                InventoryUtil.switchToHotbarSlot(ItemSign.class, false);
                Command.sendMessage("Changed to sign hotbar.");
                switchTimer.reset();
                place(posTile);
                Command.sendMessage("Done!");
                this.disable();
                hadBreak = true;
            }
        }
    }

    private void axeSwitch() {
        if (switchTimer.passedMs(switchDelay.getValue().longValue() * 3)) {
            InventoryUtil.switchToHotbarSlot(ItemAxe.class, false);
            Command.sendMessage("Switched to Axe");
            switchTimer.reset();
        }
    }

    private void mineBlock(BlockPos pos) {
        if (mineTimer.passedMs(mineDelay.getValue().longValue() * 3)) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
            Command.sendMessage("Mined");
            mineTimer.reset();
        }
    }

    private void place(BlockPos pos) {
        if (placeTimer.passedMs(placeDelay.getValue().longValue() * 3)) {
            BlockUtil.placeBlockSmartRotate(pos, EnumHand.MAIN_HAND, true, true, false);
            Command.sendMessage("Placed sign!");
            placeTimer.reset();
        }
    }
}
