package util;
import java.net.URL;
import java.security.Principal;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
Autor......: Raman Melo Cavalcante
Matricula..: 201820754
Inicio.....: 23 de aogosto de 2021
Alteracao..: 1 de setembro de 2021
Nome.......: Controller.java
Funcao.....: Onde eh instaciado as threads do tipo Train
inicializada
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/
public class Controller implements Initializable{
  
  @FXML
    private ImageView imgTrain2;

    @FXML
    private ImageView imgRick2;

    @FXML
    private ImageView imgRick1;

    @FXML
    private ImageView imgTrain1;

    @FXML
    private Slider sliderTrain1;

    @FXML
    private Slider sliderTrain2;
   
    @FXML
    public  Button btnReset;


    int circuit1 [] = {5,6,8,6,5,7,9,7,5};//crio um vetor com as opcoes para o circuito
    int circuit2 [] = {1,2,10,3,1,2,4,3,1};
 /***************************************************************
 * Nome.......: initialize
 * Funcao.....: instacia os objetos do tipo Train
 * Parametros.: URL location, ResourceBundle resources
 ***************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      btnReset.setDisable(true);//desabilito botao reset
      // os objetos tipo Train sao instanciados
      Train train1 = new Train(this,imgTrain1,circuit1,sliderTrain1,0);
      train1.start();//dando start no objeto criado
      Train train2 = new Train(this,imgTrain2,circuit2,sliderTrain2,0);
      train2.start();
      //**onde uso  o slider com a thread de cada Train
      sliderTrain1.valueProperty().addListener( (obs, oldValue, newValue) -> train1.slide = (int)(double)newValue);
      sliderTrain2.valueProperty().addListener( (obs, oldValue, newValue) -> train2.slide = (int)(double)newValue);
    }

 /***************************************************************
 * Nome.......: reset
 * Funcao.....: quando for clicado no botao rest reseto as 
 * posicoes dos trens e starto novas Threads
 * Parametros.: event do tipo ActionEvent 
 ***************************************************************/
    @FXML
    void reset(ActionEvent event){//aqui instacio novamente objetos do tipo Train e dous start
      btnReset.setDisable(true);//desabilito o botao reset
      Train train1 = new Train(this,imgTrain1,circuit1,sliderTrain1,0);
      train1.startReset1();
      train1.start();
      Train train2 = new Train(this,imgTrain2,circuit2,sliderTrain2,0);
      train2.startReset2();
      train2.start();
      sliderTrain1.valueProperty().addListener( (obs, oldValue, newValue) -> train1.slide = (int)(double)newValue);
      sliderTrain2.valueProperty().addListener( (obs, oldValue, newValue) -> train2.slide = (int)(double)newValue);
    }

    public ImageView getImgTrain1() {
      return imgTrain1;
    }

    public void setImgTrain1(ImageView imgTrain1) {
      this.imgTrain1 = imgTrain1;
    }

    public ImageView getImgTrain2() {
      return imgTrain2;
    }

    public void setImgTrain2(ImageView imgTrain2) {
      this.imgTrain2 = imgTrain2;
    }

   
    public ImageView getImgRick2() {
      return imgRick2;
    }

    public void setImgRick2(ImageView imgRick2) {
      this.imgRick2 = imgRick2;
    }

    public ImageView getImgRick1() {
      return imgRick1;
    }

    public void setImgRick1(ImageView imgRick1) {
      this.imgRick1 = imgRick1;
    }
    public Button getBtnReset() {
      return btnReset;
    }

    public void setBtnReset(Button btnReset) {
      this.btnReset = btnReset;
    }

  }
