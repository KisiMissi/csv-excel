package org.kaoden.out;

import builders.dsl.spreadsheet.builder.api.SheetDefinition;
import builders.dsl.spreadsheet.builder.poi.PoiSpreadsheetBuilder;
import org.kaoden.module.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static builders.dsl.spreadsheet.api.Keywords.*;

public class SpreadsheetWriter {

    // Путь создание новой электронной таблицы
    private static final File SPREADSHEET_FILE = new File("Out.xlsx");

    // Заполение таблицы
    public static void tableFilling(List<Task> taskList, Map<String, Integer> hourlyRate) {

        try {
            PoiSpreadsheetBuilder.create(SPREADSHEET_FILE).build(w -> w.sheet("First", s -> {

                // Добавление ряда с наименованием столбцов
                columnNames(s);


                String section = taskList.get(0).getSectionColumn(); // Хранит название текущей секции для сравения со следующей
                AtomicInteger rowIndex = new AtomicInteger(0); // Индекс строки
                AtomicInteger sectionCount = new AtomicInteger(0); // Подсчет строк в одной секции
                List<Integer> sectionSumInd = new ArrayList<>(); // Хранение индексов итоговых сумм каждой секции

                // Заполение основных поелй таблицы
                mainFields(taskList, hourlyRate, s, section, rowIndex, sectionCount, sectionSumInd);

                // Пропуск двух строк
                s.row(); s.row();

                // Добавление ряда с итоговой суммой всей таблицы
                totalSumRow(s, sectionSumInd);
            }));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void columnNames(SheetDefinition s) {
        s.row(r -> {
            r.cell(c -> {
                c.value("Name");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Section/Column");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Assignee");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Start Date");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Due Date");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Assignee Role");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Count Days");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Busy");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Ставка");
                c.width(auto);
            });
            r.cell(c -> {
                c.value("Итого");
                c.width(20);
            });
        });
    }

    private static void mainFields(List<Task> taskList, Map<String, Integer> hourlyRate, SheetDefinition s, String section, AtomicInteger rowIndex, AtomicInteger sectionCount, List<Integer> sectionSumInd) {
        for (Task task : taskList) {

            // Заполнение строки с итоговой суммой задач одной секции
            if ((! task.getSectionColumn().equals(section))) {
                section = sectionSum(s, section, rowIndex, sectionCount, sectionSumInd, task);
            }

            s.row(r -> {
                // Название задачи
                r.cell(task.getName());
                // Секция
                r.cell(task.getSectionColumn());
                // Назначение
                r.cell(task.getAssignee());
                // Дата начала
                r.cell(c -> {
                    c.value(task.getStartDate());
                    c.name("START_DATE_" + rowIndex);
                });
                // Дата окончания
                r.cell(c -> {
                    c.value(task.getDueDate());
                    c.name("END_DATE_" + rowIndex);
                });
                // Роль получателя
                r.cell(task.getAssigneeRole());
                // Подсчет дней
                r.cell(c -> {
                    c.formula("NETWORKDAYS.INTL(#{START_DATE_"+ rowIndex +"}, #{END_DATE_"+ rowIndex +"})");
                    c.name("DAYS_" + rowIndex);
                });
                // Занятость
                r.cell(c -> {
                    c.value(task.getBusy().replace(".", ","));
                    c.name("BUSY_" + rowIndex);
                });
                // Ставка
                r.cell(c -> {
                    c.value(hourlyRate.get(task.getAssigneeEmail()));
                    c.name("JOB_RATE_" + rowIndex);
                });
                // Итог (Дни * Занятость * Ставка)
                r.cell(c -> {
                    c.formula("#{DAYS_"+ rowIndex +"} * #{BUSY_"+ rowIndex +"} * #{JOB_RATE_"+ rowIndex +"}");
                    c.name("SECTION_SUM_" + rowIndex);
                });
                // Подсчет строчек одной секции
                sectionCount.getAndIncrement();

                // Инкрементирование индекса строки
                rowIndex.getAndIncrement();
            });

            // Итоговая сумма секции в случае последней задачи
            if (rowIndex.get() == taskList.size()) {
                section = sectionSum(s, section, rowIndex, sectionCount, sectionSumInd, task);
            }
        }
    }

    private static void totalSumRow(SheetDefinition s, List<Integer> sectionSumInd) {
        s.row(r -> {
            r.cell(c -> {
                c.value("Итого");
                c.style(st -> st.font(f -> {
                    f.style(bold);
                    f.size(18);
                }));
            });

            // 8 пустых ячеек
            r.cell(); r.cell(); r.cell(); r.cell(); r.cell(); r.cell(); r.cell(); r.cell();

            // Ячейка с Итоговой суммой
            r.cell(c -> {
                String exp = "";
                for (Integer ind : sectionSumInd) {
                    // Создание строки с суммой всех остальных итоговых сумм по их индексам
                    exp += "#{TOTAL_SUM_" + ind + "} + ";
                }
                exp += "0";
                c.formula(exp);
                c.value(0);
                c.style(st -> st.font(f -> {
                    f.style(bold);
                    f.size(18);
                }));
            });
        });
    }

    // Подсчет итоговой суммы одной секции
    private static String sectionSum(SheetDefinition s, String section, AtomicInteger rowIndex, AtomicInteger sectionCount, List<Integer> sectionSumInd, Task task) {
        String finalSection = section;
        s.row(r -> {
            r.cell(c -> {
                c.value("Итого");
                c.style(st -> st.font(f -> f.style(bold)));
            });
            r.cell(c -> {
                c.value(finalSection);
                c.style(st -> st.font(f -> f.style(bold)));
            });

            // 7 пустых ячеек
            r.cell(); r.cell(); r.cell(); r.cell(); r.cell(); r.cell(); r.cell();

            // Сумма задач одной секции
            r.cell(c -> {

                int sectionBegin = rowIndex.get() - sectionCount.get();
                int sectionEnd = rowIndex.get() - 1;
                // Строка со сложением всех строк секции
                String exp = "";
                for (int i = sectionBegin; i <= sectionEnd; i++) {
                    exp += "#{SECTION_SUM_" + i + "} + ";
                }
                exp += "0";

                c.formula(exp);
                c.style(st -> st.font(f -> f.style(bold)));
                c.name("TOTAL_SUM_" + rowIndex);
            });
            sectionSumInd.add(rowIndex.get()); // Добавление индекса в список
            sectionCount.set(0); // Обнуление подсчета строк в блоке
        });

        section = task.getSectionColumn(); // Переход в новую секцию
        s.row(); // Пропуск строки
        return section;
    }
}
