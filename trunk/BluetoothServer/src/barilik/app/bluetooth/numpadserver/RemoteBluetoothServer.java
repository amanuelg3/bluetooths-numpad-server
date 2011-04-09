package barilik.app.bluetooth.numpadserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RemoteBluetoothServer {

    private static WaitThread mainThread;

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
        textConectedInfo = "Nepripojený";

        final TrayIcon icon = new TrayIcon(image, "Bluetooth NumPad Server\n"+"Status: "+textConectedInfo, menu);
        icon.setImageAutoSize(true);
        try {
            tray.add(icon);
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(null, "AWTException");
        }

        icon.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setTooltipText();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                setTooltipText();
            }

            private void setTooltipText() {
                icon.setToolTip("Bluetooth NumPad Server\n"+"Status: " + (WaitThread.IS_ALIVE ? "Pripojený" : "Nepripojený"));
            }
        });

        mainThread = new WaitThread();
        Thread waitThread = new Thread(mainThread);
        waitThread.start();
    }


}
