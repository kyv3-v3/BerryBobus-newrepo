package me.earth.phobos.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import me.earth.phobos.event.events.*;
import me.earth.phobos.features.modules.render.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import me.earth.phobos.features.modules.client.*;
import java.awt.*;
import me.earth.phobos.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T>
{
    private static final ResourceLocation glint;

    public MixinRenderLivingBase(final RenderManager renderManagerIn,  final ModelBase modelBaseIn,  final float shadowSizeIn) {
        super(renderManagerIn);
    }

    @Redirect(method = { "renderModel" },  at = @At(value = "INVOKE",  target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderModelHook(final ModelBase modelBase,  final Entity entityIn,  final float limbSwing,  final float limbSwingAmount,  final float ageInTicks,  final float netHeadYaw,  final float headPitch,  final float scale) {
        boolean cancel = false;
        if (Skeleton.getInstance().isEnabled() || ESP.getInstance().isEnabled() || PopChams.getInstance().isEnabled()) {
            final RenderEntityModelEvent event = new RenderEntityModelEvent(0,  modelBase,  entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
            if (Skeleton.getInstance().isEnabled()) {
                Skeleton.getInstance().onRenderModel(event);
            }
            if (ESP.getInstance().isEnabled()) {
                ESP.getInstance().onRenderModel(event);
                if (event.isCanceled()) {
                    cancel = true;
                }
            }
            if (PopChams.getInstance().isEnabled()) {
                PopChams.getInstance().onRenderModel(event);
                if (event.isCanceled()) {
                    cancel = true;
                }
            }
        }
        if (Chams.getInstance().isEnabled() && entityIn instanceof EntityPlayer && (boolean)Chams.getInstance().colored.getValue() && !(boolean)Chams.getInstance().textured.getValue()) {
            if (!(boolean)Chams.getInstance().textured.getValue()) {
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770,  771);
                GL11.glLineWidth(1.5f);
                GL11.glEnable(2960);
                if (Chams.getInstance().rainbow.getValue()) {
                    final Color rainbowColor1 = Chams.getInstance().colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : new Color(RenderUtil.getRainbow((int)Chams.getInstance().speed.getValue() * 100,  0,  (int)Chams.getInstance().saturation.getValue() / 100.0f,  (int)Chams.getInstance().brightness.getValue() / 100.0f));
                    final Color rainbowColor2 = EntityUtil.getColor(entityIn,  rainbowColor1.getRed(),  rainbowColor1.getGreen(),  rainbowColor1.getBlue(),  (int)Chams.getInstance().alpha.getValue(),  true);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f(rainbowColor2.getRed() / 255.0f,  rainbowColor2.getGreen() / 255.0f,  rainbowColor2.getBlue() / 255.0f,  (int)Chams.getInstance().alpha.getValue() / 255.0f);
                    modelBase.render(entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
                else if (Chams.getInstance().xqz.getValue()) {
                    final Color hiddenColor = EntityUtil.getColor(entityIn,  (int)Chams.getInstance().hiddenRed.getValue(),  (int)Chams.getInstance().hiddenGreen.getValue(),  (int)Chams.getInstance().hiddenBlue.getValue(),  (int)Chams.getInstance().hiddenAlpha.getValue(),  true);
                    final Color visibleColor2 = EntityUtil.getColor(entityIn,  (int)Chams.getInstance().red.getValue(),  (int)Chams.getInstance().green.getValue(),  (int)Chams.getInstance().blue.getValue(),  (int)Chams.getInstance().alpha.getValue(),  true);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f(hiddenColor.getRed() / 255.0f,  hiddenColor.getGreen() / 255.0f,  hiddenColor.getBlue() / 255.0f,  (int)Chams.getInstance().alpha.getValue() / 255.0f);
                    modelBase.render(entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    GL11.glColor4f(visibleColor2.getRed() / 255.0f,  visibleColor2.getGreen() / 255.0f,  visibleColor2.getBlue() / 255.0f,  (int)Chams.getInstance().alpha.getValue() / 255.0f);
                    modelBase.render(entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
                }
                else {
                    final Color visibleColor3 = Chams.getInstance().colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entityIn,  (int)Chams.getInstance().red.getValue(),  (int)Chams.getInstance().green.getValue(),  (int)Chams.getInstance().blue.getValue(),  (int)Chams.getInstance().alpha.getValue(),  true);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f(visibleColor3.getRed() / 255.0f,  visibleColor3.getGreen() / 255.0f,  visibleColor3.getBlue() / 255.0f,  (int)Chams.getInstance().alpha.getValue() / 255.0f);
                    modelBase.render(entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                }
                GL11.glEnable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
            }
        }
        else if (Chams.getInstance().textured.getValue()) {
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            final Color visibleColor3 = Chams.getInstance().colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : EntityUtil.getColor(entityIn,  (int)Chams.getInstance().red.getValue(),  (int)Chams.getInstance().green.getValue(),  (int)Chams.getInstance().blue.getValue(),  (int)Chams.getInstance().alpha.getValue(),  true);
            GL11.glColor4f(visibleColor3.getRed() / 255.0f,  visibleColor3.getGreen() / 255.0f,  visibleColor3.getBlue() / 255.0f,  (int)Chams.getInstance().alpha.getValue() / 255.0f);
            modelBase.render(entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
        }
        else if (!cancel) {
            modelBase.render(entityIn,  limbSwing,  limbSwingAmount,  ageInTicks,  netHeadYaw,  headPitch,  scale);
        }
    }

    @Inject(method = { "doRender" },  at = { @At("HEAD") })
    public void doRenderPre(final T entity,  final double x,  final double y,  final double z,  final float entityYaw,  final float partialTicks,  final CallbackInfo info) {
        if (Chams.getInstance().isEnabled() && !(boolean)Chams.getInstance().colored.getValue() && entity != null) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f,  -1100000.0f);
        }
    }

    @Inject(method = { "doRender" },  at = { @At("RETURN") })
    public void doRenderPost(final T entity,  final double x,  final double y,  final double z,  final float entityYaw,  final float partialTicks,  final CallbackInfo info) {
        if (Chams.getInstance().isEnabled() && !(boolean)Chams.getInstance().colored.getValue() && entity != null) {
            GL11.glPolygonOffset(1.0f,  1000000.0f);
            GL11.glDisable(32823);
        }
    }

    static {
        glint = new ResourceLocation("textures/shinechams.png");
    }
}

