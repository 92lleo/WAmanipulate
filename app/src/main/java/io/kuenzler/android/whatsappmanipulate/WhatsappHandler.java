package io.kuenzler.android.whatsappmanipulate;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author Leonhard Künzler
 * @version 0.1
 */
public class WhatsappHandler {

    /**
     * Enables or disables (freezes) whatsapp application.
     *
     * @param enabled true: enable, false: disable
     */
    public void setWhatsappEnabled(boolean enabled) {
        String command;
        if (enabled) {
            command = "pm enable com.whatsapp";
        } else {
            command = "pm disable com.whatsapp";
        }
        Utilities.runShellCommand(command);
    }

    /**
     * Get whatsapp enabled state.
     *
     * @return true if whatsapp is enabled, else false
     */
    public boolean getWhatsappEnabled() {
        Context ct = MainActivity.sContext;
        PackageManager pm = ct.getPackageManager();
        return (pm.getApplicationEnabledSetting("com.whatsapp") == PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    /**
     * Force-Stops whatsapp.
     */
    public void forceStopWhatsapp() {
        String command = "am force-stop com.whatsapp";
        Utilities.runShellCommand(command);
    }
}