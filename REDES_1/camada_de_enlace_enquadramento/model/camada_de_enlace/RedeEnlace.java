package model.camada_de_enlace;

import javafx.scene.layout.HBox;
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

  /* **********************************************************
* Metodo: CamadaEnlaceDadosTransmissora
* Funcao: camada de nlace de dados transmissora
* Parametros: quadro, objeto de Message, hbox do grafico
* Retorno: int []
*************************************************************** */
  public static void CamadaEnlaceDadosTransmissora(int[] quadro, Message objMessage, HBox hBox_grafico) {
    EnquadramentoTransmissor et = new EnquadramentoTransmissor();
    // CamadaDeEnlaceTransmissoraEnquadramento(quadro);
    quadro = CamadaEnlaceTransmissoraEnquadramento(quadro, objMessage.getTipoEnquadro());
     // CamadaDeEnlaceTransmissoraControleDeErro(quadro);
    // CamadaDeEnlaceTransmissoraControleDeFluxo(quadro);
    
    Rede objRede = new Rede(objMessage, hBox_grafico);
    objRede.camadaFisicaTransmissora(quadro, objMessage.getProtoclo());// chama a camada fisica
  }



  /* ***************************************************************
* Metodo: CamadaEnlaceTransmissoraEnquadramento
* Funcao: escolhe qual tipo de enquadramento sera utilizado
* Parametros: quadro, tipoEnquadramento
* Retorno: int []
*************************************************************** */

public static int[] CamadaEnlaceTransmissoraEnquadramento(int[] quadro, int tipoEnquadramento) {
  EnquadramentoTransmissor objTransmissor = new EnquadramentoTransmissor();
  int[] quadroEnquadrado = null;
  switch(tipoEnquadramento){
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

  return quadroEnquadrado;
}


  /* ***************************************************************
* Metodo: CamadaDeEnlaceDadosReceptora
* Funcao: camada de enlace de dados receptora
* Parametros: quadro, tipoEnquadro
* Retorno: int []
*************************************************************** */
  public static int [] CamadaDeEnlaceDadosReceptora(int[] quadro, int tipoEnquadramento) {
    
    int [] retornoEnquadroReceptor = CamadaEnlaceDadosReceptoraEnquadramento(quadro, tipoEnquadramento);    
    // CamadaDeEnlaceTransmissoraEnquadramento(quadro);
    // CamadaDeEnlaceTransmissoraControleDeErro(quadro);
    // CamadaDeEnlaceTransmissoraControleDeFluxo(quadro);
    return retornoEnquadroReceptor;
  }

  /* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraEnquadramento
* Funcao: camada de enlace de dados receptora enquadramento
* Parametros: quadro, tipoEnquadro
* Retorno: int []
*************************************************************** */
  public static int [] CamadaEnlaceDadosReceptoraEnquadramento(int [] quadro, int tipoEnquadramento) {
    EnquadramentoReceptor objEnlace = new EnquadramentoReceptor();
    int [] quadroDesenquadrado = null;
    switch (tipoEnquadramento) {
      case 1:
        quadroDesenquadrado = objEnlace.CamadaEnlaceDadosReceptoraEnquadramentoContagemDeCaracteres(quadro);
        break;
      case 2:
        quadroDesenquadrado = objEnlace.CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBytes(quadro);
        break;
      case 3:
        quadroDesenquadrado = objEnlace.CamadaEnlaceDadosReceptoraEnquadramentoInsercaoDeBits(quadro);
        break;
      case 4:
        Rede.violacaoCamadaFisica = false;
        quadroDesenquadrado = quadro;
        break;
      default:

        break;
    }
    return quadroDesenquadrado;
  }
}
