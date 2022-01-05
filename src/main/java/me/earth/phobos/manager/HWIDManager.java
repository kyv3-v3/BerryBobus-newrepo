package me.earth.phobos.manager;

import me.earth.phobos.util.hwid.DisplayUtil;
import me.earth.phobos.util.hwid.NoStackTraceThrowable;
import me.earth.phobos.util.hwid.SystemUtil;
import me.earth.phobos.util.hwid.URLReader;

import java.util.ArrayList;
import java.util.List;

public
class HWIDManager {

    /**
     * Your pastebin URL goes inside the empty string below.
     * It should be a raw pastebin link, for example: pastebin.com/raw/pasteid
     * fixed your hwids
     * added my alts to the paste aswell as a new pc
     */

    public static final String pastebinURL = "https://pastebin.com/raw/Cm5atmLE";

    public static List <String> hwids = new ArrayList <>();

    public static
    void hwidCheck() {
        hwids = URLReader.readURL();
        boolean isHwidPresent = hwids.contains(SystemUtil.getSystemInfo());
        if (! isHwidPresent) {
            DisplayUtil.Display();
            throw new NoStackTraceThrowable("");
        }
    }
}
