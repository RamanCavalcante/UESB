package src;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Observable;
/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Buffer.java
 * Funcao: classe do Bufffer, onde estao o metodo inserir e retirar
 * ************************************************************** */

public class Buffer{
  
  Controller objController = new Controller();

  private Lancho lancho;
  private Semaphore semFull;
  private Semaphore semEmpty;
  private Semaphore semMutex;
  public static int quantidade=0;
  public   ArrayList<Lancho> listLancho;
  private ObservableList stalker, listReady;
  private Label lbl_produtor, lbl_consumidor;

  
/*********************************************************
 * Nome.......: Buffer
 * Funcao.....: Construtor da classe Buffer
 * Parametros.: stalker e listReady do tipo ObservableList, lbl_produtor
 * do tipo Label e lbl_consumidor do tipo Label
 * retorno....:
 *********************************************************/

  public Buffer(ObservableList stalker, Label lbl_produtor, Label lbl_consumidor, ObservableList listReady){
    this.stalker = stalker;
    this.listReady = listReady;
    this.lbl_produtor = lbl_produtor;
    this.lbl_consumidor = lbl_consumidor;
    this.listLancho = new ArrayList();
    this.semFull = new Semaphore(0);
    this.semEmpty = new Semaphore(20);
    this.semMutex = new Semaphore(1);
  }

/*********************************************************
 * Nome.......: inserir
 * Funcao.....: inserir o objeto no buffer
 * Parametros.: objeto novoLancho do tipo Lancho
 * retorno....: void
 *********************************************************/

  public void inserir(Lancho novoLancho){
    try{
      semEmpty.acquire();
      semMutex.acquire();
      this.lancho = novoLancho;
      Platform.runLater(()->stalker.add(lancho.getNomeLancho()));
      listLancho.add(this.lancho);
      System.out.println("Pedido realizado("+this.lancho.getNomeLancho()+")");
      Platform.runLater(()-> lbl_produtor.setText("Pedido realizado("+this.lancho.getNomeLancho()+")"));
      quantidade++;
      semMutex.release();
      semFull.release();
      
    }catch(InterruptedException ex){
      System.out.println("Buffer: Erro ao inserir"+ex.getMessage());
    }
  }

/*********************************************************
 * Nome.......: retirar
 * Funcao.....: retirar o objeto do buffer
 * Parametros.: não recebe parametros
 * retorno....: objeto do tipo Lancho
 *********************************************************/

  public Lancho retirar(){
    Lancho bkp = null;
    try{
      
      semFull.acquire();//tento acessar pelo semaforo de posicoes cheias
      semMutex.acquire();
      bkp = listLancho.get(0);
      String nome = bkp.getNomeLancho();
      Platform.runLater(new Runnable() {
        @Override public void run() {
          lbl_consumidor.setText("Preparando pedido: "+nome);
          stalker.remove(0);
          listReady.add(nome);
        }
      });
      listLancho.remove(0);
      quantidade--;
      semMutex.release();
      semEmpty.release();
    }catch(InterruptedException ex){
      System.out.println("Buffer: Erro ao retirar+ "+ex.getMessage());
      
    }
  return bkp;
  }
}

