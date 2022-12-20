package util;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 18/09/2021
 * Ultima alteracao: 28/09/2021
 * Nome: Controller.java
 * Funcao: classe onde a interface grafica e alterada
 * ************************************************************** */

public class Controller implements Initializable {
  @FXML
    private ImageView imgFilo04 = new ImageView();

    @FXML
    private ImageView imgFilo03= new ImageView();

    @FXML
    private ImageView imgFilo00= new ImageView();

    @FXML
    private ImageView imgFilo02= new ImageView();

    @FXML
    private ImageView imgFilo01= new ImageView();

    @FXML
    private ImageView imgGarfo00= new ImageView();

    @FXML
    private ImageView imgGarfo01= new ImageView();

    @FXML
    private ImageView imgGarfo02= new ImageView();

    @FXML
    private ImageView imgGarfo03= new ImageView();

    @FXML
    private ImageView imgGarfo04= new ImageView();

    @FXML
    private ImageView think01= new ImageView();

    @FXML
    private ImageView think00= new ImageView();

    @FXML
    private ImageView think04= new ImageView();

    @FXML
    private ImageView think03= new ImageView();

    @FXML
    private ImageView think02= new ImageView();
    
    
    @FXML
    private Slider sld_pensar_1;

    @FXML
    private Slider sld_comer_1;

    @FXML
    private Slider sld_pensar_2;

    @FXML
    private Slider sld_comer_2;

    @FXML
    private Slider sld_pensar_3;

    @FXML
    private Slider sld_comer_3;

    @FXML
    private Slider sld_pensar_4;

    @FXML
    private Slider sld_comer_4;

    @FXML
    private Slider sld_pensar_5;

    @FXML
    private Slider sld_comer_5;

    @FXML
    private Button startDinner;
    
    @FXML
    private Button btnStop;
/*********************************************************
 * Nome.......: initialize
 * Funcao.....: seta todas as imagens usadas na interface 
 * Parametros.: MouseEvent event
 * retorno....: void
 *********************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      Gallery objGallery = new Gallery();
      objGallery.setImg();
    }
 /*********************************************************
 * Nome.......: star
 * Funcao.....: stancia todos os processos do tipo Filosofo 
 * Parametros.: MouseEvent event
 * retorno....: void
 *********************************************************/
    @FXML
    void start(ActionEvent event){
      startDinner.setDisable(true);

      //instanciando todos os processos do tipo filosofo
      Filosofo filosofo00 = new Filosofo(0, sem.garfo00, sem.garfo01, imgFilo00, imgGarfo00, imgGarfo01, think00);
      Filosofo filosofo01 = new Filosofo(1, sem.garfo01, sem.garfo02, imgFilo01, imgGarfo01, imgGarfo02, think01);
      Filosofo filosofo02 = new Filosofo(2, sem.garfo02, sem.garfo03, imgFilo02, imgGarfo02, imgGarfo03, think02);
      Filosofo filosofo03 = new Filosofo(3, sem.garfo03, sem.garfo04, imgFilo03, imgGarfo03, imgGarfo04, think03);
      Filosofo filosofo04 = new Filosofo(4, sem.garfo04, sem.garfo00, imgFilo04, imgGarfo04, imgGarfo00, think04);
      
      //atribuindo as variaveis aos sliders correspondentes
      sld_pensar_1.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo00.setVelPensando( (int)(double)newValue));
      sld_comer_1.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo00.setVelComendo( (int)(double)newValue));
      //--------------------------------------------------------------------------------------------------------------------
      sld_pensar_2.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo01.setVelPensando((int)(double)newValue));
      sld_comer_2.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo01.setVelComendo((int)(double)newValue));
      //--------------------------------------------------------------------------------------------------------------------
      sld_pensar_3.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo02.setVelPensando((int)(double)newValue));
      sld_comer_3.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo02.setVelComendo ((int)(double)newValue));
      //-------------------------------------------------------------------------------------------------------------------
      sld_pensar_4.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo03.setVelPensando((int)(double)newValue));
      sld_comer_4.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo03.setVelComendo ((int)(double)newValue));
      //-------------------------------------------------------------------------------------------------------------------
      sld_pensar_5.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo04.setVelPensando((int)(double)newValue));
      sld_comer_5.valueProperty().addListener( (obs, oldValue, newValue) -> filosofo04.setVelComendo((int)(double)newValue));

      //todos os processos sao instanciados
      filosofo00.start();
      filosofo01.start();
      filosofo02.start();
      filosofo03.start();
      filosofo04.start();
    }
/*********************************************************
 * Nome.......: stop
 * Funcao.....: troca o valor da variavel boolean para o laco
 * da thread encerrar
 * Parametros.: MouseEvent event
 * retorno....: void
 *********************************************************/
    @FXML
    void stop(ActionEvent event) {
      System.out.println("to aqui");
      Filosofo.nietzsche = false;
    }
/*********************************************************
 * Nome.......: onMouse
 * Funcao.....: recebe o evento do mouse, verifica em qual slider
 * aconteceu o evento, e seta a imagem de acordo com o valor 
 * determinado 
 * Parametros.: MouseEvent event
 * retorno....: void
 *********************************************************/
    public void onMouse(MouseEvent event){
       if(event.getSource().equals(sld_pensar_1)){//verfica em qual slider aconteceu o evento
         if(sld_pensar_1.getValue() <2500) think00.setImage(Gallery.thought);//se o value do slider for menor que 2500 a imagem vai ser setada
         if(sld_pensar_1.getValue() <2000) think00.setImage(Gallery.thought2);
         if(sld_pensar_1.getValue() <1000) think00.setImage(Gallery.thought3);
         if(sld_pensar_1.getValue() <500)  think00.setImage(Gallery.thought4);
        }
      if(event.getSource().equals(sld_pensar_2)){
        if(sld_pensar_2.getValue() <2500) think01.setImage(Gallery.thought);
        if(sld_pensar_2.getValue() <2000) think01.setImage(Gallery.thought2);
        if(sld_pensar_2.getValue() <1000) think01.setImage(Gallery.thought3);
        if(sld_pensar_2.getValue()  <500) think01.setImage(Gallery.thought4);
      }
      if(event.getSource().equals(sld_pensar_3)){
        if(sld_pensar_3.getValue() <2500) think02.setImage(Gallery.thought);
        if(sld_pensar_3.getValue() <2000) think02.setImage(Gallery.thought2);
        if(sld_pensar_3.getValue() <1000) think02.setImage(Gallery.thought3);
        if(sld_pensar_3.getValue()  <500) think02.setImage(Gallery.thought4);
      }
      if(event.getSource().equals(sld_pensar_4)){
        if(sld_pensar_4.getValue() <2500) think03.setImage(Gallery.thought);
        if(sld_pensar_4.getValue() <2000) think03.setImage(Gallery.thought2);
        if(sld_pensar_4.getValue() <1000) think03.setImage(Gallery.thought3);
        if(sld_pensar_4.getValue()  <500) think03.setImage(Gallery.thought4);
      }
      if(event.getSource().equals(sld_pensar_5)){
        if(sld_pensar_5.getValue() <2500) think04.setImage(Gallery.thought);
        if(sld_pensar_5.getValue() <2000) think04.setImage(Gallery.thought2);
        if(sld_pensar_5.getValue() <1000) think04.setImage(Gallery.thought3);
        if(sld_pensar_5.getValue()  <500) think04.setImage(Gallery.thought4);
      }
    }
    
  }


