package org.kaoden.in;

import org.kaoden.module.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BeanListHandler {

    public static List<Task> dropoutByDate(List<Task> beans, String startDate, String endDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Task> newBeans = new ArrayList<>();

        try {

            // Начальная и конечная даты нужного периода
            Date sDate = dateFormat.parse(startDate);
            Date eDate = dateFormat.parse(endDate);

            for (Task task : beans) {

                // Проверка наличия дат начала и конца
                if (task.getDueDate().equals("") || task.getStartDate().equals(""))
                    continue;

                Date taskStartDate = dateFormat.parse(task.getStartDate());
                Date taskEndDate = dateFormat.parse(task.getDueDate());

                if ((sDate.before(taskStartDate) || sDate.equals(taskStartDate)) && (eDate.after(taskEndDate) || eDate.equals(taskStartDate)) && taskStartDate.before(eDate))
                    newBeans.add(task);

                else if ((sDate.before(taskStartDate) || sDate.equals(taskStartDate)) && taskStartDate.before(eDate)) {
                    task.setDueDate(endDate);
                    newBeans.add(task);
                }
            }

        } catch(ParseException e) {
            e.printStackTrace();
        }

        // Сортировка по столбцу Section/Column
        newBeans.sort(Comparator.comparing(Task::getSectionColumn));

        return newBeans;
    }
}
