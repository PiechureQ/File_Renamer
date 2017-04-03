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

    //funkcje napisane przeze mnie
    void updateListViewAll(){
        for (int x = 0; x < files.size(); x++ ) {
            String name = (String) files.keySet().toArray()[x];
            list.fireEvent(new ListView.EditEvent<>(list, ListView.editCommitEvent(), name, x));
        }
    }

    void updateListView(HashMap value, String clicked, int index){
        String keyString = (String) value.keySet().toArray()[0];//nowa nazwa do listy

        list.fireEvent(new ListView.EditEvent<>(list, ListView.editCommitEvent(), keyString, index));
        files.putAll(value);//dodaje nowy klucz do mapy z nowa nazwa

        files.remove(clicked);//usuwa juz nieuzywany klucz
    }

    //metody kontrolera
    public void initialize(){
        defButton.setDefaultButton(true);
        //
        task.setItems(FXCollections.observableList(Arrays.asList("Rename", "Add to name", "Remove from name")));
        task.getSelectionModel().select(0);
        //
        option.setItems(FXCollections.observableList(Arrays.asList("Apply to selected", "Apply to all", "Rename with order")));
        option.getSelectionModel().select(0);
        //
        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //

    }

    public void addClicked(ActionEvent actionEvent) {
        Window w = root.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(w);

        ObservableList<String> items = list.getItems();

        String[] strings = new String[selectedFiles.size()];
        for (File file : selectedFiles) {//HashMap, każdym elementem z listy ma przypisany File do zmiany nazwy
            strings[selectedFiles.indexOf(file)] = new String();
            strings[selectedFiles.indexOf(file)] = file.getName();
            String name = strings[selectedFiles.indexOf((file))];
            files.put(name, file);

            items.add(name);
        }

        list.setItems(items);
    }

    public void removeClicked(ActionEvent actionEvent) { //usuwa element z listy
        if (!list.getSelectionModel().getSelectedItems().isEmpty()) {
            int index = list.getSelectionModel().getSelectedIndex();

            String removeThis = list.getItems().remove(index);

            files.remove(removeThis);
        }
    }

    public void clearClicked(ActionEvent actionEvent) {
        list.setItems(null);

        files.clear();
    }

    public void doClicked(ActionEvent actionEvent) {

        switch (option.getSelectionModel().getSelectedIndex()) {
            case 0:
                if (!input.getText().isEmpty()) {
                    HashMap newName;

                    String clicked = list.getSelectionModel().getSelectedItems().toString();
                    clicked = clicked.substring(1, clicked.length() - 1);
                    int index = list.getSelectionModel().getSelectedIndex();

                    switch (task.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            if (!clicked.contains(input.getText())) {//sprawdza czy nazwa jest zbieżna z nową jeśli tak to nic nie robi
                                newName = renamer.rename((File) files.get(clicked), input.getText()); //zwraca HashMap key to nazwa do wysietlenia i value to File
                                updateListView(newName, clicked, index);
                            }
                            break;
                        case 1:
                            if (!clicked.contains(input.getText())) {//same^
                                newName = renamer.addName((File) files.get(clicked), input.getText());
                                updateListView(newName, clicked, index);
                            }
                            break;
                        case 2:
                            if (clicked.contains(input.getText())) {//odwrotnie jeśli zawiera zazwa to co sie wpisze to wtedy tylko robi
                                newName = renamer.takeName((File) files.get(clicked), input.getText());
                                updateListView(newName, clicked, index);
                            }
                            break;
                    }
                }
                break;
            case 1:
                if (!input.getText().isEmpty()) {
                    files = rUtilities.applyToAll(files, input.getText());//zmienia nazwe wszystkich
                    updateListViewAll();
                }
                break;
            case 2:
                if (!input.getText().isEmpty()) {
                    files = rUtilities.renameOrdered(files, input.getText());//zmienia nazwe i przypisuje cyfry
                    updateListViewAll();
                }
                break;
        }
    }
}
