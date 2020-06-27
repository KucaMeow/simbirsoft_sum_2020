package test.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import test.task.model.PageInfo;

@Service
public class PageInfoCreator {

    @Qualifier("wordsInPageCounterUsingJsoupImpl")
    @Autowired
    WordsInPageCounter wordsInPageCounter;
    @Autowired
    FilesUtils filesUtils;

    public PageInfo getPageInfoFromUrl (String url) {
        return PageInfo.builder()
                .url(url)
                .name(filesUtils.getFileName(url))
                .wordsAndCounts(wordsInPageCounter.wordsCountsOfPage(url))
                .build();
    }
}
