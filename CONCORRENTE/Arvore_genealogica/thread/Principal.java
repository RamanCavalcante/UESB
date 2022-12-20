import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Controller;
import util.Threads;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Raman Melo Cavalcante
Matricula: 201820754
Inicio...: 13 de agosto de 2020
Alteracao: 18 de agosto de 2020
Nome.....: Principal.java
Funcao...: Inicia a tela em JavaFx
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

public class Principal extends Application{
  public static void main(String []args){
   
  launch( args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("layout/tela.fxml"));
    primaryStage.setTitle("Arvore Genealogica");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    primaryStage.show();
  }
  
}