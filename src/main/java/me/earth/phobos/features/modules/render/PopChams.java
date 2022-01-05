package me.earth.phobos.features.modules.render;

import com.mojang.authlib.GameProfile;
import me.earth.phobos.event.events.Render3DEvent;
import me.earth.phobos.event.events.RenderEntityModelEvent;
import me.earth.phobos.event.events.TotemPopEvent;
import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.modules.client.Colors;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.MathUtil;
import me.earth.phobos.util.RenderUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public
class PopChams
        extends Module {
    private static PopChams INSTANCE = new PopChams();
    private final Setting <Integer> duration = this.register(new Setting <>("Duration", 2500, 100, 10000));
    private final Setting <Boolean> fade = this.register(new Setting <>("Fade", true));
    private final Setting <Boolean> still = this.register(new Setting <>("Static", true));
    private final Setting <Boolean> heaven = this.register(new Setting <>("Heaven", false, v -> this.still.getValue()));
    private final Setting <Double> ascension = this.register(new Setting <>("Movement", 1.0, 0.1, 4.0, v -> this.heaven.getValue() && this.still.getValue()));
    private final Setting <Boolean> outline = this.register(new Setting <>("Outline", true));
    private final Setting <Boolean> fill = this.register(new Setting <>("Fill", true));
    private final Setting <Mode> mode = this.register(new Setting <>("OutlineMode", Mode.WIREFRAME, v -> this.outline.getValue()));
    private final Setting <Float> lineWidth = this.register(new Setting <>("LineWidth", 3.0f, 0.1f, 6.0f, v -> this.outline.getValue()));
    private final Setting <Integer> oAlpha = this.register(new Setting <>("OAlpha", 255, 0, 255, v -> this.outline.getValue()));
    private final Setting <Integer> fAlpha = this.register(new Setting <>("FAlpha", 50, 0, 255, v -> this.fill.getValue()));
    private final Setting <Boolean> colorSync = this.register(new Setting <>("CSync", false, v -> this.fill.getValue() || this.outline.getValue()));
    private final Setting <Boolean> rainbow = this.register(new Setting <>("Rainbow", false, v -> this.fill.getValue() || this.outline.getValue()));
    private final Setting <Integer> oRed = this.register(new Setting <>("ORed", 255, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.outline.getValue()));
    private final Setting <Integer> oGreen = this.register(new Setting <>("OGreen", 0, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.outline.getValue()));
    private final Setting <Integer> oBlue = this.register(new Setting <>("OBlue", 180, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.outline.getValue()));
    private final Setting <Integer> fRed = this.register(new Setting <>("FRed", 255, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.fill.getValue()));
    private final Setting <Integer> fGreen = this.register(new Setting <>("FGreen", 0, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.fill.getValue()));
    private final Setting <Integer> fBlue = this.register(new Setting <>("FBlue", 180, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.fill.getValue()));
    private final Setting <Boolean> visColor = this.register(new Setting <>("VColor", false, v -> !this.rainbow.getValue() && !this.colorSync.getValue() && this.fill.getValue()));
    private final Setting <Integer> vRed = this.register(new Setting <>("VRed", 50, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.visColor.getValue() && this.fill.getValue()));
    private final Setting <Integer> vGreen = this.register(new Setting <>("VGreen", 255, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.visColor.getValue() && this.fill.getValue()));
    private final Setting <Integer> vBlue = this.register(new Setting <>("VBlue", 180, 0, 255, v -> !this.colorSync.getValue() && !this.rainbow.getValue() && this.visColor.getValue() && this.fill.getValue()));
    private final Setting <Integer> speed = this.register(new Setting <>("Speed", 40, 1, 100, v -> this.rainbow.getValue() && !this.colorSync.getValue()));
    private final Setting <Integer> saturation = this.register(new Setting <>("Saturation", 65, 0, 100, v -> this.rainbow.getValue() && !this.colorSync.getValue()));
    private final Setting <Integer> brightness = this.register(new Setting <>("Brightness", 100, 0, 100, v -> this.rainbow.getValue() && !this.colorSync.getValue()));
    private final List <PopChamContext> badPlayers = new ArrayList <>();

    public
    PopChams() {
        super("Bush's PopChams", "Puts chams over ppl popping.", Category.RENDER, true, false, false);
        this.setInstance();
    }

    public static
    PopChams getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PopChams();
        }
        return INSTANCE;
    }

    private
    void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public
    void onTotemPop(TotemPopEvent event) {
        if (!this.still.getValue())
            this.badPlayers.removeIf(player -> player.getPlayer().equals(event.getEntity()));
        this.badPlayers.add(new PopChamContext(event.getEntity(), event.getEntity().getPrimaryHand()));
    }

    @Override
    public
    void onTick() {
        this.badPlayers.removeIf(player -> player.getTime() > this.duration.getValue());
        this.badPlayers.forEach(PopChamContext::incrementTime);
    }

    @Override
    public
    void onRender3D(Render3DEvent e) {
        if (!this.still.getValue()) return;
        this.badPlayers.stream().filter(player -> player.getEvent() != null).collect(Collectors.toList()).forEach(player -> {
            double[] offset = player.getOffset();
            float factor = (float) (this.duration.getValue() - player.getTime()) / this.duration.getValue();
            double ascension = this.heaven.getValue() ? (factor - 1) * this.ascension.getValue() : 0;
            RenderEntityModelEvent event = player.getEvent();
            GlStateManager.pushMatrix();
            GlStateManager.translate(offset[0], offset[1] - ascension, offset[2]);
            GlStateManager.rotate(180, 1, 0, 0);
            GlStateManager.rotate((event.entity.rotationYaw - event.headYaw), 0, 1, 0);
            this.doThing(player.getEvent(), factor);
            GlStateManager.popMatrix();
        });
    }

    public
    void onRenderModel(RenderEntityModelEvent event) { // called in MixinRenderLivingBase
        if (this.still.getValue()) {
            this.badPlayers.stream().filter(player -> player.getPlayer().equals(event.entity) && player.getEvent() == null)
                    .forEach(player -> player.setEvent(event)); // gives the event to each PopChamContext so it can render
            return;
        }
        this.badPlayers.stream()
                .filter(player -> player.getPlayer().equals(event.entity))
                .findFirst().ifPresent(context -> {
                    this.doThing(event, (float) (this.duration.getValue() - context.getTime()) / this.duration.getValue());
                    event.setCanceled(true);
                });
    }

    private
    void doThing(RenderEntityModelEvent event, float factor) {
        if (event == null) return;
        if (this.fill.getValue()) {
            int alpha = MathUtil.clamp((int) ((this.fade.getValue() ? factor : 1) * this.fAlpha.getValue()), 0, 255);
            Color fColor = this.colorSync.getValue()
                    ? new Color( // colorsync
                    Colors.INSTANCE.getCurrentColor().getRed(),
                    Colors.INSTANCE.getCurrentColor().getGreen(),
                    Colors.INSTANCE.getCurrentColor().getBlue(), alpha)
                    : this.rainbow.getValue()
                    ? RenderUtil.getRainbowAlpha( // rainbow
                    this.speed.getValue() * 100, 0,
                    (float) this.saturation.getValue() / 100,
                    (float) this.brightness.getValue() / 100, alpha)
                    : new Color(this.fRed.getValue(), this.fGreen.getValue(), this.fBlue.getValue(), alpha); // normal
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false); // disables depth check to render through walls
            GL11.glEnable(10754);
            RenderUtil.setColor(fColor);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true); // enables depth check to render visible color
            Color vColor = new Color(this.vRed.getValue(), this.vGreen.getValue(), this.vBlue.getValue(), alpha);
            if (this.visColor.getValue() && !this.rainbow.getValue() && !this.colorSync.getValue()) // could add a visible rainbow option but not really important
                RenderUtil.setColor(vColor);
            // for some reason some things render in front of
            // normal chams so im doing a second pass in the same
            // color to stop particles and etc from showing through
            // even if visColor is false. ( happens in normal chams too )
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
        }
        if (this.outline.getValue()) { // could add visible color but its not really important
            boolean fancyGraphics = PopChams.mc.gameSettings.fancyGraphics;
            float gamma = PopChams.mc.gameSettings.gammaSetting;
            PopChams.mc.gameSettings.fancyGraphics = false;
            PopChams.mc.gameSettings.gammaSetting = 10000.0f;
            int alpha = MathUtil.clamp((int) ((this.fade.getValue() ? factor : 1) * this.oAlpha.getValue()), 0, 255);
            Color oColor = this.colorSync.getValue()
                    ? new Color( // colorsync
                    Colors.INSTANCE.getCurrentColor().getRed(),
                    Colors.INSTANCE.getCurrentColor().getGreen(),
                    Colors.INSTANCE.getCurrentColor().getBlue(), alpha)
                    : this.rainbow.getValue()
                    ? RenderUtil.getRainbowAlpha( // rainbow
                    this.speed.getValue() * 100, 0,
                    (float) this.saturation.getValue() / 100,
                    (float) this.brightness.getValue() / 100, alpha)
                    : new Color(this.oRed.getValue(), this.oGreen.getValue(), this.oBlue.getValue(), alpha); // normal
            if (this.mode.getValue() == Mode.OUTLINE) {
                if (!this.fill.getValue() && !this.still.getValue())
                    event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
                RenderUtil.renderOne(this.lineWidth.getValue());
                event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
                GlStateManager.glLineWidth(this.lineWidth.getValue());
                RenderUtil.renderTwo();
                event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
                GlStateManager.glLineWidth(this.lineWidth.getValue());
                RenderUtil.renderThree();
                RenderUtil.renderFour(oColor);
                event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
                GlStateManager.glLineWidth(this.lineWidth.getValue());
                RenderUtil.renderFive();
            } else {
                if (!this.fill.getValue() && !this.still.getValue())
                    event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6913);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GlStateManager.blendFunc(770, 771);
                RenderUtil.setColor(oColor);
                GlStateManager.glLineWidth(this.lineWidth.getValue());
                event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            try {
                PopChams.mc.gameSettings.fancyGraphics = fancyGraphics;
                PopChams.mc.gameSettings.gammaSetting = gamma;
            } catch (Exception ignored) {
            }
        }
    }

    public
    enum Mode {
        OUTLINE,
        WIREFRAME,
    }

    /**
     * Only used for static mode, this copies entity attributes such as
     * swing progress and angles that ModelBase.render needs to render
     * a player separately in onRender3d
     */
    static
    class PopChamContext {
        private final EntityPlayer player;
        private final EnumHandSide hand;
        private int time; // could be redone with a timer but not really important
        private RenderEntityModelEvent event;
        private double[] pos;

        /**
         * Called on a totem pop to create the event.
         * Other information is added when onRenderModel
         * is called in MixinRenderLivingBase.
         */
        public
        PopChamContext(EntityPlayer player, EnumHandSide hand) {
            this.player = player;
            this.hand = hand.opposite(); // ?????
        }

        public
        EntityPlayer getPlayer() {
            return this.player;
        }

        public
        int getTime() {
            return this.time;
        }

        public
        void incrementTime() {
            this.time += 50;
        }

        public
        RenderEntityModelEvent getEvent() {
            return this.event;
        }

        /**
         * "Completes" the event and adds an Entity and
         * ModelBase with the same animation progress as
         * when the player pops a totem, kind of like a
         * "snapshot" of that player.
         */
        public
        void setEvent(RenderEntityModelEvent event) {
            if (this.event != null)
                return; // prevents overwriting already built events since this is called multiple times
            EntityOtherPlayerMP entity = new EntityOtherPlayerMP(PopChams.mc.world, new GameProfile(event.entity.getUniqueID(), "Cr33pyl3mon4de"));
            entity.copyLocationAndAnglesFrom(event.entity);
            entity.rotationYaw = event.entity.rotationYaw;
            entity.setSneaking(event.entity.isSneaking());
            if (event.entity.isSneaking()) entity.posY -= 0.1;
            entity.setPrimaryHand(this.hand);
            ModelPlayer player = new ModelPlayer(event.scale, !entity.getSkinType().equals("default")); // checks for slim arms
            player.setModelAttributes(event.modelBase);
            player.setRotationAngles(event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale, event.entity);
            this.event = new RenderEntityModelEvent(
                    event.getStage(),
                    player,
                    entity,
                    event.limbSwing,
                    event.limbSwingAmount,
                    event.age,
                    event.headYaw,
                    event.headPitch,
                    event.scale * 0.925f // no idea why
            );
            this.pos = new double[]{this.event.entity.posX, this.event.entity.posY, this.event.entity.posZ};
        }

        public
        double[] getOffset() {
            if (this.pos != null) return new double[]{
                    this.pos[0] - PopChams.mc.getRenderManager().viewerPosX,
                    (this.pos[1] - PopChams.mc.getRenderManager().viewerPosY) + 1.41,
                    // 1.41 isn't perfect but isn't noticeable
                    this.pos[2] - PopChams.mc.getRenderManager().viewerPosZ
            };
            return new double[]{0, 100, 0}; // default if called before the event was built
        }
    }
}

