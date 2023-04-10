package view;
import javafx.scene.image.Image;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: Gallery
 * Funcao...........: instanciar imagens
 * ***************************************************************/
public abstract class Gallery {
  private static final String path = "img/";
  public static final Image ICON_NETWORK  = new Image(Gallery.class.getResourceAsStream(path+"icon_network.png"));
  public static final Image ICON_MESSAGE  = new Image(Gallery.class.getResourceAsStream(path+"envelope.png"));
  public static final Image GIF_ARRIVIED  = new Image(Gallery.class.getResourceAsStream(path+"messageArrivied.gif"));
}
