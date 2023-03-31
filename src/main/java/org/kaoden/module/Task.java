package org.kaoden.module;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Task {

    @CsvBindByPosition(position = 0)
    private String taskId;

    @CsvBindByPosition(position = 1)
    private String createdAt;

    @CsvBindByPosition(position = 2)
    private String completedAt;

    @CsvBindByPosition(position = 3)
    private String lastModified;

    @CsvBindByPosition(position = 4)
    private String name;

    @CsvBindByPosition(position = 5)
    private String sectionColumn;

    @CsvBindByPosition(position = 6)
    private String assignee;

    @CsvBindByPosition(position = 7)
    private String assigneeEmail;

    @CsvBindByPosition(position = 8)
    private String startDate;

    @CsvBindByPosition(position = 9)
    private String dueDate;

    @CsvBindByPosition(position = 10)
    private String tags;

    @CsvBindByPosition(position = 11)
    private String notes;

    @CsvBindByPosition(position = 12)
    private String projects;

    @CsvBindByPosition(position = 13)
    private String parentTask;

    @CsvBindByPosition(position = 14)
    private String blocked;

    @CsvBindByPosition(position = 15)
    private String blocking;

    @CsvBindByPosition(position = 16)
    private String estimatedTime;

    @CsvBindByPosition(position = 17)
    private String assigneeRole;

    @CsvBindByPosition(position = 18)
    private String countDays;

    @CsvBindByPosition(position = 19)
    private String busy;

    @CsvBindByPosition(position = 20)
    private String project;
}
