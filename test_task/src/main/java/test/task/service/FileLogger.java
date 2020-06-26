package test.task.service;

import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Logger which creates (or uses previous created file if exists)
 * txt log file with using current date as name of file
 * to log info from application
 */
@Service
public class FileLogger implements Logger{

    private PrintWriter printWriter;

    public FileLogger() {
        //TODO: Create/Open file and open here PrintWriter
        printWriter = new PrintWriter(System.out, true);
    }

    @Override
    public void info(String message) {
        printWriter.println("[INFO][" + (new Date()).toString() + "]: " + message);
        printWriter.flush();
    }

    @Override
    public void warning(String message) {
        printWriter.println("[WARNING][" + (new Date()).toString() + "]: " + message);
        printWriter.flush();
    }

    @Override
    public void error(String message) {
        printWriter.println();
        printWriter.println("[ERROR][" + (new Date()).toString() + "]: " + message);
        printWriter.println();
        printWriter.flush();
    }
}
