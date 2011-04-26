package barilik.app.bluetooth.numpadserver;

import javax.microedition.io.StreamConnection;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;

public class ProcessConnectionThread implements Runnable {

    private StreamConnection mConnection;
    private Robot robot;
    private Toolkit toolkit;
    private static ArrayList<Integer> allowedCommands = new ArrayList<Integer>(16);
    public static boolean I_AM_ALIVE = false;

    static {
        allowedCommands.add(new Integer(0x30));
        allowedCommands.add(new Integer(0x31));
        allowedCommands.add(new Integer(0x32));
        allowedCommands.add(new Integer(0x33));
        allowedCommands.add(new Integer(0x34));
        allowedCommands.add(new Integer(0x35));
        allowedCommands.add(new Integer(0x36));
        allowedCommands.add(new Integer(0x37));
        allowedCommands.add(new Integer(0x38));
        allowedCommands.add(new Integer(0x39));
        allowedCommands.add(new Integer(0x6A));
        allowedCommands.add(new Integer(0x2D));
        allowedCommands.add(new Integer(0x6B));
        allowedCommands.add(new Integer(0x6F));
        allowedCommands.add(new Integer(0x7F));
        allowedCommands.add(new Integer('\n'));
        allowedCommands.add(new Integer(0x2E));
        allowedCommands.add(new Integer(0x90));
        allowedCommands.add(new Integer('\b'));
    }

    public ProcessConnectionThread(StreamConnection connection) {
        mConnection = connection;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = mConnection.openInputStream();
            I_AM_ALIVE = true;
            System.out.println("waiting for input");
            robot = new Robot();
            toolkit = Toolkit.getDefaultToolkit();
            int command;

            boolean running = true;

            WaitThread.IS_ALIVE = true;
            do {
                command = inputStream.read();
                running = processCommand(command);
            } while (running);
            WaitThread.IS_ALIVE = false;
            I_AM_ALIVE = false;

            System.out.println("Device disconneted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Process the command from client
     *
     * @param command the command code
     */
    private boolean processCommand(int command) {
        boolean running = false;
        try {
            if (allowedCommands.contains(new Integer(command))) {
                if (command == KeyEvent.VK_NUM_LOCK) {
                    boolean newState = !toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
                    toolkit.setLockingKeyState(KeyEvent.VK_NUM_LOCK, newState);
                } else {
                    robot.keyPress(command);
                }
                running = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return running;
    }
}