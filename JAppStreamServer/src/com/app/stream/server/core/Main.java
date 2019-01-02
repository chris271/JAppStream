package com.app.stream.server.core;

import com.app.stream.server.util.BasicProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class Main {

    private static JAppStreamServerCore serverCore = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    static boolean runApplication = true;

    public static void main(String[] args) {
        try {
            //Attempts to create a Thread which opens a JFrame window and add a graphical panel component.
            LOGGER.info("JAppStreamServerUI Application Started.");
            try {
                if (args.length != 3) {
                    LOGGER.info("Command Line Arguments Incomplete Or Not Entered.");
                    LOGGER.info("Three popups will appear asking for the required Information.");
                    serverCore = new JAppStreamServerCore(
                            JOptionPane.showInputDialog("Please choose a query to search for your application." +
                                    "\nYour input should match non-changing window title text and uniquely identify the window." +
                                    "\n(EX: 'Chrome', 'IntelliJ IDEA', or To Match Dolphin Emulator Use 'FPS' )"),
                            JOptionPane.showInputDialog("Please Enter The Hostname or IP Address For The Server." +
                                    "\n(EX: 'localhost', '127.0.0.1', etc.)"),
                            Integer.parseInt(JOptionPane.showInputDialog("Please Enter The Port Number For Connections." +
                                    "\n(EX: '1', '1500', '14151')")));
                } else {
                    serverCore = new JAppStreamServerCore(args[0], args[1], Integer.parseInt(args[2]));
                }

            } catch (Exception e) {
                LOGGER.error("Problem Loading Server Program. Try Changing The Host Or Port.");
                e.printStackTrace();
                System.exit(1);
            }
            new BasicProcess(serverCore).startProcess();
            while (runApplication) {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            LOGGER.info("JAppStreamServerUI Application Closed.");
        }
        System.exit(1);
    }

}
