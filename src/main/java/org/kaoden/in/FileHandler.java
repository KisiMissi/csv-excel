package org.kaoden.in;

import com.opencsv.bean.CsvToBeanBuilder;
import org.kaoden.module.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileHandler {

    public static List<Task> readingFile(File file) {

        try {
            List<Task> beans =  new CsvToBeanBuilder<Task>(
                    new InputStreamReader(
                            new FileInputStream(file), StandardCharsets.UTF_8))
                    .withType(Task.class)
                    .build()
                    .parse();

            beans.remove(0); // Удаление первой строки с наиименованиями

            return beans;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
