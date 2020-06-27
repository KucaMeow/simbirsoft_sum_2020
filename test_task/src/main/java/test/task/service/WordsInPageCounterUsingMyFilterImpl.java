package test.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class WordsInPageCounterUsingMyFilterImpl implements WordsInPageCounter {

    @Autowired
    FilesUtils filesUtils;
    @Autowired
    WebPageDownloader webPageDownloader;
    @Autowired
    Logger log;

    private final String SEPARATORS_REGEX = "[ ,.!?\";:\\[\\]()\n\r\t]";

    @Override
    public Map<String, Integer> wordsCountsOfPage(String url) {
        webPageDownloader.downloadAndSavePage(url);
        return wordsCountsOfSavedPage(url);
    }

    /**
     * Using my filter, which leaves rubbish, but works with memory efficiency
     * @param url url of page
     * @return counts of words
     */
    @Override
    public Map<String, Integer> wordsCountsOfSavedPage(String url) {
        Map<String, Integer> counts = new HashMap<>();
        filesUtils.filterFile(url);

        String filename = filesUtils.getFileName(url);
        File filtered = new File("./filtered/" + filename + ".txt");
        BufferedReader br;
        try {
            br = new BufferedReader(
                    new FileReader(filtered));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split(SEPARATORS_REGEX);
                //The same as in WordsInPageCounterUsingJsoup, but for groups of words in line
                for(String word : words) {
                    word = word.toLowerCase().trim();
                    if(word.equals("")) continue;
                    if(counts.containsKey(word)) {
                        int temp = counts.get(word);
                        counts.put(word, ++temp);
                    } else {
                        counts.put(word, 1);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
        return counts;
    }
}
