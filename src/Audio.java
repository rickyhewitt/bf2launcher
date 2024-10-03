/*
 * BF2 Launcher
 * Audio.java
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
 * The audio class handles all audio related properties and methods,
 * deals with reading and writing the audio.con configuration file.
 */
public class Audio {

    public static int voipEnabled;
    public static int voipPlaybackVolume;
    public static int voipCaptureVolume;
    public static float voipCaptureThreshold;
    public static int voipBoostEnabled;
    public static int voipUseButton;
    public static String soundProvider;
    public static String soundQuality;
    public static int effectsVolume;
    public static int musicVolume;
    public static int helpVoiceVolume;
    public static int englishOnlyVoices;
    public static int enableEAX;

    /** Read the audio configuration file
        @param audioConfigPath The path to the audio.con file.
     **/
    public static int readConfig(String audioConfigPath) {
        String strFullFile = "";
        String strCurrentLine;
        // Read the config file
        try {
            FileInputStream fstream = new FileInputStream(audioConfigPath);
            DataInputStream datain = new DataInputStream(fstream);
            BufferedReader breader = new BufferedReader(new InputStreamReader(datain));
            while ((strCurrentLine = breader.readLine()) != null) {
                strFullFile += strCurrentLine;
            }

            datain.close();
        }

        catch (Exception ex) { Main.logError(ex.getStackTrace().toString()); return 0; }

        // Parse the config file
        voipEnabled = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setVoipEnabled")+29, strFullFile.indexOf("AudioSettings.setVoipEnabled")+30));
        voipPlaybackVolume = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setVoipPlaybackVolume")+36, strFullFile.indexOf("AudioSettings.setVoipPlaybackVolume")+37));
        voipCaptureVolume = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setVoipCaptureVolume")+35, strFullFile.indexOf("AudioSettings.setVoipCaptureVolume")+36));
        voipCaptureThreshold = Float.parseFloat(strFullFile.substring(strFullFile.indexOf("AudioSettings.setVoipCaptureThreshold")+38, strFullFile.indexOf("AudioSettings.setVoipCaptureThreshold")+41));
        voipBoostEnabled = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setVoipBoostEnabled")+34, strFullFile.indexOf("AudioSettings.setVoipBoostEnabled")+35));
        voipUseButton = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setVoipUsePushToTalk")+35, strFullFile.indexOf("AudioSettings.setVoipUsePushToTalk")+36));
        soundProvider = strFullFile.substring(strFullFile.indexOf("AudioSettings.setProvider")+26, strFullFile.indexOf("AudioSettings.setSoundQuality"));
        soundQuality = strFullFile.substring(strFullFile.indexOf("AudioSettings.setSoundQuality")+30, strFullFile.indexOf("AudioSettings.setEffectsVolume"));
        effectsVolume = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setEffectsVolume")+31, strFullFile.indexOf("AudioSettings.setEffectsVolume")+32));
        musicVolume = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setMusicVolume")+29, strFullFile.indexOf("AudioSettings.setMusicVolume")+30));
        helpVoiceVolume = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setHelpVoiceVolume")+33, strFullFile.indexOf("AudioSettings.setHelpVoiceVolume")+34));
        englishOnlyVoices = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setEnglishOnlyVoices")+35, strFullFile.indexOf("AudioSettings.setEnglishOnlyVoices")+36));
        enableEAX = Integer.parseInt(strFullFile.substring(strFullFile.indexOf("AudioSettings.setEnableEAX")+27, strFullFile.indexOf("AudioSettings.setEnableEAX")+28));
        
        return 1;
    }

    /** Write the audio configuration file
        @param audioConfigPath The path to the audio.con file.
     **/
    public static int writeConfig(String audioConfigPath) {
        FileWriter fstream;
        try {
            fstream = new FileWriter(audioConfigPath);
            BufferedWriter bwriter = new BufferedWriter(fstream);

            // Write the new file
            bwriter.write("AudioSettings.setVoipEnabled " + voipEnabled + "\n");
            bwriter.write("AudioSettings.setVoipPlaybackVolume " + voipPlaybackVolume + "\n");
            bwriter.write("AudioSettings.setVoipCaptureVolume " + voipCaptureVolume + "\n");
            bwriter.write("AudioSettings.setVoipCaptureThreshold " + voipCaptureThreshold + "\n");
            bwriter.write("AudioSettings.setVoipBoostEnabled " + voipBoostEnabled + "\n");
            bwriter.write("AudioSettings.setVoipUsePushToTalk " + voipUseButton + "\n");
            bwriter.write("AudioSettings.setProvider " + soundProvider + "\n");
            bwriter.write("AudioSettings.setSoundQuality " + soundQuality + "\n");
            bwriter.write("AudioSettings.setEffectsVolume " + effectsVolume + "\n");
            bwriter.write("AudioSettings.setMusicVolume " + musicVolume + "\n");
            bwriter.write("AudioSettings.setHelpVoiceVolume " + helpVoiceVolume + "\n");
            bwriter.write("AudioSettings.setEnglishOnlyVoices " + englishOnlyVoices + "\n");
            bwriter.write("AudioSettings.setEnableEAX " + enableEAX + "\n");

            bwriter.close();
        }
        catch (IOException ex) { Main.logError(ex.getStackTrace().toString()); }
        return 0;
    }
}
