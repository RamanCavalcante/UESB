package model;

import java.util.Arrays;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
  Message msg;
  GUI gui;
  // alusaum a aplicao de onde recebo a mensagem
  public Aplicacao(Message message, GUI gui) {
    this.msg = message;
    this.gui = gui;    
  }

  /*
   * ***************************************************************
   * Metodo: startAplicacao
   * Funcao: inicia aplicacao
   * Parametros: void
   * Retorno: void
   */
  public void startAplicacao() throws InterruptedException {
    /// AQUI VAI FICAR CHAMADA DA CAMADA DE ENLACE //////
    
    int[] quadro = camadaDeAplicacaoTransmissora(msg.getMessageText());
    // pritando vetor de menssagem em bits
    RedeEnlace redeEnlace = new RedeEnlace(gui, msg);
    redeEnlace.CamadaEnlaceDadosTransmissora(quadro);

  }

  /*
   * ***************************************************************
   * Metodo: camadaDeAplicacaoTransmissora
   * Funcao: realiza a aplicacao transmissora
   * Parametros: String
   * Retorno: int []
   */
  
  public static int[] camadaDeAplicacaoTransmissora(String mensagem) {
    
    int quadro[] = new int[mensagem.length()];// declara e instancia o vetor quadro
    for (int i = 0; i < mensagem.length(); i++) {// laco para preencher o vetor
      quadro[i] = mensagem.charAt(i);// atribui cada letra em uma posicao
    }

    String strBits = Converter.bitsToString(Converter.asciiToBits(quadro));
    int[] bits = new int[strBits.length()];
    for (int i = 0; i < strBits.length(); i++) {
      if (Character.getNumericValue(strBits.charAt(i)) != -1) { // nao pega o espaco
        bits[i] = Character.getNumericValue(strBits.charAt(i));
      }
    }
    return bits;
  }

  /*
   * ***************************************************************
   * Metodo: camadaDeAplicacaoReceptora
   * Funcao: realiza a aplicacao receptora
   * Parametros: int []
   * Retorno: int []
   */
  public String camadaDeAplicacaoReceptora(int[] quadro) {

    String msgFinal = convertBinaryToMsg(quadro);
    return msgFinal;
  }

  /*
   * ***************************************************************
   * Metodo: convertBinaryToMsg
   * Funcao: converte de binario para string
   * Parametros: int []
   * Retorno: string
   */

  public static String convertBinaryToMsg(int[] quadro) {
    String input = "";

    for (int i = 0; i < quadro.length; i++) {
      input = input + quadro[i];
    }
    StringBuilder sb = new StringBuilder();
    Arrays.stream(input.split("(?<=\\G.{8})")).forEach(s -> sb.append((char) Integer.parseInt(s, 2)));
    return sb.toString();
  }

  public static int[] converterEmBits(char[] chars){
    int[] ascii = new int[chars.length];
    String[] binaryStrings = new String[chars.length];
    int[] bits = new int[chars.length * 7];

    // Converte cada caractere em seu valor da tabela ASCII
    for (int i = 0; i < chars.length; i++)
      ascii[i] = chars[i];

    // Converte cada valor ASCII em Binario - SAO NECESSARIO 7 CASAS DECIMAIS PARA CADA CHAR
    String mensagemCodificada = "";
    for (int i = 0; i < ascii.length; i++) {
      String binaryString = Integer.toBinaryString(ascii[i]);
      binaryStrings[i] = String.format("%07d",Integer.valueOf(binaryString));
      mensagemCodificada += binaryStrings[i];
    }

    char[] bitsChar = mensagemCodificada.toCharArray();
    for (int i = 0; i < bits.length; i++) {
      bits[i] = Character.getNumericValue(bitsChar[i]);
    }
    return bits;
  }

  // public static String convertBinaryToMsg(int[] bits){
  //   String mensagem = "";
  //   String letra = "";
  //   for (int i = 0; i < bits.length; i++) {
  //     letra += bits[i];
  //     if(letra.length() % 8 == 0){
  //       int ascii = Integer.parseInt(letra,2);
  //       mensagem += ((char) ascii);
  //       letra = "";
  //     }
  //   }
  //   return mensagem;
  // }

  

}
