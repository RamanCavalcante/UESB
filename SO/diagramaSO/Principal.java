
import java.io.IOException;

import javax.xml.crypto.KeySelector.Purpose;

import controller.ControllerSample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application{

  public static void main(String []args){
    launch(args);
  }

  public void start(Stage primaryStage) throws IOException{
    ControllerSample controller = new ControllerSample();
    Parent root = FXMLLoader.load(getClass().getResource("/view/sample.fxml"));
    primaryStage.setTitle("Gerenciamento de processos");
    primaryStage.setScene(new Scene(root));
    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    primaryStage.show();
  }
}