package org.kaoden.in;

import org.junit.jupiter.api.Test;
import org.kaoden.module.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeanListHandlerTest {

    @Test
    void dropOutByDate_OUT_OF_PERIOD() {

        // Случай, когда обе даты не попадают в период
        // Если обе задачи не попадают в нужный период, то задача не должна попасть в конечный список

        String startDate = "2023-02-01";
        String endDate = "2023-02-28";

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_OUT_OF_PERIOD.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);
        List<Task> expectedList = new ArrayList<>();

        assertEquals(expectedList, testBeanList);
    }

    @Test
    void dropOutByDate_NULL_BUSY() {

        // Случай, когда BUSY отсутствует
        // Если нет занятости, то задача не должна попасть в конечный список

        String startDate = "2023-02-01";
        String endDate = "2023-02-28";

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_NULL_BUSY.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);
        List<Task> expectedList = new ArrayList<>();

        assertEquals(expectedList, testBeanList);
    }

    @Test
    void dropoutByDate_UNCHANGED_TASK() {

        // Случай, когда задача полностью находиться в нужном периоде
        // Задача сохраняет свои даты начала и окончания

        String startDate = "2023-01-01";
        String endDate = "2023-02-28";

        // Ожидаемые даты задачи
        String expectedStartDate = "2023-01-26";
        String expectedEndDate = "2023-02-03";

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_UNCHANGED_TASK.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);

        assertEquals(expectedStartDate +"/"+ expectedEndDate,
                testBeanList.get(0).getStartDate() +"/"+ testBeanList.get(0).getDueDate());
    }

    @Test
    void dropoutByDate_START_AND_DUE_DATE_EQUALS() {

        // Случай, когда начальная и конечная дата один и тот же день

        String startDate = "2023-02-15";
        String endDate = "2023-02-21";

        // Ожидаемые даты
        // Начальная и конечная даты = endDate

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_START_AND_DUE_DATE_EQUALS.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);

        assertEquals(endDate +"/"+ endDate,
                testBeanList.get(0).getStartDate() +"/"+ testBeanList.get(0).getDueDate());
    }

    @Test
    void dropoutByDate_CHAGE_START_DATE() {

        // Случай когда начальная дата стоит раньше
        // Начальная дата из файла должна смениться 2023-01-31 -> 2023-02-01

        String startDate = "2023-02-01";
        String endDate = "2023-02-28";

        // Ожидаемые даты задачи (меняется начальная)
        String expectedEndDate = "2023-02-03";

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_CHANGE_START_DATE.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);

        assertEquals(startDate +"/"+ expectedEndDate,
                testBeanList.get(0).getStartDate() +"/"+ testBeanList.get(0).getDueDate());
    }

    @Test
    void dropoutByDate_CHANGE_DUE_DATE() {

        // Случай, когда конечная дата стоит позже нужной
        // Конечная дата задачи должна смениться 2023-01-27 -> 2023-01-23

        String startDate = "2023-01-01";
        String endDate = "2023-01-23";

        // Ожидаемые даты задачи (меняется конечная)
        String expectedStartDate = "2023-01-10";

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_CHANGE_DUE_DATE.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);

        assertEquals(expectedStartDate +"/"+ endDate,
                testBeanList.get(0).getStartDate() +"/"+ testBeanList.get(0).getDueDate());
    }

    @Test
    void dropByDate_CHANGE_START_AND_DUE_DATE() {

        // Случай когда и начальная стоит раньше, а конечная позже
        // Начальная дата из файла должна смениться 2023-01-20 -> 2023-01-22
        // Конечная дата задачи должна смениться 2023-01-25 -> 2023-01-23

        String startDate = "2023-01-22";
        String endDate = "2023-01-23";

        // Ожидаемые даты задачи (меняются обе)
        // Дата начала expectedStartDate = startDate
        // Дата окончания expectedDueDate = endDate

        File testFile = new File("src\\test\\resources\\in\\BeanListHandler\\TestInputFile_CHANGE_START_AND_DUE_DATE.csv");
        List<Task> testBeanList = BeanListHandler.dropoutByDate(FileHandler.readingFile(testFile), startDate, endDate);

        assertEquals(startDate +"/"+ endDate,
                testBeanList.get(0).getStartDate() +"/"+ testBeanList.get(0).getDueDate());
    }
}