package util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Semaphore;
import model.Job;
import model.ListProcesses;
import types.IndexMemory;
import types.Pointer;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 01/10/2022
 * Ultima alteracao.: 09/10/2022
 * Nome: ...........: ManagementMemory
 * Funcao...........: estrutura de controle que gerencia processos
 * ***************************************************************/

public class ManagementMemory extends Thread {
  ListProcesses list;
  Gui gui;  
  int typesAllocation;


  public boolean stop = false;
  private String colors [] = {"#CDF0EA", "#F2D388", "#F2D388", "#C98474", "#874C62", "#554994",
  "#F675A8", "#FFCCB3", "#ECC5FB", "#6D8B74", "#E7FBBE", "#FFC898", "#C37B89", "FF7878", "F57328"};
  private IndexMemory memory [] = new IndexMemory[30];
  private List<Pointer> fastFitPartitions;
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


        if(!list.getListProcesAux().isEmpty()){
          list.getListProcess().addAll(list.getListProcesAux());
          list.getListProcesAux().clear();
        }
  
        if(!list.getListProcess().isEmpty()){

            gui.updateLists();
            semUpdates.release();

            relocatableStaticPartitioned();
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
/**
 * Escolhe qual escalonamento dinamico sera usado
 * @param typesAllocation
 * @param sizeProcess
 * @param pointers
 * @return
 * @throws InterruptedException
 */
  public Optional<Pointer> choiceTypesAllocation(int typesAllocation, int sizeProcess, Pointer pointers) throws InterruptedException{
    switch (typesAllocation) {
      case 0:
        return  Optional.of(lookFreeSapace_FirstFit(sizeProcess, pointers))
        .orElse( null);
      case 1: 
        return Optional.of(lookFreeSpace_WorstFit_BestFit(sizeProcess, pointers, typesAllocation))
        .orElse(null);
      case 2:
        return Optional.of(lookFreeSpace_WorstFit_BestFit(sizeProcess, pointers, typesAllocation))
        .orElse(null);
      case 3:
        return Optional.of(lookFreeSapace_FastFit(sizeProcess, pointers))
        .orElse(null);
      default:
        return Optional.empty();
    }
  }

/**
 * cria os processos
 * @param numberProcess
 */
  public void createProcess(int numberProcess){

    if(this.typesAllocation == 3)
      produceProcessToFastFit(numberProcess);
    else
      produceProcess(numberProcess);

    list.getListProcesAux().addAll(list.getListProcessBkp());
    list.getListProcessBkp().clear();
    listNoRepeat.clear();
  }



  public void produceProcess(int numberProcess){
    
    for(int i = 0; i < numberProcess; i++){
      Job job = new Job();
    
      int numbRandom = randomNoRepeat();

      System.out.println("random : "+numbRandom);
      job.setRuntime(numbRandom);//tempo do processo
      job.setSizeProcess(1+numbRandom);//tamanho do processo
      job.setColor(colors[numbRandom]);//escolhe uma cor para o processo 
      list.getListProcessBkp().add(job);
    }
  }

  public void produceProcessToFastFit(int numberProcess){
    List<Integer> sizeProcess = Arrays.asList(1, 2, 4);
    Collections.shuffle(sizeProcess);
    for(int i = 0; i < numberProcess; i++){
      Job job = new Job();
      int numRandom = randomNoRepeat();
      Collections.shuffle(sizeProcess);

      System.out.println("random : "+numRandom);
      job.setRuntime(numRandom);//tempo do processo
      job.setSizeProcess(sizeProcess.get(0));//tamanho do processo padrado 1, 2 ou 4
      job.setColor(colors[numRandom]);//escolhe uma cor para o processo 
      list.getListProcessBkp().add(job);
    }
  }
/**
 * nao vai repetir um aletorio e retorna um inteiro
 * @return
 */
  public int randomNoRepeat(){

    Random random = new Random();
    int min = 2;
    int max = 8;
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

  /**
   * Cada processo cria uma particao para si mesmo 
   * @throws InterruptedException
   */
  private void relocatableStaticPartitioned() throws InterruptedException{
    
    IndexMemory indexStart =  new IndexMemory(0, false);
    IndexMemory indexEnd =  new IndexMemory(30, false);

    Job job = list.getListProcess().get(0);

    choiceTypesAllocation(
      this.typesAllocation,
        job.getSizeProcess(),
          new Pointer(indexStart, indexEnd))
            .ifPresent(n -> job.setPointers(n));

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
    }{
      System.out.println("sem espaco livre ");
    }
  }

  /**
   * 
   * @param sizeProcess
   * @param point
   * @return
   */
  private Optional<Pointer> lookFreeSapace_FirstFit(int sizeProcess, Pointer point){
    
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
            return Optional.of(pointer);
          }
      }else{
          sizeControll = 0;
      }
    } 
    return Optional.empty();
  }

  /**
   * olha os espacos livre pra alocar processo worstFit ou bestfit
   * @param sizeProcess
   * @param point
   * @param typesAllocation
   * @return
   */
  private Optional<Pointer> lookFreeSpace_WorstFit_BestFit(int sizeProcess, Pointer point, int typesAllocation){
    
    List<Pointer> listPointers = findPointers(point, sizeProcess);
    Comparator<Pointer> comparatorPointer = (o1, o2) -> { 
      if(o1.getsizePointer() > o2.getsizePointer()) return 1;
      else if(o1.getsizePointer() < o2.getsizePointer()) return -1;
      else return 0;
    }; 

    if(listPointers.isEmpty()){ return Optional.empty(); }
    
    else if(typesAllocation == 1){ //caso best fit
     return Optional.of(listPointers.stream().min(comparatorPointer).get());
    }

    else if(typesAllocation == 2){//caso worst fit
      return Optional.of(listPointers.stream().max(comparatorPointer).get()); 
    }
   
    return Optional.empty();
  }


/**
 * olha os espacos livres FastFit
 * @param sizePrcess
 * @param point
 * @return
 */
public Optional<Pointer> lookFreeSapace_FastFit(int sizePrcess, Pointer point){
  //particao 1 = processos de 1kb
  //particao 2 = processos de 2kb
  //particao 3 = processos de 4kb
  //particao 4 - processos de outros tamanhos
  List<Pointer> pointersFree = findPointers(fastFitPartitions
    .get(findPartitionFastFit(sizePrcess)), sizePrcess);

  if(pointersFree.isEmpty())
    return Optional.empty();
  else
    return Optional.of(pointersFree.get(0));
}

/**
 * procuras espacos livres com o tamanho do processo que vai ser alocado
 * @param point
 * @param sizeProcess
 * @return
 */
 public List<Pointer> findPointers(Pointer point, int sizeProcess){
  List<Pointer> listPointers = new ArrayList<>();
  int sizeControll = 0;
  int indexStart = point.getPointerStart().getIndex();
  int indexEnd = point.getPointerEnd().getIndex();
  int indexStartTemp = 0;
  int indexEndTemp = 0;
  boolean openPointer = false;
  
  //onde coleta todos os espacos livres
  for(int i = indexStart; i < indexEnd; i++){
    if(memory[i].isBusy() == false){
      if(openPointer == false){
        indexStartTemp = i;
        openPointer = true;
      }
      sizeControll ++;
    }else{ sizeControll = 0; }

    if(sizeControll == sizeProcess){
      indexEndTemp = i;
      openPointer = false;
      Pointer pointer = new Pointer();
      pointer.setPointerEnd(new IndexMemory(indexEndTemp, false));
      pointer.setPointerStart(new IndexMemory(indexStartTemp, false));
      listPointers.add(pointer);
      sizeControll = 0;
    }
  }
  return listPointers;
 }


  //metodos auxiliares
  //************************************************* */
    
  //metodos auxiliares
  //************************************************* */
    
  //metodos auxiliares
  //************************************************* */
 
  public int findPartitionFastFit(int sizeProcess){
    if(sizeProcess == 1) return 0;
    if(sizeProcess == 2) return 1;
    if(sizeProcess == 4) return 2;
    else return 3;
  }
  
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
  public int findAllocationFastFit(int sizeProcess){
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
    for(int i = indexStart; i <=indexEnd; i++){ 
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

  public List<Pointer> getFastFitPartitions() {
    return fastFitPartitions;
  }
  public void setFastFitPartitions(List<Pointer> fastFitPartitions) {
    this.fastFitPartitions = fastFitPartitions;
  }
  public Gui getGui() {
    return gui;
  }
  public void setGui(Gui gui) {
    this.gui = gui;
  }
}
