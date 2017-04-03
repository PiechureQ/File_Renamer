package com.piechureq.filerenamer2;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created on 2017-03-28.
 */
public class RenamerUtilities {
    private Renamer renamer = new Renamer();

    //metoda apply to all
    HashMap<String, File> applyToAll(HashMap<String, File> files, String content){

        Collection<File> fileCollection = files.values();

        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        ArrayList<String> extList = new ArrayList<>();

        int i = 0;

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            HashMap<String, File> tempMap = new HashMap<>();

            if (extList.contains(renamer.getExt(file))){//sprawdza czy pliki o tym samym rozszerzeniu sie powtorzyły
                i++;
                tempMap = renamer.rename(file, content + " (" + i + ")");
            }else {
                tempMap = renamer.rename(file, content);
                extList.add(renamer.getExt(file));
            }

            filesOut.putAll(tempMap);
            System.out.println("Nowa nazwa   " + tempMap.toString());
        }

        return filesOut;
    }



    //metoda renameOrdered
    HashMap<String, File> renameOrdered(HashMap<String, File> files, String content){//zmienia nazwe plików na nowa podana + cyfre dla numeracji

        Collection<File> fileCollection = files.values();

        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        int i = 0;

        while (fileIterator.hasNext()){
            File file = fileIterator.next();

            i++;

            System.out.println();

            HashMap<String, File> tempMap = renamer.rename(file, content + " (" + i + ")");

            filesOut.putAll(tempMap);
        }

        return filesOut;
    }



    //TODO metoda addOrder




    //TODO metoda renameFromMask




    //TODO metoda addFromMask
}
