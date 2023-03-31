package org.kaoden.in;

import org.kaoden.Main;
import org.kaoden.module.Task;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class BeanListHandler {

    private static Logger logger;
    static {
        try (FileInputStream ins = new FileInputStream("D:\\Projects\\CsvParsing\\logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(BeanListHandler.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Task> dropoutByDate(List<Task> beans, String startDate, String endDate) {

        List<Task> newBeans = new ArrayList<>();

        // Начальная и конечная даты нужного периода
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        for (Task task : beans) {

            // Проверка наличия дат начала и конца
            if (task.getDueDate().equals("") || task.getStartDate().equals("")) {
                logger.log(Level.INFO, "Skip task without DATE: " + task.getTaskId());
                continue;
            }

            // Проверка наличия занятости
            if (task.getBusy().equals("")) {
                logger.log(Level.INFO, "Skip task without BUSY: " + task.getTaskId());
                continue;
            }

            LocalDate taskStartDate = LocalDate.parse(task.getStartDate());
            LocalDate taskEndDate = LocalDate.parse(task.getDueDate());

            // Сравнение начально даты периода и начальной даты задачи
            int comparisonStartDate = sDate.compareTo(taskStartDate);
            // Сравненеие конечной даты периода и конечной даты задачи
            int comparisonEndDate = eDate.compareTo(taskEndDate);

            // Дата начала позже или равна нужной начальной даты и
            // дата окончания раньше или равна нужной конечной даты
            if (comparisonStartDate <= 0 && comparisonEndDate >=0)
                newBeans.add(task);

            // Начальная дата стоит раньше нужной, а конечная до нужной конечной даты
            // Начальная дата периода стоит раньше конечной даты задачи
            else if (comparisonStartDate > 0 && comparisonEndDate >= 0 && sDate.isBefore(taskEndDate)) {
                task.setStartDate(startDate);
                newBeans.add(task);
            }

            // Начальная дата попадате в период, а конечная стоит позже
            // Дата начал задачи не стоит позже конечной даты
            else if (comparisonStartDate <= 0 && taskStartDate.isBefore(eDate)) {
                task.setDueDate(endDate);
                newBeans.add(task);
            }

            // Начальная дата стоит раньше нужного
            // конечная дата стоит позже нужного
            else if (comparisonStartDate > 0 && comparisonEndDate < 0) {
                task.setStartDate(startDate);
                task.setDueDate(endDate);
                newBeans.add(task);
            }

            // Если дата конечная дата задачи стоит раньше нужной начальной даты
            // или начальная дата задачи стоит позже нужной конечной даты, то
            // такая задача пропускается

        }

        // Сортировка по столбцу Section/Column
        newBeans.sort(Comparator.comparing(Task::getSectionColumn));

        return newBeans;
    }
}
