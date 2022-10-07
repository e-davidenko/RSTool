package com.excecc.rservertool;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class HelloController {
    @FXML
    protected Label welcomeText;
    public CheckBox rvtAR;
    public CheckBox rvtKR;
    public TextField targetPC;
    public Label pingStatus;

    @FXML
    protected void onHelloButtonClick() throws IOException {

        welcomeText.setText(String.valueOf(targetPC));
    }

    @FXML
    protected void checkPing() throws IOException {
        //String path = "C:/";
        //Path path1 = Paths.get(path + "1.txt");
        //System.out.println(path1.toString());
        //Scanner scanner = new Scanner(path1);

        StringBuilder targetFile = new StringBuilder();
        targetFile.append("\\\\");
        targetFile.append(targetPC.getCharacters());
        targetFile.append("\\C$\\1.txt");
        StringBuilder pathSB = targetFile;
        Path path = Paths.get(pathSB.toString());
        File file = new File(path.toString());
        List<String> data = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        if (data.contains("RevitAR")) {
            rvtAR.setSelected(true);
        } else {
            rvtAR.setSelected(false);
        }
        if (data.contains("RevitKR")) {
            rvtKR.setSelected(true);
        } else {
            rvtKR.setSelected(false);
        }
    }
}
