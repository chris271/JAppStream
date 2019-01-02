package com.app.stream.server.core;

import com.app.stream.server.ui.JAppStreamServerUI;
import com.sun.jna.platform.WindowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAppStreamServerCore implements Runnable {

    private final JAppStreamServerUI serverUI;
    private final String appQuery;
    private static final Logger LOGGER = LoggerFactory.getLogger(JAppStreamServerCore.class);

    JAppStreamServerCore(JAppStreamServerUI clientUI, String appQuery) {
        this.serverUI = clientUI;
        this.appQuery = appQuery;
    }

    public void run() {

        while (serverUI.isShowing()) {
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
            serverUI.setUIRectangle(WindowUtils.getAllWindows(true).stream().filter(
                    desktopWindow -> desktopWindow.getTitle().contains(appQuery)).findFirst().get().getLocAndSize());
        } catch (Exception e) {
            LOGGER.info("Failed To Update: ");
            e.printStackTrace();
        }
    }
}