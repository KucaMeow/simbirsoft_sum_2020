package test.task.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WordsInPageCounterUsingJsoupImpl implements WordsInPageCounter{

    @Autowired
    WebPageDownloader webPageDownloader;
    @Autowired
    FilesUtils filesUtils;
    @Autowired
    Logger log;

//    private final char[] SEPARATORS = {' ', ',', '.', '!', '?', '"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'};
    private final String SEPARATORS_REGEX = "[ ,.!?\";:\\[\\]()\n\r\t]";

    @Override
    public Map<String, Integer> wordsCountsOfPage(String url) {
        webPageDownloader.downloadAndSavePage(url);
        return wordsCountsOfSavedPage(url);
    }

    @Override
    public Map<String, Integer> wordsCountsOfSavedPage(String url) {
        //Init map to count words here
        Map<String, Integer> counts = new HashMap<>();
        //Get all text from page and split it by separators
        String content = getPageText(url);
        String[] words = content.split(SEPARATORS_REGEX);
        //Go through array of words
        for(String word : words) {
            //Making words in lower case to create equality between "Слово" and "слово" for example. And trim them
            word = word.toLowerCase().trim();
            //Ignore empty word
            if(word.equals("")) continue;

            //Check if map contains word as key. If contain, increase count, else init key and value in map
            if(counts.containsKey(word)) {
                int temp = counts.get(word);
                counts.put(word, ++temp);
            } else {
                counts.put(word, 1);
            }
        }
        return counts;
    }

    private String getPageText (String url) {
        //Get file from downloads
        String fileName = filesUtils.getFileName(url);
        try {
            File page = new File("./downloads/" + fileName + ".html");
            //Parse document by jsoup
            Document doc = Jsoup.parse(page, "UTF-8");
            //Get text from page's body
            return doc.body().text();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }
}
