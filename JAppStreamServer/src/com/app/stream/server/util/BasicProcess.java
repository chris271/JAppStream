package com.app.stream.server.util;

public class BasicProcess {

    private Runnable processBody = null;
    private Thread process = null;


    public BasicProcess() {
    }

    public BasicProcess(Runnable processBody) {
        this.processBody = processBody;
        process = new Thread(this.processBody);
    }

    public void setProcessBody(Runnable processBody) {
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
