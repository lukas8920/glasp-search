package org.kehrbusch.procedure.sentence;

public interface Stoppable {
    public void setInterruptedSignal();
    public boolean hasInterruptedSignal();
    public boolean isRunning();
}
