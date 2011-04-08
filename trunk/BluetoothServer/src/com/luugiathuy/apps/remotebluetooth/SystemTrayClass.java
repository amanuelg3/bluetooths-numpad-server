/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.luugiathuy.apps.remotebluetooth;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 *
 * @author mArtinko5MB
 */
public class SystemTrayClass {

    public SystemTrayClass(){
        if (!SystemTray.isSupported()) {
      System.out.println("SystemTray is not supported");
      return;
    }

    SystemTray tray = SystemTray.getSystemTray();
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage("C:\\Users\\mArtinko5MB\\Documents\\NetBeansProjects\\TrayExample1\\build\\images\\question.png");

    PopupMenu menu = new PopupMenu();

    MenuItem closeItem = new MenuItem("Ukončiť");
    closeItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    menu.add(closeItem);
    TrayIcon icon = new TrayIcon(image, "SystemTray Demo", menu);
    icon.setImageAutoSize(true);
        try {
            tray.add(icon);
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(null, "AWTException");
        }
  }
}
