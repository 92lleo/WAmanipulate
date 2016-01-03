package io.kuenzler.android.whatsappmanipulate;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Manages all data moving and providings.
 *
 * @author Leonhard Künzler
 * @version 0.1
 */
public class DataManager {

    private String sdPath, waPath, ownPath;
    private final MainActivity main;

    /**
     * Creates new DataManager.
     *
     * @param main MainActivity object
     */
    public DataManager(MainActivity main) {
        this.main = main;
    }

    /**
     * Sets environment paths for data partitions.
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
        //TODO: do not hardcode, only for testing
        String dbpath = "/databases/msgstore.db";
        sdPath = "/storage/emulated/0/msgstore.db";
        waPath = "/data/data/com.whatsapp" + dbpath;
        ownPath = "/data/data/io.kuenzler.android.whatsappmanipulate" + dbpath;
    }

    /**
     * Copies wa database.
     * Copies the whatsapp database from or to whatsapp/whatsmanipulate directory
     *
     * @param direction 1: from wa to wm, 2 other direction
     */
    public void copyWAdatabase(int direction) {
        //prepare commands
        String command;
        if (direction == 1) {
            command = "cat " + waPath + " > " + ownPath;
        } else if (direction == 2) {
            command = "cat " + ownPath + " > " + waPath;
        } else {
            //two way street
            throw new IllegalArgumentException("direction has to be 1 or 2, was " + direction);
        }
        if (!checkDirs()) {
            //TODO: needed? //return;
        }
        //copy file
        Utilities.runShellCommandAsRoot(new String[]{command});
        //check if file is empty (copy error)
        try {
            BufferedReader br = new BufferedReader(new FileReader(ownPath));
            if (br.readLine() == null) {
                main.toast("Copied db is empty:( (dir " + direction + ")");
            }
        } catch (IOException e) {
            Log.e("SU Error", "Error while reading " + ownPath, e);
            e.printStackTrace();
        }
    }

    /**
     * Checks if directories exist.
     *
     * @return true if all exist
     */
    public boolean checkDirs() {
        boolean result = true;
        //result = new File(sdPath).exists();
        result = new File(waPath).exists() && result;
        //result = new File(ownPath).exists() && result;
        main.toast(String.valueOf(result)); //TODO: delete
        return result;
    }
}
