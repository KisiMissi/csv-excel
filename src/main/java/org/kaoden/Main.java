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
            new File("src\\main\\resources\\In.csv");

    public static void main(String[] args){

        // Списко задач
        List<Task> taskList = FileHandler.readingFile(file);
        // Почасовая ставка сотрудников
        Map<String, Integer> hourlyRate = new HashMap<>();

        try {
            taskList.stream()
                    .filter(x -> !x.getAssigneeEmail().equals(""))
                    .forEach(x -> hourlyRate.put(x.getAssigneeEmail(), 350));
        } catch(NullPointerException e) {
            // Остановка программы
            System.out.println("Input file is empty.");
        }

        // Сортировка задач по дате
        taskList = BeanListHandler.dropoutByDate(taskList, START_DATE, END_DATE);

        // Создание и заполнение таблицы
        SpreadsheetWriter.tableFilling(taskList, hourlyRate);
    }
}
