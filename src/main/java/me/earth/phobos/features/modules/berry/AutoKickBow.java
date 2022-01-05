package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.command.Command;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.TextUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;

public class AutoKickBow extends Module {

  public Setting<Boolean> gapple = this.register(new Setting<Boolean>("Switch", true));

  public AutoKickBow() {
    super("AutoKickBow", "better bowbomb", Module.Category.BERRY, true, false, false);
  }
  
  static {
  
  }
  
  public int findGapple() {
    byte b = -1;
    for (byte b1 = 0; b1 < 9; b1++) {
      ItemStack itemStack = (ItemStack)mc.player.inventory.mainInventory.get(b1);
      if (itemStack.getItem() instanceof net.minecraft.item.ItemAppleGold)
        b = b1; 
    } 
    return b;
  }
  
  public void changeItem(int paramInt) {
    mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(paramInt));
    mc.player.inventory.currentItem = paramInt;
  }
  
  public void onUpdate() {
    if (mc.player.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 25) {
      mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
      mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
      mc.player.stopActiveHand();
      if (gapple.getValue()) {
        if (findGapple() != -1) {
          changeItem(findGapple());
        } else {
          Command.sendMessage(TextUtil.LIGHT_PURPLE + "No gapples found in hotbar, not switching...");
          disable();
        }
      }
    }
  }
}
