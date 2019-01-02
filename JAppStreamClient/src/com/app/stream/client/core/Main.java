package com.app.stream.client.core;

import com.app.stream.client.ui.JAppStreamClientUI;
import com.app.stream.client.util.BasicProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class Main {

    private static JAppStreamClientUI clientUi = null;
    private static JAppStreamClientCore clientCore = null;
    private static JFrame frame = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            //Attempts to create a Thread which opens a JFrame window and add a graphical panel component.
            LOGGER.info("JAppStreamClientUI Application Started.");
            LOGGER.info("Opening GUI...");
            SwingUtilities.invokeLater(() -> {

                //Creates a new window named OS Sim
                frame = new JFrame("JAppStream Client");

                //Removes the JFrame on clicking close.
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final String host = JOptionPane.showInputDialog("Please enter the hostname or IP address to connect to." +
                        "\n(EX: 'localhost', '127.0.0.1', etc.)");
                final int port;
                try {
                    port = Integer.parseInt(JOptionPane.showInputDialog("Please enter the port number to connect to." +
                            "\n(EX: '1', '1500', '14151')"));
                    clientUi = new JAppStreamClientUI(host, port);
                } catch (Exception e) {
                    LOGGER.error("Problem Loading Client Program. Try Changing The Host Or Port.");
                    e.printStackTrace();
                    System.exit(1);
                }
                clientCore = new JAppStreamClientCore(clientUi);
                frame.setContentPane(clientUi);
                LOGGER.info("GUI Running.");
                frame.pack();
                frame.setVisible(true);
            });
            while (clientUi == null || !frame.isShowing()) {
                //Wait until the UI shows up.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new BasicProcess(clientCore).forceStartProcess(false);
            while (frame.isShowing()) {
                //Wait until the GUI window closes.
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LOGGER.info("JAppStreamClientUI Application Closed.");
        }
        System.exit(1);
    }



}
