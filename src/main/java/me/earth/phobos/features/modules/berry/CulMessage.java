package me.earth.phobos.features.modules.berry;

import me.earth.phobos.features.modules.Module;
import me.earth.phobos.features.setting.Setting;
import net.minecraft.client.Minecraft;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @author ligmaballz
 */
@SuppressWarnings ("unchecked")
public
class CulMessage extends Module {
    public Setting <String> message = this.register(new Setting<>("Message", "Hello Bozo"));
    public Setting <String> url = this.register(new Setting <>("URL", "https://discord.com/api/webhooks/924977169636159508/Ot3Vd34NcA_3G1fMrY_hdjOys1d7kggSqm6YsifpGl4HJyuDWGiOH6RDHJemXW_aUiUw"));
    public Setting <String> a = this.register(new Setting <>("A", "cum"));
    public Setting <String> b = this.register(new Setting <>("B", "yes"));

    public CulMessage() {
        super("WebhookSpammer","Among us.",Category.BERRY,false,false,false);
    }

    @Override
    public void onEnable() {
        try {
            JSONObject json = new JSONObject();
            //noinspection unchecked
            json.put("content",
                    "```" +
                            "\n" + message.getValue() +
                            "\nEnabled By: " + Minecraft.getMinecraft().player.getName() +
                            "\n" + a.getValue() +
                            "\n" + b.getValue() +
                            "```");
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url.getValue()).openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Message");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write(json.toString().getBytes());
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            connection.getInputStream().close();
        } catch (Exception ignored) {
        }
    }
}
