package barilik.app.bluetooth.numpadserver;

import java.io.IOException;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.JOptionPane;

public class WaitThread implements Runnable {
    public static boolean IS_ALIVE = false;
    /**
     * Constructor
     */
    public WaitThread() {
    }

    @Override
    public void run() {
        waitForConnection();
    }

    /**
     * Waiting for connection from devices
     */
    private void waitForConnection() {
        // retrieve the local Bluetooth device object
        LocalDevice local = null;

        StreamConnectionNotifier notifier;
        StreamConnection connection = null;

        // setup the server to listen for connection
        try {
            local = LocalDevice.getLocalDevice();
            local.setDiscoverable(DiscoveryAgent.GIAC);

            UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
            System.out.println(uuid.toString());

            String url = "btspp://localhost:" + uuid.toString() + ";name=RemoteBluetooth";
            notifier = (StreamConnectionNotifier) Connector.open(url);
        } catch (BluetoothStateException e) {
            if(!LocalDevice.isPowerOn()){
                JOptionPane.showMessageDialog(null, e.getMessage()+"\n(Program sa ukončí)\n Tip: Zapni Bluetooth zariadenie");
                System.exit(0);
            }else {
                JOptionPane.showMessageDialog(null, e.getMessage()+"\nProgram sa ukončí\n Tip: Nepodporované zariadenie Bluetooth");
                System.exit(0);
            }
            e.printStackTrace();
            return;
        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        // waiting for connection
        while (true) {
            try {
                System.out.println("waiting for connection...");
                connection = notifier.acceptAndOpen();
                Thread processThread = new Thread(new ProcessConnectionThread(connection));
                processThread.start();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
