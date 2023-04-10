package model.camada_fisica;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Aplicacao;
import model.GUI;
import model.camada_de_enlace.Quadro;
import model.camada_de_enlace.RedeEnlace;
import model.camada_de_enlace.enquadramento.EnquadramentoReceptor;
import model.camada_de_enlace.enquadramento.EnquadramentoTransmissor;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;


import controller.Display;
 
/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 21/05/2022
* Ultima alteracao.: 21/05/2022
* Nome.............: Rede.java
* Funcao...........: Onde acontece todas as camadas fisicas
****************************************************************/

public class Rede {


  String strMensagem;
  Message mensagem;

  TextArea grafico_lbl;
  private static int[] fluxoBrutoDeBits;
  public static boolean violacaoCamadaFisica = false;
  public static int time = 1000;
  public static int porcetagem_error = 0;
  public static boolean acabou = false;
  public static Semaphore AreaCritica = new Semaphore(1);
  public static String msgFinal = "";

  Message msg;
  GUI gui;


  public Rede(){ }

/* ***************************************************************
* Metodo: Rede
* Funcao: Construtor da classe
* Parametros: objeto do tipo Message, objeto HBox para o grafico
* Retorno: 
**************************************************************** */
  
  public Rede(Message objmessage, GUI gui) {

    this.msg = objmessage;
    this.gui = gui;

  }

/* ***************************************************************
* Metodo: camadaFisicaTransmissora
* Funcao: escolhe qual o tipo de codificao sera usada e chama o meioDeComunicacao
* Parametros: vetor de inteiros, e o tipo de codificacao
* Retorno: void
**************************************************************** */

  public void camadaFisicaTransmissora(Quadro qquadro) throws InterruptedException {

    int [] quadro = qquadro.getQuadro();

    CamadaFisicaTransmissao objTR = new CamadaFisicaTransmissao();
    int tipoDeCodificacao =  msg.getProtoclo();// alterar de acordo o teste
  
    switch (tipoDeCodificacao) {
      case 1: // codificao binaria
        fluxoBrutoDeBits = objTR.camadaFisicaTransmissoraCodificacaoBinaria(quadro);
        break;
      case 2: // codificacao manchester
        fluxoBrutoDeBits = objTR.camadaFisicaTransmissoraCodificacaoManchester(quadro);
        break;
      case 3: // codificacao manchester diferencial
        fluxoBrutoDeBits = objTR.camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
        break;
    }
    
    gui.toStringMessage(quadro, "CAMADA FISICA TRANSMISSORA");
    qquadro.setQuadro(fluxoBrutoDeBits);
    meioDeComunicacao(qquadro);
  }

/* ***************************************************************
* Metodo: meioDeComunicacao
* Funcao: passa o fluxo bruto de bits do ponto A para o ponto B
* Parametros: vetor de inteiros
* Retorno: void
**************************************************************** */

  public void meioDeComunicacao(Quadro qquadro) throws InterruptedException {
    
    gui.toStringMessage(qquadro.getQuadro(), "MEIO DE COMUNICACAO");
    int [] fluxoBrutoDeBits = qquadro.getQuadro();
    

    try {
      AreaCritica.acquire(); // ACQUIRE SEMAPHORE
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];
    Random gerador = new Random();  
   
    int aleatorio = gerador.nextInt(101);
        // passando bit a bit de um vetor para outro
        
        for (int i = 0; i < fluxoBrutoDeBitsPontoA.length; i++) {
        
          if(porcetagem_error > aleatorio){
            if(fluxoBrutoDeBitsPontoA[i] == 1){
              fluxoBrutoDeBitsPontoB[i] = 0;
            }else{
              fluxoBrutoDeBitsPontoB[i] = 1;
            }
          }else{
            
            fluxoBrutoDeBitsPontoB[i] = fluxoBrutoDeBitsPontoA[i];
          }
          
        }

        int [] flag = verificaFlag(fluxoBrutoDeBitsPontoA);

        if(flag != null){
          fluxoBrutoDeBitsPontoB = flag;
        } 
        
        int[] fluxo = null;
        if(violacaoCamadaFisica == true){
          fluxo = EnquadramentoTransmissor.CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoDaCamadaFisica(fluxoBrutoDeBitsPontoB);
        }else{
          fluxo = fluxoBrutoDeBitsPontoB;
        }
        Display.show(fluxo, gui, time);
        
        qquadro.setQuadro(fluxo);
        //chama a proxima camada
        camadaFisicaReceptora(qquadro);    

    AreaCritica.release();
  }

/* ***************************************************************
* Metodo: camadaFisicaReceptora
* Funcao: escolhe qual o tipo de decodificacao sera usada e chama o camadaDeAplicacaoReceptora
* Parametros: vetor de inteiros, e o tipo de codificacao
* Retorno: void
**************************************************************** */

  public void camadaFisicaReceptora(Quadro qquadro) {
    
    int [] quadro = null;
    if(violacaoCamadaFisica == true){
      quadro = EnquadramentoReceptor.CamadaEnlaceDadosReceptoraEnquadramentoViolacaoDaCamadaFisica(qquadro.getQuadro());
    }else{
      quadro = qquadro.getQuadro();
    }
    CamadaFisicaRecepcao objRE = new CamadaFisicaRecepcao();
    int tipoDeCodificacao = msg.getProtoclo();

    switch (tipoDeCodificacao) {
      case 1: // codificao binaria
        fluxoBrutoDeBits = objRE.camadaFisicaReceptoraCodificacaoBinaria(quadro);
        break;
      case 2: // codificacao manchester
        fluxoBrutoDeBits = objRE.camadaFisicaReceptoraDecodificacaoManchester(quadro);
        break;
      case 3: // codificacao manchester diferencial
        fluxoBrutoDeBits = objRE.CamadaFisicaReceptoraDecodificacaoManchesterDiferencial(quadro);
        break;
    }// fim do switch/case
     // chama proxima cama
    gui.toStringMessage(fluxoBrutoDeBits, "CAMADA FISICA RECEPTORA");
    qquadro.setQuadro(fluxoBrutoDeBits);
     camadaDeAplicacaoReceptora(qquadro);
  }

  public void camadaDeAplicacaoReceptora(Quadro qquadro) {
    // string mensagem = quadro []; //estava trabalhando com bits
    // chama proxima camada
    RedeEnlace objEnlace = new RedeEnlace(gui, msg);
    int [] quadro =  objEnlace.CamadaDeEnlaceDadosReceptora(qquadro);
    
    if(quadro != null){

      String mensagem = Aplicacao.convertBinaryToMsg(quadro);
      aplicacaoReceptora(mensagem);
    }
  }

  public  void aplicacaoReceptora(String mensagem) {
    // exibe a mensagem
    // exibe a mensagem
    // exibe a mensagem

    gui.getSaida_lbl().appendText(mensagem);
    
  }

  public int [] verificaFlag(int [] quadro){
    int [] nill = null;
    int [] flagEnd = {0,1,0,1,1,1,0,0};
    int [] flagEndM = {0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1};
    int [] flagEndMD = {0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1};
    if(Arrays.equals(quadro, flagEnd)){
      return flagEnd;
    }else if(Arrays.equals(quadro, flagEndM)){
      return flagEndM;
    }else if(Arrays.equals(quadro, flagEndMD)){
      return flagEndMD;
    }else{
      return nill;
    }
    
  }

}
