/*
 * BF2 Launcher
 * Video.java
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
 * The video class handles all video related properties and methods,
 * deals with reading and writing the video.con configuration file.
 */
public class Video {

    /** Resolution / Width (pixels) **/
    public static int videoWidth;
    /** Resolution / Height (pixels) **/
    public static int videoHeight;
    public static int refreshRate;
    public static int terrainQuality;
    public static int geometryQuality;
    public static int lightingQuality;
    public static int dynamicLightingQuality;
    public static int dynamicShadowsQuality;
    public static int effectsQuality;
    public static int textureQuality;
    public static int textureFilteringQuality;
    public static String antialiasing;
    public static int viewDistanceScale;
    public static int videoOptionScheme;

    /** Read the video config
        @param videoConfigPath The path to the video.con file.
     **/
    public static int readConfig(String videoConfigPath) {
        String strFullFile = "";
        String strCurrentLine;
        // Read the config file
        try {
            FileInputStream fstream = new FileInputStream(videoConfigPath);
            DataInputStream datain = new DataInputStream(fstream);
            BufferedReader breader = new BufferedReader(new InputStreamReader(datain));
            while ((strCurrentLine = breader.readLine()) != null) {
                strFullFile += strCurrentLine;
            }

            datain.close();
        }

        catch (Exception ex) { Main.logError(ex.getStackTrace().toString()); return 0; }

        // Parse the config file
        String resAsString = strFullFile.substring(strFullFile.indexOf("VideoSettings.setResolution")+28, strFullFile.indexOf("@"));
        videoWidth = Integer.parseInt(resAsString.substring(0, resAsString.indexOf("x")));
        videoHeight = Integer.parseInt(resAsString.substring(resAsString.indexOf("x")+1, resAsString.length()));
        refreshRate = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("@")+1, strFullFile.indexOf("@")+3));
        terrainQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setTerrainQuality")+32, strFullFile.indexOf("VideoSettings.setTerrainQuality")+33));
        geometryQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setGeometryQuality")+33, strFullFile.indexOf("VideoSettings.setGeometryQuality")+34));
        lightingQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setLightingQuality")+33, strFullFile.indexOf("VideoSettings.setLightingQuality")+34));
        dynamicLightingQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setDynamicLightingQuality")+40, strFullFile.indexOf("VideoSettings.setDynamicLightingQuality")+41));
        dynamicShadowsQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setDynamicShadowsQuality")+39, strFullFile.indexOf("VideoSettings.setDynamicShadowsQuality")+40));
        effectsQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setEffectsQuality")+32, strFullFile.indexOf("VideoSettings.setEffectsQuality")+33));
        textureQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setTextureQuality")+32, strFullFile.indexOf("VideoSettings.setTextureQuality")+33));
        textureFilteringQuality = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setTextureFilteringQuality")+41, strFullFile.indexOf("VideoSettings.setTextureFilteringQuality")+42));
        antialiasing = strFullFile.substring(strFullFile.indexOf("VideoSettings.setAntialiasing")+30, strFullFile.indexOf("VideoSettings.setAntialiasing")+33);
        //System.out.println("AA: " + antialiasing);
        viewDistanceScale = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setViewDistanceScale")+35, strFullFile.indexOf("VideoSettings.setViewDistanceScale")+36));
        videoOptionScheme = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("VideoSettings.setVideoOptionScheme")+35, strFullFile.indexOf("VideoSettings.setVideoOptionScheme")+36));
        
        return 1;
    }

    /** Write the video config
        @param videoConfigPath The path to the video.con file.
     **/
    public static int writeConfig(String videoConfigPath) {
        FileWriter fstream;
        try {
            fstream = new FileWriter(videoConfigPath);
            BufferedWriter bwriter = new BufferedWriter(fstream);

            // Write the new file
            bwriter.write("VideoSettings.setTerrainQuality " + terrainQuality + "\n");
            bwriter.write("VideoSettings.setGeometryQuality " + geometryQuality + "\n");
            bwriter.write("VideoSettings.setLightingQuality " + lightingQuality + "\n");
            bwriter.write("VideoSettings.setDynamicLightingQuality " + dynamicLightingQuality + "\n");
            bwriter.write("VideoSettings.setDynamicShadowsQuality " + dynamicShadowsQuality + "\n");
            bwriter.write("VideoSettings.setEffectsQuality " + effectsQuality + "\n");
            bwriter.write("VideoSettings.setTextureQuality " + textureQuality + "\n");
            bwriter.write("VideoSettings.setTextureFilteringQuality " + textureFilteringQuality + "\n");
            bwriter.write("VideoSettings.setResolution " + videoWidth + "x" + videoHeight + "@" + refreshRate + "\n");
            bwriter.write("VideoSettings.setAntialiasing " + antialiasing + "\n");
            bwriter.write("VideoSettings.setViewDistanceScale " + viewDistanceScale + "\n");
            bwriter.write("VideoSettings.setVideoOptionScheme " + videoOptionScheme + "\n");

            bwriter.close();
        }
        catch (IOException ex) { Main.logError(ex.getStackTrace().toString()); }
        return 0;
    }
}
