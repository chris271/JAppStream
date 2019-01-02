package com.app.stream.server.core;

import com.app.stream.common.util.SerializableImage;
import com.sun.jna.platform.WindowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JAppStreamServerCore implements Runnable {

    private final String appQuery;
    private static final Logger LOGGER = LoggerFactory.getLogger(JAppStreamServerCore.class);
    private final Robot robot;
    private final String host;
    private final int port;

    JAppStreamServerCore(String appQuery, String host, int port) throws AWTException {
        this.appQuery = appQuery;
        this.host = host;
        this.port = port;
        this.robot = new Robot();
    }

    public void run() {

        ImageIO.setUseCache(false);

        while (Main.runApplication) {
            updateCurrentState();
            try {
                Thread.sleep(32);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void updateCurrentState() {
        try {
            final SerializableImage screen = new SerializableImage(robot.createScreenCapture(
                    WindowUtils.getAllWindows(true).stream()
                            .filter(desktopWindow -> desktopWindow.getTitle().contains(appQuery))
                            .findFirst().get().getLocAndSize()));
            try {
                final Socket socket = new Socket(host, port);
                final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(screen);
                outputStream.flush();
                socket.close();
            } catch (IOException e) {
                LOGGER.error("Failed To Update Client Image");
                e.printStackTrace();
            }
        } catch (Exception e) {
            LOGGER.error("Failed To Update Window Rectangle");
            e.printStackTrace();
        }
    }
}