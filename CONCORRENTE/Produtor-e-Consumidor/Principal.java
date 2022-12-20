
import src.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Principal.java
 * Funcao: Inicia a tela "sample.fxml"
 * ************************************************************** */

public class Principal extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Controller controller = new Controller();
    Parent root = FXMLLoader.load(getClass().getResource("res/layout/sample.fxml"));
    primaryStage.setTitle("Siri Cascudo");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); 
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

