import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Gallery;

public class Principal extends Application {
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: Principal.java
 * Funcao...........: inicializa tela
 * ***************************************************************/


  public static void main(String [] args){
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/sample.fxml"));
    root = loader.load();
    Controller controller = loader.getController();
    controller.setStage(primaryStage);
    primaryStage.setTitle("");
    primaryStage.getIcons().add(Gallery.ICON_GRAPH);
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest((t) ->{
      Platform.exit();
      System.exit(0);
    });
    primaryStage.show();
  }
}
