
import java.io.IOException;

import controller.ControllerMain;
import controller.ControllerStart;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 16/07/2022
* Ultima alteracao.: 25/07/2022
* Nome.............: Principal.java
* Funcao...........: Inicializar programa
*************************************************************** */

public class Principal extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) throws Exception {

    ControllerStart start = new ControllerStart();
    Parent root = FXMLLoader.load(getClass().getResource("/view/screen/sample_start.fxml"));
    primaryStage.setTitle("Windows 2.0");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });// encerra todos processos

    primaryStage.setOnShown(ev -> {
      PauseTransition pt = new PauseTransition(Duration.seconds(4));
      pt.setOnFinished(e -> {
        try {
          Parent rootMain = FXMLLoader.load(getClass().getResource("/view/screen/sample_main.fxml"));
          primaryStage.setScene(new Scene(rootMain));
          primaryStage.show();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      });
      pt.play();
    });
    primaryStage.show();

  }

}
