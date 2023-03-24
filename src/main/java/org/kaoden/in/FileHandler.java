package org.kaoden.in;

import com.opencsv.bean.CsvToBeanBuilder;
import org.kaoden.module.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileHandler {

    public static List<Task> readingFile(File file) {

        try(
                InputStreamReader reader = new InputStreamReader(
                        new FileInputStream(file), StandardCharsets.UTF_8)
                ) {

            return new CsvToBeanBuilder<Task>(reader)
                    .withType(Task.class)
                    .withSkipLines(1) // Пропуск строки с наименованиями
                    .build()
                    .parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
