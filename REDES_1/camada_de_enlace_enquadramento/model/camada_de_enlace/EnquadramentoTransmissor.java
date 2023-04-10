package model.camada_de_enlace;

import java.util.ArrayList;

import model.Aplicacao;
import model.camada_fisica.Rede;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 19/03/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: EnquadramentoTransmissor.java
* Funcao...........: enquadra o quadro na transmissao
************************************************************** */

public class EnquadramentoTransmissor {
static ArrayList <String> enquadro = new ArrayList<>();


/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraEnquadramentoContagemDeCaracteres
* Funcao: realiza enquadro de contagem de caracteres
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  
  public int[] CamadaEnlaceDadosTransmissoraEnquadramentoContagemDeCaracteres(int[] quadro) {

    
    // implementacao do algoritmo
    String strBits = Aplicacao.convertBinaryToMsg(quadro);//converte o quadro de bits em menssagem
    String [] quadroStr = strBits.split("");//separa cada caracter da menssagem em uma posicao do vetor
    // int margemQuadro = (quadro.length/4)+(quadro.length%4);// tamanho de espaco do enquadramento no vetor
    int [] quadroEnquadrado;
    String [] aviaozim = new String [5];

    for(int i = 0, k = 1, j = 0; i<=quadroStr.length; i++){
      if(j<4){
        if(i == quadroStr.length){
          aviaozim[0] = String.valueOf(j+1);
          appendArray(aviaozim);
        }else{
          aviaozim[k] = quadroStr[i];
          j++;
          k++;
        }
      }else{
        aviaozim[0] = String.valueOf(j+1);
        j = 0;
        k = 1;
        i --;
        appendArray(aviaozim);
      }
    }
    String [] strEnquadro = enquadro.toArray(new String[0]);
    String junto = "";
    for(int i = 0; i<strEnquadro.length; i++){
      junto+=strEnquadro[i];
    }
    quadroEnquadrado = Aplicacao.camadaDeAplicacaoTransmissora(junto);

    return quadroEnquadrado;
    // fim do metodo CamadaEnlaceDadosTransmissoraContagemDeCaracteres
  } 

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBytes
* Funcao: realiza enquadro de contagem de insercao de bytes
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  public int [] CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBytes (int[] quadro) {
  //implementacao do algoritmo
    String msg = Aplicacao.convertBinaryToMsg(quadro);
    String start = "S";
    String end = "E";

    String [] quadroStr = msg.split("");

    for(int i = 0; i <quadroStr.length; i++){
      if(0 == start.compareTo(quadroStr[i])){
        start = "/S";
        end = "/E";
      }
      if(0 == end.compareTo(quadroStr[i])){
        start = "/S";
        end = "/E";
      }
    }
    ArrayList<String>arrayQuadro = new ArrayList<>();

    for (int i = 0, j = 0; i <quadroStr.length; i++, j++ ){
      
      if(j == 5){
        arrayQuadro.add(end); 
        j = 0;
      }if(j == 0){
        arrayQuadro.add(start);
        i--;
      }else{
        arrayQuadro.add(quadroStr[i]);}
      }
      arrayQuadro.add(end);
  
    String [] str = arrayQuadro.toArray(new String [0]);
    String junto = "";
    for(String i : str){
      junto+=i;
    }
    int [] quadroFinal = Aplicacao.camadaDeAplicacaoTransmissora(junto);
    return quadroFinal;
  } //fim do metodo CamadaEnlaceDadosTransmissoraInsercaoDeBytes

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBits
* Funcao: realiza enquadro de contagem de insercao de bits
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  public int [] CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBits (int [] quadro) {
    //implementacao do algoritmo
    ArrayList <Integer> quadroTemporario = new ArrayList<>();
    int contBits = 0;
    for(int i = 0; i <quadro.length; i++){
      if(1 == quadro[i]){
        if(contBits == 5){
          quadroTemporario.add(0);
          contBits = 0;
          i--;   
       }else{
         quadroTemporario.add(quadro[i]);
         contBits++;
       }
      }else{
        contBits = 0;
        quadroTemporario.add(quadro[i]);
    }
  }
    int [] quadroFinal = new int[quadroTemporario.size()];
    for(int  i = 0; i<quadroTemporario.size(); i++){
      quadroFinal[i] = quadroTemporario.get(i);
    }
    return quadroFinal;
  } //fim do metodo CamadaEnlaceDadosTransmissoraInsercaoDeBits

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoDaCamadaFisica
* Funcao: realiza enquadro da violacao da camada física
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  
  public static int [] CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoDaCamadaFisica(int [] quadro) {
  //implementacao do algoritmo

    ArrayList <Integer> quadroTemporario = new ArrayList<>();
    int contBits = 0;
    for(int i = 0; i < quadro.length; i++){
      if(contBits == 5){
        quadroTemporario.add(1);
        quadroTemporario.add(1);
        contBits = 0;
        i--;
      }else{
        contBits++;
        quadroTemporario.add(quadro[i]);
      }
    }
    int [] quadroFinal = new int[quadroTemporario.size()];
    for(int i = 0; i < quadroTemporario.size(); i++){
      quadroFinal[i] = quadroTemporario.get(i);
    }
    return quadroFinal;
}//fim do metodo CamadaEnlaceDadosTransmissoraViolacaoDaCamadaFisica


  public static void appendArray(String [] aviaozim){ 
    for(int l = 0; l<Integer.parseInt(aviaozim[0]); l++){
      enquadro.add(aviaozim[l]);
    }
  }

  public static void printVetor(int [] quadro){
    for(int i = 0; i<quadro.length; i++){
        System.out.print(quadro[i]+"");
      }
    }
}
