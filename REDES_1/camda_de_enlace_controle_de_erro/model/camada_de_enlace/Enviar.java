package model.camada_de_enlace;

import java.util.ArrayList;

import model.Aplicacao;
import model.GUI;
import model.camada_fisica.Message;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 21/05/2022
* Ultima alteracao.: 21/05/2022
* Nome.............: Enviar.java
* Funcao...........: classe thread que envia quadro pra CamadaEnlaceDadosTransmissoraControleDeErro
************************************************************** */

public class Enviar{ 
  int [] seq;
  int [] quadro;
  boolean ack;
  ArrayList<int []>  listQuadros;
  int  [] flagEnd;
  public static int cont = 0;
  GUI gui;
  Message msg;

/* ***************************************************************
* Metodo: Enviar
* Funcao: enviar quandros em sequencia
* Parametros: listQuadro, seq, gui, msg
* Retorno: 
*****************************************************************/

  public Enviar(ArrayList<int[]> listQuadros, int [] seq, GUI gui, Message msg){
    
    this.listQuadros = listQuadros;
    this.flagEnd = new int [] {0,1,0,1,1,1,0,0};
    this.seq = seq;
    this.gui = gui;
    this.msg = msg;
  }

/* ***************************************************************
* Metodo: EnviarQuadros
* Funcao: envia quadros enquanto a lista nao estiver vazia
* Parametros: 
* Retorno: void
**************************************************************** */

  public void EnviarQuadro(){
    //caso o array esteja vazio 
    if(!listQuadros.isEmpty()){//verifica sem tem mais quadros para enviar
    
    try {
      RedeEnlace sendControleDeErro = new RedeEnlace(gui, msg);//objeto do tipo RedeEnlace eh criado
           
      //envia a menssagem que esta na posicao 0 do array
      Quadro qquadro = new Quadro(listQuadros.get(0), seq, gui, msg);
      sendControleDeErro.CamadaEnlaceDadosTransmissoraControleDeErro(qquadro);
      
      qquadro.start();//thread do time do quadro eh iniciada

    } catch (InterruptedException e){ e.printStackTrace();}
    listQuadros.remove(0);//remove o quadro da posicao 0 do array que acabou de ser enviada

    //um novo objeto com a lista menos com uma posicao eh criado
    Enviar newEnviar = new Enviar(listQuadros, 
      Aplicacao.camadaDeAplicacaoTransmissora(String.valueOf(cont)), gui, msg);
      cont++; //aumenta o numero de sequencia
    //chama de forma recursiva o metodo do novo objeto
    newEnviar.EnviarQuadro();
    
    /**,
     * caso o array esteja vazio,
     * um novo quadro flag sera enviado
     */
    }else{

      RedeEnlace sendControleDeErro = new RedeEnlace(gui, msg);  
      try {

        Quadro qquadro = new Quadro(flagEnd, Aplicacao.camadaDeAplicacaoTransmissora(""+cont+1), gui, msg);
        sendControleDeErro.CamadaEnlaceDadosTransmissoraControleDeErro(qquadro);
      
      } catch (InterruptedException e) {e.printStackTrace();} 
      
    }

  }

  public int[] getSeq() {
    return seq;
  }

  public void setSeq(int[] seq) {
    this.seq = seq;
  }

  public int[] getQuadro() {
    return quadro;
  }

  public void setQuadro(int[] quadro) {
    this.quadro = quadro;
  }

  public boolean isAck() {
    return ack;
  }

  public void setAck(boolean ack) {
    this.ack = ack;
  }

  public ArrayList<int[]> getListQuadros() {
    return listQuadros;
  }

  public void setListQuadros(ArrayList<int[]> listQuadros) {
    this.listQuadros = listQuadros;
  }

  public int[] getFlagEnd() {
    return flagEnd;
  }

  public void setFlagEnd(int[] flagEnd) {
    this.flagEnd = flagEnd;
  }

  public int getCont() {
    return cont;
  }

  public void setCont(int cont) {
    this.cont = cont;
  }
}