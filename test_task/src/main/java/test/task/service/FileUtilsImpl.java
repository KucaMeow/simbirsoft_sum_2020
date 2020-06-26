package test.task.service;

import org.springframework.stereotype.Service;

@Service
public class FileUtilsImpl implements FilesUtils {

    /**
     * Replace all '/' to '_' in url
     */
    @Override
    public String getFileName(String url) {
        return url.replaceAll("/", "_");
    }
}
