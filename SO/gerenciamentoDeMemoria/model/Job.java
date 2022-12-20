package model;

import types.Pointer;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 09/09/2022
 * Ultima alteracao.: 17/09/2022
 * Nome: ...........: Job
 * Funcao...........: classe de atributos dos processos
 * ***************************************************************/
public class Job extends Thread{
  private String nameProcess;
  private int sizeProcess;
  private Pointer pointers;
  private int runtime;
  private String color;
  
  public Job(){}

  /**
   * 
   * @param nameProcess
   * @param sizeProcess
   * @param runtime
   * @param color
   */
  public Job(String nameProcess, int sizeProcess, int runtime, String color) {
    this.nameProcess = nameProcess;
    this.sizeProcess = sizeProcess;
    this.runtime = runtime;
    this.color = color;
    this.pointers = null;
  }

  public String getNameProcess() {
    return nameProcess;
  }
  public void setNameProcess(String nameProcess) {
    this.nameProcess = nameProcess;
  }
  public int getSizeProcess() {
    return sizeProcess;
  }
  public void setSizeProcess(int sizeProcess) {
    this.sizeProcess = sizeProcess;
  }
  public Pointer getPointers() {
    return pointers;
  }
  public void setPointers(Pointer pointers) {
    this.pointers = pointers;
  }
  public int getRuntime() {
    return runtime;
  }
  public void setRuntime(int runtime) {
    this.runtime = runtime;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

}
