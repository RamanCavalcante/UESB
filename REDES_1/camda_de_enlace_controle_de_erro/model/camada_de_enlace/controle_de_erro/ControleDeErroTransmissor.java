package model.camada_de_enlace.controle_de_erro;

import model.camada_fisica.Converter;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 28/03/2022
* Ultima alteracao.: 14/04/2022
* Nome.............: ControleDeErroTransmissor.java
* Funcao...........: realiza protocolos de erro transmissor
************************************************************** */

public class ControleDeErroTransmissor {

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraControleDeErroBitParidadePar
* Funcao: realiza o controle de erro Bit de paridade par transmissor
* Parametros: quadro
* Retorno: int []
*************************************************************** */

  public int[] CamadaEnlaceDadosTransmissoraControleDeErroBitParidadePar(int[] quadro) {
    // implementacao do algoritmo
    int contBits = 0, l = 0;
    int[] quadroFinal = new int[quadro.length + 1];
    for (int i : quadro) {
      if (i == 1)
        contBits++;
      quadroFinal[l] = quadro[l];
      l++;
    }
    if (contBits % 2 == 0) {
      quadroFinal[quadroFinal.length - 1] = 0;
    } else {
      quadroFinal[quadroFinal.length - 1] = 1;
    }
    return quadroFinal;
  }// fim do metodo CamadaEnlaceDadosTransmissoraControledeErroBitParidadePar

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraControleDeErroBitParidadeImpar
* Funcao: realiza o controle de erro Bit de paridade impar transmissor
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  
  public int[] CamadaEnlaceDadosTransmissoraControleDeErroBitParidadeImpar(int quadro[]) {
    int contBits = 0, l = 0;
    int[] quadroFinal = new int[quadro.length + 1];
    for (int i : quadro) {
      if (i == 1)
        contBits++;
      quadroFinal[l] = quadro[l];
      l++;
    }
    if (contBits % 2 == 0) {
      quadroFinal[quadroFinal.length - 1] = 1;
    } else {
      quadroFinal[quadroFinal.length - 1] = 0;
    }
    return quadroFinal;
  }// fim do metodo CamadaEnlaceDadosTransmissoraControledeErroBitParidadeImpar

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraControleDeErroCRC
* Funcao: realiza o controle de erro CRC 32 transmissor
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  
  public int[] CamadaEnlaceDadosTransmissoraControleDeErroCRC(int quadroPuro[]) {
    // implementacao do algoritmo
    // usar polinomio CRC-32(IEEE 802)

    int polinomio = 32;
    int[] gerador =      { 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 };
    int[] geradorMenor = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    int[] quadro = new int[quadroPuro.length + 32];

    for (int i = 0; i < quadroPuro.length; i++) {
      quadro[i] = quadroPuro[i];
    }

    int[] quadroBitsAdicionais = new int[quadro.length + polinomio - 1];
    quadroBitsAdicionais = alocabitsAdicionais(quadro, quadroBitsAdicionais);
    int[] resto = getSubArray(quadroBitsAdicionais, 0, polinomio);

    for (int i = polinomio; i < quadroBitsAdicionais.length; i++) {
      resto[resto.length - 1] = quadroBitsAdicionais[i];
      if (resto[0] == 0) {
        resto = xor(resto, geradorMenor);
      } else {
        resto = xor(resto, gerador);
      }
    }

    int[] quadroFinal = new int[quadro.length + resto.length - 1];
    System.arraycopy(quadro, 0, quadroFinal, 0, quadro.length);
    System.arraycopy(resto, 0, quadroFinal, quadro.length, resto.length - 1);

    return quadroFinal;
  }// fim do metodo CamadaEnlaceDadosTransmissoraControledeErroCRC

/* ***************************************************************
* Metodo: CamadaEnlaceDadosTransmissoraControleDeErroCodigoDeHamming
* Funcao: realiza controle de erro codico de hamming do lado transmissor
* Parametros: quadro
* Retorno: int []
*************************************************************** */

  public int[] CamadaEnlaceDadosTransmissoraControleDeErroCodigoDeHamming(int quadro[]) {
    boolean find = false;
    int p = 1, d;
    
    double count;
    while(!find){
      count = Math.pow(2, p);
      d = quadro.length + p + 1;
      if(count >= d)
        find = true;
      else
        p++;
    }

    int [] quadroBitsAdicionais = new int [quadro.length+p];
    
    quadroBitsAdicionais = alocabitsDeInformacao(quadroBitsAdicionais, quadro);

    for(int i = 0; i < quadroBitsAdicionais.length; i++){
      if(potenciaDeDois(i+1)){ 
        quadroBitsAdicionais[i] = somaParidade(i, quadroBitsAdicionais);
      }
    }
    
    return quadroBitsAdicionais;
  }

/* ***************************************************************
* Metodo: alocabitsAdicionais
* Funcao: funcao auxiliar do codigo de hamming alocando bits adicionais
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  
  public static int[] alocabitsAdicionais(int[] quadro, int[] quadroBitsAdicionais) {
    for (int i = 0; i < quadro.length; i++) {
      quadroBitsAdicionais[i] = quadro[i];
    }
    return quadroBitsAdicionais;
  }

/* ***************************************************************
* Metodo: xor
* Funcao: funcao auxiliar realiza operacao xor 
* Parametros: quadro
* Retorno: int []
*************************************************************** */
  
  private static int[] xor(int[] resto, int[] gerador) {
    int[] retornoXor = new int[resto.length];
    for (int i = 1, j = 0; i < resto.length; i++, j++) {
      if (resto[i] == gerador[i])
        retornoXor[j] = 0;
      else
        retornoXor[j] = 1;
    }
    return retornoXor;
  }

/* ***************************************************************
* Metodo: getSubarray
* Funcao: retorna um vetor contido dentro do vetor recebido
* Parametros: quadro
* Retorno: int []
*************************************************************** */

  private static int [] getSubArray(int vet[], int start, int end){

    int [] retorno = new int[(end-start)];
    int count = 0;
    System.out.println(retorno.length+" ======");
    for(int i = 0; i<vet.length; i++){
      if(i>=start && i< end){

        retorno[count] = vet[i];
        count++;
      }
    }
    return retorno;
  }
 
/* ***************************************************************
* Metodo: potenciaDeDois
* Funcao: verifica se inteiro recebido é potencia de dois
* Parametros: inteiro
* Retorno: boolean
*************************************************************** */

  public static boolean potenciaDeDois(int num
  ){
    while(2<=num){
      if(num%2!=0)
       break;
      num = num/2;
    }
    if(num == 1)
     return true;
   else
     return false; 
   }

/* ***************************************************************
* Metodo: somaParidade
* Funcao: realiza a soma de paridade
* Parametros: inteiro
* Retorno: int []
*************************************************************** */

   public static int somaParidade(int posicao, int [] quadro){
    int contBits = 0;
    int paridade = 0;
    boolean pass = false;
    for(int i = posicao; i < quadro.length; i = i + (posicao+1)){
      while(contBits < posicao+1){
        if(contBits != 0 | pass == true){
          if(i < quadro.length)
            paridade = paridade + quadro[i];
        }
        contBits ++;
        pass = true;
        i++;
      }
      contBits = 0;  
    }
    if(paridade % 2 == 0)
      return 0;
    else
      return 1;
  }  
  
/* ***************************************************************
* Metodo: alocabitsDeInformacao
* Funcao: aloca bits de informacoes no vetor
* Parametros: quadro
* Retorno: int []
*************************************************************** */

  public static int [] alocabitsDeInformacao(int [] quadroBitsAdicionais, int [] quadro){
    for(int i = 0, l = 0; i < quadroBitsAdicionais.length; i++){
      if(!potenciaDeDois(i+1)){
        quadroBitsAdicionais[i] = quadro[l];
        l++;
      }else{ quadroBitsAdicionais[i] = i+1;}

    }
    return quadroBitsAdicionais;
  }

}