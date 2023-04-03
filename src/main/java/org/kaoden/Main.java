package org.kaoden;

import org.kaoden.module.Task;
import org.kaoden.in.BeanListHandler;
import org.kaoden.in.FileHandler;
import org.kaoden.out.SpreadsheetWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    // Начальная и конечная даты должны подаваться на вход вместе с файлом
    private static final String START_DATE = "2023-02-01";
    private static final String END_DATE = "2023-02-28";

    // Путь к входному файлу (позже убрать)
    private final static File file =
            new File("src\\main\\resources\\In.csv");

    private static Logger logger;

    static {
        try (FileInputStream ins = new FileInputStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(FileHandler.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        // Почасовая ставка сотрудников
        Map<String, Integer> hourlyRate = new HashMap<>();

        try {
            // Списко задач
            List<Task> taskList = FileHandler.readingFile(file);

            taskList.stream()
                    .filter(x -> !x.getAssigneeEmail().equals(""))
                    .forEach(x -> hourlyRate.put(x.getAssigneeEmail(), 350));

            // Сортировка задач по дате
            taskList = BeanListHandler.dropoutByDate(taskList, START_DATE, END_DATE);

            // Создание и заполнение таблицы
            SpreadsheetWriter.tableFilling(taskList, hourlyRate);

        } catch (IOException e) {
            logger.log(Level.WARNING, "File not found", e);
            System.exit(1);

        } catch(NullPointerException e) {
            logger.log(Level.WARNING, "File is empty", e);
            System.exit(1);
        }
    }
}
