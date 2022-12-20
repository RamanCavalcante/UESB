package util;

import java.util.Random;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 18/09/2021
 * Ultima alteracao: 28/09/2021
 * Nome: Filosofo.java
 * Funcao: Classe dos objetos Filosofos onde estao os metodos de acao
 * dos processos
 * ************************************************************** */

public class Filosofo extends Thread {
  public static boolean nietzsche;
  private int index, time;
  private int velComendo, velPensando;
  Filosofo OsPARCA [] = {}; 
  private Semaphore garfo1, garfo2;
  public boolean eating;
  ImageView imgFilo, imgGarfo1, imgGarfo2, think;
  Random tardis = new Random();

/*********************************************************
 * Nome.......: Filosofo
 * Funcao.....: construtor da classe Filosofo
 * Parametros.: void
 * retorno....: void
 *********************************************************/
  
 public Filosofo(){}
  public Filosofo(int  i, Semaphore garfo1, Semaphore garfo2, ImageView imgFilo, ImageView imgGarfo1, ImageView imgGarfo2, ImageView think) { 
    this.index = i;
    this.time = tardis.nextInt((15 - 5)+1) +5;
    this.velPensando = 1000;
    this.velComendo = 1000;
    this.garfo1 = garfo1;
    this.garfo2 = garfo2;
    this.imgFilo = imgFilo;
    this.imgGarfo1 = imgGarfo1;
    this.imgGarfo2 = imgGarfo2;
    this.think = think;
  }
 /*********************************************************
 * Nome.......: run
 * Funcao.....: executa o processo
 * Parametros.: void
 * retorno....: void
 *********************************************************/
  public void run(){
    nietzsche = true;
   while(nietzsche == true){
     
    try {pensando(time);} catch (InterruptedException e) { }
   }
  }

/*********************************************************
 * Nome.......: pensando
 * Funcao.....: executa o estado pensando, recebendo um numero
 * aleatorio para o sleep
 * Parametros.: int time, o numero aleatorio recebido
 * retorno....: void
 *********************************************************/

  public void pensando(int time) throws InterruptedException{
    System.out.println("to pensando - id:"+this.index+"*"+time);
    Platform.runLater(new Runnable() {
      @Override
      public void run() {            
        think.setVisible(true);
        think.setImage(Gallery.hulk);
        imgFilo.setImage(Gallery.pensando);
      }
    });   
    while(time>0){
      Thread.sleep(this.velPensando);
      time--; 
    }
    fome();
  }

  /*********************************************************
 * Nome.......: fome
 * Funcao.....: faz a verificacao por meio semaforos para 
 * o filosofo comer
 * Parametros.: void 
 * retorno....: void
 *********************************************************/

  public void fome() throws InterruptedException{
      
    Platform.runLater(new Runnable() {
       @Override
       public void run() {            
       think.setVisible(false);
       imgFilo.setImage(Gallery.fome);
       }
     });
    System.out.println("deu fome - id: "+this.index);
    time = tardis.nextInt((15 - 5)+1) +5;
 
    garfo1.acquire();
    garfo2.acquire();
    comendo(time);
  }

/*********************************************************
 * Nome.......: comendo
 * Funcao.....: executa o estado comendo, recebe um numero
 * aleatorio para o sleep
 * Parametros.: int time
 * retorno....: void
 *********************************************************/
  public void comendo(int time) throws InterruptedException{
   pegarGarfos();
   eating =true;
    System.out.println("to comendo - id: "+this.index+" * "+time);
    while(time>0){
      Thread.sleep(this.velComendo);
      time--;
      System.out.println(time);
    }
    devolverGarfos();
    garfo1.release();
    garfo2.release();
  }
 /*********************************************************
 * Nome.......: pegarGarfos
 * Funcao.....: quando chamado as imagen sao setadas no 
 * imageView
 * Parametros.: void
 * retorno....: void
 *********************************************************/
  public void pegarGarfos(){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {            
        imgFilo.setImage(Gallery.comendo);
        imgGarfo1.setVisible(false);
        imgGarfo2.setVisible(false); 
      }
    });
      
  }
/*********************************************************
 * Nome.......: devolverGarfos
 * Funcao.....: quando chamado as imagen sao setadas no 
 * imageView
 * Parametros.: void
 * retorno....: void
 *********************************************************/
  public void devolverGarfos(){
    Platform.runLater(new Runnable() {
      @Override
      public void run() {            
        imgGarfo1.setVisible(true);
        imgGarfo2.setVisible(true);
        imgGarfo1.setImage(Gallery.garfo);
        imgGarfo2.setImage(Gallery.garfo);
      }
    });
  }
  //******************************************************************************************* */
  public Filosofo[] getOsPARCA() {
    return OsPARCA;
  }

  public void setOsPARCA(Filosofo[] osPARCA) {
    OsPARCA = osPARCA;
  }
 
  public boolean isEating() {
    return eating;
  }
  public void setEating(boolean eating) {
    this.eating = eating;
  }
 
  public int getIndex() {
    return index;
  }
  public void setIndex(int index) {
    this.index = index;
  }

  public Random getTardis() {
    return tardis;
  }
  public void setTardis(Random tardis) {
    this.tardis = tardis;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }
  public int getVelComendo() {
    return velComendo;
  }

  public void setVelComendo(int velComendo) {
    this.velComendo = velComendo;
  }

  public int getVelPensando() {
    return velPensando;
  }

  public void setVelPensando(int velPensando) {
    this.velPensando = velPensando;
  }

}
