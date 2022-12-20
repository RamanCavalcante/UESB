package util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import model.Job;
import model.ListProcesses;
import types.IndexMemory;
import types.Pointer;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 09/09/2022
 * Ultima alteracao.: 17/09/2022
 * Nome: ...........: ManagementMemory
 * Funcao...........: estrutura de controle que gerencia processos
 * ***************************************************************/

public class ManagementMemory extends Thread {
  ListProcesses list;
  Gui gui;  
  int typesAllocation;
  
  private String nameProcesses[] = {"Bananonina", "Me Want Banana", "BeeDoBeeDo", "Baboi",
  "TataBalaTu", "Poopaye", "Birosca", "Bugiganga", "Bulhufas","Chumbrega", "Calvo de cria", 
  "Mixuruquinha", "Bulhufas","Chumbrega", "C  alvo de cria", "Mixuruquinha"};

  public boolean stop = false;
  private String colors [] = {"#CDF0EA", "#F2D388", "#F2D388", "#C98474", "#874C62", "#554994", "#F675A8", "#FFCCB3", "#ECC5FB", "#6D8B74", "#E7FBBE", "#FFC898", "#C37B89", "FF7878", "F57328"};
  private IndexMemory memory [] = new IndexMemory[30];
  private List<Pointer> absolutePartitions;
  List<Integer> listNoRepeat = new ArrayList<>();

  Semaphore semUpdates;

  public ManagementMemory(){
    instance();
    this.semUpdates = new Semaphore(1);
  }


  private void instance(){
    for (int i = 0; i < memory.length; i++) {
      memory[i] = new IndexMemory(i, false);
    }
  }

  private List<Pointer> instacePartitionsAbsolute(){
    return Arrays.asList(
    new Pointer(new IndexMemory(0, false), new IndexMemory(5, false)),
    new Pointer(new IndexMemory(5, false), new IndexMemory(15, false)),
    new Pointer(new IndexMemory(15, false), new IndexMemory(30, false)));
  }

  /*********************************************************
 * Nome.......: run
 * Funcao.....: ...
 * Parametros.: ...
 * retorno....: void
 ***********************************************************/

  public void run(){
   
      while(stop == false){
      
      try {
        //realiza atualizacao da lista
        semUpdates.acquire();

        list.getListProcess().addAll(list.getListProcessBkp());
        list.getListProcessBkp().clear();
  
        if(!list.getListProcess().isEmpty()){

            gui.updateLists();
            semUpdates.release();

            choiceTypesAllocation(typesAllocation);
            System.out.println("fiz escalonamento");
            semUpdates.acquire();
            gui.updateLists();
            semUpdates.release();
            Thread.sleep(2000);
          }else{
            semUpdates.release();
          }
    }catch (InterruptedException e){}}
  }

  public void choiceTypesAllocation(int typesAllocation) throws InterruptedException{

    switch (typesAllocation) {
      case 0:
        simpleContiguous();
        break;
      case 1:
        absoluteStaticPartitioned();
        break;
      case 2:
        relocatableStaticPartitioned();
      default:
        break;
    }
  }

/**
 * 
 * @param numberProcess
 */
  public void createProcess(int numberProcess){

    for(int i = 0; i < numberProcess; i++){
      Job job = new Job();
      int numbRandom = 0;
    
      numbRandom = randomNoRepeat();

      System.out.println("random : "+numbRandom);
      job.setRuntime(numbRandom);//tempo do processo
      job.setNameProcess(nameProcesses[i+numbRandom]);//nome do processo
      job.setSizeProcess(1+numbRandom);//tamanho do processo
      job.setColor(colors[numbRandom]);//escolhe uma cor para o processo 
      list.getListProcessBkp().add(job);
    }
    listNoRepeat.clear();
  }

  public int randomNoRepeat(){

    Random random = new Random();
    int min = 5;
    int max = 10;
    int numbReturn = 0;

    while (numbReturn == 0) {
      int numbRandom = 1+ random.nextInt(max -(min -1)+ min);
      if(!listNoRepeat.contains(numbRandom)){
        listNoRepeat.add(numbRandom);
        numbReturn = numbRandom;
      }
    }
    return numbReturn;
  }
  // Alocacao Contígua Simples
  // Alocacao Particionada Estática Absoluta
  // Alocacao Particionada Estática Realocavel
  
  private void simpleContiguous() throws InterruptedException{

    IndexMemory indexStart =  new IndexMemory(0, false);
    IndexMemory indexEnd =  new IndexMemory(30, false);

    if(memory[0].isBusy() == false ){
    
      Job job = list.getListProcess().get(0);
      list.getListProcess().remove(job);
      gui.updateLists();
      // Optional<Pointer> pointer = lookFreeSapace(job.getSizeProcess());
      System.out.println("Processo "+job.getNameProcess()+" iniciado = "+job.getRuntime());
      job.setPointers(lookFreeSapace(job.getSizeProcess(), new Pointer(indexStart, indexEnd)));
        
      addProcessMemory(job);//adiciona processo na memoria
        
      Thread.sleep(job.getRuntime()*1000);
        
      removeProcessMemory(job);//libera memoria
    }
  }


  /**
   * @throws InterruptedException
   * esta alocacao divide a memoria em 3 partes absolutas [0 - 4], [5 - 14], [15 - 29] 
   */
  private void absoluteStaticPartitioned() throws InterruptedException{
    
    Job job = list.getListProcess().get(0);
    int whyAllocation = findAllocation(job.getSizeProcess());
    
    boolean busy = memory[absolutePartitions.get(whyAllocation).getPointerStart().getIndex()].isBusy();

    if(busy == false){
      semUpdates.acquire();/////////////aqui fecha

      list.getListProcess().remove(job);

      System.out.println("Processo "+job.getNameProcess()+" iniciado = "+job.getRuntime());
      job.setPointers(lookFreeSapace(job.getSizeProcess(), absolutePartitions.get(whyAllocation)));
      
      if(job.getPointers() != null){
        new Thread(() -> {try {
          //processo sera adicionado da memoria
            addProcessMemory(job);
            semUpdates.release();/////////////aqui abre
            
            sleep(job.getRuntime()*1000);
    
          //processo sera removido da memoria 
            semUpdates.acquire();
            removeProcessMemory(job);
            semUpdates.release();
    
            }catch(InterruptedException e){}}).start();
      }else{
        list.getListProcess().add(job);
        semUpdates.release();////////// aqui abre
      }
    }
  }


  /**
   * Cada processo cria uma particao para si mesmo 
   */
  private void relocatableStaticPartitioned(){
    
    IndexMemory indexStart =  new IndexMemory(0, false);
    IndexMemory indexEnd =  new IndexMemory(30, false);

    Job job = list.getListProcess().get(0);

    job.setPointers(lookFreeSapace(job.getSizeProcess(), new Pointer(indexStart, indexEnd)));

    if(job.getPointers() != null){
      new Thread(() -> {try {
        
        semUpdates.acquire();
        list.getListProcess().remove(job);
        addProcessMemory(job);
        semUpdates.release();

        sleep(job.getRuntime()*1000);
        
        semUpdates.acquire();
        removeProcessMemory(job);
        semUpdates.release();

        } catch (InterruptedException e) {}
      }).start();
    }
  }
  
  //metodos auxiliares
  //************************************************* */
    
  //metodos auxiliares
  //************************************************* */
    
  //metodos auxiliares
  //************************************************* */

  public void callUpdates() throws InterruptedException{
    semUpdates.acquire();
    gui.updateLists();
    semUpdates.release();
  }

  /**
   * 
   * @param sizeProcess
   * @return
   */
  public int findAllocation(int sizeProcess){
    if(sizeProcess < 5 ) return 0;
    else if(sizeProcess < 10) return 1;
    else if(sizeProcess < 15) return 2;
    return 0;
  }

  /**
   * 
   * @param job
   */
  public void addProcessMemory(Job job){
    
    fillAllocation(job);
    gui.addProcess(job);
    list.getListMemory().add(job);
    
    gui.updateLists();
  }
  /**
   * 
   * @param job
   */
  public void removeProcessMemory(Job job){
    
    desallocateMemory(job);
    clearAllocation(job);
    gui.clearMemory(job);
    list.getListMemory().remove(job);
    list.getListProcessesFinish().add(job);

    gui.updateLists();
  }

  /**
   * 
   * @param job
   */
  private void desallocateMemory(Job job){
    
    list.getListProcess().remove(job);
    
    int indexOfJobStart = job.getPointers().getPointerStart().getIndex();
    int indexOfJobEnd = job.getPointers().getPointerEnd().getIndex();
    memory[indexOfJobStart] = new IndexMemory(indexOfJobStart, false);
    memory[indexOfJobEnd] = new IndexMemory(indexOfJobEnd, false);
  }

  /**
   * 
   * @param job
   */
  private void fillAllocation(Job job){
    int indexStart = job.getPointers().getPointerStart().getIndex();
    int indexEnd = job.getPointers().getPointerEnd().getIndex();
    for(int i = indexStart; i < indexEnd; i++){ 
      memory[i].setBusy(true);
    }
  }

  private void clearAllocation(Job job){
    int indexStart = job.getPointers().getPointerStart().getIndex();
    int indexEnd = job.getPointers().getPointerEnd().getIndex();
    for(int i = indexStart; i < indexEnd; i++){ 
      memory[i].setBusy(false);
    }
    System.out.println("limpei alocacao");
  }

  /**
   * 
   * @param sizeProcess
   * @param point
   * @return
   */
  private Pointer lookFreeSapace(int sizeProcess, Pointer point){
    
    int sizeControll = 0;
    Pointer pointer = new Pointer();
    int indexStart =  point.getPointerStart().getIndex();
    int indexEnd = point.getPointerEnd().getIndex();
    
    for(int i = indexStart; i < indexEnd && sizeControll <= sizeProcess; i++){
      
      if(memory[i].isBusy() == false){//verifica se espaco esta ocupado
          if(pointer.getPointerStart() == null){
            pointer
            .setPointerStart(new IndexMemory(i, true));
            memory[i] = pointer.getPointerStart();
          }
          sizeControll++;
          if(sizeControll == sizeProcess){
            pointer
            .setPointerEnd(new IndexMemory(i, true));
            memory[i] = pointer.getPointerEnd();
            return pointer;
          }
      }else{
          sizeControll = 0;
      }
    } 
    return null;
  }



  public int getTypesAllocation() {
    return typesAllocation;
  }

  public void setTypesAllocation(int typesAllocation) {
    this.typesAllocation = typesAllocation;
  }

  public ListProcesses getList() {
    return list;
  }
  public void setList(ListProcesses list) {
    this.list = list;
  }
  public String[] getNameProcesses() {
    return nameProcesses;
  }
  public void setNameProcesses(String[] nameProcesses) {
    this.nameProcesses = nameProcesses;
  }
  public List<Pointer> getAbsolutePartitions() {
    return absolutePartitions;
  }
  public void setAbsolutePartitions(List<Pointer> absolutePartitions) {
    this.absolutePartitions = absolutePartitions;
  }
  public Gui getGui() {
    return gui;
  }
  public void setGui(Gui gui) {
    this.gui = gui;
  }
}
