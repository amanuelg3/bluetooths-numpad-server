package com.luugiathuy.apps.remotebluetooth;

public class RemoteBluetoothServer extends SystemTrayClass{
	
	public static void main(String[] args) throws Exception {
                new RemoteBluetoothServer();
		Thread waitThread = new Thread(new WaitThread());
		waitThread.start();
	}
}
