package me.earth.phobos.features.modules.berry;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.earth.phobos.util.BlockInteractHelper;
import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.features.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class PortalBuilder
extends Module {
    public Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", true));
    private final Setting<Integer> tick_for_place = this.register(new Setting<Integer>("BPT", 2, 1, 8));
    Vec3d[] targets = new Vec3d[]{new Vec3d(1.0, 1.0, 0.0), new Vec3d(1.0, 1.0, 1.0), new Vec3d(1.0, 1.0, 2.0), new Vec3d(1.0, 1.0, 3.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(1.0, 4.0, 0.0), new Vec3d(1.0, 5.0, 0.0), new Vec3d(1.0, 5.0, 1.0), new Vec3d(1.0, 5.0, 2.0), new Vec3d(1.0, 5.0, 3.0), new Vec3d(1.0, 4.0, 3.0), new Vec3d(1.0, 3.0, 3.0), new Vec3d(1.0, 2.0, 3.0)};
    int new_slot = 0;
    int old_slot = 0;
    int y_level = 0;
    int tick_runs = 0;
    int blocks_placed = 0;
    int offset_step = 0;
    boolean sneak = false;

    public PortalBuilder() {
        super("PortalBuilder", "Auto nether portal.", Module.Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        if (PortalBuilder.mc.player != null) {
            this.old_slot = PortalBuilder.mc.player.inventory.currentItem;
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                Command.sendMessage(ChatFormatting.RED + "Cannot find obi in hotbar!");
                this.toggle();
            }
            this.y_level = (int)Math.round(PortalBuilder.mc.player.posY);
        }
    }

    @Override
    public void onDisable() {
        if (PortalBuilder.mc.player != null) {
            if (this.new_slot != this.old_slot && this.old_slot != -1) {
                PortalBuilder.mc.player.inventory.currentItem = this.old_slot;
            }
            if (this.sneak) {
                PortalBuilder.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PortalBuilder.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.sneak = false;
            }
            this.old_slot = -1;
            this.new_slot = -1;
        }
    }

    @Override
    public void onUpdate() {
        if (PortalBuilder.mc.player != null) {
            this.blocks_placed = 0;
            while (this.blocks_placed < this.tick_for_place.getValue()) {
                if (this.offset_step >= this.targets.length) {
                    this.offset_step = 0;
                    break;
                }
                BlockPos offsetPos = new BlockPos(this.targets[this.offset_step]);
                BlockPos targetPos = new BlockPos(PortalBuilder.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ()).down();
                boolean try_to_place = true;
                if (!PortalBuilder.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    try_to_place = false;
                }
                for (Entity entity : PortalBuilder.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    try_to_place = false;
                    break;
                }
                if (try_to_place && this.place_blocks(targetPos)) {
                    ++this.blocks_placed;
                }
                ++this.offset_step;
            }
            if (this.blocks_placed > 0 && this.new_slot != this.old_slot) {
                PortalBuilder.mc.player.inventory.currentItem = this.old_slot;
            }
            ++this.tick_runs;
        }
    }

    private boolean place_blocks(BlockPos pos) {
        if (!PortalBuilder.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!BlockInteractHelper.checkForNeighbours(pos)) {
            return false;
        }
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockInteractHelper.canBeClicked(neighbor)) continue;
            PortalBuilder.mc.player.inventory.currentItem = this.new_slot;
            Block neighborPos = PortalBuilder.mc.world.getBlockState(neighbor).getBlock();
            if (BlockInteractHelper.blackList.contains(neighborPos)) {
                PortalBuilder.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PortalBuilder.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.sneak = true;
            }
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (this.rotate.getValue().booleanValue()) {
                BlockInteractHelper.faceVectorPacketInstant(hitVec);
            }
            PortalBuilder.mc.playerController.processRightClickBlock(PortalBuilder.mc.player, PortalBuilder.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            PortalBuilder.mc.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = PortalBuilder.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            return i;
        }
        return -1;
    }
}
