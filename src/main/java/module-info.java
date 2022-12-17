module hr.java.vjezbe.glavna {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.java.vjezbe.glavna to javafx.fxml;
    exports hr.java.vjezbe.glavna;
}