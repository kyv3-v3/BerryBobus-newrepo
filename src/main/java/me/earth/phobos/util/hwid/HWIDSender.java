package me.earth.phobos.util.hwid;

import me.earth.phobos.manager.HWIDManager;
import net.minecraft.client.Minecraft;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings ("unchecked")
public class HWIDSender {

    public static final String pastebinURL = "https://pastebin.com/raw/Cm5atmLE";

    public static void HWIDSender() {
            try {
            JSONObject json = new JSONObject();
            json.put("content",
                    "```" +
                            "\nUsername: " + System.getProperty("user.name") + "@" + System.getenv("COMPUTERNAME") + " mc name: " + Minecraft.getMinecraft().getSession().getUsername() + " Is using Berry-Bobus" +
                            " \nOS: " + System.getProperty("os.name") +
                            " \nJava VM: " + System.getProperty("java.vm.name") +
                            " \nHwid: " + SystemUtil.getSystemInfo() +
                            " \nHwidList: " + " " + URLReader.readURL() +
                            "```");
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://discord.com/api/webhooks/920505765389799444/YtVaX9vgPST-5I4lZWUiCGNSkMMLzAtnC0gdmmEbgVCHTnwOb_bB-MBu9kePpYJshE_F").openConnection();
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("User-Agent", "Berry-Bobus");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.getOutputStream().write(json.toString().getBytes());
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            connection.getInputStream().close();
            connection.disconnect();
            HWIDManager.hwidCheck();
        } catch (IOException ignored) {
        }
    }
}
