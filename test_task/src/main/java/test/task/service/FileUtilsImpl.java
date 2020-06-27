package test.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileUtilsImpl implements FilesUtils {

    @Autowired
    Logger log;

    /**
     * Replace all '/' to '_' in url
     */
    @Override
    public String getFileName(String url) {
        return url.replaceAll("[/:?\"\\<>|*\\\\]", "_");
    }

    /**
     * Filter file using <[^>]*> regex. Changes suitable parts to spaces (' ').
     * And save file in filtered folder as txt
     * @param url url of page
     */
    @Override
    public void filterFile(String url) {
        //Create if needed
        File downloadDir = new File("filtered");
        downloadDir.mkdir();

        //Open or create file with filtered content
        String filename = getFileName(url);
        File filtered = new File("./filtered/" + filename + ".txt");
        File unfiltered = new File("./downloads/" + filename + ".html");
        try {
            filtered.createNewFile();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }

        try {
            //Flag to check if Program is in <body> tag
            boolean inBody = false;
            BufferedReader br = new BufferedReader(new FileReader(unfiltered));
            PrintWriter pw = new PrintWriter(new FileOutputStream(filtered), true);
            String line;

            //Read all lines
            while ((line = br.readLine()) != null) {
                //If body tag is open, flag is true and we won't ingore this line
                if(line.contains("<body")) {
                    inBody = true;
                }
                //If in body remove tags here and write new string without tags in new file
                //Can miss tags which are written to more than one string for example:
                //<a class = "some class"
                //         style = "some styles..." >
                if(inBody) {
                    String newLine = line.replaceAll("<[^>]*>", " ");
                    pw.println(newLine);
                } else {
                    //If not in body, ignore line (usually it would be head)
                    continue;
                }
                //If body tag is closed, stop filtering
                if(line.contains("</body")) {
                    break;
                }
            }
            br.close();
            pw.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
