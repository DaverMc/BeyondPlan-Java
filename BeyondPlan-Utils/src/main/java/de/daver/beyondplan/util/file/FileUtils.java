package de.daver.beyondplan.util.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public interface FileUtils {

    static File createFile(File parent, String child) throws IOException {
        return createFile(new File(parent, child));
    }

    static File createFile(String parent, String child) throws IOException {
        return createFile(new File(parent, child));
    }

    static File createFile(String path) throws IOException {
        return createFile(new File(path));
    }

    static File createFile(URI uri) throws IOException {
        return createFile(new File(uri));
    }

    static File createFile(File file) throws IOException {
        if(file.exists()) return file;
        if(file.isFile()) {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        else file.mkdirs();
        return file;
    }

    static boolean deleteFile(File parent, String child) throws IOException {
        return deleteFile(new File(parent, child));
    }

    static boolean deleteFile(String parent, String child) throws IOException {
        return deleteFile(new File(parent, child));
    }

    static boolean deleteFile(String path) throws IOException {
        return deleteFile(new File(path));
    }

    static boolean deleteFile(URI uri) throws IOException {
        return deleteFile(new File(uri));
    }

    static boolean deleteFile(File file) {
        if(!file.exists()) return false;
        if(file.isFile()) return file.delete();
        for(File child : file.listFiles()) {
            deleteFile(child);
        }
        return file.delete();
    }

}
