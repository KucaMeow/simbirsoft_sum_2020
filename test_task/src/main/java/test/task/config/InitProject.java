package test.task.config;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class InitProject {
    /**
     * Makes needed dirs
     */
    public InitProject() {
        File downloadDir = new File("downloads");
        downloadDir.mkdir();
        File logDir = new File("logs");
        logDir.mkdir();
    }
}
