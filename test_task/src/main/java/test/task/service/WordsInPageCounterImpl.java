package test.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WordsInPageCounterImpl implements WordsInPageCounter{

    @Autowired
    WebPageDownloader webPageDownloader;

    private final char[] SEPARATORS = {' ', ',', '.', '!', '?', '"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'};

    @Override
    public Map<String, Integer> wordsCountsOfPage(String url) {
        webPageDownloader.downloadAndSavePage(url);
        return wordsCountsOfSavedPage(url);
    }

    @Override
    public Map<String, Integer> wordsCountsOfSavedPage(String url) {
        Map<String, Integer> counts = new HashMap<>();

        return counts;
    }
}
