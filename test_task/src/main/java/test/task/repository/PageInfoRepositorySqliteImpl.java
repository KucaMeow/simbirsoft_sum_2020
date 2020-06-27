package test.task.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import test.task.dao.WordRow;
import test.task.model.PageInfo;
import test.task.service.Logger;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PageInfoRepositorySqliteImpl implements PageInfoRepository {

    @Autowired
    Logger log;
    final JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_CREATE_TABLE = "" +
            "create table if not exists pages \n" +
            "(\n" +
            "    url TEXT not null,\n" +
            "    filename TEXT not null,\n" +
            "    word TEXT not null,\n" +
            "    count int not null\n" +
            ");\n";
    //language=SQL
    private final String SQL_INSERT_INTO = "insert into pages values (?, ?, ?, ?)";
    //language=SQL
    private final String SQL_DETETE_BY_URL = "delete from pages where url = ?";
    //language=SQL
    private final String SQL_SELECT_BY_URL = "select * from pages where url = ?";

    public PageInfoRepositorySqliteImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.execute(SQL_CREATE_TABLE);
    }

    private RowMapper<WordRow> rowRowMapper = (row, rowNum) ->
            WordRow.builder()
                    .url(row.getString("url"))
                    .filename(row.getString("filename"))
                    .word(row.getString("word"))
                    .count(row.getInt("count"))
                    .build();

    /**
     * Uses jdbc template, connected to SQLite. At first delete all previous records from table. After insert new
     * @param pageInfo PageInfo object to save
     */
    @Override
    public void savePageInfo(PageInfo pageInfo) {
        //Delete all words with url of current page
        jdbcTemplate.update(connection -> {
           PreparedStatement ps = connection.prepareStatement(SQL_DETETE_BY_URL);
           ps.setString(1, pageInfo.getUrl());
           return ps;
        });
        //Save all words for page
        for(Map.Entry<String, Integer> entry : pageInfo.getWordsAndCounts().entrySet()) {
            jdbcTemplate.update(SQL_INSERT_INTO, pageInfo.getUrl(), pageInfo.getName(), entry.getKey(), entry.getValue());
        }
    }

    /**
     * Uses jdbc template, connected to SQLite. Get all records from table, map it to Row object (specified dao class).
     * After create PageInfo object and fill it with info from table
     * @param url url to page
     * @return Optional of PageInfo: if saved in local repository, will return Optional with object;
     * if object hasn't been found, will return Optional of empty
     */
    @Override
    public Optional<PageInfo> getPageInfo(String url) {
        //Get all rows with this url
        List<WordRow> words = jdbcTemplate.query(SQL_SELECT_BY_URL, new Object[] {url}, rowRowMapper);
        //If there is no words in table, return empty
        if(words.isEmpty()) return Optional.empty();
        //Create PageInfo
        PageInfo pi = PageInfo.builder().url(url).name(words.get(0).getFilename()).build();
        //Fill PageInfo map with words from table
        for(WordRow word : words) {
            pi.getWordsAndCounts().put(word.getWord(), word.getCount());
        }
        return Optional.of(pi);
    }
}
