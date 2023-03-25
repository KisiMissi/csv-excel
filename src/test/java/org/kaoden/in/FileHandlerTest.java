package org.kaoden.in;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.File;

public class FileHandlerTest extends TestCase {

    private final static File file =
            new File("D:\\Projects\\CsvParsing\\src\\main\\resources\\In.csv");;

    public void testReadingFile() {
        Assert.assertNotNull("The returned list must not be empty.", FileHandler.readingFile(file));
    }
}