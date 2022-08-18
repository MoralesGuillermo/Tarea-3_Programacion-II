/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.calculadora;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author waiting
 */
public class CalculadoraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // EL RESULTADO DEBE DE MOSTRARSE EN COLOR GRIS ANTES DE APRETAR =
    // EL BOTON = CAMBIA EL LABEL DE ARRIBA POR EL DE LA RESPUESTA
    // Y EL DE LA RESPUESTA LO BORRA
    
    private String num1 = "";
    private String num2 = "";
    private String operacion = "";
    private String expr = "";
    private double resultado = 0;
    private Alert alert = new Alert(AlertType.WARNING);
    @FXML private Label ex;
    @FXML private Label result;
    
    /*
    Establecer y reiniciar los valores de los labels y
    variables si ocurre un Syntax o Math Error
    */
    private final void calcERROR(String type){
        ex.setText(type);
        result.setText("");
        num1 = "";
        num2 = "";
        operacion = "";
        expr = "";
        resultado = 0;     
    }
    
    
    public void Handle(ActionEvent e){
        Button pressed = (Button) e.getSource();
        /* Revisar si el botón presionado es de operacion
           los botones de operacion tienen op en su id*/ 
        if (pressed.getId().contains("op")){
            // Establecer la operacion
            setOperacion(pressed.getText());    // TODO: CHECK FOR CE AND C BUTTONS
        }else{
            //Establecer el numero
            setNumero(pressed.getText());
        }
    }
    
    private void setNumero(String num){
        // Establer los valores de los números de la suma
        // TODO: CHANGE THE NUMBERS SHOWN IN THE LABEL
        if (operacion.isBlank()){
            num1 += num;
        }else{
            num2 += num;
        }
        // Mostrar el label de la expresion realizada
        expr += num;
        ex.setText(expr);
        // Establecer el resultado de la expresion (si esta completa esta)
        setResultado();
    }
    
    private void setOperacion(String oper){
        // Establecer la operación elegida
        // TODO: CHANGE THE NUMBERS SHOWN IN THE LABEL
        if (operacion.isBlank() && !num1.isBlank()){
            operacion = oper;
            expr += operacion;
            ex.setText(expr);
        }else if (num1.isBlank()){
            /* 
            Denegar ingresar un operador antes del primer
            número
            */
            calcERROR("SYNTAX ERROR");
        }else if (num2.isBlank() && !operacion.isBlank()){
            // Indicar al usuario que no se permiten operaciones anidadas
            alert.setTitle("Advertencia");
            alert.setHeaderText("Operacion denegada");
            alert.setContentText("No se permiten operaciones "
                    + "anidadas");
            alert.showAndWait();
        }else{
            /*
            Se calcula el resultado y se usa este como num1
            para hacer multiples operaciones
            */
            setResultado();
            num1 = String.valueOf(resultado);
            operacion = oper;
            expr += operacion;
            ex.setText(expr);
            num2 = "";
        }
        
    }
    
    // Establecer y mostrar el resultado de la operacion
    private void setResultado(){
        // Transformar num1, num2 de String a int
        if (!num1.isBlank() && !num2.isBlank()){
            double Rnum1 = Double.parseDouble(num1);
            double Rnum2 = Double.parseDouble(num2);
                if (!operacion.isBlank()){
                switch (operacion){
                    case "+":
                        resultado = Rnum1 + Rnum2;
                        break;
                    case "-":
                        resultado = Rnum1 - Rnum2;
                        break;
                    case "*":
                        resultado = Rnum1 * Rnum2;
                        break;
                    case "/":
                        if (Rnum2 != 0){
                            resultado = Rnum1 / Rnum2;
                        }else{
                            calcERROR("MATH ERROR");
                            return;
                        }
                        break;
                    case "^":
                        resultado += (double) Math.pow(Rnum1, Rnum2); 
                        break;
                }
                // Establecer el texto de la Label con el resultado
                result.setText(String.valueOf(resultado));
            } 
        }
    }
    
    // Establecer los numeros y la operacion a ""
    @FXML 
    private void CDelete(){
        num1 = "";
        num2 = "";
        operacion = "";
        ex.setText("0");
        expr = "";
        result.setText("");
        resultado = 0;
    }
    
    /* CE Button functionality
        Deletes the last number inputted but 
        it doesnt delete the whole operation
        if the second number has been inputted
    */
    @FXML
    private void CEDelete(){
        if (num2.isBlank()){
            num1 = "";
            operacion = "";
            ex.setText("0");
            expr = "";
            result.setText("");
        }else{
            num2 = "";
            ex.setText(num1 + operacion);
            expr = num1 + operacion;
            result.setText("");
        }
    }
    
    /*
    Establecer la label de ex con el resultado 
    borrar la de resultado. A su vez, reiniciar
    los valores de los numeros y la operacion
    */
    @FXML 
    private void opEquals(){
        if (!num2.isBlank()){
            // Se escribio la operacion completa
            ex.setText(result.getText());
            result.setText("");
            num1 = String.valueOf(resultado);
            expr = num1;
            num2 = "";
            operacion = "";
        }else if (!num1.isBlank() && operacion.isBlank()){
            // Solo se ha escrito num1
            ex.setText(num1);
        }else if (!num1.isBlank() && !operacion.isBlank()){
            // Syntax Error
            calcERROR("SYNTAX ERROR");
        }else{
            // No se ha escrito nada
            ex.setText("0");
        }     
    }
    
    /*
    Aplicar la operacion inversa al numero que se esta ingresando
    */
    @FXML
    private void Inverse(){
        if (num2.isBlank()){
            // Se aplica la operacion a num1
            if (!num1.isBlank() && !num1.equals("0")){
                System.out.println(num1.equals("0"));
                // Conseguir el valor inverso del numero
                double num = 1/Double.parseDouble(num1);
                num1 = String.valueOf(num);
                ex.setText(num1 + operacion );
            }else{
                calcERROR("MATH ERROR"); 
            }
            
        }else{
            if (!num2.isBlank() && !num2.equals("0")){
                // Se aplica la operacion a num2
                // Conseguir el valor inverso del numero
                double num = 1/Double.parseDouble(num2);
                num2 = String.valueOf(num);
                ex.setText(num1 + operacion + num2 );
                setResultado();
            }else{
                calcERROR("MATH ERROR"); 
            }
            
        }
        
    }
}
