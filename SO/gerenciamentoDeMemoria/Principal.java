import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.controller.Controller;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 09/09/2022
 * Ultima alteracao.: 17/09/2022
 * Nome: ...........: Principal
 * Funcao...........: inicia interface
 * ***************************************************************/

  public class Principal extends Application{
  
    public static void main(String[] args){
      launch(args);
    }
  
    @Override
    public void start(Stage primaryStage) throws Exception {
      Controller controller = new Controller();
      Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
      primaryStage.setTitle("");
      primaryStage.setScene(new Scene(root));
      primaryStage.setOnCloseRequest(t -> {
        Platform.exit();
        System.exit(0);
      });
      primaryStage.show();
    }
  }