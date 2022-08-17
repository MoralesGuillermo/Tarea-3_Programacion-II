module com.mycompany.calculadora {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.calculadora to javafx.fxml;
    exports com.mycompany.calculadora;
}
