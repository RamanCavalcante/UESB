package src;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Controller.java
 * Funcao: Onde eu faco controle de acoes na interface grafica
 * ************************************************************** */

public class Controller implements Initializable{

    @FXML
    private ListView<?> listPedidos;

    @FXML
    private ImageView imgConsumidor;

    @FXML
    private ImageView imgProdutor;

    @FXML
    private Button start;

    @FXML
    private Button stop;
    
    @FXML
    private Slider sld_consumidor;

    @FXML
    private Slider sld_produtor;

    @FXML 
    public Label lbl_produtor;
    
    @FXML
    public Label lbl_consumidor;
    
    @FXML
    private ListView<?> listProntos;

    @FXML
    private Label lbl_velProdutor;
    
    @FXML
    private Label lbl_velConsumidor;

/*********************************************************
 * Nome.......: initialize
 * Funcao.....: da start na thread da classe Gallery para 
 * setar as imagens 
 * Parametros.: 
 * retorno....: void
 *********************************************************/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      Gallery gallery = new Gallery();
      gallery.start();//a classe Gallary eh startada setando todas imagens
    }

/*********************************************************
 * Nome.......: start
 * Funcao.....: acao do botao start
 * Parametros.: event do tipo ActionEvent
 * retorno....: void
 *********************************************************/
    
    @FXML
    void start(ActionEvent event) {
      Produtor.toto = true;//dando valor de true para que as threads produtor e consumidor possam rodar
     //o objeto do tipo ObservableList eh criado para atualizar a lista de pedidos e outro para lista de
     // e outra para lista de pedidos
      ObservableList lista = FXCollections.observableArrayList();
      ObservableList listReady = FXCollections.observableArrayList();
      listPedidos.setItems(lista);
      listProntos.setItems(listReady);
      Buffer buffer = new Buffer(lista, lbl_produtor, lbl_consumidor, listReady);//instanciando um objeto do tipo buffer
      Produtor mr_krabs = new Produtor(buffer, sld_produtor);//instanciando o produtor
      Consumidor spongeBob = new Consumidor(buffer, sld_consumidor, lbl_consumidor);//instanciando o consumidor

      sld_produtor.valueProperty().addListener( (obs, oldValue, newValue) -> mr_krabs.sld_produtor = (int)(double)newValue);
      sld_consumidor.valueProperty().addListener( (obs, oldValue, newValue) -> spongeBob.sld_consumidor = (int)(double)newValue);

      mr_krabs.start();
      spongeBob.start();
    }

/*********************************************************
 * Nome.......: stop 
 * Funcao.....: Acao do botao stop
 * Parametros.: event do tipo ActionEvent
 * retorno....: void
 *********************************************************/

    @FXML
    void stop(ActionEvent event) {
      //trocando o valor para false da variavel da condicao das threads produtor e consumidor
      Produtor.toto = false;
    }

/*********************************************************
 * Nome.......: onMouse
 * Funcao.....: pega o valor do slider enquanto ele eh 
 * arrastado 
 * Parametros.: event do tipo MouseEvent 
 * retorno....: void
 *********************************************************/

  public void onMouse(MouseEvent event) {
    if (event.getSource().equals(sld_produtor)) {//verifica em qual dos slider estiver mexendo
      //verifica qual o valor slider e seta a imagem
      if(sld_produtor.getValue()<10000) imgProdutor.setImage(Gallery.prod00);
      if(sld_produtor.getValue()<9000)  imgProdutor.setImage(Gallery.prod09);
      if(sld_produtor.getValue()<8000)  imgProdutor.setImage(Gallery.prod01);
      if(sld_produtor.getValue()<7000)  imgProdutor.setImage(Gallery.prod02);
      if(sld_produtor.getValue()<6000)  imgProdutor.setImage(Gallery.prod03);
      if(sld_produtor.getValue()<5000)  imgProdutor.setImage(Gallery.prod04);
      if(sld_produtor.getValue()<4000)  imgProdutor.setImage(Gallery.prod05);
      if(sld_produtor.getValue()<3000)  imgProdutor.setImage(Gallery.prod06);
      if(sld_produtor.getValue()<2000)  imgProdutor.setImage(Gallery.prod07);
      lbl_velProdutor.setText(""+(int)sld_produtor.getValue());
    }

    if(event.getSource().equals(sld_consumidor)){
      if(sld_consumidor.getValue()<15000) imgConsumidor.setImage(Gallery.bob01);
      if(sld_consumidor.getValue()<13000) imgConsumidor.setImage(Gallery.bob02);
      if(sld_consumidor.getValue()<12000) imgConsumidor.setImage(Gallery.bob03);
      if(sld_consumidor.getValue()<10000) imgConsumidor.setImage(Gallery.bob04);
      if(sld_consumidor.getValue()<8000)  imgConsumidor.setImage(Gallery.bob05);
      if(sld_consumidor.getValue()<6000)  imgConsumidor.setImage(Gallery.bob06);
      if(sld_consumidor.getValue()<4000)  imgConsumidor.setImage(Gallery.bob07);
      if(sld_consumidor.getValue()<2500)  imgConsumidor.setImage(Gallery.bob08);
      if(sld_consumidor.getValue()<1500)  imgConsumidor.setImage(Gallery.bob09);
      if(sld_consumidor.getValue()<1000)  imgConsumidor.setImage(Gallery.bob10);
      lbl_velConsumidor.setText(""+(int)sld_consumidor.getValue());
    }
  }
}
