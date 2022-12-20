import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 18/09/2021
 * Ultima alteracao: 28/09/2021
 * Nome: Principal.java
 * Funcao: inicial a interface
 * ************************************************************** */

public class Principal extends Application {
  public static void main(String []args){
    launch(args);  
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Controller controller = new Controller();
    Parent root = FXMLLoader.load(getClass().getResource("/res/layout/sample.fxml"));
    primaryStage.setTitle("MET - GALA");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); 
    primaryStage.show();
  }
}

