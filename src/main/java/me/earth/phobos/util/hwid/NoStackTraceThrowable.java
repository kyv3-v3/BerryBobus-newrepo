package me.earth.phobos.util.hwid;

import me.earth.phobos.Phobos;

public
class NoStackTraceThrowable extends RuntimeException {

    public
    NoStackTraceThrowable(final String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public
    String toString() {
        return "" + Phobos.getVersion();
    }

    @Override
    public synchronized
    Throwable fillInStackTrace() {
        return this;
    }
}