package me.earth.phobos.util.hwid;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public
class DisplayUtil {

    public static
    void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        throw new NoStackTraceThrowable("Verification was unsuccessful!");
    }

    /**
     * The frame if the HWID check was unsuccessful.
     */

    public static
    class Frame extends JFrame {
        public
        Frame() {
            this.setTitle("Verification failed.");
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            copyToClipboard();
            String message = "haha nigga, you are not on the HWID list." + "\n" + "HWID: " + SystemUtil.getSystemInfo() + "\n(Copied to clipboard.)";
            JOptionPane.showMessageDialog(this, message, "Send ur hwid to ligmaballz#6211 if u think this a mistake.", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
        }

        /**
         * The convenience function to automatically copy the HWID to clipboard.
         */

        public static
        void copyToClipboard() {
            StringSelection selection = new StringSelection(SystemUtil.getSystemInfo());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}