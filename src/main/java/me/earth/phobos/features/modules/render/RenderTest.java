package me.earth.phobos.features.modules.render;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.modules.client.Components;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
//import net.minecraft.*;

public class RenderTest
        extends Module {

    public Setting<Boolean> imageNft = this.register(new Setting<Boolean>("ImageNft", false));
    public static ResourceLocation nft = new ResourceLocation("textures/nft.png");
    public Setting<Integer> imageX = this.register(new Setting<Object>("ImageX", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(1000), v -> this.imageNft.getValue()));
    public Setting<Integer> imageY = this.register(new Setting<Object>("ImageY", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(1000), v -> this.imageNft.getValue()));
    public Setting<Integer> imageWidth = this.register(new Setting<Object>("ImageWidth", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000), v -> this.imageNft.getValue()));
    public Setting<Integer> imageHeight = this.register(new Setting<Object>("ImageHeight", Integer.valueOf(100), Integer.valueOf(0), Integer.valueOf(1000), v -> this.imageNft.getValue()));


    public RenderTest() {
        super("NftTest", "RenderTest", Category.RENDER, true, false, false);
    }

    public void drawImageNft() {
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        ResourceLocation nft = null;
        mc.getTextureManager().bindTexture(nft);
        Components.drawCompleteImage(this.imageX.getValue(), this.imageY.getValue(), this.imageWidth.getValue(), this.imageHeight.getValue());
        mc.getTextureManager().deleteTexture(nft);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
    }
}

