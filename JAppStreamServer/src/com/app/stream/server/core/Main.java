package com.app.stream.server.core;

import com.app.stream.server.ui.JAppStreamServerUI;
import com.app.stream.server.util.BasicProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class Main {

    private static JAppStreamServerUI serverUi = null;
    private static JAppStreamServerCore serverCore = null;
    private static JFrame frame = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            //Attempts to create a Thread which opens a JFrame window and add a graphical panel component.
            LOGGER.info("JAppStreamServerUI Application Started.");
            LOGGER.info("Opening GUI...");
            SwingUtilities.invokeLater(() -> {

                //Creates a new window named OS Sim
                frame = new JFrame("JAppStream Server");

                //Removes the JFrame on clicking close.
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                final String host = JOptionPane.showInputDialog("Please Enter The Hostname or IP Address For The Server." +
                        "\n(EX: 'localhost', '127.0.0.1', etc.)");
                final int port;
                try {
                    port = Integer.parseInt(JOptionPane.showInputDialog("Please Enter The Port Number For Connections." +
                            "\n(EX: '1', '1500', '14151')"));
                    serverUi = new JAppStreamServerUI(host, port);
                } catch (Exception e) {
                    LOGGER.error("Problem Loading Server Program. Try Changing The Host Or Port.");
                    e.printStackTrace();
                    System.exit(1);
                }
                serverCore = new JAppStreamServerCore(serverUi,
                        JOptionPane.showInputDialog("Please choose a query to search for your application." +
                                "\nYour input should match non-changing window title text and uniquely identify the window." +
                                "\n(EX: 'Chrome', 'IntelliJ IDEA', or To Match Dolphin Emulator Use 'FPS' )"));
                frame.setContentPane(serverUi);
                LOGGER.info("GUI Running.");
                frame.pack();
                frame.setVisible(true);
            });
            while (serverUi == null || !frame.isShowing()) {
                //Wait until the UI shows up.
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            new BasicProcess(serverCore).forceStartProcess(false);
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
            LOGGER.info("JAppStreamServerUI Application Closed.");
        }
        System.exit(1);
    }



}
