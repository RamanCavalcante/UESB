package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import model.Edge;
import model.Graph;
import model.Node;


/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 27/02/2023
 * Ultima alteracao.: 05/02/2023
 * Nome: ...........: GeneratingGraph.java
 * Funcao...........: classe que gera o grafo a partir do doc txt
 * ***************************************************************/

public class GeneratingGraph {

  private Graph graph;
  public GeneratingGraph(Graph graph){
    this.graph = graph;
  }

  /**
   *
   * @return
   * @throws IOException
   */
  public Optional<List<Node>> loadBackBone() throws IOException{
    String fileName = "backBone.txt";
    File file = new File(fileName);
    List<Node> nodes;
    if(fileFound(file)){
      nodes = readFile(fileName);
      return Optional.of(nodes);
    }else{
      return Optional.empty();
    }
  }

  /**
   *
   * @param file
   * @return
   */
  public boolean fileFound(File file){
    if(!file.exists()){
      System.out.println("Arquivo não encontrado"+file.getAbsolutePath());
      return false;
    }else{
      return true;
    }
  }

  /**
   * 
   * @param fileName
   * @return
   * @throws IOException
   */
  public List<Node> readFile(String fileName) throws IOException{
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    String firstLine = reader.readLine();
    int semicolonIndex = firstLine.indexOf(";");
    int numNodes = Integer.parseInt(firstLine.substring(0, semicolonIndex));
    List<Node> listOfNodes = new ArrayList<>();

    reader
      .lines()
      .forEach(line ->{
        String [] parts = line.split(";");
        Node node1 = graph.buildNode(new Node((Integer.parseInt(parts[0]) )));
        Node node2 = graph.buildNode(new Node((Integer.parseInt(parts[1]) )));
        Edge edge  = graph.setEdgesAndGet(new Edge(node1, node2, Integer.parseInt(parts[2])));
        node1.addEdges(edge);
        node2.addEdges(edge);
        listOfNodes.addAll(Arrays.asList(node1, node2));
      });
      reader.close();
    drawEdges();
    return listOfNodes;
  }

  public void drawEdges(){

  }
}
