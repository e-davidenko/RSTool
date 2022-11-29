module com.excecc.rservertool {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires jPowerShell;
    requires json.simple;
    opens com.excecc.rservertool to javafx.fxml;
    exports com.excecc.rservertool;
}