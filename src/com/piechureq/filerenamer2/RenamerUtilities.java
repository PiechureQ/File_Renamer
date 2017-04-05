package com.piechureq.filerenamer2;

import java.io.File;
import java.util.*;

/**
 * Created on 2017-03-28.
 */
public class RenamerUtilities {
    private Renamer renamer = new Renamer();

    HashMap<String, File> renameAll(HashMap<String, File> files, String content){//zmienia nazwe wszytkich

        Collection<File> fileCollection = files.values();
        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        ArrayList<String> extList = new ArrayList<>();

        int i = 0;

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            HashMap<String, File> tempMap;

            if (extList.contains(renamer.getExt(file))){//sprawdza czy pliki o tym samym rozszerzeniu sie powtorzyły
                i++;
                tempMap = renamer.rename(file, content + " (" + i + ")");
            }else {
                tempMap = renamer.rename(file, content);
                extList.add(renamer.getExt(file));
            }

            filesOut.putAll(tempMap);
        }

        return filesOut;
    }

    HashMap<String, File> renameOrdered(HashMap<String, File> files, String content){//zmienia nazwe plików na nowa podana + cyfre dla numeracji

        Collection<File> fileCollection = files.values();
        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        int i = 0;

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            i++;

            HashMap<String, File> tempMap = renamer.rename(file, content + " (" + i + ")");

            filesOut.putAll(tempMap);
        }

        return filesOut;
    }

    HashMap<String, File> addToAll(HashMap<String, File> files, String content){//dodaje do wszystkich nazw

        Collection<File> fileCollection = files.values();
        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            HashMap<String, File> tempMap = renamer.addName(file, content);

            filesOut.putAll(tempMap);
        }

        return filesOut;
    }

    HashMap<String, File> addOrder(HashMap<String, File> files){//dodale cyferki do kazdej nazwy

        Collection<File> fileCollection = files.values();
        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        int i = 0;

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            i++;

            HashMap<String, File> tempMap = renamer.addName(file, " (" + i + ")");

            filesOut.putAll(tempMap);
        }

        return filesOut;
    }

    HashMap<String, File> takeFromAll(HashMap<String, File> files, String content){//usuwa wpisane z wszyskich

        Collection<File> fileCollection = files.values();
        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            HashMap<String, File> tempMap = renamer.takeName(file, content);

            filesOut.putAll(tempMap);
        }

        return filesOut;
    }

    //TODO renameFromMask etc.
}
