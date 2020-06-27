package test.task.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import test.task.model.PageInfo;
import test.task.service.FilesUtils;
import test.task.service.Logger;

import java.io.*;
import java.util.Optional;

/**
 * PageInfoRepository using local file storage with json objects
 */
@Repository
public class PageInfoRepositoryLocalJsonImpl implements PageInfoRepository{

    @Autowired
    Logger log;
    @Autowired
    FilesUtils filesUtils;


    /**
     * Save new file in jsons folder with .json type, using Jackson's ObjectMapper to create json string
     * from Page info object. Writes this json string to file
     * @param pageInfo PageInfo object to save
     */
    @Override
    public void savePageInfo(PageInfo pageInfo) {
        File folder = new File("jsons");
        folder.mkdir();

        File jsonFile = new File("./jsons/" + pageInfo.getName() + ".json");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(jsonFile), true);
            pw.println((new ObjectMapper()).writeValueAsString(pageInfo));
            pw.close();
        } catch (FileNotFoundException | JsonProcessingException e) {
            log.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    /**
     * Open saved file in jsons folder. If object can't be found, will return empty Optional.
     * Uses Jackson's ObjectMapper to read object from saved json file
     * @param url url to page
     * @return Optional of PageInfo: if saved in local repository, will return Optional with object;
     * if object hasn't been found, will return Optional of empty
     */
    @Override
    public Optional<PageInfo> getPageInfo(String url) {
        File jsonFile = new File("./jsons/" + filesUtils.getFileName(url) + ".json");
        try {
            PageInfo pageInfo = (new ObjectMapper()).readValue(jsonFile, PageInfo.class);
            return Optional.of(pageInfo);
        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
