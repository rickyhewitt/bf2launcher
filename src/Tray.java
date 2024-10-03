/*
 * BF2 Launcher
 * Tray.java
 * Copyright (C) 2009 Ricky Hewitt.
 *
 * Licensed under the GNU GPL version 3.
 * See http://www.gnu.org/licenses/ for
 * more information.
 */
package javabf2launcher;

import java.awt.event.*;
import java.awt.*;

/**
 * @author kahrn
 * The tray class handles all system tray related
 * configuration and events.
 */
public class Tray {

    // TrayIcon
    public TrayIcon trayIcon;

    // Assemble the right-click popup menu.
    final PopupMenu popup = new PopupMenu();
    final MenuItem showItem = new MenuItem("Show");
    final MenuItem hideItem = new MenuItem("Hide");
    final MenuItem exitItem = new MenuItem("Exit");

    public Tray() {
        // Check to see if the SystemTray functionality is supported.
        if (SystemTray.isSupported()) {

            // Initialize the tray icon and set the image.
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("tray.gif");

            /** ActionListeners for the SystemTray. **/
            // Show Application
            ActionListener showListener = new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  Main.newJFrame.setVisible(true);
                  /** Modify show/hide menu
                   *  Each individual item must be reorganized to ensure
                   *  correct order.
                   */
                  popup.remove(showItem);
                  popup.remove(exitItem);
                  popup.add(hideItem);
                  popup.add(exitItem);
              }
            };

            // Hide Application
            ActionListener hideListener = new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  // Hide app window
                  Main.newJFrame.setVisible(false);
                  // Modify show/hide menu
                  popup.remove(hideItem);
                  popup.remove(exitItem);
                  popup.add(showItem);
                  popup.add(exitItem);
              }
            };

            // Exit Application
            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };

            // Define actions for right-click popup menu.
            showItem.addActionListener(showListener);
            hideItem.addActionListener(hideListener);
            exitItem.addActionListener(exitListener);
            // Add popup elements to popup menu.
            popup.add(hideItem);
            popup.add(exitItem);

            trayIcon = new TrayIcon(image, "BF2 Launcher", popup);

            // Performed when tray double-clicked.
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Show / Hide application window
                    if (Main.newJFrame.isVisible()) {
                        Main.newJFrame.setVisible(false);
                    }
                    else {
                        Main.newJFrame.setVisible(true);
                    }
                }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        }
    }

    /** hideInTray
     *  Minimizes the application window
     *  to the system tray.
     */
    public void hideInTray() {
          // Hide app window
          Main.newJFrame.setVisible(false);
          // Modify show/hide menu
          popup.remove(hideItem);
          popup.remove(exitItem);
          popup.add(showItem);
          popup.add(exitItem);
    }

    /** showFromTray
     *  Brings the application window
     *  from the system tray.
     */
    public void showFromTray() {
          // Hide app window
          Main.newJFrame.setVisible(false);
          // Modify show/hide menu
          popup.remove(showItem);
          popup.remove(exitItem);
          popup.add(hideItem);
          popup.add(exitItem);
    }
}
