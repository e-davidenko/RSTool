package com.excecc.rservertool;

import javafx.scene.control.CheckBox;

import java.util.List;

public class RevitFileChecker extends Controller {
    private String serverName;
    @Override
    public String toString() {
        return serverName;
    }

    public RevitFileChecker(String serverName) {
        this.serverName = "Revit" + serverName;
    }

    public void checkExist(List<String> data, CheckBox serverName) {
        if (data.contains(serverName.getText())) {
            serverName.setSelected(true);
        } else {
            serverName.setSelected(false);
        }
    }
}
