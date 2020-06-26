package test.task.service;

/**
 * Interface of object which should download and somehow save page from input url
 */
public interface WebPageDownloader {
    /**
     * Download and save page from ulr.
     * Should return true if page is downloaded and saved correctly.
     * Should return false if there was some problems while downloading or saving
     *
     * @param url url of page to download
     * @return true or false
     */
    boolean downloadAndSavePage(String url);
}
