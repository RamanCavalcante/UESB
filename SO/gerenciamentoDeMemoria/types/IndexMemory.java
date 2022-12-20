package types;
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