package com.piechureq.filerenamer2;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by xmichalx on 2017-03-28.
 */
public class RenamerUtilities {
    private Renamer renamer = new Renamer();

    //metoda apply to all
    HashMap<String, File> applyToAll(HashMap<String, File> files, String content){

        Collection<File> fileCollection = files.values();

        Iterator<File> fileIterator = fileCollection.iterator();

        HashMap<String, File> filesOut = new HashMap<>();

        //petla dla kazdego elementu mapy
        while (fileIterator.hasNext()){
            File f = fileIterator.next();
            //if poprzednie maja takei samo rozszerzenie
            HashMap tempMap = renamer.rename(f, content);

            filesOut.putAll(tempMap);
            System.out.println("Nowa nazwa   " + tempMap.toString());
        }

        //sprawdzac czy rozszerzenia sie pokrywaja jesli tak to dodac cyfre

        return filesOut;
    }



    //TODO metoda renameOrdered




    //TODO metoda addOrder




    //TODO metoda renameFromMask




    //TODO metoda addFromMask
}
