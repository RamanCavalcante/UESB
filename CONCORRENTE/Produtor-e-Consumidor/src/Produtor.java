package src;

import javafx.application.Platform;
import javafx.scene.control.Slider;
/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Produtor.java
 * Funcao: Classe do Produtor do tipo Thread onde rodo o laco 
 * ************************************************************** */
public class Produtor extends Thread{
  private Buffer buffer;
  private Lancho lancho;
  public static boolean toto = true;//usado como condicao no laco da thread
  public static int sld_produtor = 5000;
  public Slider slideer;

  Controller controller = new Controller();
/*********************************************************
 * Nome.......: Produtor
 * Funcao.....: construtor da classe
 * Parametros.: Um objeto do tipo Buffer e o id do slider
 * retorno....: ...
 *********************************************************/
  public Produtor(Buffer buffer, Slider slideer){
    this.buffer = buffer;
    this.slideer = slideer;
    
  }
 //setando objetos do tipo Lancho
  Lancho HSiri = new Lancho("Hamburguer de siri", 5000);
  Lancho baguete = new Lancho("Sanduice de baguete", 11000);
  Lancho x_molusco = new Lancho("X - Lula Molusco", 3000);
  Lancho Qqueijo = new Lancho("Queijo com Queijo", 8000);
  Lancho eggs = new Lancho("The Walking Eggs",10000);
  Lancho picle = new Lancho("PICLE RIIIICK!!",5000);
  Lancho vegano = new Lancho("Contra File",5);
  Lancho objLancho = new Lancho();

/*********************************************************
 * Nome.......: run
 * Funcao.....: executar a thread
 * Parametros.: 
 * retorno....: 
 *********************************************************/

  public void run(){
      while(toto==true){//enquanto a variavel for true 
      System.out.println(Buffer.quantidade+" - Pendentes\n");
      int op = ((int)( Math.random() * 6));//guarda um valor aleatorio na variavel op
      switch(op){//verficando o valor de op para escolher qual objeto 
        case 0:
          objLancho = HSiri;
          break;
        case 1:
          objLancho = eggs;
          break;
        case 2:
          objLancho = x_molusco;
          break;
        case 3:
          objLancho = Qqueijo;
          break;
        case 4:
          objLancho = vegano;
          break;
        case 5:
          objLancho = picle;
          break;
        }
        try{Thread.sleep((long)slideer.getValue());} catch (InterruptedException e) { }
        this.buffer.inserir(objLancho);//o objeto eh inserindo no buffer
    }
  }
}
