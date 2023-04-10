import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Gallery;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Principal
 * Funcao...........: iniciar aplicacao
 * ***************************************************************/
public class Principal extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root;
    FXMLLoader loader = new FXMLLoader(getClass().getResource("view/sample.fxml"));
    root = loader.load();
    Controller controller = loader.getController();
    controller.setStage(primaryStage);
    primaryStage.setTitle("Network for inundation");
    primaryStage.getIcons().add(Gallery.ICON_NETWORK);
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });// Ao fechar a janela todos os processos sao fechados tambem
    primaryStage.show();
    // controller.showDialogStart();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
