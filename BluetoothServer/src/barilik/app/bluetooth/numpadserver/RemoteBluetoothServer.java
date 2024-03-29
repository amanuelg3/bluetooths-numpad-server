package barilik.app.bluetooth.numpadserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class RemoteBluetoothServer {
    private static WaitThread mainThread;

    public RemoteBluetoothServer() {
        String textConectedInfo;

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(getClass().getResource("/bluetooth_logo.png"));

        PopupMenu menu = new PopupMenu();


        MenuItem aboutItem = new MenuItem("O programe");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "[Bluetooth Numpad Server]\n" +
                        "Verzia 1.0 beta\n" +
                        "Zdrojový kód: " +
                        "http://code.google.com/p/bluetooths-numpad-server/\n" +
                        "Kontakt:" +
                        "martin.barilik@student.tuke.sk\n");
            }
        });
        MenuItem closeItem = new MenuItem("Ukončiť");
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ProcessConnectionThread.I_AM_ALIVE) {
                    JOptionPane.showMessageDialog(null, "Musíš najprv odpojiť zariadenie");
                } else {
                    System.exit(0);
                }
            }
        });
        menu.add(aboutItem);
        menu.add(closeItem);

        textConectedInfo = "Nepripojený";

        final TrayIcon icon = new TrayIcon(image, "Bluetooth NumPad Server\n" + "Status: " + textConectedInfo, menu);
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
                icon.setToolTip("Bluetooth NumPad Server\n" + "Status: " + (WaitThread.IS_ALIVE ? "Pripojený" : "Nepripojený"));
            }
        });

        mainThread = new WaitThread();
        Thread waitThread = new Thread(mainThread);
        waitThread.start();
    }

    public static void main(String[] args) throws Exception {
        new RemoteBluetoothServer();
    }
}
