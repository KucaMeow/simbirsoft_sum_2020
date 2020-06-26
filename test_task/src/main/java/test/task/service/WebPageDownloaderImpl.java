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
    @Autowired
    FilesUtils filesUtils;

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
            //Create and init URL object
            URL page;
            page = new URL(url);
            //Open input stream in connection to page
            InputStream is = page.openStream();
            //Init buffered reader on input stream
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            //Get file name
            String fileName = filesUtils.getFileName(url);

            //Creating file which I'll use to save page content there
            File pageFile = new File("./downloads/" + fileName + ".html");
            pageFile.createNewFile();

            //Open print writer to created file with auto flush
            PrintWriter pw = new PrintWriter(
                    new FileOutputStream(
                            pageFile), true);
            //Read lines from page and write it into created file to save
            while ((line = br.readLine()) != null) {
                pw.println(line);
            }
            //Closing all streams
            br.close();
            pw.close();
            is.close();

            log.info("Page " + url + " has been successfully saved with name " + fileName + ".html");
            log.info("Saved in " + pageFile.getAbsolutePath());

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
