
package model.camada_fisica;

import model.Aplicacao;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2022
* Nome.............: CamadaFisicaTransamissora.java
* Funcao...........: Parte da camada fisica de transmissao
************************************************************** */

public class CamadaFisicaTransmissao {

/* ***************************************************************
* Metodo: camadaFisicaTransmissoraCodificacaoBinaria
* Funcao: recebe vetor de bits e realiza codificacao binaria
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaTransmissoraCodificacaoBinaria(int [] quadro){
    int [] bitsCodificados = quadro;
    return bitsCodificados;    
	}

/* ***************************************************************
* Metodo: camadaFisicaTransmissoraCodificacaoManchester
* Funcao: recebe vetor de bits e realiza decodificacao manchester
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaTransmissoraCodificacaoManchester(int [] quadro){
		int [] bitsCodificados = new int[quadro.length*2];
    for(int i = 0, j = 0; i < quadro.length; i++, j+=2){
      if(quadro[i]==1){
        bitsCodificados[j] = 1;
        bitsCodificados[j+1] = 0;
      }else{
        bitsCodificados[j] = 0;
        bitsCodificados[j+1] = 1;
      }
    }
    return bitsCodificados;
	}

/* ***************************************************************
* Metodo: camadaFisicaTransmissoraCodificacaoManchesterDiferencial
* Funcao: recebe vetor de bits e realiza decodificacao manchester diferencial
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaTransmissoraCodificacaoManchesterDiferencial(int [] quadro){
    int [] bitsCodificados = new int[quadro.length*2];
    for(int i = 0, j = 0; i < quadro.length; i++, j+=2){
      if(i == 0){
        if(quadro[i] == 1){
          bitsCodificados[j] = 1;
          bitsCodificados[j + 1] = 0;
        }else{
          bitsCodificados[j] = 0;
          bitsCodificados[j + 1] = 1;
        }
      }else{
        if(quadro[i - 1] == quadro[i]){
          bitsCodificados[j] = bitsCodificados[j - 1];
          bitsCodificados[j + 1] = bitsCodificados[j - 2];
        }else{
          bitsCodificados[j] = bitsCodificados[j - 2];
          bitsCodificados[j + 1] = bitsCodificados[j -1];
        }
      }
    }
    return bitsCodificados;
  }
}
