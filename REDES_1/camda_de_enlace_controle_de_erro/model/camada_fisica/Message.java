package model.camada_fisica;

import javafx.scene.control.TextArea;
import model.GUI;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: Message.java
* Funcao...........: class objeto da usada para criar a mensagem
************************************************************** */

public class Message extends Thread {
  


  private String messageText;
  private int protoclo;
  private int tipoEnquadro;
  private int tipoDeControleDeErro;
  private String messageTextoutgoing;
  TextArea saida_lbl, grafico_lbl, printInfo;

/* ***************************************************************
* Metodo: Message
* Funcao: Construtor da classe
* Parametros: messageText, protocolo, messageTextoutgoing, saida_lbl, grafico_lbl, tipoEnquadro
* Retorno: 
**************************************************************** */

  public Message(String messageText, String messageTextoutgoing, int protocolo, int tipoEnquadro, int tipoDeControleDeErro){
    this.tipoEnquadro = tipoEnquadro;
    this.tipoDeControleDeErro = tipoDeControleDeErro;
    this.protoclo = protocolo;
    this.messageText = messageText;
    this.protoclo = protocolo;
    this.messageTextoutgoing = messageTextoutgoing;
    
  }

  public TextArea getPrintInfo() {
    return printInfo;
  }

  public void setPrintInfo(TextArea printInfo) {
    this.printInfo = printInfo;
  }

  public String getMessageText() {
    return messageText;
  }

  public void setMessageText(String messageText) {
    this.messageText = messageText;
  }
  
  public int getTipoEnquadro(){
    return tipoEnquadro;
  }

  public void setTipoEnquadro(int tipoEnquadro){
    this.tipoEnquadro = tipoEnquadro;
  }

  public int getProtoclo() {
    return protoclo;
  }

  public void setProtoclo(int protoclo) {
    this.protoclo = protoclo;
  }

  public String getMessageTextoutgoing() {
    return messageTextoutgoing;
  }

  public void setMessageTextoutgoing(String messageTextoutgoing) {
    this.messageTextoutgoing = messageTextoutgoing;
  }

  public int getTipoDeControleDeErro() {
    return tipoDeControleDeErro;
  }

  public void setTipoDeControleDeErro(int tipoDeControleDeErro) {
    this.tipoDeControleDeErro = tipoDeControleDeErro;
  }
}
