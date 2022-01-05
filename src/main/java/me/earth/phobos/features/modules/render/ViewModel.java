package me.earth.phobos.features.modules.render;

import me.earth.phobos.features.modules.*;
import me.earth.phobos.features.setting.*;
import me.earth.phobos.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ViewModel extends Module
{
    private static ViewModel INSTANCE;
    public Setting<Settings> settings;
    public Setting<Boolean> noEatAnimation;
    public Setting<Double> eatX;
    public Setting<Double> eatY;
    public Setting<Boolean> doBob;
    public Setting<Double> mainX;
    public Setting<Double> mainY;
    public Setting<Double> mainZ;
    public Setting<Double> offX;
    public Setting<Double> offY;
    public Setting<Double> offZ;
    public Setting<Integer> mainRotX;
    public Setting<Integer> mainRotY;
    public Setting<Integer> mainRotZ;
    public Setting<Integer> offRotX;
    public Setting<Integer> offRotY;
    public Setting<Integer> offRotZ;
    public Setting<Double> mainScaleX;
    public Setting<Double> mainScaleY;
    public Setting<Double> mainScaleZ;
    public Setting<Double> offScaleX;
    public Setting<Double> offScaleY;
    public Setting<Double> offScaleZ;

    public ViewModel() {
        super("ViewModel",  "Change the looks of items in 1st player view.",  Module.Category.RENDER,  true,  false,  false);
        this.settings = (Setting<Settings>)this.register(new Setting("Settings", Settings.TRANSLATE));
        this.noEatAnimation = (Setting<Boolean>)this.register(new Setting("NoEatAnimation", true,  v -> this.settings.getValue() == Settings.TWEAKS));
        this.eatX = (Setting<Double>)this.register(new Setting("EatX", 1.0, (-2.0), 5.0,  v -> this.settings.getValue() == Settings.TWEAKS && !this.noEatAnimation.getValue()));
        this.eatY = (Setting<Double>)this.register(new Setting("EatY", 1.0, (-2.0), 5.0,  v -> this.settings.getValue() == Settings.TWEAKS && !this.noEatAnimation.getValue()));
        this.doBob = (Setting<Boolean>)this.register(new Setting("ItemBob", true,  v -> this.settings.getValue() == Settings.TWEAKS));
        this.mainX = (Setting<Double>)this.register(new Setting("MainX", 1.2, (-2.0), 4.0,  v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainY = (Setting<Double>)this.register(new Setting("MainY", (-0.95), (-3.0), 3.0,  v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainZ = (Setting<Double>)this.register(new Setting("MainZ", (-1.45), (-5.0), 5.0,  v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offX = (Setting<Double>)this.register(new Setting("OffX", 1.2, (-2.0), 4.0,  v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offY = (Setting<Double>)this.register(new Setting("OffY", (-0.95), (-3.0), 3.0,  v -> this.settings.getValue() == Settings.TRANSLATE));
        this.offZ = (Setting<Double>)this.register(new Setting("OffZ", (-1.45), (-5.0), 5.0,  v -> this.settings.getValue() == Settings.TRANSLATE));
        this.mainRotX = (Setting<Integer>)this.register(new Setting("MainRotationX", 0, (-36), 36,  v -> this.settings.getValue() == Settings.ROTATE));
        this.mainRotY = (Setting<Integer>)this.register(new Setting("MainRotationY", 0, (-36), 36,  v -> this.settings.getValue() == Settings.ROTATE));
        this.mainRotZ = (Setting<Integer>)this.register(new Setting("MainRotationZ", 0, (-36), 36,  v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotX = (Setting<Integer>)this.register(new Setting("OffRotationX", 0, (-36), 36,  v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotY = (Setting<Integer>)this.register(new Setting("OffRotationY", 0, (-36), 36,  v -> this.settings.getValue() == Settings.ROTATE));
        this.offRotZ = (Setting<Integer>)this.register(new Setting("OffRotationZ", 0, (-36), 36,  v -> this.settings.getValue() == Settings.ROTATE));
        this.mainScaleX = (Setting<Double>)this.register(new Setting("MainScaleX", 1.0, 0.1, 5.0,  v -> this.settings.getValue() == Settings.SCALE));
        this.mainScaleY = (Setting<Double>)this.register(new Setting("MainScaleY", 1.0, 0.1, 5.0,  v -> this.settings.getValue() == Settings.SCALE));
        this.mainScaleZ = (Setting<Double>)this.register(new Setting("MainScaleZ", 1.0, 0.1, 5.0,  v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleX = (Setting<Double>)this.register(new Setting("OffScaleX", 1.0, 0.1, 5.0,  v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleY = (Setting<Double>)this.register(new Setting("OffScaleY", 1.0, 0.1, 5.0,  v -> this.settings.getValue() == Settings.SCALE));
        this.offScaleZ = (Setting<Double>)this.register(new Setting("OffScaleZ", 1.0, 0.1, 5.0,  v -> this.settings.getValue() == Settings.SCALE));
        this.setInstance();
    }

    public static ViewModel getInstance() {
        if (ViewModel.INSTANCE == null) {
            ViewModel.INSTANCE = new ViewModel();
        }
        return ViewModel.INSTANCE;
    }

    private void setInstance() {
        ViewModel.INSTANCE = this;
    }

    @SubscribeEvent
    public void onItemRender(final RenderItemEvent event) {
        event.setMainX((double)this.mainX.getValue());
        event.setMainY((double)this.mainY.getValue());
        event.setMainZ((double)this.mainZ.getValue());
        event.setOffX(-this.offX.getValue());
        event.setOffY((double)this.offY.getValue());
        event.setOffZ((double)this.offZ.getValue());
        event.setMainRotX((double)(this.mainRotX.getValue() * 5));
        event.setMainRotY((double)(this.mainRotY.getValue() * 5));
        event.setMainRotZ((double)(this.mainRotZ.getValue() * 5));
        event.setOffRotX((double)(this.offRotX.getValue() * 5));
        event.setOffRotY((double)(this.offRotY.getValue() * 5));
        event.setOffRotZ((double)(this.offRotZ.getValue() * 5));
        event.setOffHandScaleX((double)this.offScaleX.getValue());
        event.setOffHandScaleY((double)this.offScaleY.getValue());
        event.setOffHandScaleZ((double)this.offScaleZ.getValue());
        event.setMainHandScaleX((double)this.mainScaleX.getValue());
        event.setMainHandScaleY((double)this.mainScaleY.getValue());
        event.setMainHandScaleZ((double)this.mainScaleZ.getValue());
    }

    static {
        ViewModel.INSTANCE = new ViewModel();
    }

    private enum Settings
    {
        TRANSLATE,
        ROTATE,
        SCALE,
        TWEAKS;
    }
}