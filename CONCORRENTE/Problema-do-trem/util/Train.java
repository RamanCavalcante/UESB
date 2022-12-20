package util;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
Autor......: Raman Melo Cavalcante
Matricula..: 201820754
Inicio.....: 23 de aogosto de 2021
Alteracao..: 1 de setembro de 2021
Nome.......: Train.java
Funcao.....: gerencia e cria os processos
=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-*/

public class Train extends Thread {
  ImageView img;
  int circuito[];
  public int slide = 20;//varivel usada nos sleeps podendo aumentar ou diminuir velocidade do trem
  int tunnelTrain;//usado para verificar qual foi o tunel que o trem passou
  public Slider slideer;// usado para aumentar ou diminuir a velocidade do trem
  public static boolean podePassarTunnel1 = true;// usado para verificar se a passagem pelo tunel esta livre
  public static boolean podePassarTunnel2 = true;// usado para verificar se a passagem pelo tunel esta livre
  public static Controller objControle;

  public Train(Controller controle){
    this.objControle = controle;
  }
/*********************************************************
 * Nome.......: Train
 * Funcao.....: construtor da class
 * Parametros.: Controller do tipo controller, img do tipo
 * ImageView, circuito do tipo vetor int, slideer do tipo
 * Slider, tunnelTrain do tipo int
 *********************************************************/
 
  public Train(Controller controle, ImageView img, int []circuito, Slider slideer, int tunnelTrain){
    this.objControle = controle;
    this.img = img;
    this.circuito = circuito;
    this.slideer = slideer;
    this.tunnelTrain = tunnelTrain;
  }
//metodo run da classe
  @Override
  public void run(){
    
    for(int i = 0; i < circuito.length; i++){//laco de repeticao usado para percorrer o vetor do circuito
      switch(circuito[i]){//switch usada para escolhar a acao pelo valor do indice do vetor
        case 1:
          andarDireita();// caso o indice do vetor seja 1 chamo este metodo
          if(tunnelTrain !=0){//verifica em qual dos tuneis o trem passou
            if(tunnelTrain == 2 && podePassarTunnel1 == true){//condicao perfeita para passar pelo tunel
              break;
            }else if(tunnelTrain == 2 && podePassarTunnel1 == false){//nessa condicao o entre entra em 
              //sleep para esperar o outro trem passar
              while(podePassarTunnel1==false){
                try { Thread.sleep(2000);} catch (InterruptedException e) { }
              }
              try { Thread.sleep(1000);} catch (InterruptedException e) { }//logo depois que o podePassar
              //troca o valor para true espero 1 segundo para chamar o proximo metodo
            }
            //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
            if(tunnelTrain == 1 && podePassarTunnel2 == true){// se acontecer essa condicao e perfeita para o trem passar  e apenas sair do if
              break;
            }else if(tunnelTrain ==1 && podePassarTunnel2 == false){//se essa condicao acontecer entra no laco do while ficando em sleep enquanto o valor podePassar for false
              while(podePassarTunnel2==false){
                try { Thread.sleep(2000);} catch (InterruptedException e) { }
              }
              try { Thread.sleep(1000);} catch (InterruptedException e) { }//um segundo de sleep logo 
               //depois do podePassar trocar o valor para true
            }
          }
          break;
        case 2:
          subirDireita();// caso o indice do vetor seja 2 chamo este metodo
          break;
        case 3:
          descerDireita();// caso o indice do vetor seja 3 chamo este metodo
          break;
        case 4:
          tunnelTrain = 1;
          passarpeloTunnelDireita1();// caso o indice do vetor seja 4 chamo este metodo
          break;
        case 5:
          andarEsquerda();// caso o indice do vetor seja 5 chamo este metodo
          if(tunnelTrain !=0){
            if(tunnelTrain == 2 && podePassarTunnel1 == true){
              break;
            }else if(tunnelTrain == 2 && podePassarTunnel1 == false){
              while(podePassarTunnel1==false){
                try { Thread.sleep(2000);} catch (InterruptedException e) { }
              }
              try { Thread.sleep(1000);} catch (InterruptedException e) { }
            }
            //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
            if(tunnelTrain == 1 && podePassarTunnel2 == true){
              break;
            }else if(tunnelTrain ==1 && podePassarTunnel2 == false){
              while(podePassarTunnel2==false){
                try { Thread.sleep(2000);} catch (InterruptedException e) { }
              }
              try { Thread.sleep(1000);} catch (InterruptedException e) { }
            }
          }
          break;
        case 6:
          subirEsquerda();// caso o indice do vetor seja 6 chamo este metodo
          break;
        case 7:
          descerEsquerda();// caso o indice do vetor seja 7 chamo este metodo
          break;
        case 8:
          tunnelTrain = 1;//caso o indice do vetor seja 8 mudo o valor da varivel tunnelTrain para verificacao em um dos metodos andar para direita ou esquerda
          passarpeloTunnelEsquerda1();//caso o indice do vetor seja 8 chamo este metodo
          break;
        case 9:
          tunnelTrain = 2;//caso o indice do vetor seja 9 mudo o valor da varivel tunnelTrain para verificacao em um dos metodos andar para direita ou esquerda
          passarpeloTunnelEsquerda2();//caso o indice do vetor seja 9 chamo este metodo
          break;
        case 10:
          tunnelTrain = 2;//caso o indice do vetor seja 10 mudo o valor da varivel tunnelTrain para verificacao em um dos metodos andar para direita ou esquerda
          passarpeloTunnelDireita2();//caso o indice do vetor seja 10 chamo este metodo
          break;
      }
    }
 try { Thread.sleep(1000);} catch (InterruptedException e) { } 
 objControle.getBtnReset().setDisable(false);
  }
  
 /***************************************************************
 * Nome.......: andarDireita
 * Funcao.....: animar o objeto do tipo Train para a direita
 * Parametros.: void
 ***************************************************************/
  public void andarDireita(){
    int i= 0;
    while(i<160){
      Platform.runLater(() -> img.setLayoutX(img.getLayoutX() + 1));//faz a imagem se movimentar a cada laco rodado
      try { Thread.sleep(slide);} catch (InterruptedException e) { } //sleep para aumentar ou diminuir a velocidade
    i++;//aumenta mais no contador para rodar o laco
    }
  }
/***************************************************************
 * Nome.......: subirDireita
 * Funcao.....: animar o objeto do tipo Train para subir para 
 * direita
 * Parametros.: void
 ***************************************************************/
  public void subirDireita(){
    int i= 0;
    while(i<45){//para animar a imagem na diagonal rodo o laco alterando os valores no x e no y 
      Platform.runLater(() -> img.setLayoutY(img.getLayoutY() - 1));//alterando o valor no y
      Platform.runLater(() -> img.setLayoutX(img.getLayoutX() + 1));//alterando o valor no x
      try { Thread.sleep(slide);} catch (InterruptedException e) { }
    i++;
    }
  }

 /***************************************************************
 * Nome.......: descerDireita
 * Funcao.....: animar o objeto do tipo Train para descer para 
 * direita
 * Parametros.: void
****************************************************************/
  public void descerDireita(){
    int i= 0;
    while(i<45){//para animar a imagem na diagonal rodo o laco alterando os valores no x e no y 
      Platform.runLater(() -> img.setLayoutY(img.getLayoutY() + 1));//alterando o valor no y
      Platform.runLater(() -> img.setLayoutX(img.getLayoutX() + 1));//alterando o valor no x
      try { Thread.sleep(slide);} catch (InterruptedException e) { }
    i++;
    }
  }

 /***************************************************************
 * Nome.......: andarEsquerda
 * Funcao.....: animar o objeto do tipo Train para andar para esquerda
 * Parametros.: void
 ***************************************************************/
  public void andarEsquerda(){
    int i = 0;
    while(i<150){
      Platform.runLater(() -> img.setLayoutX(img.getLayoutX() - 1));
      try { Thread.sleep(slide);} catch (InterruptedException e) { }
      i++;
    }
  }

/****************************************************************
 * Nome.......: subirEsquerda
 * Funcao.....: animar o objeto do tipo Train para subir para 
 * Esquerda
 * Parametros.: void
 ***************************************************************/
  public void subirEsquerda(){
    int i = 0;
    while(i<40){
      Platform.runLater(() -> img.setLayoutY(img.getLayoutY() - 1));
      Platform.runLater(() -> img.setLayoutX(img.getLayoutX() - 1));
      try { Thread.sleep(slide);} catch (InterruptedException e) { }
    i++;
    }
  }

 /***************************************************************
 * Nome.......: descerEsquerda
 * Funcao.....: animar o objeto do tipo Train para descer para 
 * esquerda
 * Parametros.: void
 ***************************************************************/
  public void descerEsquerda(){
    int i = 0;
    while(i<35){//para animar a imagem na diagonal rodo o laco alterando os valores no x e no y 
      Platform.runLater(() -> img.setLayoutY(img.getLayoutY() + 1));//alterando o valor no y
      Platform.runLater(() -> img.setLayoutX(img.getLayoutX() - 1));//alterando o valor no x
      try { Thread.sleep(slide);} catch (InterruptedException e) { }
    i++;
    }
  }

 /***************************************************************
 * Nome.......: passarpeloTunnelEsquerda1
 * Funcao.....: animar o objeto do tipo Train pra passar o 
 * tunel 1 pela esquerda 
 * Parametros.: void
 ***************************************************************/
  public synchronized void passarpeloTunnelEsquerda1(){
    int i = 0;
    tunnelTrain = 1;
    podePassarTunnel1 = false;
    objControle.getImgRick2().setImage(Gallery.Flags);
      while(i<140){
        Platform.runLater(() -> img.setLayoutX(img.getLayoutX() - 1));
        try { Thread.sleep(slide);} catch (InterruptedException e) { }
      i++;
      }
    objControle.getImgRick2().setImage(Gallery.morgado);
    podePassarTunnel1 = true;
  }

 /***************************************************************
 * Nome.......: passarpeloTunnelEsquerda2
 * Funcao.....: animar o objeto do tipo Train pra passar o 
 * tunel 2 pela esquerda
 * Parametros.: void
 ***************************************************************/
  public synchronized void passarpeloTunnelEsquerda2(){
    int i = 0;
    tunnelTrain = 2;
    podePassarTunnel2 = false;
    objControle.getImgRick1().setImage(Gallery.Flags);
      while(i<130){
        Platform.runLater(() -> img.setLayoutX(img.getLayoutX() - 1));
        try { Thread.sleep(slide);} catch (InterruptedException e) { }
      i++;
      }  
    objControle.getImgRick1().setImage(Gallery.morgado);
    podePassarTunnel2 = true;
  }

 /***************************************************************
 * Nome.......: passarpeloTunnelDireita1
 * Funcao.....: animar o objeto do tipo Train pra passar pelo
 * tunel 1 pela direita
 * Parametros.: void
 ***************************************************************/
  public void passarpeloTunnelDireita1(){
    int i = 0;
    tunnelTrain = 1;
    podePassarTunnel1 = false;
    objControle.getImgRick2().setImage(Gallery.Flags);
      while(i<125){
        Platform.runLater(() -> img.setLayoutX(img.getLayoutX() + 1));
        try { Thread.sleep(slide);} catch (InterruptedException e) { }
      i++;
      }
      objControle.getImgRick2().setImage(Gallery.morgado);  
      podePassarTunnel1 = true; 
  }

 /***************************************************************
 * Nome.......: passarpeloTunnelDireita2
 * Funcao.....: animar o objeto do tipo Train pra passar pelo 
 * tunel 2 pela direita
 * Parametros.: void
 ***************************************************************/
  public synchronized void passarpeloTunnelDireita2(){
    int i = 0;
    tunnelTrain = 2;
    podePassarTunnel2 = false;
    objControle.getImgRick1().setImage(Gallery.Flags);
      while(i<130){
        Platform.runLater(() -> img.setLayoutX(img.getLayoutX() + 1));
        try { Thread.sleep(slide);} catch (InterruptedException e) { }
      i++;
      }
    objControle.getImgRick1().setImage(Gallery.morgado);
  podePassarTunnel2 = true;
  }
 /***************************************************************
 * Nome.......: startReset1
 * Funcao.....: reseta a posicao do trem 1
 * Parametros.: void
 ***************************************************************/
  public void startReset1(){
    Platform.runLater(() -> img.setLayoutX(913));
    Platform.runLater(() -> img.setLayoutY(243));
  }
/***************************************************************
 * Nome.......: startReset2
 * Funcao.....: reseta a posicao do trem 2
 * Parametros.: void
 ***************************************************************/
  public void startReset2(){
    Platform.runLater(() -> img.setLayoutX(18));
    Platform.runLater(() -> img.setLayoutY(239));

  }
}
