package com.piechureq.filerenamer2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.*;

public class MainController {
    @FXML private GridPane root;
    @FXML private ListView<String> list;
    @FXML private ComboBox<String> task;
    @FXML private TextField input;
    @FXML private ChoiceBox<String> option;
    @FXML private Button defButton;

    private Renamer renamer = new Renamer();
    private RenamerUtilities rUtilities = new RenamerUtilities();
    HashMap files = new HashMap();

    public void initialize(){
        defButton.setDefaultButton(true);
        //
        task.setItems(FXCollections.observableList(Arrays.asList("Renamer", "Add to name", "Remove from name")));
        task.getSelectionModel().select(0);
        //
        option.setItems(FXCollections.observableList(Arrays.asList("Apply to selected", "Apply to all")));
        option.getSelectionModel().select(0);
        //
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //

    }

    public void addClicked(ActionEvent actionEvent) {
        Window w = root.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory();

        //try catch? blad przy anulowniu

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(w);
        String[] strings = new String[selectedFiles.size()];
        for (File file : selectedFiles) {//HashMap, każdym elementem z listy ma przypisany File do zmiany nazwy
            strings[selectedFiles.indexOf(file)] = new String();
            strings[selectedFiles.indexOf(file)] = file.getName();
            files.put(strings[selectedFiles.indexOf((file))], file);
        }
        List<String> selectedFilesS = Arrays.asList(strings);
        ObservableList<String> items = list.getItems();
        ObservableList<String> newItems = FXCollections.observableList(selectedFilesS);
        items.addAll(newItems);
        list.setItems(items);
    }

    public void removeClicked(ActionEvent actionEvent) { //usuwa element z listy
        if (!list.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList<String> items = FXCollections.observableList(list.getItems());
            int index = list.getSelectionModel().getSelectedIndex();
            String mapElem = items.get(index);
            items.remove(index);
            list.setItems(items);

            //usuwa z hashmapy
            files.remove(mapElem);
        }
    }

    public void clearClicked(ActionEvent actionEvent) {
        list.setItems(null);

        files.clear();
    }

    public void doClicked(ActionEvent actionEvent) {//TODO obsługa wielu wybranych , obsluga wszystkich
        if (option.getSelectionModel().getSelectedIndex() == 0) {

            if (!input.getText().isEmpty()) {
                HashMap newName;
                String keyString;
                String assist = list.getSelectionModel().getSelectedItems().toString();
                int index = list.getSelectionModel().getSelectedIndex();
                assist = assist.substring(1, assist.length() - 1);

                switch (task.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        if (!assist.contains(input.getText())) {//sprawdza czy nazwa jest zbieżna z nową jeśli tak to nic nie robi
                            newName = renamer.rename((File) files.get(assist), input.getText()); //zwraca HashMap key to nazwa do wysietlenia i value to File
                            keyString = (String) newName.keySet().toArray()[0];//nowa nazwa do listy

                            System.out.println(keyString);

                            list.fireEvent(new ListView.EditEvent<>(list, ListView.editCommitEvent(), keyString, index));
                            files.put(keyString, newName.get(keyString));//dodaje nowy klucz do mapy z nowa nazwa
                            if (files.get(assist).equals(keyString))
                                files.remove(assist);//usuwa juz nieuzywany klucz
                        }
                        break;
                    case 1:
                        if (!assist.contains(input.getText())) {//same^
                            newName = renamer.addName((File) files.get(assist), input.getText());
                            keyString = (String) newName.keySet().toArray()[0];

                            System.out.println(keyString);

                            list.fireEvent(new ListView.EditEvent<>(list, ListView.editCommitEvent(), keyString, index));
                            files.put(keyString, newName.get(keyString));
                            if (files.get(assist).equals(keyString))
                                files.remove(assist);
                        }
                        break;
                    case 2:
                        if (assist.contains(input.getText())) {//odwrotnie jeśli zawiera zazwa to co sie wpisze to wtedy tylko robi
                            newName = renamer.takeName((File) files.get(assist), input.getText());
                            keyString = (String) newName.keySet().toArray()[0];

                            System.out.println(keyString);

                            list.fireEvent(new ListView.EditEvent<>(list, ListView.editCommitEvent(), keyString, index));
                            files.put(keyString, newName.get(keyString));
                            if (files.get(assist).equals(keyString))
                                files.remove(assist);
                        }
                        break;
                }
            }
        }


        if(option.getSelectionModel().getSelectedIndex() == 1){

            files = rUtilities.applyToAll(files, input.getText());

            for (int x = 0; x < files.size(); x++ ) {
                String name = (String) files.keySet().toArray()[x];
                list.fireEvent(new ListView.EditEvent<>(list, ListView.editCommitEvent(), name, x));
            }

        }
    }
}
