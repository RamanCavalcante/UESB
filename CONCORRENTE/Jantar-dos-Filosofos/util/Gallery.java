package util;
import javafx.scene.image.Image;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 18/09/2021
 * Ultima alteracao: 28/09/2021
 * Nome: Gallery.java
 * Funcao: classe onde as imagens sao setadas em objetos do tipo Image
 * ************************************************************** */

public class Gallery {

    public static  Image fome, comendo, pensando, thought, garfo, hulk, thought2, thought3, thought4;
    
    public void setImg(){
        fome     = new Image(Gallery.class.getResourceAsStream("/res/img/fome.png"));
        comendo  = new Image(Gallery.class.getResourceAsStream("/res/img/comendo.png"));
        pensando = new Image(Gallery.class.getResourceAsStream("/res/img/pensando.png"));
        thought  = new Image(Gallery.class.getResourceAsStream("/res/img/pensamento.gif"));
        garfo    = new Image(Gallery.class.getResourceAsStream("/res/img/garfo.png"));
        hulk     = new Image(Gallery.class.getResourceAsStream("/res/img/pensamento.gif"));
        thought2 = new Image(Gallery.class.getResourceAsStream("/res/img/pensamento02.gif"));
        thought3 = new Image(Gallery.class.getResourceAsStream("/res/img/pensamento03.gif"));
        thought4 = new Image(Gallery.class.getResourceAsStream("/res/img/pensamento04.gif"));
    
    }
}
