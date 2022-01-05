package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import me.earth.phobos.features.modules.berry.Stuff;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;

/**
 * @author ligmaballz
 */
public class TestMessage extends Module {

    public Setting<String> message = this.register(new Setting<>("Message", "this is entirely made by copilot"));
    public Setting<String> url = this.register(new Setting <>("URL", "https://discord.com/api/webhooks/925349812143013988/mgpV6BkN_72iNUqqbklnYhJlMq_GVEfSg7LCiVhmcm2mtLzgb2iVzxI4ujoEcOnXwDxi"));

    public TestMessage() {
        super("TestMessage", "sends message to a url", Category.BERRY, true, false, false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        try {
            String url = this.url.getValue();
            String message = this.message.getValue();
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            con.setRequestProperty("Content-Length", String.valueOf(message.length()));
            con.getOutputStream().write(message.getBytes("UTF-8"));
            con.getOutputStream().flush();
            con.getOutputStream().close();
            con.getResponseCode();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void onRender() {
        url.getValue();
        toggle();
    }
}
