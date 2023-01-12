module hr.java.vjezbe.glavna {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires tornadofx.controls;


    opens hr.java.vjezbe.glavna to javafx.fxml;
    exports hr.java.vjezbe.glavna;
    exports hr.java.vjezbe.controller;
    opens hr.java.vjezbe.controller to javafx.fxml;
}