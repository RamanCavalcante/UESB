package model.camada_de_enlace;

import java.util.ArrayList;
import model.Aplicacao;
import model.GUI;
import model.camada_fisica.Message;

public class SplitQuadro {

  /* ***************************************************************
* Metodo: split
* Funcao: separa os quadros
* Parametros: tipo do enquadramaento, quadro, receptora
* Retorno: 
*************************************************************** */
  public static void split(int tipoEnquadramento, int [] quadro, GUI gui, Message msg) throws InterruptedException{
    
  
    gui.toStringMessage(quadro, "ANTES DE SEPARAR QUADROS");

    ArrayList<int[]> quadrosSeparado = null;
    
    switch (tipoEnquadramento) {
      case 1://caso o enquadro for contagem de caracteres
        quadrosSeparado = splitEnquadroContagemDeCaracteres(quadro);
        break;
      case 2://caso o enquadro for insecao de bytes
        quadrosSeparado = splitEnquadroIncersaoDebytes(quadro);
        break;
      case 3://caso o enquadro for insercao de bis
        quadrosSeparado = splitEnquadroIncersaoDeBits(quadro);
        break;
    }

    int [] seq = {0,0,0,0,0,0,0,0};
    Enviar sendQuadros = new Enviar(quadrosSeparado,seq, gui, msg);
    sendQuadros.EnviarQuadro();
  }


  /* ***************************************************************
* Metodo: splitEnquadroContagemDeCaracteres
* Funcao: separa os quadro com contagem de caracteres
* Parametros: quadro, receptora
* Retorno: int []
*************************************************************** */
  private static ArrayList<int[]> splitEnquadroContagemDeCaracteres(int [] quadro){

    String msg = Aplicacao.convertBinaryToMsg(quadro);
    ArrayList<int [] > listEnquadro = new ArrayList<>();
    ArrayList<String> desenquadro = new ArrayList<>();
    // String str = convertBinaryToMsg(quadro);
    String [] quadroStr = msg.split("");
    int bloco = 5;
    // if(receptora == true) bloco ++;
    for(int i = 0, cont = 1; i <=quadroStr.length; i ++, cont ++ ){
      if(cont<=bloco && i+1<=quadroStr.length){
          desenquadro.add(quadroStr[i]);
      }else{     
        String str = String.join("",desenquadro);
        int vet [] = Aplicacao.camadaDeAplicacaoTransmissora(str);
        listEnquadro.add(vet);
        desenquadro = new ArrayList<>();
        if(i < quadroStr.length){
          i--;
          cont = 0;
        }}
    }
    return listEnquadro;
  }
  
  /* ***************************************************************
* Metodo: splitEnquadroIncersaoDebytes
* Funcao: separa os quadro de incersao de bytes
* Parametros: quadro, receptora
* Retorno: int []
*************************************************************** */
  private static ArrayList<int []> splitEnquadroIncersaoDebytes(int [] quadro){

		// implementacao do algoritmo para DESENQUADRAR
    
    boolean comFlag = false;
    String msg = "";
    msg = Aplicacao.convertBinaryToMsg(quadro);

    String [] quadro2 = msg.split("");

    String escape = "/";
    for(int i = 0; i<quadro2.length; i++){
      if(0 == escape.compareTo(quadro2[i])){
        comFlag = true;
      }
    }
    
    ArrayList<int[]> listQuadro;
    if(comFlag == false){
      listQuadro = desfazIncersaoDeBytesSemFlags(quadro2);
    }else{
      listQuadro = desfazIncersaoDeBytesComFlags(quadro2);
    }

    return listQuadro;
  }
 
 
  private static ArrayList<int[]> desfazIncersaoDeBytesSemFlags(String [] quadro){
    int contCaractere = 0;
    ArrayList <String> quadroTemporario = new ArrayList<>();
    ArrayList <int []> listQuadro = new ArrayList<>();
    String msg = "";
    for(int i = 0; i<quadro.length-1; i++){

      if(contCaractere == 6 ){
          contCaractere = 0;
          msg = String.join("", quadroTemporario);
          listQuadro.add(Aplicacao.camadaDeAplicacaoTransmissora(msg));
          quadroTemporario = new ArrayList<>();
          i--;
      }else{
        quadroTemporario.add(quadro[i]);  
        contCaractere++;
      }
    }
    return listQuadro;
  }

  private static ArrayList<int[]> desfazIncersaoDeBytesComFlags(String [] quadro){
    String escape = "/";
    String msg = "";
    boolean inicio = true;
    ArrayList<int []> listQuadro = new ArrayList<>();
    ArrayList <String> quadroTemporario = new ArrayList<>();
    for(int i = 0; i<quadro.length; i++){
      if(0 ==escape.compareTo(quadro[i])){
        quadroTemporario.add(quadro[i]);
        i++;
        quadroTemporario.add(quadro[i]);
        if(inicio == false){
          msg = String.join("", quadroTemporario);
          quadroTemporario = new ArrayList<>();
          listQuadro.add(Aplicacao.camadaDeAplicacaoTransmissora(msg));
          inicio = true;
        }
      }else{
        inicio = false;
        quadroTemporario.add(quadro[i]);
      }
    }
    return listQuadro;
  }
 
  
  /* ***************************************************************
* Metodo: splitEnquadroIncersaoDeBits
* Funcao: separa os quadro de incesao de bits
* Parametros: quadro, receptora
* Retorno: int []
*************************************************************** */
  public static ArrayList<int []> splitEnquadroIncersaoDeBits(int [] quadro){
		//implementacao do algoritmo
    ArrayList<int[]> listQuadro = new ArrayList<>();
    ArrayList <Integer> quadroTemporario = new ArrayList<>();
    int contBits = 0;
    boolean enquadro = false;
    for(int i = 0; i <quadro.length; i++){
      if(0 == quadro[i]){
        if(contBits == 5){
          contBits = 0;
          enquadro = true;
          int [] pass = new int[quadroTemporario.size()];
          for(int l = 0; l <quadroTemporario.size(); l ++){
            pass[l] = quadroTemporario.get(l); 
          }
          listQuadro.add(pass);
          quadroTemporario = new ArrayList<>();
      }else{
        quadroTemporario.add(quadro[i]);
        contBits = 0;
      }
      }else{
        contBits++;
        quadroTemporario.add(quadro[i]);
      }
    }
    if(!enquadro){
      int [] pass = new int[quadroTemporario.size()];
      for(int l = 0; l <quadroTemporario.size(); l ++){
        pass[l] = quadroTemporario.get(l); 
      }
    }
    return listQuadro;
  }


}
    

