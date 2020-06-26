package test.task;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import test.task.service.WordsInPageCounter;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("test.task");
        WordsInPageCounter wordsInPageCounter = context.getBean(WordsInPageCounter.class);

        System.out.println(wordsInPageCounter.wordsCountsOfPage("https://www.simbirsoft.com/").toString());
    }
}
