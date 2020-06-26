package test.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class WebPageDownloaderImpl implements WebPageDownloader {

    @Autowired
    Logger log;

    /**
     * Uses java.net.URL class to connect to web page.
     * Using BufferedReader and PrintWriter writes it to html file
     * with using url as name
     * @param url url of page to download
     * @return true (or will throw exception)
     */
    @Override
    public boolean downloadAndSavePage(String url) {
        try {
            URL page;
            page = new URL(url);
            InputStream is = page.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            File pageFile = new File("/downloads/" + Math.abs(url.hashCode()) + ".html");
            log.info(pageFile.getAbsolutePath());
            pageFile.createNewFile();

            PrintWriter pw = new PrintWriter(
                    new FileOutputStream(
                            pageFile), true);
            while ((line = br.readLine()) != null) {
                pw.println(line);
            }
            br.close();
            pw.close();
            is.close();

            log.info("Page " + url + " has been successfully saved with name " + Math.abs(url.hashCode()));
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
        return true;
    }
}
