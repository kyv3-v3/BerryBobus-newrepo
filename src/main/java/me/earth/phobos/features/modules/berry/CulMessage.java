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
 * //perry just pasted his amogus code and renamed the file lol
 * @since 12/28/2021.
 */
public
class CulMessage extends Module {
    public Setting <String> message = this.register(new Setting<>("Message", "message"));
    public Setting <String> url = this.register(new Setting <>("URL", "ur url"));
    public Setting <String> a = this.register(new Setting <>("A", "message"));
    public Setting <String> b = this.register(new Setting <>("B", "message"));

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
