package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/****************************************************************
 * Autor ............: Raman Melo Cavalcante
 * Matricula ........: 201820754
 * Inicio ...........: 16/07/2022
 * Ultima alteracao .: 22/07/2022
 * Nome .............: ControllerMain.java
 * Funcao ...........: Realiza alteracoes na interface graficas
 * **************************************************************/

public class ControllerMain implements Initializable {

  @FXML
  private AnchorPane AnchorHome;

  @FXML
  private VBox box_iniciar;

  @FXML
  private Button btn2;

  @FXML
  private MenuItem btn_close;

  @FXML
  private MenuItem btn_close1;

  @FXML
  private Button btn_encerrar;

  @FXML
  private Button btn_galeria;

  @FXML
  private Button btn_iniciar;

  @FXML
  private MenuItem btn_load;

  @FXML
  private MenuItem btn_load1;

  @FXML
  private MenuItem btn_save;

  @FXML
  private MenuItem btn_save1;

  @FXML
  private Button btn_seta_left, btn_seta_right;

  @FXML
  private Button btn_word_bar, btn_galeria_bar;

  @FXML
  private Button btn_word_start;

  @FXML
  private BorderPane editor;

  @FXML
  private BorderPane editor1;

  @FXML
  private ImageView imgView_galeria;

  @FXML
  private Button window_editor, window_galeria;


  ArrayList<Image> listIMG = new ArrayList<>();
  int indexGaleria, maxIndexGaleria;

/* ***************************************************************
* Metodo: initialize
* Funcao: seta configuracoes da interface
* Parametros: ...
* Retorno: void
**************************************************************** */

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    indexGaleria = 0;
    maxIndexGaleria = 5;
    box_iniciar.setVisible(false);
    window_editor.setVisible(false);
    window_galeria.setVisible(false);
    btn_word_bar.setVisible(false);
    btn_galeria_bar.setVisible(false);

    

    listIMG.add(Gallery.james_webb01);
    listIMG.add(Gallery.james_webb02);
    listIMG.add(Gallery.james_webb03);
    listIMG.add(Gallery.james_webb04);
    listIMG.add(Gallery.james_webb05);
    listIMG.add(Gallery.james_webb06);
    

    window_editor.setOnMouseDragged(e -> {
      window_editor.setLayoutX(e.getSceneX());
      window_editor.setLayoutY(e.getSceneY());
    });

    window_galeria.setOnMouseDragged(e -> {
      window_galeria.setLayoutX(e.getSceneX());
      window_galeria.setLayoutY(e.getSceneY());
    });
  }

/* ***************************************************************
* Metodo: menuStart
* Funcao: abre o menu iniciar
* Parametros: ...
* Retorno: void
**************************************************************** */

  @FXML
  void menuStart(ActionEvent event) {
    if (box_iniciar.isVisible()) {
      box_iniciar.setVisible(false);
    } else {
      box_iniciar.setVisible(true);
    }
  }

/* ***************************************************************
* Metodo: closeSO
* Funcao: encerra o SO
* Parametros: ...
* Retorno: void
**************************************************************** */

  @FXML
  void closeSO(ActionEvent event) {

    Stage stage = (Stage) btn_encerrar.getScene().getWindow();
    stage.close();
  }


/* ***************************************************************
* Metodo: openEditor
* Funcao: abre o editor de texto
* Parametros: ...
* Retorno: void
**************************************************************** */

  @FXML
  void openEditor(ActionEvent event) throws IOException {
    
    if (window_editor.isVisible()) {
      btn_word_bar.setVisible(false);
      window_editor.setVisible(false);
    } else {
      window_editor.setVisible(true);
      btn_word_bar.setVisible(true);
    }
  }

/* ***************************************************************
* Metodo: open_galeria
* Funcao: abre a galeria
* Parametros: ...
* Retorno: void
**************************************************************** */


  @FXML
  void open_galeria(ActionEvent event) {
    imgView_galeria.setImage(listIMG.get(0));
    if(window_galeria.isVisible()){
      btn_galeria_bar.setVisible(false);
      window_galeria.setVisible(false);
    }else{
      btn_galeria_bar.setVisible(true);
      window_galeria.setVisible(true);
    }
  }

/* ***************************************************************
* Metodo: passIMG
* Funcao: navega pelas imagens usando as setas
* Parametros: ...
* Retorno: void
**************************************************************** */

  @FXML
  void passIMG(ActionEvent event) {
    if(event.getSource().equals(btn_seta_left)){
      if(indexGaleria == 0){
        indexGaleria = maxIndexGaleria;
      }else{
        indexGaleria--;
      }
    }
    else if(event.getSource().equals(btn_seta_right)){
        if(indexGaleria == maxIndexGaleria){
          indexGaleria = 0;
        }else{
          indexGaleria++;
        }
      }  
    imgView_galeria.setImage(listIMG.get(indexGaleria));
  }


  @FXML
  private void onSave() {

  }

  @FXML
  private void onLoad() {

  }

  @FXML
  private void onClose() {
    System.exit(0);

  }

  @FXML
  private void onAbout() {

  }

}
