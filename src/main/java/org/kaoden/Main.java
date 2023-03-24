package org.kaoden;

import org.kaoden.module.Task;
import org.kaoden.in.BeanListHandler;
import org.kaoden.in.FileHandler;
import org.kaoden.out.SpreadsheetWriter;

import java.io.File;
import java.util.*;

public class Main {

    // Начальная и конечная даты должны подаваться на вход вместе с файлом
    private static final String START_DATE = "2023-02-01";
    private static final String END_DATE = "2023-02-28";

    // Путь к входному файлу (позже убрать)
    private final static File file =
            new File("D:\\Projects\\CsvParsing\\src\\main\\resources\\In.csv");

    private static final Map<String, Integer> hourlyRate = new HashMap<>(); // Содержит почасовую ставку

    public static void main(String[] args){

        // Список "Задач"
        List<Task> taskList = FileHandler.readingFile(file);

        taskList.stream()
                .filter(x -> !x.getAssigneeEmail().equals(""))
                .forEach(x -> hourlyRate.put(x.getAssigneeEmail(), 350));

        taskList = BeanListHandler.dropoutByDate(taskList, START_DATE, END_DATE);

        new SpreadsheetWriter().tableFilling(taskList, hourlyRate);
    }
}
