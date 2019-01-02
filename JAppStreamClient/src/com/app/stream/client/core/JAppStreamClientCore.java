package com.app.stream.client.core;

import com.app.stream.client.ui.JAppStreamClientUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAppStreamClientCore implements Runnable {

    private final JAppStreamClientUI clientUI;
    private boolean exitApp = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(JAppStreamClientCore.class);

    JAppStreamClientCore(JAppStreamClientUI clientUI) {
        this.clientUI = clientUI;
    }

    public void run() {

        while (clientUI.isShowing() && !exitApp) {
            updateCurrentState();
            try {
                Thread.sleep(32);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void updateCurrentState() {

    }

}