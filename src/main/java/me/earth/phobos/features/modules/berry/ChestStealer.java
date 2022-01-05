package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

public class ChestStealer extends Module {

    public Setting<Boolean> onsneak = new Setting<Boolean>("OnSneak", true);

    public ChestStealer() {
        super("ChestStealer", "steal monkes in chests", Module.Category.BERRY, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (onsneak.getValue() && mc.player.isSneaking()) {
        if (ChestStealer.mc.player.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest) ChestStealer.mc.player.openContainer;
            for (int items = 0; items < chest.getLowerChestInventory().getSizeInventory(); ++items) {
                ItemStack stack = chest.getLowerChestInventory().getStackInSlot(items);
                ChestStealer.mc.playerController.windowClick(chest.windowId, items, 0, ClickType.QUICK_MOVE, (EntityPlayer) ChestStealer.mc.player);
                if (!this.isChestEmpty(chest)) continue;
                ChestStealer.mc.player.closeScreen();
            }
        }
        }
    }

    private boolean isChestEmpty(ContainerChest chest) {
        int items = 0;
        if (items < chest.getLowerChestInventory().getSizeInventory()) {
            ItemStack slot = chest.getLowerChestInventory().getStackInSlot(items);
            Command.sendMessage("Chest is empty");
            return false;
        }
        return true;
    }
}