package me.earth.phobos.util.hwid;

import me.earth.phobos.features.modules.render.AntiDump;
import me.earth.phobos.manager.HWIDManager;

/**
 * @author ligmaballz
 */
public class Yes {
    public static void NiggaCheck() {
        if (AntiDump.dumpDetected()) {
            try {
                HWIDManager.hwidCheck();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}      
