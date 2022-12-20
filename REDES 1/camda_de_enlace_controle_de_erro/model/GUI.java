package model;

import static javafx.scene.paint.Color.RED;

import java.util.Arrays;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: GUI.java
* Funcao...........: classe utilizada para instanciar objetos da interface
****************************************************************/

public class GUI {

  
  private TextArea entrada_lbl;
  private TextArea saida_lbl;
  private HBox hbox_grafico;
  private Slider grafico_sld;
  private Label lbl_sldGrafico;
  private Slider porcetagem_erro;
  private TextArea lbl_print_info;
  private HBox HBoxAck;

  /* ***************************************************************
* Metodo: GUI
* Funcao: classe que gerencia objetos da interface
* Parametros: ...
* Retorno: 
********************************************************************/

  public GUI(TextArea entrada_lbl, TextArea saida_lbl, HBox hbox_grafico, Slider grafico_sld, Label lbl_sldGrafico,
      Slider porcetagem_erro, TextArea lbl_print_info, HBox HBoxAck) {
    this.entrada_lbl = entrada_lbl;
    this.saida_lbl = saida_lbl;
    this.hbox_grafico = hbox_grafico;
    this.grafico_sld = grafico_sld;
    this.lbl_sldGrafico = lbl_sldGrafico;
    this.porcetagem_erro = porcetagem_erro;
    this.lbl_print_info = lbl_print_info;
    this.HBoxAck = HBoxAck; 
  }



  public void toStringMessage(int []quadro, String  camadaAtual ){
    final String iNFO;
    String mensagem = Aplicacao.convertBinaryToMsg(quadro);
    String printINFO = "ETAPA: "+camadaAtual+"\n";
    printINFO += "MESSAGEM: ["+mensagem+"]\n";
    printINFO += "BITS: "+Arrays.toString(quadro)+"\n";
    printINFO += "\n--------------------------------------------------------------\n";

    System.out.println(printINFO);
    iNFO = printINFO;
    
    Platform.runLater(() -> lbl_print_info.appendText(iNFO));
  }
  
  public void toStringMessage(int []quadro, String  camadaAtual, boolean quadroSemErro){
    final String iNFO;
    String printINFO = "ETAPA: "+camadaAtual+"\n";
    printINFO += "MESSAGEM: ["+Aplicacao.convertBinaryToMsg(quadro)+"]\n";
    printINFO += "BITS: "+Arrays.toString(quadro)+"\n";
    printINFO += "QUADRO SEM ERRO: "+quadroSemErro+"\n";
    printINFO += "\n--------------------------------------------------------------\n";
    
    System.out.println(printINFO);
    iNFO = printINFO;
    
    Platform.runLater(() -> lbl_print_info.appendText(iNFO));
  }

  // public void printErro(int [], quadro)

  public void reloginAck(int time, VBox vBox, String msg){

    ObservableList observableList = HBoxAck.getChildren();
    vBox.setPrefHeight(150);
    Label label = new Label();
    label.setText("["+msg+"]");
  
    ProgressIndicator progress = new ProgressIndicator();
    progress.setPrefHeight(150);
    progress.setProgress(0);
    progress.accessibleTextProperty();

    vBox.setAlignment(Pos.CENTER);
    vBox.getChildren().add(label);
    vBox.getChildren().add(progress);
    
    Platform.runLater(() -> observableList.add(vBox));
    new Thread(() -> {
      while (progress.getProgress() < 1) {
        Platform.runLater(() -> progress.setProgress(progress.getProgress() + 0.01));
        try {
          Thread.sleep((long) (0.01 * (time * 1000L)));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
       stopThread();
       removeAck(vBox);
    }).start();
  }


  private void stopThread() {
    try {
      Thread.sleep((long) (2 * 1000));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void removeAck(VBox vBox) {
    ObservableList observableList = HBoxAck.getChildren();
    if (observableList.contains(vBox))
      Platform.runLater(() -> observableList.remove(vBox));
  }

  
  public void reviewing(int [] quadro) {

    final String iNFO;
    String printINFO = "QUADRO DANIFICADO SENDO ENVIADO NOVAMENTE\n";
    printINFO += "MESSAGEM: ["+Aplicacao.convertBinaryToMsg(quadro)+"]\n";
    printINFO += "BITS: "+Arrays.toString(quadro)+"\n";
    printINFO += "\n-----------------------------------------\n";
    
    iNFO = printINFO;
    System.out.println(iNFO);
    Platform.runLater(()->lbl_print_info.appendText(iNFO));
  }
  
  public TextArea getEntrada_lbl() {
    return entrada_lbl;
  }

  public void setEntrada_lbl(TextArea entrada_lbl) {
    this.entrada_lbl = entrada_lbl;
  }

  public TextArea getSaida_lbl() {
    return saida_lbl;
  }

  public void setSaida_lbl(TextArea saida_lbl) {
    this.saida_lbl = saida_lbl;
  }

  public HBox getHbox_grafico() {
    return hbox_grafico;
  }

  public void setHbox_grafico(HBox hbox_grafico) {
    this.hbox_grafico = hbox_grafico;
  }

  public Slider getGrafico_sld() {
    return grafico_sld;
  }

  public void setGrafico_sld(Slider grafico_sld) {
    this.grafico_sld = grafico_sld;
  }

  public Label getLbl_sldGrafico() {
    return lbl_sldGrafico;
  }

  public void setLbl_sldGrafico(Label lbl_sldGrafico) {
    this.lbl_sldGrafico = lbl_sldGrafico;
  }

  public Slider getPorcetagem_erro() {
    return porcetagem_erro;
  }

  public void setPorcetagem_erro(Slider porcetagem_erro) {
    this.porcetagem_erro = porcetagem_erro;
  }

  public TextArea getLbl_print_info() {
    return lbl_print_info;
  }

  public void setLbl_print_info(TextArea lbl_print_info) {
    this.lbl_print_info = lbl_print_info;
  }

  public HBox getHBoxAck() {
    return HBoxAck;
  }

  public void setHBoxAck(HBox hBoxAck) {
    HBoxAck = hBoxAck;
  }

}


