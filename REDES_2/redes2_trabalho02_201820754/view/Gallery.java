package view;

import javafx.scene.image.Image;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: Gallery.java
 * Funcao...........: classe responsavel por instaciar imagens
 * ***************************************************************/

public class Gallery {
  private static final String path = "img/";
  public static final Image ICON_GRAPH  = new Image(Gallery.class.getResourceAsStream(path+"graph.png"));
  public static final Image ICON_START  = new Image(Gallery.class.getResourceAsStream(path+"start-button.png"));
  public static final Image ICON_PLAY  = new Image(Gallery.class.getResourceAsStream(path+"nav.gif"));
  public static final Image ICON_TOCH = new Image(Gallery.class.getResourceAsStream(path+"click.gif"));
  public static final Image ICON_DRAG = new Image(Gallery.class.getResourceAsStream(path+"drag.gif"));
  public static final Image GIF_PING = new Image(Gallery.class.getResourceAsStream(path+"ping.gif"));
}
