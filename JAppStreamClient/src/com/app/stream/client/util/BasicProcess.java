package com.app.stream.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicProcess {

    private final Runnable processBody;
    private final Thread process;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicProcess.class);

    public BasicProcess(Runnable processBody) {
        this.processBody = processBody;
        process = new Thread(this.processBody);
    }

    public Runnable getProcessBody() {
        return processBody;
    }

    public Thread getProcess() {
        return process;
    }

    public void startProcess() {
        this.process.start();
    }

    public boolean isAlive() {
        return process.isAlive();
    }
    
}
