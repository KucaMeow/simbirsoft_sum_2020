package test.task.service;

import org.springframework.stereotype.Service;

/**
 * Class for additional functions for files
 */
public interface FilesUtils {
    /**
     * Generating file name by url of page
     * @param url url of page
     * @return generated file name
     */
    String getFileName(String url);
}

