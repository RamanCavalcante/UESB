package model.camada_fisica;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Aplicacao;
import model.camada_de_enlace.EnquadramentoReceptor;
import model.camada_de_enlace.EnquadramentoTransmissor;
import model.camada_de_enlace.RedeEnlace;

import java.util.ArrayList;

import controller.Display;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 27/03/2022
* Nome.............: Rede.java
* Funcao...........: Onde acontece todas as camadas fisicas
************************************************************** */

public class Rede {

  int protocolo, tipoEnquadramento;
  String strMensagem;
  Message mensagem;
  TextArea saida_lbl, grafico_lbl;
  private static int[] fluxoBrutoDeBits;
  HBox hbox_grafico;
  public static boolean violacaoCamadaFisica = false;
  public static int time = 2000;
  static int[] globalfluxo = null;
  public Rede() {
  }

/* ***************************************************************
* Metodo: Rede
* Funcao: Construtor da classe
* Parametros: objeto do tipo Message, objeto HBox para o grafico
* Retorno: 
**************************************************************** */
  
  public Rede(Message objmessage, HBox hbox_grafico) {
    this.strMensagem = objmessage.messageText;
    this.protocolo = objmessage.protoclo;
    this.saida_lbl = objmessage.saida_lbl;
    this.grafico_lbl = objmessage.grafico_lbl;
    this.hbox_grafico = hbox_grafico;
    this.tipoEnquadramento = objmessage.tipoEnquadro;

  }

/* ***************************************************************
* Metodo: camadaFisicaTransmissora
* Funcao: escolhe qual o tipo de codificao sera usada e chama o meioDeComunicacao
* Parametros: vetor de inteiros, e o tipo de codificacao
* Retorno: void
**************************************************************** */

  public void camadaFisicaTransmissora(int[] quadro, int protoclo) {

    CamadaFisicaTransmissao objTR = new CamadaFisicaTransmissao();
    int tipoDeCodificacao = protoclo; // alterar de acordo o teste
  
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
    meioDeComunicacao(fluxoBrutoDeBits);
  }

/* ***************************************************************
* Metodo: meioDeComunicacao
* Funcao: passa o fluxo bruto de bits do ponto A para o ponto B
* Parametros: vetor de inteiros
* Retorno: void
**************************************************************** */

  public void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];
    new Thread(new Runnable() {
     
      @Override
      public void run() {

        // passando bit a bit de um vetor para outro
        for (int i = 0; i < fluxoBrutoDeBitsPontoA.length; i++) {
          fluxoBrutoDeBitsPontoB[i] = fluxoBrutoDeBitsPontoA[i];
        }
        int[] fluxo = null;
        if(violacaoCamadaFisica == true){
          fluxo = EnquadramentoTransmissor.CamadaEnlaceDadosTransmissoraEnquadramentoViolacaoDaCamadaFisica(fluxoBrutoDeBitsPontoB);
        }else{
          fluxo = fluxoBrutoDeBitsPontoB;
        }
        globalfluxo = fluxo;
        Display.show(fluxo, hbox_grafico, time, new Quadro() {
          @Override
          public void fazer() {
            camadaFisicaReceptora(globalfluxo, protocolo);
          }
        });
      }
    }).start();

  }

/* ***************************************************************
* Metodo: camadaFisicaReceptora
* Funcao: escolhe qual o tipo de decodificacao sera usada e chama o camadaDeAplicacaoReceptora
* Parametros: vetor de inteiros, e o tipo de codificacao
* Retorno: void
**************************************************************** */

  public void camadaFisicaReceptora(int[] quadroV, int protocolo) {
    
    int [] quadro = null;
    if(violacaoCamadaFisica == true){
      quadro = EnquadramentoReceptor.CamadaEnlaceDadosReceptoraEnquadramentoViolacaoDaCamadaFisica(quadroV);
    }else{
      quadro = quadroV;
    }
    CamadaFisicaRecepcao objRE = new CamadaFisicaRecepcao();
    int tipoDeCodificacao = protocolo;

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
    camadaDeAplicacaoReceptora(fluxoBrutoDeBits);
  }

  public void camadaDeAplicacaoReceptora(int[] quadro) {
    // string mensagem = quadro []; //estava trabalhando com bits
    // chama proxima camada
    int [] desenquadro = RedeEnlace.CamadaDeEnlaceDadosReceptora(quadro, tipoEnquadramento);
    String mensagem = Aplicacao.convertBinaryToMsg(desenquadro);
    aplicacaoReceptora(mensagem);
  }

  public void aplicacaoReceptora(String mensagem) {
    // exibe a mensagem
    // exibe a mensagem
    // exibe a mensagem
    saida_lbl.setText(mensagem);
  }

  public static void printVetor(int []quadro){
    for(int i = 0; i<quadro.length; i++){
        System.out.print(quadro[i]);
      }
    }


    
}
