package model.camada_de_enlace;

import java.util.ArrayList;
import javafx.scene.layout.VBox;
import model.Aplicacao;
import model.GUI;
import model.camada_fisica.Message;


/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 21/05/2022
* Ultima alteracao.: 21/05/2022
* Nome.............: Quadro.java
* Funcao...........: executa todo o percusso do quadro
************************************************************** */

public class Quadro extends Thread{
  int time;
  int [] quadro;
  int [] seq;
  ArrayList<int[]> listQuadros = new ArrayList<>();
  boolean ack;
  GUI gui;
  Message msg;
  String msgQuadro;
  public static int haveACKfalse;

/* ***************************************************************
* Metodo: Quadro
* Funcao: metodo construtor do quadro
* Parametros: quadro, seq, gui, msg
* Retorno: 
*************************************************************** */

  public Quadro(int [] quadro, int [] seq, GUI gui, Message msg){
    this.quadro = quadro;
    this.seq = seq;
    this.ack = false;
    listQuadros.add(quadro);    
    this.gui = gui;
    this.msg = msg;
    this.msgQuadro = Aplicacao.convertBinaryToMsg(quadro);
    this.time = 5;
  }


  /**
   * run da classe quadro, roda enquanto nao acontecer otimeout
   */
  public void run(){
    VBox vBox = new VBox();
    gui.reloginAck(5, vBox,msgQuadro);
    System.out.println("to aqui mas nao entrei"+seq.toString());
    
    ArrayList<int[]> reviewingQ = new ArrayList<>();
    reviewingQ.add(listQuadros.get(0));

    Enviar sendReviewing = new Enviar(reviewingQ, seq, gui, msg);
    
    while(!this.ack){//laco enquanto o ack for falso 
      if(time>0){//verifica se o timeout acabou
          try {Thread.sleep(1000);//seleepa por um segundo
          } catch (InterruptedException e) {e.printStackTrace();}
          time --;

        }else{

          gui.reviewing(quadro);
          sendReviewing.EnviarQuadro();
        }
    }
  }
  
  public int[] getQuadro() {
    return quadro;
  }
  public void setQuadro(int[] quadro) {
    this.quadro = quadro;
  }
  public int[] getSeq() {
    return seq;
  }
  public void setSeq(int[] sequencia) {
    this.seq = sequencia;
  }
  public boolean getAck() {
    return ack;
  }
  public void setAck(boolean ack) {
    this.ack = ack;
  }

  public void errorAck(){
    if(haveACKfalse>0)
      haveACKfalse--;
  }

}
