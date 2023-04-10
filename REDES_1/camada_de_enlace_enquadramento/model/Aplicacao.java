package model;

import java.util.Arrays;

import javafx.scene.layout.HBox;
import model.camada_de_enlace.RedeEnlace;
import model.camada_fisica.Converter;
import model.camada_fisica.Message;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2022
* Nome.............: Aplicao.java
* Funcao...........: recebe mensagem da aplicacao
************************************************************** */

public class Aplicacao {
  Message objMessage;
  HBox hbox_grafico;

  // alusaum a aplicao de onde recebo a mensagem
  public Aplicacao(Message strMSG, HBox hbox_grafico) {
    this.objMessage = strMSG;
    this.hbox_grafico = hbox_grafico;
  }
/* ***************************************************************
* Metodo: startAplicacao
* Funcao: inicia aplicacao
* Parametros: void
* Retorno: void
**************************************************************** */
  public void startAplicacao() {
    /// AQUI VAI FICAR CHAMADA DA CAMADA DE ENLACE //////

    int[] quadro = camadaDeAplicacaoTransmissora(this.objMessage.getMessageText());
    RedeEnlace.CamadaEnlaceDadosTransmissora(quadro, this.objMessage, this.hbox_grafico);
    
  }

/* ***************************************************************
* Metodo: camadaDeAplicacaoTransmissora
* Funcao: realiza a aplicacao transmissora
* Parametros: String 
* Retorno: int []
**************************************************************** */
  public static int [] camadaDeAplicacaoTransmissora(String mensagem) {

    int quadro[] = new int[mensagem.length()];// declara e instancia o vetor quadro
    for (int i = 0; i < mensagem.length(); i++) {// laco para preencher o vetor
      quadro[i] = mensagem.charAt(i);// atribui cada letra em uma posicao
    }

    String strBits = Converter.bitsToString(Converter.asciiToBits(quadro));
        int [] bits = new int [strBits.length()];
        for (int i = 0; i < strBits.length(); i++) {
          if (Character.getNumericValue(strBits.charAt(i)) != -1) { // nao pega o espaco
            bits [i] = Character.getNumericValue(strBits.charAt(i));
          }
        }
    return bits;
  }

/* ***************************************************************
* Metodo: camadaDeAplicacaoReceptora
* Funcao: realiza a aplicacao receptora
* Parametros: int [] 
* Retorno: int []
**************************************************************** */
  public String camadaDeAplicacaoReceptora(int [] quadro){

    String msgFinal = convertBinaryToMsg(quadro);
    return msgFinal;  
  }

/* ***************************************************************
* Metodo: convertBinaryToMsg
* Funcao: converte de binario para string 
* Parametros: int [] 
* Retorno: string
**************************************************************** */

  public static String convertBinaryToMsg(int [] quadro) {
    String input = "";

    for(int i = 0; i<quadro.length; i++){
      input = input + quadro[i];
    }
    StringBuilder sb = new StringBuilder();
    Arrays.stream(input.split("(?<=\\G.{8})")).forEach(s -> sb.append((char) Integer.parseInt(s, 2)));
    return sb.toString();
  }

  public static String printVetor(int []quadro){
    String str= "";  
    for(int i = 0; i<quadro.length; i++){
        str+=quadro[i];
      }
    return str;
}
}
