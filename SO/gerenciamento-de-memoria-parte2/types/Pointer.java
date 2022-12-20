package types;
public class Pointer {

  private IndexMemory pointerEnd;
  private IndexMemory pointerStart;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 01/10/2022
 * Ultima alteracao.: 09/10/2022
 * Nome: ...........: Pointer
 * Funcao...........: todo ponteiro tem indice de inicio e fim 
 * ***************************************************************/
  public Pointer(){}
  public Pointer(IndexMemory pointerStart, IndexMemory pointerEnd){
    this.pointerStart = pointerStart;
    this.pointerEnd = pointerEnd;
  }

  public int getsizePointer(){
    return this.pointerEnd.getIndex() - this.pointerStart.getIndex();
  }
  public IndexMemory getPointerEnd() {
    return pointerEnd;
  }
  public void setPointerEnd(IndexMemory pointerEnd) {
    this.pointerEnd = pointerEnd;
  }
  public IndexMemory getPointerStart() {
    return pointerStart;
  }
  public void setPointerStart(IndexMemory pointerStart) {
    this.pointerStart = pointerStart;
  }
}
