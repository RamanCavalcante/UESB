package src;
import javafx.scene.image.Image;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Gallery.java
 * Funcao: A classe Gallery eh do tipo thread para que as imagens
 * sejam setadas no initializable
 * ************************************************************** */

public class Gallery extends Thread {

  public static Image bob01, bob02, bob03, bob04, bob05, bob06, bob07, bob08, bob09, bob10, prod00, prod01, prod02, prod03, prod04, prod05, prod06, prod07, prod08, prod09, bobIcon;
  
/*********************************************************
 * Nome.......: run
 * Funcao.....: setar as imagens 
 * Parametros.: nao recebe parametros
 * retorno....: void
 *********************************************************/

  public void run(){
    
    bob01 = new Image(Gallery.class.getResourceAsStream("/res/img/bob01.gif"));
    bob02 = new Image(Gallery.class.getResourceAsStream("/res/img/bob02.gif"));
    bob03 = new Image(Gallery.class.getResourceAsStream("/res/img/bob03.gif"));
    bob04 = new Image(Gallery.class.getResourceAsStream("/res/img/bob04.gif"));
    bob05 = new Image(Gallery.class.getResourceAsStream("/res/img/bob05.gif"));
    bob06 = new Image(Gallery.class.getResourceAsStream("/res/img/bob06.gif"));
    bob07 = new Image(Gallery.class.getResourceAsStream("/res/img/bob07.gif"));
    bob08 = new Image(Gallery.class.getResourceAsStream("/res/img/bob08.gif"));
    bob09 = new Image(Gallery.class.getResourceAsStream("/res/img/bob09.gif"));
    bob10 = new Image(Gallery.class.getResourceAsStream("/res/img/bob10.gif"));
  
    prod00 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward00.gif"));
    prod01 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward01.gif"));
    prod02 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward02.gif"));
    prod03 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward03.gif"));
    prod04 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward04.gif"));
    prod05 = new Image(Gallery.class.getResourceAsStream("/res/img/mr_krabs01.gif"));
    prod06 = new Image(Gallery.class.getResourceAsStream("/res/img/mr_krabs02.gif"));
    prod07 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward05.gif"));
    prod08 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward06.gif"));
    prod09 = new Image(Gallery.class.getResourceAsStream("/res/img/squidward07.gif"));
  
    bobIcon = new Image(Gallery.class.getResourceAsStream("/res/img/bobIcon.jpg"));
  }
 

}


