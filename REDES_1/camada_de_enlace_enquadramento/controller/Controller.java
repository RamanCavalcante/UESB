package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Aplicacao;
import model.camada_fisica.Message;
import model.camada_fisica.Rede;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 19/03/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: Controller.java
* Funcao...........: Parte que controla todos o componentes da interface
************************************************************** */


public class Controller	implements Initializable {



	@FXML
	public static Button btn_enviar;

	@FXML
	public TextArea entrada_lbl;

	@FXML
	public ChoiceBox<String> menu_codificacao;

  @FXML
  private ChoiceBox<String> menu_Enquadro;

	@FXML
	public TextArea saida_lbl;

  @FXML
  public TextArea grafico_lbl;

  @FXML
  private HBox hbox_grafico;

  @FXML
  private Slider grafico_sld;

  @FXML
  private Label lbl_sldGrafico;

/* ***************************************************************
* Metodo: initialize
* Funcao: inicializa as lista do choice box e o slider
* Parametros: ...
* Retorno: void
*************************************************************** */
	public void initialize(URL location, ResourceBundle resources) {
		String [] listCodificacao = {"...","Binario", "Manchester","Manchester Diferencial"};
		menu_codificacao.setItems(FXCollections.observableArrayList(listCodificacao));
		menu_codificacao.setValue(listCodificacao[1]);
    String [] listEnquadro = {"...","Contagem de caracteres","Incersao de bytes","incersao de bits","violacao da camada Fisica"};
    menu_Enquadro.setItems(FXCollections.observableArrayList(listEnquadro));
    menu_Enquadro.setValue(listEnquadro[1]);

    grafico_sld.valueProperty().addListener( (obs, oldValue, newValue) -> Rede.time = (int)(double)newValue);
	}


/* ***************************************************************
* Metodo: SendMessage
* Funcao: inicializa o envento de enviar a menssagem
* Parametros: ...
* Retorno: void
*************************************************************** */
	
	@FXML
  void SendMessage(ActionEvent event) {
    
		int protocoloSelect = 0;
    int tipoEnquadramento = 0;
		// Encerra o enveto caso a caixa de texto esteja vazia
		if(entrada_lbl.getText().equals("")){		return;} 
      protocoloSelect = menu_codificacao.getSelectionModel().getSelectedIndex();
      tipoEnquadramento = menu_Enquadro.getSelectionModel().getSelectedIndex();
			Message objMessage = new Message(entrada_lbl, protocoloSelect, "", saida_lbl, grafico_lbl, tipoEnquadramento);
			Aplicacao objAplicacao = new Aplicacao(objMessage, hbox_grafico);
            objAplicacao.startAplicacao();
			//btn_enviar.setDisable(true);
	}
/* ***************************************************************
* Metodo: onMouse
* Funcao: atualiza o valor do slider na variavel
* Parametros: ...
* Retorno: void
*************************************************************** */
  @FXML
  void onMouse(MouseEvent event) {
    if(event.getSource().equals(grafico_sld)){
      lbl_sldGrafico.setText(""+(int)Rede.time);
    }
  }

  

} 

