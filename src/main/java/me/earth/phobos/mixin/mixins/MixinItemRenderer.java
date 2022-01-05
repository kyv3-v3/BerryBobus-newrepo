package me.earth.phobos.mixin.mixins;

import com.google.common.util.concurrent.AbstractFuture;
import me.earth.phobos.event.events.RenderItemEvent;
import me.earth.phobos.features.modules.render.NoRender;
import me.earth.phobos.features.modules.render.SmallShield;
import me.earth.phobos.features.modules.render.ViewModel;
import me.earth.phobos.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(value={ItemRenderer.class})
public abstract class MixinItemRenderer {
    public Minecraft mc;
    private boolean injection;

    public MixinItemRenderer() {
        this.injection = true;
    }

    @Shadow
    public abstract void renderItemInFirstPerson(AbstractClientPlayer var1, float var2, float var3, EnumHand var4, float var5, ItemStack var6, float var7);

    @Inject(method={"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderItemInFirstPersonHook(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo info) {
        if (this.injection) {
            info.cancel();
            SmallShield offset = SmallShield.getINSTANCE();
            float xOffset = 0.0f;
            float yOffset = 0.0f;
            this.injection = false;
            if (hand == EnumHand.MAIN_HAND) {
                if (offset.isOn() && player.getHeldItemMainhand() != ItemStack.EMPTY) {
                    xOffset = offset.mainX.getValue().floatValue();
                    yOffset = offset.mainY.getValue().floatValue();
                }
            } else if (!offset.normalOffset.getValue().booleanValue() && offset.isOn() && player.getHeldItemOffhand() != ItemStack.EMPTY) {
                xOffset = offset.offX.getValue().floatValue();
                yOffset = offset.offY.getValue().floatValue();
            }
            this.renderItemInFirstPerson(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + xOffset, stack, p_187457_7_ + yOffset);
            this.injection = true;
        }
    }

    @Redirect(method={"renderArmFirstPerson"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal=0))
    public void translateHook(float x, float y, float z) {
        SmallShield offset = SmallShield.getINSTANCE();
        boolean shiftPos = Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().player.getHeldItemMainhand() != ItemStack.EMPTY && offset.isOn();
        GlStateManager.translate((float)(x + (shiftPos ? offset.mainX.getValue().floatValue() : 0.0f)), (float)(y + (shiftPos ? offset.mainY.getValue().floatValue() : 0.0f)), (float)z);
    }

    @Inject(method={"renderFireInFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderFireInFirstPersonHook(CallbackInfo info) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().fire.getValue().booleanValue()) {
            info.cancel();
        }
    }

    @Inject(method={"renderSuffocationOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderSuffocationOverlay(CallbackInfo ci) {
        if (NoRender.getInstance().isOn() && NoRender.getInstance().blocks.getValue().booleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = { "transformSideFirstPerson" },  at = { @At("HEAD") },  cancellable = true)
    public void transformSideFirstPerson(final EnumHandSide hand,  final float p_187459_2_,  final CallbackInfo cancel) {
    final RenderItemEvent event = new RenderItemEvent(0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  1.0,  0.0,  0.0,  0.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0);
            MinecraftForge.EVENT_BUS.post((Event)event);
        if ( ViewModel.getInstance ( ).isEnabled ( ) ) {
            boolean bob = ViewModel.getInstance ( ).isDisabled ( ) || ViewModel.getInstance ( ).doBob.getValue ( );
            int i = hand == EnumHandSide.RIGHT ? 1 : - 1;
            GlStateManager.translate ( (float) i * 0.56F , - 0.52F + ( bob ? p_187459_2_ : 0 ) * - 0.6F , - 0.72F );
            if ( hand == EnumHandSide.RIGHT ) {
                GlStateManager.translate ( event.getMainX ( ) , event.getMainY ( ) , event.getMainZ ( ) );
                RenderUtil.rotationHelper ( (float) event.getMainRotX ( ) , (float) event.getMainRotY ( ) , (float) event.getMainRotZ ( ) );
                //if ( ! ( Minecraft.getMinecraft ( ).player.getHeldItemMainhand ( ).getItem ( ) instanceof ItemBlock ) )
                //    GlStateManager.scale ( event.getMainHandItemWidth ( ) , 1 , 1 );
            } else { // leaving for if i wanna try to fix it later
                GlStateManager.translate ( event.getOffX ( ) , event.getOffY ( ) , event.getOffZ ( ) );
                RenderUtil.rotationHelper ( (float) event.getOffRotX ( ) , (float) event.getOffRotY ( ) , (float) event.getOffRotZ ( ) );
                //if ( ! ( Minecraft.getMinecraft ( ).player.getHeldItemOffhand ( ).getItem ( ) instanceof ItemBlock ) )
                //    GlStateManager.scale ( event.getOffHandItemWidth ( ) , 1 , 1 );
            }
            cancel.cancel ( );
        }
    }
}

