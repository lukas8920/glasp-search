package org.kehrbusch.procedure.sentence;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class TimeoutTask extends TimerTask {
    private static final Logger logger = Logger.getLogger(TimeoutTask.class.getName());

    private final Stoppable[] threads;
    private final Timer timer;

    public TimeoutTask(Stoppable[] thread, Timer timer) {
        this.threads = thread;
        this.timer = timer;
    }

    public void stopTimer(){
        this.timer.cancel();
    }

    @Override
    public void run() {
        if(threads != null) {
            for (Stoppable t : threads){
                if (t.isRunning()) t.setInterruptedSignal();
                logger.info("Interrupted all threads.");
            }
        }
    }
}
