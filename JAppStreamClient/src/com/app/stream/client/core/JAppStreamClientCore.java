package com.app.stream.client.core;

import com.app.stream.client.ui.JAppStreamClientUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;

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
        try {
            clientUI.getKeyCodes().forEach(keyCode -> LOGGER.info("Key Pressed: " + KeyEvent.getKeyText(keyCode)));
        } catch (Exception e) {
            LOGGER.info("Failed To Update: ");
            e.printStackTrace();
        }
    }

}