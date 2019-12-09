package main;

import Utils.Manager;
import modules.PageView;
import controller.SessionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

@SpringBootApplication
@ComponentScan(basePackageClasses = SessionController.class)
public class Bootstrapper {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrapper.class, args);

        List<String> csvFileList = Arrays.asList("input_1.csv", "input_2.csv", "input_3.csv");
        String line;
        BufferedReader bufferedReader;

        try {
            for (String csvFile: csvFileList) {
                System.out.printf("reading file: %s%n", csvFile);
                File file = ResourceUtils.getFile("classpath:csvFiles/" + csvFile);
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

                while ((line = bufferedReader.readLine()) != null) {
                    String[] pageViewRow = line.split(",");
                    PageView pageView = new PageView(pageViewRow[0], pageViewRow[1],
                            pageViewRow[2], new Timestamp(Long.parseLong(pageViewRow[3])));
                    Manager.addNewPageView(pageView);
                }
                bufferedReader.close();
            }
            Manager.sortPagesViewsToSessions();
            Manager.sortSessionsByLengths();
        } catch(Exception e){
            System.out.println(e);
        }
    }
}