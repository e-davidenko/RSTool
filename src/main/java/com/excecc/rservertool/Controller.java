package com.excecc.rservertool;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Controller {
    @FXML
    protected Label welcomeText;
    public CheckBox revitAR;
    public CheckBox revitKR;
    public CheckBox revitEL;
    public CheckBox revitOV;
    public CheckBox revit2022;
    public CheckBox revitVK;
    public CheckBox revitTX;

    public ListView<String> listPC;

    public Button refreshPC;

    public void setListPC(ListView<String> listPC) {
        this.listPC = listPC;
    }

    private int countLocal = 0;
    @FXML
    protected void getListPC() throws IOException {
        if (countLocal == 0) {
            welcomeText.setText("Подождите");
            listPC.setItems(JsonConfig.getPCList().filtered(x -> x.length() > 2));
            welcomeText.setText("Список ПК получен");
            countLocal++;
            pingStatus.setText("");
            sumPC.setText("Количество ПК - " + JsonConfig.getPCList().filtered(x -> x.length() > 2).size());
        }
    }


public String targetPC;
    public Label pingStatus;
    public Label sumPC;
    public ChoiceBox<String> menuBox;
    private int count = 0;


    @FXML
    protected void updateSRV() {
        while (count == 0) {
            menuBox.getItems().clear();
            LinkedHashSet<String> collection = new LinkedHashSet<>(Arrays.asList("2022", "2021", "2020"));
            collection.stream().sorted().forEach(p -> menuBox.getItems().add(p));
            menuBox.setValue("2022");
            count++;
        }
    }

    @FXML
    protected void onButtonClick() throws IOException {
        welcomeText.setText(String.valueOf(targetPC));
        revitFileEditor();
        welcomeText.setText("Успешно обновлены параметры на ПК " + targetPC);
    }

    public void revitFileEditor() throws IOException {
        StringBuilder targetFileEdit = new StringBuilder();
        targetFileEdit.append("\\\\");
        targetFileEdit.append(targetPC);
        targetFileEdit.append("\\AutoDesk$\\Revit Server ");
        targetFileEdit.append(menuBox.getValue());
        targetFileEdit.append("\\Config\\");
        targetFileEdit.append("RSN.ini");
        System.err.println("target file is " + targetFileEdit);
        File file = new File(targetFileEdit.toString());
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            if (revit2022.isSelected()) {
                fileWriter.append(revit2022.getText());
                fileWriter.append("\n");
            }
            if (revitAR.isSelected()) {
                fileWriter.append(revitAR.getText());
                fileWriter.append("\n");
            }
            if (revitEL.isSelected()) {
                fileWriter.append(revitEL.getText());
                fileWriter.append("\n");
            }
            if (revitVK.isSelected()) {
                fileWriter.append(revitVK.getText());
                fileWriter.append("\n");
            }
            if (revitOV.isSelected()) {
                fileWriter.append(revitOV.getText());
                fileWriter.append("\n");
            }
            if (revitKR.isSelected()) {
                fileWriter.append(revitKR.getText());
                fileWriter.append("\n");
            }
            if (revitTX.isSelected()) {
                fileWriter.append(revitTX.getText());
                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();


        } catch (IOException e) {
            file.createNewFile();
            welcomeText.setText("Проблемы с записью файла");
        }
    }

    @FXML
    protected void checkPing() {
        targetPC = listPC.getSelectionModel().getSelectedItem().replace("[", "").replace("]", "").strip();
        RevitFileChecker r2022 = new RevitFileChecker("2022");
        RevitFileChecker ar = new RevitFileChecker("AR");
        RevitFileChecker kr = new RevitFileChecker("KR");
        RevitFileChecker el = new RevitFileChecker("EL");
        RevitFileChecker vk = new RevitFileChecker("VK");
        RevitFileChecker ov = new RevitFileChecker("OV");
        RevitFileChecker tx = new RevitFileChecker("TX");

        StringBuilder targetFile = new StringBuilder();
        targetFile.append("\\\\");
        targetFile.append(targetPC);
        targetFile.append("\\AutoDesk$\\Revit Server ");
        targetFile.append(menuBox.getValue());
        targetFile.append("\\\\Config\\\\RSN.ini");
        System.out.println(targetFile.toString());
        Path path = Paths.get(targetFile.toString());
        File file = new File(path.toString());
        List<String> data = null;
        try {
            data = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            welcomeText.setText("");
        } catch (IOException e) {
            welcomeText.setText("Не удалось найти ПК или прочитать файл");
        }
        try {
            r2022.checkExist(data, revit2022);
            ar.checkExist(data, revitAR);
            kr.checkExist(data, revitKR);
            el.checkExist(data, revitEL);
            ov.checkExist(data, revitOV);
            tx.checkExist(data, revitTX);
            vk.checkExist(data, revitVK);
            welcomeText.setMaxWidth(320);
            welcomeText.setWrapText(true);
            welcomeText.setText("Успешно получена информация с ПК " + targetPC);
        } catch (Exception e) {
            welcomeText.setText("Проблемы с подключением к ПК");
        }
    }

    public void updatePCList(MouseEvent mouseEvent) throws IOException {

        JsonConfig.updatePCList();

    }
}
