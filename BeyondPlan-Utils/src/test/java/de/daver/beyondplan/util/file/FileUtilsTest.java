package de.daver.beyondplan.util.file;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileUtilsTest {

    @Test
    @Order(1)
    void createFile() throws IOException {
        File file = FileUtils.createFile("/test.txt");
        assertTrue(file.exists());
    }

    @Test
    @Order(2)
    void createDirectory() throws IOException {
        File dir = FileUtils.createFile("/test");
        assertTrue(dir.exists());
    }

    @Test
    @Order(3)
    void deleteFile() throws IOException {
        File file = new File("/test.txt");
        assertTrue(FileUtils.deleteFile(file));
    }

    @Test
    @Order(4)
    void deleteDirectory() throws IOException {
        File dir = new File("/test");
        assertTrue(FileUtils.deleteFile(dir));
    }

}