package test.task.service;

public interface Logger {
    /**
     * Log some info
     */
    void info(String message);

    /**
     * Log some warnings
     */
    void warning(String message);

    /**
     * Log some errors
     */
    void error(String message);
}
