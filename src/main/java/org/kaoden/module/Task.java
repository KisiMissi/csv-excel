package org.kaoden.module;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

public class Task {

    @CsvBindByPosition(position = 0)
    private String taskId;

    @CsvBindByPosition(position = 1)
    private String createdAt;

    @CsvBindByPosition(position = 2)
    private String completedAt;

    @CsvBindByPosition(position = 3)
    private String lastModified;

    @Getter
    @CsvBindByPosition(position = 4)
    private String name;

    @Getter
    @CsvBindByPosition(position = 5)
    private String sectionColumn;

    @Getter
    @CsvBindByPosition(position = 6)
    private String assignee;

    @Getter
    @CsvBindByPosition(position = 7)
    private String assigneeEmail;

    @Getter
    @CsvBindByPosition(position = 8)
    private String startDate;

    @Getter
    @Setter
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

    @Getter
    @CsvBindByPosition(position = 17)
    private String assigneeRole;

    @Getter
    @CsvBindByPosition(position = 18)
    private String countDays;

    @Getter
    @CsvBindByPosition(position = 19)
    private String busy;

    @CsvBindByPosition(position = 20)
    private String project;
}
