package model.camada_de_enlace;

import java.util.ArrayList;

import model.Aplicacao;
import model.camada_fisica.Rede;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 19/03/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: EnquadroReceptor.java
* Funcao...........: desenquadra o quadro na recepcao
************************************************************** */

public class EnquadramentoReceptor {	

  /* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraEnquadramentoContagemDeCaracteres
* Funcao: realiza desenquadramento contagem de caracteres
* Parametros: quadro
* Retorno: int 
*************************************************************** */
	public int [] CamadaEnlaceDadosReceptoraEnquadramentoContagemDeCaracteres(int quadro[]) {
		// implementacao do algoritmo para DESENQUADRAR
    String msg = Aplicacao.convertBinaryToMsg(quadro);
    
    ArrayList<String> desenquadro = new ArrayList<>();
    // String str = convertBinaryToMsg(quadro);
    String [] quadroStr = msg.split("");
    int bloco = Integer.parseInt(quadroStr[0]);
    String [] aviaozim = new String[5];

    for(int i = 0, cont = 0; i <=quadroStr.length; i++, cont++ ){
      if(cont<bloco && i+1<=quadroStr.length){
          aviaozim[cont] = quadroStr[i];
      }else{
        cont = 0;
        for(int l = 1; l < aviaozim.length; l++){
          desenquadro.add(aviaozim[l]);
        }
        aviaozim = null;
        aviaozim = new String[5];
      }
    }
    
    String [] desenquadroStr = desenquadro.toArray(new String[0]);
    
    String quadroMsg = "";
    for(int i = 0; i <desenquadroStr.length; i++){
      if(desenquadroStr[i]!= null){
        quadroMsg += desenquadroStr[i];
      }
    }
    System.out.println(quadroMsg);
    int [] desenquadroBist = Aplicacao.camadaDeAplicacaoTransmissora(quadroMsg);
    
  return desenquadroBist;
  }// fim do metodo CamadaEnlaceDadosReceptoraContagemDeCaracteres

  /* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBytes
* Funcao: realiza desenquadramento contagem insercao de bytes
* Parametros: quadro
* Retorno: int 
*************************************************************** */
  public int [] CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBytes(int quadro[]) {
		// implementacao do algoritmo para DESENQUADRAR
    
    boolean comFlag = false;
    String msg = "", junto = "";
    msg = Aplicacao.convertBinaryToMsg(quadro);

    String [] quadro2 = msg.split("");

    String escape = "/";
    for(int i = 0; i<quadro2.length; i++){
      if(0 == escape.compareTo(quadro2[i])){
        comFlag = true;
      }
    }
    
    String [] retorno = null;
    if(comFlag == false){
      retorno = desfazIncersaoDeBytesSemFlags(quadro2);
    }else{
      retorno = desfazIncersaoDeBytesComFlags(quadro2);
    }

    for(String i : retorno){
      junto+=i;
    }
    int [] desenquadroBist = Aplicacao.camadaDeAplicacaoTransmissora(junto);

    return desenquadroBist;
	} // fim do metodo CamadaEnlaceDadosReceptoraInsercaoDeBytes

  /* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBits
* Funcao: realiza desenquadramento contagem insercao de bits
* Parametros: quadro
* Retorno: int 
*************************************************************** */
	public int [] CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBits(int quadro[]) {
		//implementacao do algoritmo
    ArrayList <Integer> quadroTemporario = new ArrayList<>();
    int contBits = 0;
    for(int i = 0; i <quadro.length; i++){
      if(0 == quadro[i]){
        if(contBits == 5){
          contBits = 0;
      }else{
        quadroTemporario.add(quadro[i]);
        contBits = 0;
      }
      }else{
        contBits++;
        quadroTemporario.add(quadro[i]);
      }
    }
    int [] quadroFinal = new int[quadroTemporario.size()];
    for(int  i = 0; i<quadroTemporario.size(); i++){
      quadroFinal[i] = quadroTemporario.get(i);
    }
    return quadroFinal;
	} // fim do metodo CamadaEnlaceDadosReceptoraInsercaoDeBits

  /* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraEnquadramentoViolacaoDaCamadaFisica
* Funcao: realiza desenquadramento contagem insercao de bits
* Parametros: quadro
* Retorno: int 
*************************************************************** */
	public static int [] CamadaEnlaceDadosReceptoraEnquadramentoViolacaoDaCamadaFisica(int quadro[]) {
		// implementacao do algoritmo para DESENQUADRAR
    int contBits = 0;
    ArrayList<Integer> quadroTemporario = new ArrayList<>();
    for(int i = 0; i < quadro.length; i++){
      if(contBits == 5){
        i++;
        contBits = 0;
      }else{
        contBits ++;
        quadroTemporario.add(quadro[i]);
      }
    }
    int [] quadroFinal = new int[quadroTemporario.size()];
    for(int i = 0; i < quadroTemporario.size(); i++){
      quadroFinal[i] = quadroTemporario.get(i);
    }
    return quadroFinal;
	} // fim do metodo CamadaEnlaceDadosReceptoraViolacaoDaCamadaFisica


  /* ***************************************************************
* Metodo: desfazIncersaoDeBytesSemFlags
* Funcao: realiza desenquadramento sem flags na insercao de bytes
* Parametros: quadro
* Retorno: int 
*************************************************************** */
  public static String [] desfazIncersaoDeBytesSemFlags(String [] quadro){
    int contCaractere = 0;
    ArrayList <String> quadroTemporario = new ArrayList<>();
    for(int i = 1; i<quadro.length-1; i++){
      
      if(contCaractere == 4 | contCaractere == 5 ){
        if(contCaractere == 5){
          contCaractere = 0;
        }else{
          contCaractere++;
        }
      }else{
        quadroTemporario.add(quadro[i]);  
        contCaractere++;
      }
    }
    String [] quadroFinal = quadroTemporario.toArray(new String[0]);
    return quadroFinal;
  }
  
  /* ***************************************************************
* Metodo: desfazIncersaoDeBytesComFlags
* Funcao: realiza enquadramento com flags na insercao de bytes
* Parametros: quadro
* Retorno: int 
*************************************************************** */
  public static String [] desfazIncersaoDeBytesComFlags(String [] quadro){
    String escape = "/";
    ArrayList <String> quadroTemporario = new ArrayList<>();
    for(int i = 2; i<quadro.length; i++){
      if(0 ==escape.compareTo(quadro[i])){
        i++;
      }else{
        quadroTemporario.add(quadro[i]);
      }
    }
    String [] quadroFinal = quadroTemporario.toArray(new String[0]);
    return quadroFinal;
  }
 

}   




