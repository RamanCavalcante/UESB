package src;

import javafx.scene.control.Slider;
import javafx.application.Platform;
import javafx.scene.control.Label;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Consumidor.java
 * Funcao: Classe do consumidor do tipo Thread onde chamo o metodo
 * retirar rodando o laco while enquanto a variavel de condicao for
 * verdadeira
 * ************************************************************** */

public class Consumidor extends Thread{
  private Buffer buffer;
  Lancho lancho;
  public static int sld_consumidor = 5000;
  public Slider slideer;//variavel usada no sleep para regular a velocidade
  private Label lbl_consumidor;//variavel usada para exibir as acoes do consumidor
  
  public Consumidor(Buffer buffer, Slider slideer, Label lbl_consumidor){
    this.buffer = buffer;
    this.slideer = slideer;
    this.lbl_consumidor = lbl_consumidor;
  }

/*********************************************************
 * Nome.......: run
 * Funcao.....: executa a thread
 * Parametros.: nao recebe parametro
 * retorno....: void
 *********************************************************/

  public void run(){
    while(Produtor.toto == true){// enquanto a variavel da classe produtor for true o laco vai rodar
      try{Thread.sleep(100);} catch (InterruptedException e) { }
      lancho = buffer.retirar();//chamando o metodo retirar
      System.out.println("Preparando "+lancho.getNomeLancho()+"\n");
      try{Thread.sleep((long)slideer.getValue());} catch (InterruptedException e) { }
      System.out.println("Pedido "+lancho.getNomeLancho()+" Pronto\n");
      Platform.runLater(()->lbl_consumidor.setText("Pedido "+lancho.getNomeLancho()+" pronto"));//printa no label a acao do consumidor
      try{Thread.sleep(2000);} catch (InterruptedException e) { }
      
    }
  }
}
