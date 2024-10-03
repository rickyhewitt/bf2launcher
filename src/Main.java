/*
 * BF2 Launcher
 * Main.java
 * Copyright (C) 2009 Ricky Hewitt.
 *
 * Licensed under the GNU GPL version 3.
 * See http://www.gnu.org/licenses/ for
 * more information.
 * 
 * Created on 12 July 2009, 00:37
 */

package javabf2launcher;

import javax.swing.*;
import java.io.*;
import java.util.prefs.*;

// Required for exit-overide
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Ricky Hewitt [kahrny@gmail.com]
 * The Main class is responsible for initial
 * program execution and configuration, and loading
 * the initial gui.
 */
public class Main extends JFrame {

    /** The main JFrame handler. ***/
    public static MainJFrame newJFrame;
    // The tray handler
    public static Tray newTray;
    
    /** Load the preferences API **/
    public static Preferences prefs = Preferences.userNodeForPackage(Main.class);

    // Version string
    private static final String Version = "0.4";

    // Server IP and port
    public static String serverIP = "";
    public static String serverPort = "";

    // Username and Password
    public static String username = "";
    public static String password = "";
    
    /*** The location of the folder containing the BF2 components. ***/
    // Check for previous value
    public static String bf2folder = prefs.get("bf2folder", System.getenv("ProgramFiles") + "\\EA GAMES\\Battlefield 2\\");

    /** The filename for BF2. Default is BF2.exe. Must be in bf2folder.**/
    public static String bf2exe = "BF2.exe";

    /** The location of the BF2 Profile folder **/
    public static String bf2profilepath = System.getenv("USERPROFILE") + "\\My Documents\\Battlefield 2\\Profiles\\";

    /** Load main program structure before initializing the JFrame. **/
    public static void main(String[] args) {
        System.out.println("Application Started");
        // Start gui
        System.out.println("Loading GUI/Swing");

        // Change theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Main.logError(e.getStackTrace().toString());
        }
        
        // Show a development warning on first run..
        if (prefs.getBoolean("firstRun", true)) {
            JOptionPane.showMessageDialog(null, "This software is in development stage.\nMany features are incomplete and some may be broken.\nUse with caution.", "Caution!", JOptionPane.WARNING_MESSAGE);
            prefs.putBoolean("firstRun", false);
        }
        
        // Set the bf2folder location
        if (checkFolder(bf2folder) == 0) {
            JOptionPane.showMessageDialog(newJFrame, "The BF2 folder could not be found. Will now prompt for BF2 folder.\n", "BF2 Folder", JOptionPane.ERROR_MESSAGE);
            setbf2Folder(); 
        }

        // Set the bf2 profile location
        if (checkFolder(bf2profilepath) == 0) {
            JOptionPane.showMessageDialog(newJFrame, "The BF2 Profile folder could not be found. Will now prompt for BF2 Profile folder.\n", "BF2 Profile Folder", JOptionPane.ERROR_MESSAGE);
            setbf2ProfileFolder();
        }

        // Read generic configuration
        GenericConfig.readConfig(bf2profilepath + "\\0001\\General.con");

        // Read video configuration
        Video.readConfig(bf2profilepath + "\\0001\\Video.con");

        // Read audio configuration
        Audio.readConfig(bf2profilepath + "\\0001\\Audio.con");
        
        // Load main GUI
        newJFrame = new MainJFrame();
        newJFrame.setLocationRelativeTo(null);
        newJFrame.setVisible(true);

        // Initialize tray icon
        newTray = new Tray();

        // Configure close operation
        newJFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Custom window handling operations
        newJFrame.addWindowListener(new WindowAdapter() {
            // Custom window close operation
            public void windowClosing(WindowEvent e) {
                // Show confirmation dialog
                if (JOptionPane.showConfirmDialog(newJFrame, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_OPTION) == 0) {
                    System.exit(0);
                }
            }

            // Custom window minimize operation
            public void windowIconified(WindowEvent e) {
                newTray.hideInTray();
            }
        });
    }
    
    /** Set the location of the bf2 folder **/
    public static void setbf2Folder() {
        JFileChooser fileChooserBf2Folder = new JFileChooser();
        fileChooserBf2Folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileChooserOption = fileChooserBf2Folder.showOpenDialog(newJFrame);
        if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
            File fileBf2File = new File(fileChooserBf2Folder.getSelectedFile().getPath() + "\\BF2.exe");
            // Check that the directory exists and that bf2.exe is inside.
            if (fileChooserBf2Folder.getSelectedFile().isDirectory() && fileBf2File.isFile()) {
                bf2folder = fileChooserBf2Folder.getSelectedFile().getPath();
                prefs.put("bf2folder", bf2folder);
            }
            
            // Invalid bf2 folder!
            else {
                JOptionPane.showMessageDialog(newJFrame, "Invalid BF2 directory! Will now quit.", "Invalid BF2 directory", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        else {
            JOptionPane.showMessageDialog(newJFrame, "Invalid BF2 directory! Will now quit.", "Invalid BF2 directory", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /** Set the location of the bf2 profile folder **/
    public static void setbf2ProfileFolder() {
        JFileChooser fileChooserBf2ProfileFolder = new JFileChooser();
        fileChooserBf2ProfileFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileChooserOption = fileChooserBf2ProfileFolder.showOpenDialog(newJFrame);
        if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
            // Check that the directory exists and that the config files exist
            File fileBf2VideoFile = new File(fileChooserBf2ProfileFolder.getSelectedFile().getPath() + "\\0001\\Video.con");
            if (fileChooserBf2ProfileFolder.getSelectedFile().isDirectory() && fileBf2VideoFile.isFile()) {
                bf2profilepath = fileChooserBf2ProfileFolder.getSelectedFile().getPath();
                prefs.put("bf2profilefolder", bf2profilepath);
            }

            // Invalid bf2 folder!
            else {
                JOptionPane.showMessageDialog(newJFrame, "Invalid BF2 Profile Path! Will now quit.", "Invalid BF2 Profile", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }

        else {
            JOptionPane.showMessageDialog(newJFrame, "Invalid BF2 Profile Path! Will now quit.", "Invalid BF2 Profile", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    /** Checks the input folder to ensure it exists. Returns 1 on valid directory.
        @param folderPath The path of the folder.
     **/
    public static int checkFolder(String folderPath) {
        // Check to see if folder exists
        File w_folder = new File(folderPath);
        if (!w_folder.isDirectory()) {
            return 0;
        }
        
        else {
            return 1;
        }
    }

    /** Check to see if a mod exists in the mod folder.
        modname must be the foldername for the mod.
        e.g. "pr" for project reality, "xpack" for special forces.
        @param modName The modification directory name. e.g. "xpack" for special forces.
     **/
    public static boolean checkMod(String modName) {
        // Check to see if folder exists
        File mod_bf2folder = new File(bf2folder + "\\mods\\" + modName);
        System.out.println(mod_bf2folder.getPath());
        if (mod_bf2folder.isDirectory()) {
            return true;
        }
        
        else {
            return false;
        }
    }

    /** Return the version information **/
    public static String getVersionAsString() {
        return Version;
    }

    /** Convert an int to a boolean. **/
    public static boolean intToBool(int inputBool) {
        if (inputBool < 1) { return false; }
        else { return true; }
    }

    /** Browse to a web address. **/
    public static void openURL(String inputURL) {
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + inputURL);
        }

        catch (Exception e) { Main.logError(e.getStackTrace().toString(), 1); }
    }

    /** logError
     * Log error to file.
     * @param errorString The error to be logged.
     */
    public static void logError(String errorString) {
        FileWriter fstream;
        try {
            fstream = new FileWriter("error.log");
            BufferedWriter bwriter = new BufferedWriter(fstream);
            bwriter.write(errorString);
            bwriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** logError
     *  Error log will save error log to
     *  a file and print error.
     *  @param errorString The error to be logged.
     *  @param errorType The type of error to show:
     *  <pre>
     *   0 -- Do not show error
     *   1 -- Log and Show error using System.out.println
     *   2 -- Log and Show error using swing error dialog
     *   3 -- Log error and inform that an error occured
     *  </pre>
     */
    public static void logError(String errorString, int errorType) {
        logError(errorString);
        if (errorType == 1) {
            System.out.println(errorString);
        }

        if (errorType == 2) {
            JOptionPane.showMessageDialog(Main.newJFrame, errorString, "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (errorType == 3) {
            JOptionPane.showMessageDialog(Main.newJFrame, "An error has occured. The error has been saved to error.log.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
}
