
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Controller;

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
Autor......: Raman Melo Cavalcante
Matricula..: 201820754
Inicio.....: 23 de aogosto de 2021
Alteracao..: 1 de setembro de 2021
Nome.......: Principal.java
Funcao.....: Inicia tela em Javafx
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/

public class Principal extends Application{
  public static void main(String []args){
   
  launch( args);
  }

  public  void start(Stage primaryStage) throws Exception {
    Controller objControle = new Controller();  
    Parent root = FXMLLoader.load(getClass().getResource("layout/Tela.fxml"));
    primaryStage.setTitle("Problema do trem");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    primaryStage.show();
  }
  
}
