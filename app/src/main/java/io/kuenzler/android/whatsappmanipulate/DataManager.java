package io.kuenzler.android.whatsappmanipulate;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 *
 */
public class DataManager {

    private String sdPath, waPath, ownPath;
    private final MainActivity main;

    /**
     * @param main
     */
    public DataManager(MainActivity main) {
        this.main = main;
    }

    /**
     *
     */
    public void preparePaths() {
        /* String state = Environment.getExternalStorageState();
        File filesDir;
        // Make sure it's available
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            // filesDir = main.getExternalFilesDir(null);
            filesDir = main.getFilesDir();
        } else {
            // Load another directory, probably local memory
            filesDir = main.getFilesDir();

        }
        main.toast(filesDir.toString());*/
        // sdPath = activity.getContext().getexternalbla;
        // waPath = activity.getContext().getFilesDir();
        // ownPath = activity.getContext().getownbla;
        //TODO: do not hardcode
        sdPath = "/storage/emulated/0/msgstore.db";
        waPath = "/data/data/com.whatsapp/databases/msgstore.db";
        ownPath = "/data/data/io.kuenzler.android.whatsappmanipulate/msgstore.db";
    }

    /**
     * @param direction not used yet
     */
    public void copyWAdata(int direction) {
        if (!checkDirs()) {
            //  return;
        }
        String[] cmds = {"cat " + waPath + " > " + sdPath,
                "cat " + waPath + " > " + ownPath,
        };
        runAsRoot(cmds);
    }

    /**
     * @return
     */
    public boolean checkDirs() {
        boolean result = true;
        //result = new File(sdPath).exists();
        result = new File(waPath).exists() && result;
        //result = new File(ownPath).exists() && result;
        main.toast(String.valueOf(result));
        return result;
    }

    /**
     * @param cmds
     */
    protected void runAsRoot(String[] cmds) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
            main.toast("worked");
        } catch (IOException e) {
            Log.e("su", e.toString(), e);
            main.toast("ex: " + e.toString());
        } catch (Exception e) {
            //no root?
            Log.e("su", e.toString(), e);
            main.toast("ex: " + e.toString());
        }
    }
}
