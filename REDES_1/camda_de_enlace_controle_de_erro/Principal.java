import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import controller.Controller;
/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2020
* Nome.............: Principal.java
* Funcao...........: Inicializar programa
*************************************************************** */

public class Principal extends Application {

  public static Image icon = new Image("view/img/joker.jpg");
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
    Controller objController = new Controller();
    primaryStage.setTitle("REDES I ");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    }); // encerra todos os processos
    primaryStage.getIcons().add(icon);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
