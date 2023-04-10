package model.camada_de_enlace;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import controller.Controller;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Aplicacao;
import model.GUI;
import model.camada_de_enlace.controle_de_erro.ControleDeErroReceptor;
import model.camada_de_enlace.controle_de_erro.ControleDeErroTransmissor;
import model.camada_de_enlace.enquadramento.EnquadramentoReceptor;
import model.camada_de_enlace.enquadramento.EnquadramentoTransmissor;
import model.camada_fisica.Converter;
import model.camada_fisica.Message;
import model.camada_fisica.Rede;


/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 19/03/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: RedeEnlace.java
* Funcao...........: camada de aplicacao de enlace 
***************************************************************/

public class RedeEnlace {

  public static ArrayList<int[]> alocaQuadros = new ArrayList<>();
  
  private static int [] vetorVazio;
  

  GUI gui;
  Message msg;

  public RedeEnlace(GUI gui, Message msg){
    this.gui = gui;
    this.msg = msg;
  }

  public RedeEnlace(){}

  /*
   * **********************************************************
   * Metodo: CamadaEnlaceDadosTransmissora
   * Funcao: camada de nlace de dados transmissora
   * Parametros: quadro, objeto de Message, hbox do grafico
   * Retorno: int []
   */
  public void CamadaEnlaceDadosTransmissora(int[] quadro) throws InterruptedException {
    

    gui.toStringMessage(quadro, "CAMADA DE ENLACE DE DADOS TRANSMISSORA");
    // CamadaDeEnlaceTransmissoraEnquadramento(quadro);
    // A proxima camada eh chamada dentro da CamadaEnlaceTransmissoraEnquadramento()
    CamadaEnlaceTransmissoraEnquadramento(quadro);
    // chama a camada fisica
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceTransmissoraEnquadramento
   * Funcao: escolhe qual tipo de enquadramento sera utilizado
   * Parametros: quadro, tipoEnquadramento
   * Retorno: int []
   */

  public void CamadaEnlaceTransmissoraEnquadramento(int[] quadro) throws InterruptedException {
    EnquadramentoTransmissor objTransmissor = new EnquadramentoTransmissor();
    int[] quadroEnquadrado = null;
    int tipoEnquadramento = msg.getTipoEnquadro();
    

    switch (tipoEnquadramento) {
      case 1:
        quadroEnquadrado = objTransmissor.CamadaEnlaceDadosTransmissoraEnquadramentoContagemDeCaracteres(quadro);
        break;
      case 2:
        quadroEnquadrado = objTransmissor.CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBytes(quadro);
        break;
      case 3:
        quadroEnquadrado = objTransmissor.CamadaEnlaceDadosTransmissoraEnquadramentoInsercaoDeBits(quadro);
        break;
      case 4:
        Rede.violacaoCamadaFisica = true;
        quadroEnquadrado = quadro;
        break;
      default:
        break;
    }
    
    gui.toStringMessage(quadroEnquadrado, "CAMADA DE ENLACE DE DADOS ENQUADRAMENTO TRANSMISSOR");

    /**
     * depois de separar os quadros em threads
     * a funcao CamadaEnlaceDadosTransmissoraControleDeErro()
     * eh chamada 
     */

    SplitQuadro.split(tipoEnquadramento, quadroEnquadrado, gui, msg);
    ///
  }
  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDadosTransmissoraControleDeErro
   * Funcao:
   * Parametros: quadro, tipo de controle de erro
   * Retorno: int []
   */

  public void CamadaEnlaceDadosTransmissoraControleDeErro(Quadro qquadro) throws InterruptedException {
    
    int [] flagEnd = {0, 1, 0, 1, 1, 1, 0, 0};


    int [] quadro = qquadro.getQuadro();
    ControleDeErroTransmissor objControleDeErro = new ControleDeErroTransmissor();
    int[] quadroFinal = null;
    
    int tipoControleDeErro = msg.getTipoDeControleDeErro();

    if(Arrays.equals(flagEnd, quadro))
      tipoControleDeErro = 5;
  
    switch (tipoControleDeErro) {
      case 1: // bit de paridade par
        quadroFinal = objControleDeErro.CamadaEnlaceDadosTransmissoraControleDeErroBitParidadePar(quadro);
        break;
      case 2: // bit de paridade impar
        quadroFinal = objControleDeErro.CamadaEnlaceDadosTransmissoraControleDeErroBitParidadeImpar(quadro);
        break;
      case 3: // CRC
        quadroFinal = objControleDeErro.CamadaEnlaceDadosTransmissoraControleDeErroCRC(quadro);
        // codigo
        break;
      case 4: // codigo de Hamming
        quadroFinal = objControleDeErro.CamadaEnlaceDadosTransmissoraControleDeErroCodigoDeHamming(quadro);
        // codigo
        break;
      case 5:
        quadroFinal = quadro;
    }// fim do switch/case

    gui.toStringMessage(quadroFinal,"CAMADA DE ENLACE DE DADOS CONTROLE DE ERRO TRANSMISSOR");
    
    Rede objRede = new Rede(msg, gui);

    qquadro.setQuadro(quadroFinal);

    objRede.camadaFisicaTransmissora(qquadro);
  }

  /*
   * ***************************************************************
   * Metodo: CamadaDeEnlaceDadosReceptora
   * Funcao: camada de enlace de dados receptora
   * Parametros: quadro, tipoEnquadro
   * Retorno: int []
   */

  public int[] CamadaDeEnlaceDadosReceptora(Quadro qquadro) {

    int [] quadro = CamadaEnlaceDadosReceptoraControleDeErro(qquadro);
    quadro = CamadaEnlaceDadosReceptoraEnquadramento(quadro);

    return quadro;
  }

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDadosReceptoraControleDeErro
   * Funcao:
   * Parametros: quadro, tipo de controle de erro
   * Retorno: int []
   */

  public int[] CamadaEnlaceDadosReceptoraControleDeErro(Quadro qquadro) {

    int[] quadroFinal = null;
    int [] quadro = qquadro.getQuadro();
    int [] vetorVazio = null;
    boolean quadroSemErro = false;

    
   
    int tipoControleDeErro = msg.getTipoDeControleDeErro();
   
    if(verificaFlag(quadro)){
     tipoControleDeErro = 5;
   }
    switch (tipoControleDeErro) {
      case 1: // bit de paridade par
        quadroFinal = ControleDeErroReceptor.CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadePar(quadro);
        quadroSemErro = ControleDeErroReceptor.quadroSemErroParidadePar(quadro);
        break;
      case 2: // bit de paridade impar
        quadroFinal = ControleDeErroReceptor.CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadeImpar(quadro);
        quadroSemErro =ControleDeErroReceptor.quadroSemErroParidadeImpar(quadro);
        break;
      case 3: // CRC
        quadroFinal = ControleDeErroReceptor.CamadaEnlaceDadosReceptoraControleDeErroCRC(quadro);
        quadroSemErro = ControleDeErroReceptor.quadroSemErroCrc(quadro);
        break;
      case 4: // codigo de hamming
        quadroFinal = ControleDeErroReceptor.CamadaEnlaceDadosReceptoraControleDeErroCodigoDeHamming(quadro);
        quadroSemErro = ControleDeErroReceptor.quadroSemErroHamming(quadro);
        break;
      case 5:
        quadroFinal = quadro;
        quadroSemErro = true;
    }// fim do switch/case

    gui.toStringMessage(quadro,"CAMADA DE ENLACE DE DADOS CONTROLE DE ERRO RECEPTOR", quadroSemErro);

    if(quadroSemErro){
      
      qquadro.setAck(true);
      return quadroFinal;      
      
    }else{
 
      return vetorVazio; //retorna um vetor null caso o quadro esteja com erro
    }

  }// fim do metodo CamadaEnlaceDadosReceptoraControleDeErro

  /*
   * ***************************************************************
   * Metodo: CamadaEnlaceDadosReceptoraEnquadramento
   * Funcao: camada de enlace de dados receptora enquadramento
   * Parametros: quadro, tipoEnquadro
   * Retorno: int []
   */
  public int[] CamadaEnlaceDadosReceptoraEnquadramento(int[] quadro) {

    int [] flagEnd = {0,1,0,1,1,1,0,0};

    EnquadramentoReceptor enlace = new EnquadramentoReceptor();
    int tipoEnquadramento = msg.getTipoEnquadro();
    if(quadro != null){
      if(!verificaFlag(quadro)){
      
      switch (tipoEnquadramento) {
        case 1:
          alocaQuadros.add(enlace.CamadaEnlaceDadosReceptoraEnquadramentoContagemDeCaracteres(quadro));
          break;
        case 2:
          alocaQuadros.add(enlace.CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBytes(quadro));
          break;
        case 3:
          alocaQuadros.add(enlace.CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBits(quadro));
          break;
        case 4:
          Rede.violacaoCamadaFisica = false;
          alocaQuadros.add(quadro);
          break;
        default:
          break;
      }
    return vetorVazio;
  }else{

      quadro = juntaQuadros(alocaQuadros);
      alocaQuadros.clear();
      
      gui.toStringMessage(quadro,"CAMADA DE ENLACE DE DADOS ENQUADRAMENTO RECEPTOR");
      return quadro;
    }
  }else{ return vetorVazio; }
  }

  public static int [] juntaQuadros(ArrayList<int[]> arrayQUadros){
    ArrayList<Integer> temp = new ArrayList<>();
    for(int i = 0; i < arrayQUadros.size(); i++){
      for(int j = 0; j < arrayQUadros.get(i).length; j++){
        temp.add(arrayQUadros.get(i)[j]);
      }
    }
    int [] quadro = new int[temp.size()];
    for(int i = 0; i < temp.size(); i++ ){
      quadro[i] = temp.get(i);
    }
    return quadro;
  }

  public boolean verificaFlag(int [] quadro){
    int [] flagEnd = {0,1,0,1,1,1,0,0};
    int [] flagEndM = {0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1};
    int [] flagEndMD = {0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1};
    if(Arrays.equals(quadro, flagEnd)){
      return true;
    }else if(Arrays.equals(quadro, flagEndM)){
      return true;
    }else if(Arrays.equals(quadro, flagEndMD)){
      return true;
    }else{
      return false;
    }
  }
}
