
import modelo.Filme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Filme;
import controller.TelaInicialController;

/********************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 25/07/2021
 * Ultima alteracao: 01/08/2021
 * Nome: Principal
 * Funcao: Inicializa o programa
 ********************************************/
public class Principal extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    /******************************************
    * Metodo: start
    * Funcao: chama TelaInicial.fxml
    * Parametros: Stage primarystage
    * Retorno: void
    ******************************************/
    @Override
    public void start(Stage primaryStage) throws Exception{
    
			Parent root = FXMLLoader.load(getClass().getResource("/layout/TelaInicial.fxml"));
        Scene scene  = new Scene(root);
        primaryStage.setTitle("Copia do letterbox");
        primaryStage.setScene(scene);
        primaryStage.show();
    
			}
}
