package com.excecc.rservertool;



import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class JsonConfig {
    public static List<String> getOUConfig() throws IOException {
        Object obj = null;
        try {
            File file = new File("\\\\192.168.4.1\\SOFT\\RServerTool\\config.json");
            obj = new JSONParser().parse(new FileReader(file));
        } catch (IOException i) {
            throw new IOException(i);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) obj;
        Map ous = ((Map) jsonObject.get("OUs"));
        Iterator<Map.Entry> iterator = ous.entrySet().iterator();
        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry pair = iterator.next();
            list.add((String) pair.getValue());
        }
        System.err.println(Arrays.asList(list.toArray()));
        return list;
    }

    public static ObservableList<String> getPCList() throws IOException {
        List<String> pcs = new ArrayList<>();

        File file = new File("\\\\192.168.4.1\\SOFT\\RServerTool\\listPC.bin");
        String fileContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
        fileContent = fileContent.replace("----", "");
        fileContent = fileContent.replace(" ", "");
        fileContent = fileContent.replace("Name", "");
        List<String> list = (Arrays.asList(fileContent.split("\\r?\\n")));
        Collections.sort(list);


//        int minus = JsonConfig.getOUConfig().size() * 4;
//        minus = minus + (JsonConfig.getOUConfig().size() - 1);
//        System.err.println("Minus is:" + minus + " list is " + list.size());
//        list = list.subList(minus, list.size());
        fileContent = Arrays.toString(list.toArray());
        pcs = FXCollections.observableList(list);








        System.err.println(pcs);

        return FXCollections.observableList(pcs);

    }

    public static void updatePCList() throws IOException {
        Object obj = null;
        File file = new File("\\\\192.168.4.1\\SOFT\\RServerTool\\listPC.bin");
        StringBuilder command = new StringBuilder();
        command.append("$OUs= \n");
        for (int i = 0; i < JsonConfig.getOUConfig().size(); i++) {
            command.append("\"");
            command.append(JsonConfig.getOUConfig().get(i));
            command.append("\", \n");
        }
        command.deleteCharAt(command.length() - 3);
        System.err.println(String.valueOf(command));

        try (PowerShell powerShell = PowerShell.openSession()) {
            PowerShellResponse setOU = powerShell.executeCommand(String.valueOf(command));
            String listStringPC = powerShell.executeCommand("Foreach($OU in $OUs) {Get-ADComputer -Filter * -SearchBase $OU -Properties  Name | Select-Object -Property Name | ft -Wrap -Auto}").getCommandOutput();
            FileWriter fileWriter = new FileWriter(file, false);
            listStringPC = listStringPC.replace("----", "");
            listStringPC = listStringPC.replace(" ", "");
            listStringPC = listStringPC.replace("Name", "");
            List<String> list = (Arrays.asList(listStringPC.split(", ")));
            Collections.sort(list);
            int minus = JsonConfig.getOUConfig().size() * 4;
            minus = minus + (JsonConfig.getOUConfig().size() - 1);
            System.err.println("Minus is:" + minus + " list is " + list.size());
//            list = list.subList(minus, list.size());
            listStringPC = Arrays.toString(list.toArray());
            ObservableList<String> observableList = FXCollections.observableList(list);
//                    .subList(minus, list.size()));
            fileWriter.append(listStringPC);
            System.err.println("ITS HERE: " + listStringPC);
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
