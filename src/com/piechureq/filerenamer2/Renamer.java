package com.piechureq.filerenamer2;

import java.io.File;
import java.util.HashMap;

/**
 * Created on 2017-03-13.
 */
public class Renamer {
    public static File name;

    public String getExt(File target){
        String targetPath = target.getPath();

        String ext = targetPath.substring(targetPath.lastIndexOf('.') + 1);

        return ext;
    }

    public String getPath(File target){
        String targetPath = target.getPath();

        String path = targetPath.substring(0, targetPath.lastIndexOf('\\') + 1);

        return path;
    }

    public HashMap<String, File> rename(File target, String newName) {
        String targetPath = target.getPath();//cale
        String collect;
        String path = "";
        String ext;

        if (targetPath.length() >= targetPath.lastIndexOf('\\')) {
            path = targetPath.substring(0, targetPath.lastIndexOf('\\') + 1);
        }

        ext = targetPath.substring(targetPath.lastIndexOf('.') + 1);

        if (ext == targetPath) {
            collect = path + newName;
        }else {
            collect = path + newName + "." + ext;
        }

        File tempFile = new File(collect);
        boolean success = target.renameTo(tempFile);

        if(success) {
            System.out.println("New Name" + tempFile);
            name = tempFile;
        }
        HashMap<String, File> files = new HashMap();
        files.put(newName + "." + ext, tempFile);
        return files;
    }

    public HashMap<String, File> addName(File target, String content){
        String targetPath = target.getPath();//cale
        String collect;
        String base = "";
        String path = "";
        String ext;

        if (targetPath.length() >= targetPath.lastIndexOf('\\')) {
            path = targetPath.substring(0, targetPath.lastIndexOf('\\') + 1);
        }

        ext = targetPath.substring(targetPath.lastIndexOf('.') + 1);

        if (ext == targetPath) {
            base = targetPath.substring(targetPath.lastIndexOf('\\') + 1);
            collect = path + base + content;
        }else {
            base = targetPath.substring(targetPath.lastIndexOf('\\') + 1, targetPath.lastIndexOf('.'));
            collect = path + base + content + "." + ext;
        }

        File tempFile = new File(collect);
        boolean success = target.renameTo(tempFile);

        if(success) {
            System.out.println("New Name" + tempFile);
            name = tempFile;
        }

        HashMap<String, File> files = new HashMap();
        files.put(base + content + "." + ext, tempFile);
        return files;
    }

    public HashMap<String, File> takeName(File target, String content){
        String targetPath = target.getPath();//cale
        String collect;
        String base = "";
        String path = "";
        String ext;

        if (targetPath.length() >= targetPath.lastIndexOf('\\')) {
            path = targetPath.substring(0, targetPath.lastIndexOf('\\') + 1);
        }

        ext = targetPath.substring(targetPath.lastIndexOf('.') + 1);

        if (ext == targetPath) {
            base = targetPath.substring(targetPath.lastIndexOf('\\') + 1);
            collect = path + base.replace(content, "");
        }else {
            base = targetPath.substring(targetPath.lastIndexOf('\\') + 1, targetPath.lastIndexOf('.'));
            collect = path + base.replace(content, "") + "." + ext;
        }

        File tempFile = new File(collect);
        boolean success = target.renameTo(tempFile);

        if(success) {
            System.out.println("New Name" + tempFile);
            name = tempFile;
        }

        HashMap<String, File> files = new HashMap();
        files.put(base.replace(content, "") + "." + ext, tempFile);
        return files;
    }


}
