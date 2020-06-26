package test.task.service;

import java.util.Map;

/**
 * Class with counter of words logic
 */
public interface WordsInPageCounter {
    /**
     * Should download, save page and then count words there.
     * Should use WebPageDownloader to download and save page
     * @param url url of page
     * @return map of words as keys and their counts as value
     */
    Map<String, Integer> wordsCountsOfPage(String url);

    /**
     * Should count words using url.
     * Should take downloaded page in project and count words there
     * @param url url of page
     * @return map of words as keys and their counts as value
     */
    Map<String, Integer> wordsCountsOfSavedPage(String url);
}
