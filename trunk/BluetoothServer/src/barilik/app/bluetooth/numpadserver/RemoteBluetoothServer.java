package barilik.app.bluetooth.numpadserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoteBluetoothServer {

    public static void main(String[] args) throws Exception {
        String textConectedInfo;

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("bluetooth_logo.png");

        PopupMenu menu = new PopupMenu();

        MenuItem closeItem = new MenuItem("Ukončiť");
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(closeItem);
        if (WaitThread.IS_ALIVE){
            textConectedInfo = "Pripojený";
        }else{
            textConectedInfo = "Nepripojený";
        }
        TrayIcon icon = new TrayIcon(image, "Bluetooth NumPad Server\n"+"Status: "+textConectedInfo, menu);
        icon.setImageAutoSize(true);
        try {
            tray.add(icon);
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(null, "AWTException");
        }


        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();
    }
}
