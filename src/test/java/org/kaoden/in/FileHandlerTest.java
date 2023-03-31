package org.kaoden.in;

import org.junit.jupiter.api.Test;
import org.kaoden.module.Task;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void readingFile_NO_NULL() {

        // Проверка корректного чтения файла

        // Создание тестовой задачи
        Task testTask = new Task();
        testTask.setTaskId("1204010543302850");
        testTask.setCreatedAt("2023-02-21");
        testTask.setCompletedAt("2023-02-22");
        testTask.setLastModified("2023-03-03");
        testTask.setName("Updates: FE");
        testTask.setSectionColumn("QR quality");
        testTask.setAssignee("Vladislav Pridvorov");
        testTask.setAssigneeEmail("v.pridvorov@sparen.life");
        testTask.setStartDate("2023-02-17");
        testTask.setDueDate("2023-02-21");
        testTask.setTags("");
        testTask.setNotes("");
        testTask.setProjects("WS Plan");
        testTask.setParentTask("");
        testTask.setBlocked("");
        testTask.setBlocking("");
        testTask.setEstimatedTime("");
        testTask.setAssigneeRole("FE");
        testTask.setCountDays("");
        testTask.setBusy("1.00");
        testTask.setProject("");

        List<Task> testTaskList = Collections.singletonList(testTask);

        assertEquals(testTaskList,
                FileHandler.readingFile(new File("src\\test\\resources\\in\\TestInputFile.csv")));
    }
}