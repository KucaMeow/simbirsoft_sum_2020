package test.task.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        //Here I init printWriter object into file with name of current date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd yyyy");
        Date date = new Date();
        String fileName = simpleDateFormat.format(date);
        File file = new File("./logs/" + fileName + ".log");
        try {
            file.createNewFile();
            printWriter = new PrintWriter(new FileOutputStream(file, true), true);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    //Here I just input needed message into PrintWriter with prefixes
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
