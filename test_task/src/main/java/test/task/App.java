package test.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import test.task.model.PageInfo;
import test.task.repository.PageInfoRepository;
import test.task.service.PageInfoCreator;
import test.task.service.WordsInPageCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("test.task");
        PageInfoCreator pageInfoCreator = context.getBean(PageInfoCreator.class);
        System.out.println("Write url of page you want to analyze");
        String url = (new Scanner(System.in)).next();
        PageInfo pi = pageInfoCreator.getPageInfoFromUrl(url);
        //Do smth with PageInfo

        //For example:
        PageInfoRepository rep1 = context.getBean("pageInfoRepositoryLocalJsonImpl", PageInfoRepository.class);
        rep1.savePageInfo(pi);
        PageInfoRepository rep2 = context.getBean("pageInfoRepositorySqliteImpl", PageInfoRepository.class);
        rep2.savePageInfo(pi);

        File folder = new File("counts");
        folder.mkdir();

        //Printing needed info about counts of words and saving it to file
        File jsonFile = new File("./counts/" + pi.getName() + ".txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(jsonFile), true);
            for(Map.Entry<String, Integer> entry : pi.getWordsAndCounts().entrySet()) {
                pw.println(entry.getKey() + " - " + entry.getValue());
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}
