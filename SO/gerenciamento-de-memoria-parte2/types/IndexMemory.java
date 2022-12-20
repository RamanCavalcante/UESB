package types;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 01/10/2022
 * Ultima alteracao.: 09/10/2022
 * Nome: ...........: IndexMemory
 * Funcao...........: todo index tem o seu indice e o seu estado
 * ***************************************************************/
public class IndexMemory {
  
  int index;
  boolean busy;
  public IndexMemory(int index, boolean busy){
    this.index = index;
    this.busy = busy;
  }
  
  public int getIndex() {
    return index;
  }
  public void setIndex(int index) {
    this.index = index;
  }
  public boolean isBusy() {
    return busy;
  }
  public void setBusy(boolean busy) {
    this.busy = busy;
  } 
}