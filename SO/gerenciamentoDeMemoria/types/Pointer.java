package types;
public class Pointer {

  private IndexMemory pointerEnd;
  private IndexMemory pointerStart;

  public Pointer(){}
  public Pointer(IndexMemory pointerStart, IndexMemory pointerEnd){
    this.pointerStart = pointerStart;
    this.pointerEnd = pointerEnd;
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
