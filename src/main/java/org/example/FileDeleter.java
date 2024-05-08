package org.example;

import java.io.File;

public class FileDeleter {

    public boolean deleteFile(String path){
        File file = new File(path);

        if(file.exists()){
            return file.delete();
        }
        return false;
    }
}
