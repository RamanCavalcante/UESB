package util;

import model.Node;

/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: SweepingGraph.java
 * Funcao...........: classe responsavel por iniciar o algoritmo
 * ***************************************************************/
public class SweepingGraph{

  private DijkstraFunctions dk;
  private boolean NoStop;
  public SweepingGraph(DijkstraFunctions dk){
    this.dk = dk;
    this.NoStop = true;
  }

  public void run() {
    Node nextNode = dk.startDijkstra();
  }

}
