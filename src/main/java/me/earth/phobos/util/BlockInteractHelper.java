package me.earth.phobos.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class BlockInteractHelper {
    public static final List<Block> blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE, Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
    public static final List<Block> shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static PlaceResult place(BlockPos pos, float p_Distance, boolean p_Rotate, boolean p_UseSlabRule) {
        IBlockState l_State = BlockInteractHelper.mc.world.getBlockState(pos);
        boolean l_Replaceable = l_State.getMaterial().isReplaceable();
        boolean l_IsSlabAtBlock = l_State.getBlock() instanceof BlockSlab;
        if (!l_Replaceable && !l_IsSlabAtBlock) {
            return PlaceResult.NotReplaceable;
        }
        if (!BlockInteractHelper.checkForNeighbours(pos)) {
            return PlaceResult.Neighbors;
        }
        if (p_UseSlabRule && l_IsSlabAtBlock && !l_State.isFullCube()) {
            return PlaceResult.CantPlace;
        }
        Vec3d eyesPos = new Vec3d(BlockInteractHelper.mc.player.posX, BlockInteractHelper.mc.player.posY + (double)BlockInteractHelper.mc.player.getEyeHeight(), BlockInteractHelper.mc.player.posZ);
        for (EnumFacing side : EnumFacing.values()) {
            EnumActionResult l_Result2;
            Vec3d hitVec;
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockInteractHelper.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(BlockInteractHelper.mc.world.getBlockState(neighbor), false) || !(eyesPos.distanceTo(hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5))) <= (double)p_Distance)) continue;
            Block neighborPos = BlockInteractHelper.mc.world.getBlockState(neighbor).getBlock();
            boolean activated = neighborPos.onBlockActivated((World)BlockInteractHelper.mc.world, pos, BlockInteractHelper.mc.world.getBlockState(pos), (EntityPlayer)BlockInteractHelper.mc.player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (blackList.contains(neighborPos) || shulkerList.contains(neighborPos) || activated) {
                BlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockInteractHelper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            if (p_Rotate) {
                BlockInteractHelper.faceVectorPacketInstant(hitVec);
            }
            if ((l_Result2 = BlockInteractHelper.mc.playerController.processRightClickBlock(BlockInteractHelper.mc.player, BlockInteractHelper.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND)) == EnumActionResult.FAIL) continue;
            BlockInteractHelper.mc.player.swingArm(EnumHand.MAIN_HAND);
            if (activated) {
                BlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)BlockInteractHelper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            return PlaceResult.Placed;
        }
        return PlaceResult.CantPlace;
    }

    public static ValidResult valid(BlockPos pos) {
        if (!BlockInteractHelper.mc.world.checkNoEntityCollision(new AxisAlignedBB(pos))) {
            return ValidResult.NoEntityCollision;
        }
        if (!BlockInteractHelper.checkForNeighbours(pos)) {
            return ValidResult.NoNeighbors;
        }
        IBlockState l_State = BlockInteractHelper.mc.world.getBlockState(pos);
        if (l_State.getBlock() == Blocks.AIR) {
            BlockPos[] l_Blocks;
            for (BlockPos l_Pos : l_Blocks = new BlockPos[]{pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down()}) {
                IBlockState l_State2 = BlockInteractHelper.mc.world.getBlockState(l_Pos);
                if (l_State2.getBlock() == Blocks.AIR) continue;
                for (EnumFacing side : EnumFacing.values()) {
                    BlockPos neighbor = pos.offset(side);
                    if (!BlockInteractHelper.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(BlockInteractHelper.mc.world.getBlockState(neighbor), false)) continue;
                    return ValidResult.Ok;
                }
            }
            return ValidResult.NoNeighbors;
        }
        return ValidResult.AlreadyBlockThere;
    }

    public static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = BlockInteractHelper.getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{BlockInteractHelper.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(yaw - BlockInteractHelper.mc.player.rotationYaw)), BlockInteractHelper.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(pitch - BlockInteractHelper.mc.player.rotationPitch))};
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(BlockInteractHelper.mc.player.posX, BlockInteractHelper.mc.player.posY + (double)BlockInteractHelper.mc.player.getEyeHeight(), BlockInteractHelper.mc.player.posZ);
    }

    public static void faceVectorPacketInstant(Vec3d vec) {
        float[] rotations = BlockInteractHelper.getLegitRotations(vec);
        BlockInteractHelper.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], rotations[1], BlockInteractHelper.mc.player.onGround));
    }

    public static boolean canBeClicked(BlockPos pos) {
        return BlockInteractHelper.getBlock(pos).canCollideCheck(BlockInteractHelper.getState(pos), false);
    }

    private static Block getBlock(BlockPos pos) {
        return BlockInteractHelper.getState(pos).getBlock();
    }

    private static IBlockState getState(BlockPos pos) {
        return BlockInteractHelper.mc.world.getBlockState(pos);
    }

    public static boolean checkForNeighbours(BlockPos blockPos) {
        if (!BlockInteractHelper.hasNeighbour(blockPos)) {
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = blockPos.offset(side);
                if (!BlockInteractHelper.hasNeighbour(neighbour)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    private static boolean hasNeighbour(BlockPos blockPos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = blockPos.offset(side);
            if (BlockInteractHelper.mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) continue;
            return true;
        }
        return false;
    }

    public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f;
                    float f2 = f = sphere ? (float)cy + r : (float)(cy + h);
                    if (!((float)y < f)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (!(!(dist < (double)(r * r)) || hollow && dist < (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static enum PlaceResult {
        NotReplaceable,
        Neighbors,
        CantPlace,
        Placed;

    }

    public static enum ValidResult {
        NoEntityCollision,
        AlreadyBlockThere,
        NoNeighbors,
        Ok;

    }
}
