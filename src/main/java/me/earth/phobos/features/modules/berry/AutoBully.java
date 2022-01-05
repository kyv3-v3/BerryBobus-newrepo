package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.util.TimerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author aux, ligmaballz, fixed by autism, fixed cause autism is stupid and optimized by v1_2 (delay, randomstrings and cleaner code in general)
 * additions: public chat(basically normal chat spammer) and private chat(/msg etc)
 * public chat is in works ill fix when i get back to stuff but module works normally
 * help from perry :D
 */
public class AutoBully extends Module {
    private final Setting<String> message = this.register(new Setting<String>("Message", "Your message >:)"));
    private final Setting<String> victim = this.register(new Setting<String>("Victim", "insert victim >:)"));
    private final Setting<Integer> delay = this.register(new Setting<Integer>("DelayInSecs", 2, 0, 10));
    private final Setting<Boolean> randomCharacters = this.register(new Setting<Boolean>("RandomCharacters", true));
    private final Setting<Boolean> publicChat = this.register(new Setting<Boolean>("PublicChat", false));
    private final Setting<Boolean> privateChat = this.register(new Setting<Boolean>("PrivateChat", false));
    private TimerUtil timer = new TimerUtil();
    public AutoBully() {
        super("AutoBully", "Big meanie, will make people very sad", Category.BERRY, true, false, false);
    }
    @SubscribeEvent
    public void onUpdate() {
        if (this.privateChat.getValue()) {
            if (this.timer.passedMs((long) this.delay.getValue() * 1000L)) {
                mc.player.sendChatMessage(("/msg " + victim.getValue() + " " + message.getValue() + (this.randomCharacters.getValue() ? (" | " + RandomStringUtils.random(5, true, true)) : "")));
                this.timer.reset();
            } else {
                if (this.publicChat.getValue()) {
                    if (this.timer.passedMs((long) this.delay.getValue() * 1000L)) {
                        mc.player.sendChatMessage((message.getValue() + (this.randomCharacters.getValue() ? (" | " + RandomStringUtils.random(5, true, false)) : "")));
                        this.timer.reset();
                    }
                }
            }
        }
    }
}



