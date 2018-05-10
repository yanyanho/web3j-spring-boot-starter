package org.web3j.spring.autoconfigure;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ExecutionTimeoutRule extends TestWatcher {
    public int timeout = 1;

    @Override
    protected void starting(Description description) {
        final ExecutionTimeout annotation = description.getAnnotation(ExecutionTimeout.class);
        if (annotation != null) {
            this.timeout = annotation.value();
        }
    }

    public int getTimeout() {
        return timeout;
    }
}
