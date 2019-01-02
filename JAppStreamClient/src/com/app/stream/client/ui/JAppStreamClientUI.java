package com.app.stream.client.ui;

import com.app.stream.client.util.UIMouseAdapter;
import com.app.stream.common.util.SerializableImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class JAppStreamClientUI extends JPanel {

    private final CopyOnWriteArrayList<Integer> keyCodes = new CopyOnWriteArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(JAppStreamClientUI.class);
    private final Robot robot;
    private final ServerSocket serverSocket;
    private final UIMouseAdapter uiMouseAdapter;
    private final int BASE_SCREEN_WIDTH = 1280;
    private final int BASE_SCREEN_HEIGHT = 720;
    private final double SCALING = 1.0;
    private final int BASE_WIDTH = (int) (SCALING * 300);
    private final int BASE_PADDING = (int) (SCALING * 80);
    private final String host;
    private final int port;
    private volatile int currentWidth = BASE_SCREEN_WIDTH;
    private volatile int currentHeight = BASE_SCREEN_HEIGHT;
    private double aspectXRatio = 1.0;
    private double aspectYRatio = 1.0;
    private int xLocBase = currentWidth / 2 - (int)(BASE_WIDTH * aspectXRatio) - (int)(BASE_WIDTH / 4 * aspectXRatio);
    private int yLocBase = currentHeight / 8;
    private int keyDelayer = 0;
    private volatile Rectangle uiRectangle = null;

    /**
     * Custom constructor for OSPanel Object.
     *
     * Creates and adds all UIElements to an ArrayList.
     * Sets the children for each button.
     *
     */
    public JAppStreamClientUI(String host, int port) throws AWTException, IOException {
        //Self explanatory
        this.setBackground(Color.WHITE);
        this.setSize(new Dimension(BASE_SCREEN_WIDTH, BASE_SCREEN_HEIGHT));
        this.setPreferredSize(new Dimension(BASE_SCREEN_WIDTH, BASE_SCREEN_HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.host = host;
        this.port = port;

        //Allow adding of new components to the panel in particular positions.
        setLayout(new BorderLayout());

        //Add custom MouseListener using MouseAdapter.
        uiMouseAdapter = new UIMouseAdapter();
        addMouseListener(uiMouseAdapter);
        addMouseWheelListener(uiMouseAdapter);

        //KeyListeners only work on focused windows.
        this.setFocusable(true);
        this.requestFocusInWindow();
        //Anonymous class for KeyListener.
        addKeyListener(getKeyListener());

        robot = new Robot();
        serverSocket = new ServerSocket(14151);
        ImageIO.setUseCache(false);
    }

    private KeyListener getKeyListener() {
        return new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyCodes.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //Sometimes multiple instances of the keycode get added so remove all.
                while(keyCodes.contains(e.getKeyCode()))
                    keyCodes.remove(keyCodes.indexOf(e.getKeyCode()));
            }

            @Override
            public void keyTyped (KeyEvent e) {
                //Must be implemented to satisfy KeyListener.
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        //Calls System Graphics Component.
        super.paintComponent(g2d);
        //Iterate over the GUI in order to repaint changes.
        performKeyEvents();
        drawUI(g2d);
        //Effectively recalls paintComponent(g);
        repaint();
        try {
            Thread.sleep(32);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void drawUI(Graphics2D g2d) {
        try {
            final Socket socket = serverSocket.accept();
            final ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            final SerializableImage image = (SerializableImage) objectInputStream.readObject();
            objectInputStream.close();
            g2d.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performKeyEvents() {
        final int KEY_TIMER_THRESHOLD = 6;
        if (keyDelayer < KEY_TIMER_THRESHOLD) {
            keyDelayer++;
        }
    }

    public void setUIRectangle(Rectangle uiRectangle) {
        this.uiRectangle = uiRectangle;
    }
}