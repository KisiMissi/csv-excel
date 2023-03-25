package org.kaoden;

import org.kaoden.module.Task;
import org.kaoden.in.BeanListHandler;
import org.kaoden.in.FileHandler;
import org.kaoden.out.SpreadsheetWriter;

import java.io.File;
import java.io.FileInputStream;
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
            new File("D:\\Projects\\CsvParsing\\src\\main\\resources\\In.csv");

    private static final Map<String, Integer> hourlyRate = new HashMap<>(); // Содержит почасовую ставку

    private static Logger logger;
    static {
        try (FileInputStream ins = new FileInputStream("D:\\Projects\\CsvParsing\\logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(BeanListHandler.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        // Список "Задач"
        List<Task> taskList = FileHandler.readingFile(file);

        try {
            taskList.stream()
                    .filter(x -> !x.getAssigneeEmail().equals(""))
                    .forEach(x -> hourlyRate.put(x.getAssigneeEmail(), 350));
        } catch(NullPointerException e) {
            logger.log(Level.WARNING, "Exception: ", e);
        }

        // Фильтрация задач по дате
        taskList = BeanListHandler.dropoutByDate(taskList, START_DATE, END_DATE);

        // Создание электронной таблицы
        SpreadsheetWriter.tableFilling(taskList, hourlyRate);
    }
}
