package test.task.repository;

import test.task.model.PageInfo;

import java.util.Optional;

/**
 * Repository of PageInfo with two simple commands save and get
 */
public interface PageInfoRepository {
    void savePageInfo(PageInfo pageInfo);
    Optional<PageInfo> getPageInfo(String url);
}
