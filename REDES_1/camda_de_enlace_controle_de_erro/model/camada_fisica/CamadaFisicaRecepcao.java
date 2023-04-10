
package model.camada_fisica;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 10/01/2022
* Ultima alteracao.: 05/02/2022
* Nome.............: CamadaFisicaRecepcao.java
* Funcao...........: Parte da camada fisica de recepcao
************************************************************** */

public class CamadaFisicaRecepcao {

/* ***************************************************************
* Metodo: camadaFisicaReceptoraCodificacaoBinaria
* Funcao: recebe vetor de bits e realiza decodificacao binaria
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */
	
  public int [] camadaFisicaReceptoraCodificacaoBinaria (int [] quadro) {
    int [] bitsDecodificados = quadro;    
    return bitsDecodificados;
  }

/* ***************************************************************
* Metodo: camadaFisicaReceptoraDecodificacaoManchester
* Funcao: recebe vetor de bits e realiza decodificacao manchester
* Parametros: vetor de inteiros
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] camadaFisicaReceptoraDecodificacaoManchester(int [] quadro){
    int [] bitsDecodificados = new int [quadro.length/2];
    for(int i = 0, j = 0; i < quadro.length; i+=2, j++){
      if(quadro[i] == 1 && quadro[i + 1] == 0){bitsDecodificados[j] = 1;
      }
      if(quadro[i] == 0 && quadro[i + 1] == 1){bitsDecodificados[j] = 0;
      }
    }
    return bitsDecodificados;
  }

/* ***************************************************************
* Metodo: CamadaFisicaReceptoraDecodificacaoManchesterDiferencial
* Funcao: realiza decodificacao manchester diferencial
* Parametros: vetor de inteiros 
* Retorno: vetor de inteiros convertido
*************************************************************** */

	public int [] CamadaFisicaReceptoraDecodificacaoManchesterDiferencial (int [] quadro){
    int [] bitsDecodificados = new int[quadro.length/2];
    for(int i = 0, j = 0; i < quadro.length; i+=2, j++){
      if(i==0){
        if(quadro[i] == 1 && quadro[i + 1] ==0){bitsDecodificados[j] = 1;
        }
        if(quadro[i] == 0 && quadro[i + 1] ==1){bitsDecodificados[j] = 0;
        }
      }else{
        if(quadro[i-1] == quadro[i]){
          bitsDecodificados[j] = bitsDecodificados[j - 1];
        }else{
          if(bitsDecodificados[j - 1] == 1){
            bitsDecodificados[j] = 0;
          }else{
            bitsDecodificados[j] = 1;
          }
        }
      }
    }
    return bitsDecodificados;
  }

}	
