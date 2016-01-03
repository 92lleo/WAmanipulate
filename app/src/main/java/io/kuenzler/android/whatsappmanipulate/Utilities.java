package io.kuenzler.android.whatsappmanipulate;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Leonhard Künzler
 * @version 0.2
 */
public class Utilities {

    /**
     * @param cmd
     */
    public static void runShellCommand(String cmd) {
        runShellCommand(new String[]{cmd});
    }

    /**
     * @param cmds
     */
    public static void runShellCommand(String[] cmds) {
        if (cmds.length < 1) {
            return;
        }
        try {
            Process p = Runtime.getRuntime().exec(cmds[0] + "\n");
            if (cmds.length == 1) {
                return;
            }
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (int i = 1; i < cmds.length; i++) {
                os.writeBytes(cmds[i] + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            Log.e("su", e.toString(), e);
        } catch (Exception e) {
            //no root?
            Log.e("su", e.toString(), e);
        }
    }

    /**
     *
     * @param cmd
     */
    public static void runShellCommandAsRoot(String cmd) {
        runShellCommandAsRoot(new String[]{cmd});
    }

    /**
     * @param cmds
     */
    public static void runShellCommandAsRoot(String[] cmds) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            Log.e("su", e.toString(), e);
        } catch (Exception e) {
            //no root?
            Log.e("su", e.toString(), e);
        }
    }
}