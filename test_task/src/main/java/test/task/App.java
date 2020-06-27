package test.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import test.task.model.PageInfo;
import test.task.repository.PageInfoRepository;
import test.task.service.PageInfoCreator;
import test.task.service.WordsInPageCounter;

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

        System.out.println(pi.toString());
    }
}
