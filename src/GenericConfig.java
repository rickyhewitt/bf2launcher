/*
 * BF2 Launcher
 * Generic.java
 * Copyright (C) 2009 Ricky Hewitt.
 *
 * Licensed under the GNU GPL version 3.
 * See http://www.gnu.org/licenses/ for
 * more information.
 */

package javabf2launcher;

import java.io.*;

/**
 * @author kahrn
 * The GenericConfig class handles all generic properties and methods,
 * deals with reading and writing the generic.con configuration file.
 */
public class GenericConfig {

    public static int drawFps = -1;

    /** Used for storing configuration file **/
    private static String strFullFile = "";

    /** Read the generic configuration file
        @param genericConfigPath The path to the general.con file.
     **/
    public static int readConfig(String genericConfigPath) {
        // Read the current file
        String strCurrentLine;
        // Read the config file
        try {
            FileInputStream fstream = new FileInputStream(genericConfigPath);
            DataInputStream datain = new DataInputStream(fstream);
            BufferedReader breader = new BufferedReader(new InputStreamReader(datain));
            while ((strCurrentLine = breader.readLine()) != null) {
                strFullFile += strCurrentLine + "\n";
            }

            datain.close();
        }

        catch (Exception ex) { Main.logError(ex.getStackTrace().toString()); return 0; }
        
        // Parse current file
        if (strFullFile.indexOf("renderer.drawFps") != -1) {
            drawFps = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("renderer.drawFps")+17, strFullFile.indexOf("renderer.drawFps")+18));
        }

        return 0;
    }

    /** Write the generic configuration file
        @param genericConfigPath The path to the general.con file.
     **/
    public static int writeConfig(String genericConfigPath) {
        // Write config file
        FileWriter fstream;
        try {
            fstream = new FileWriter(genericConfigPath);
            BufferedWriter bwriter = new BufferedWriter(fstream);

            // Write new file
            bwriter.write(strFullFile);
            if (strFullFile.indexOf("renderer.drawFps") == -1) {
                bwriter.write("renderer.drawFps " + drawFps);
            }

            bwriter.close();
        }
        
        catch (IOException ex) { Main.logError(ex.getStackTrace().toString()); }
        return 0;
    }
}


