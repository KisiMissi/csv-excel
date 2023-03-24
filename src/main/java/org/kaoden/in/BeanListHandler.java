package org.kaoden.in;

import org.kaoden.module.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeanListHandler {

    public static List<Task> dropoutByDate(List<Task> beans, String startDate, String endDate) {

        List<Task> newBeans = new ArrayList<>();

        // Начальная и конечная даты нужного периода
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        for (Task task : beans) {

            // Проверка наличия дат начала и конца
            if (task.getDueDate().equals("") || task.getStartDate().equals("")) {
                System.out.println("Missing START DATE or DUE DATE : " + task.getTaskId());
                continue;
            }

            // Проверка наличия занятости
            if (task.getBusy().equals("")) {
                System.out.println("Missing BUSY : " + task.getTaskId());
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
        }

        // Сортировка по столбцу Section/Column
        newBeans.sort(Comparator.comparing(Task::getSectionColumn));

        return newBeans;
    }
}
