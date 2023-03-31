package org.kaoden.in;

import com.opencsv.bean.CsvToBeanBuilder;
import org.kaoden.module.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileHandler {

    private static Logger logger;

    static {
        try (FileInputStream ins = new FileInputStream("logging.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(FileHandler.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            logger.log(Level.WARNING, "IOException: ", e);
            throw new NullPointerException();
        }
    }
}
