package util;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import jdk.nashorn.internal.ir.CatchNode;

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Raman Melo Cavalcante
Matricula: 201820754
Inicio...: 13 de agosto de 2021
Alteracao: 18 de agosto de 2021
Nome.....: Threads.java
Funcao...: Classe onde estãos todas as threads e são gerenciadas 
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

public class Threads extends Thread{
  public static int ano = 0;//variavel global usada para contar os anos
  boolean podeRodar =true;// variavel do tipo booleano usada para roda o laço
  public static Controller objControle;//objto global do tipo Controller usado para metodos da classe

  public Threads(Controller controle){//aqui estou instanciando a variavel objControle
    this.objControle = controle;
  }

  public void run(){
    
    PrimeiroFilho primeiroFilho = new PrimeiroFilho();
    SegundoFilho segundoFilho = new SegundoFilho();
    TerceiroFilho terceiroFilho = new TerceiroFilho();

    objControle.changeImage(objControle.getIdPai(), 0);//chamando o metodo para para alterar as imagens na tela
    
    while(podeRodar){
      Platform.runLater(new Runnable(){//usando o Platform.runLater não ocorre erro de execao 
        @Override
        public void run() {
          objControle.writeLabel(objControle.getIdLabelPai(),ano);//chamando o metodo para imprimir no label
        } });  
      objControle.changeImage(objControle.getIdPai(),ano);//chamando o metodo para a mudanca de imagem no imageView
      System.out.println("Ano "+ano);//Imprimi no termin
        switch(ano){
          case 0:
            System.out.println("Nasce o pai");//Caso o ano seja 0 pai deve nascer
            break;
          case 22:
            primeiroFilho.start();//caso o ano seja 22 a thread do primeiro filho serh iniciada 
            break;
          case 25:
            segundoFilho.start();//caso o ano seja 25 a thread do segundo filho serah iniciada
            break;
          case 32:
            terceiroFilho.start();//caso o ano seja 32 a thread do segundo filho serah iniciada
            break;
          case 90:
            podeRodar = false;//trocando o valor para false encerrando o laco 
            System.out.println("O pai morre aos "+ano);//caso o ano 90 o pai morre
            objControle.changeImage(objControle.getIdPai(), -1);//passando como parametro para o metodo trocar a imagem no ImageView
            break;
        }
        try {
          Thread.sleep(1000);} catch (InterruptedException e) {}//sleep de um 1 segundo depois enquanto acontece o laco
          ano++;//soma mais 1 na variavel ano
    }
  }
  /* ***************************************************************
* classe: PrimeiroFilho
* Funcao: o primeiro filho tem o primeiro neto, o bisneto e depois
morre 
* Parametros: void
* Retorno: void
*************************************************************** */
  public static class PrimeiroFilho extends Thread{
    PrimeiroNeto primeiroNeto = new PrimeiroNeto();
    int idade =0;//declara a idade como 0
    boolean podeRodar = true;//declara como true usada para roda o laco while com o run
    public PrimeiroFilho(){}
   
    public void run(){ 
      objControle.changeImage(objControle.getIdPrimeiroFilho(), 0); //chama o metodo que muda a imagem
      //no imageView mandado como parametro
      while(podeRodar){//while com variavel do tipo boolean
      
          switch(Threads.ano){
            case 22:
              System.out.println("O pai tem o primeiro Filho aos "+Threads.ano+" anos");
              break;
            case 38:
              //caso o ano seja 38 a thread primeiroNeto serah iniciada
              primeiroNeto.start();
              break;
            case 83:
              //caso o ano seja 83 "podeRodar" vai receber o valor false para o encerramento do laco
              //o metodo "changeImage" vai ser chamado para alterar o ImageView 
              podeRodar = false;
              System.out.println("O primeiro filho morreu aos 61 anos");              
              objControle.changeImage(objControle.getIdPrimeiroFilho(), -1);
              break;
            default://caso o ano nao seja nenhum do especificados em cima ele sai do laco
              break;
        }
        idade++;
        try {sleep(1000); }catch(InterruptedException e){}// um sleep de um segundo 
        objControle.changeImage(objControle.getIdPrimeiroFilho(), idade);//o metedo changeImage eh chamado
        //para alterar a imagem conforme o ano          
      }
    }
  }
/****************************************************************
* classe: SegundoFilho
* Funcao: o segundo Filho tem um segundo neto e depois morre
* Parametros: void
* Retorno: void
*************************************************************** */
  public static class SegundoFilho extends Thread{
    int idade = 0; 
    boolean podeRodar = true;
    public SegundoFilho(){}
    SegundoNeto segundoNeto = new SegundoNeto();
    public void run(){
      objControle.changeImage(objControle.getIdSegundoFillho(), 0);
      while(podeRodar){
       
        switch(Threads.ano){
          case 25:
            System.out.println("O pai tem seu segundo filho aos "+Threads.ano+" anos");
            break;
          case 45:
            segundoNeto.start();
          break;
          case 80:
            podeRodar = false;
            System.out.println("O segundo filho morreu aos 55 anos");
            objControle.changeImage(objControle.getIdSegundoFillho(), -1);//chamando o metedo change
            //changeImage para mudar o ImageView para imagem de morto
            break;
          default:
          break;
        }
        idade++;
        try{sleep(1000);}catch (InterruptedException e) {}//sleep de 1 segundo
        objControle.changeImage(objControle.getIdSegundoFillho(), idade);
      }
    }
  }

/* ***************************************************************
* classe: TereceiroFilho
* Funcao: O terceiro filho tem soh nasce e morre 
* Parametros: void
* Retorno: void
*************************************************************** */
public static class TerceiroFilho extends Thread{
  int anoNascimento, idade=0;
  boolean podeRodar = true;
  public TerceiroFilho(){}
  public void run(){
  objControle.changeImage(objControle.getIdTerceiroFilho(), 0);
   while(podeRodar){
    switch(Threads.ano){
      case 32:
        System.out.println("O pai tem o seu terceiro filho aos "+Threads.ano+" anos");
        anoNascimento = Threads.ano;
        break;   
      case 87:
        podeRodar = false;
        System.out.println("O terceiro filho morre com 55 anos");
        objControle.changeImage(objControle.getIdTerceiroFilho(), -1);
      break;
      default:
      break;   
     }
     idade++;
     try{sleep(1000);}catch (InterruptedException e) {}
     objControle.changeImage(objControle.getIdTerceiroFilho(), idade);
   }
  }
}
  /* ***************************************************************
* classe: PrimeiroNeto
* Funcao: PrimeiroNeto nasce, tem o Bisneto e morre
* Parametros: void
* Retorno: void
*************************************************************** */
  public static class PrimeiroNeto extends Thread {
    int idade=0;
    boolean podeRodar = true;
    PrimeiroBisNeto primeiroBis = new PrimeiroBisNeto();
    
    public PrimeiroNeto(){}
      
    public void run(){
      objControle.changeImage(objControle.getIdPrimeiroNeto(), 0);
      while(podeRodar){
        switch(Threads.ano){
          case 38:
            System.out.println("O pai e avo (Primeiro Filho) aos "+Threads.ano+" anos");
            break;
          case 68:
            primeiroBis.start();
            break;
          case 73:     
            podeRodar = false;
            System.out.println("O primeiro neto morre aos 35 anos");
            objControle.changeImage(objControle.getIdPrimeiroNeto(), -1);
            break;
          default:
          break;
        }try{sleep(1000);}catch (InterruptedException e){}
      idade++;
      objControle.changeImage(objControle.getIdPrimeiroNeto(), idade);
      }
    }
  }

 /* ***************************************************************
* classe: SegundoNeto
* Funcao: o Segundo neto nasce tem um segundo neto e morre
* Parametros: void  
* Retorno: void
*************************************************************** */
  public static class SegundoNeto extends Thread {
    int idade = 0;//declara  a variavel idade e guarda 0 nela
    boolean podeRodar = true;//declara a variavel podeRodar como true
    public SegundoNeto(){}
    public void run(){//run da thread
      objControle.changeImage(objControle.getIdSegundoNeto(), 0);//chama o meteodo changeImage
      while(podeRodar){
        switch(Threads.ano){
          case 45:
            System.out.println("O pai e avo (segundo Filho) aos "+Threads.ano+" anos");
            break;
          case 78:
            podeRodar = false;
            System.out.println("O segundo neto morre aos 33 anos");
            objControle.changeImage(objControle.getIdSegundoNeto(), -1);
          default:
          break;
        }try{sleep(1000);}catch (InterruptedException e) {}//sleep de 1 segundo
        idade++;
        objControle.changeImage(objControle.getIdSegundoNeto(), idade);
      }
    }  
  }
  //Classe Bisneto
  public static class PrimeiroBisNeto extends Thread {
    int anoNascimento, idade = 0;
    boolean podeRodar = true;
    public PrimeiroBisNeto(){} 
    public void run(){
      objControle.changeImage(objControle.getIdBisNeto(), 0);
      while(podeRodar){
        switch(Threads.ano){
          case 68:
            System.out.println("O pai e bisavo (primeiro filho) aos "+Threads.ano+" anos");
            anoNascimento = Threads.ano;
            break;
          case 80:
            podeRodar = false;
            System.out.println("O bisneto morre aos 12 anos");
            objControle.changeImage(objControle.getIdBisNeto(), -1);
            break;
          default:
          break;
        }try{sleep(1000);}catch (InterruptedException e) {}
        idade++;
        objControle.changeImage(objControle.getIdBisNeto(),idade);
      }
    }
  }
}
