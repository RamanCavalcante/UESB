package src;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 06/09/20201
 * Ultima alteracao: 15/09/2021
 * Nome: Lancho.java
 * Funcao: A classe do objeto Lancho
 * ************************************************************** */

public class Lancho {
  private String nomeLancho;
  private int tempoPreparo;

  public Lancho(){}
  public Lancho(String nomeLancho, int tempoPreparo) {
    this.nomeLancho = nomeLancho;
    this.tempoPreparo = tempoPreparo;
  }
  public String getNomeLancho() {
    return nomeLancho;
  }
  public void setNomeLancho(String nomeLancho) {
    this.nomeLancho = nomeLancho;
  }
  
  public int getTempoPreparo() {
    return tempoPreparo;
  }
  public void setTempoPreparo(int tempoPreparo) {
    this.tempoPreparo = tempoPreparo;
  }
  
}
