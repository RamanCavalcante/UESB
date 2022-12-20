package model.camada_de_enlace.controle_de_erro;

/* ***************************************************************
* Autor............: Raman Melo Cavalcante
* Matricula........: 201820754
* Inicio...........: 28/03/2022
* Ultima alteracao.: 14/04/2022
* Nome.............: ControleDeErroReceptor.java
* Funcao...........: Parte da camada de enlace controle de erro receptor
************************************************************** */

public class ControleDeErroReceptor {
  
  
/* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadePar
* Funcao: realiza o controle de erro Bit de paridade par
* Parametros: quadro
* Retorno: int []
*************************************************************** */

  public static int [] CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadePar (int [] quadro){
    int contBits = 0;
    int [] quadroFinal = new int[quadro.length - 1];
    for(int i = 0; i <quadro.length; i++){
      if(quadro[i] == 1)contBits++;
      if(i < quadroFinal.length)
        quadroFinal[i] = quadro[i];
    }
  
    if(contBits%2 == 0)
      System.out.println("quadro sem erro");
    else
      System.out.println("quadro danificado");
    
    
    return quadroFinal;
  }//fim do metodo CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadePar

/* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadeImpar
* Funcao: realiza o controle de erro Bit de paridade impar
* Parametros: quadro
* Retorno: int []
*************************************************************** */

  public static int [] CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadeImpar (int [] quadro) {
    //implementacao do algoritmo para VERIFICAR SE HOUVE ERRO
    int contBits = 0;
    int [] quadroFinal = new int[quadro.length - 1];
    for(int i = 0; i <quadro.length; i++){
      if(quadro[i] == 1)contBits++;
      if(i < quadroFinal.length)
        quadroFinal[i] = quadro[i];
    }
    if(contBits%2 == 0)
      System.out.println("quadro danificado");
    else
      System.out.println("quadro sem erro");
    
    return quadroFinal;
   }//fim do metodo CamadaEnlaceDadosReceptoraControleDeErroBitDeParidadeImpar
  
/* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraControleDeErroCRC
* Funcao: realiza o controle de erro CRC polinomio 32
* Parametros: quadro
* Retorno: int []
*************************************************************** */ 

  public static int [] CamadaEnlaceDadosReceptoraControleDeErroCRC (int quadro []) {
    //implementacao do algoritmo para VERIFICAR SE HOUVE ERRO
    //usar polinomio CRC-32(IEEE 802)

    int polinomio = 32;
    int [] gerador =      {1,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1,0,0,0,1,1,1,0,1,1,0,1,1,0,1,1,1};
    int [] geradorMenor = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    boolean ok = true;
    int [] resto = getSubArray(quadro, 0, polinomio);

    for(int i = polinomio; i < quadro.length; i++){
      resto[resto.length-1] = quadro[i];
      if(resto[0] == 0){
        resto = xor(resto, geradorMenor);
      }else{
        resto = xor(resto, gerador);
      }
    }
    
    for(int i : resto){
      if(i == 1)
        ok = false;        
    }
    if(!ok)
      System.out.println("quadro danificado");
    else
      System.out.println("quadro sem erro");


    int [] temporario = new int[quadro.length-(polinomio-1)];
      System.arraycopy(quadro, 0, temporario, 0, quadro.length-(polinomio-1));
  
    int [] quadroFinal = new int [temporario.length-32];
    for(int i = 0; i < quadroFinal.length; i++){
      quadroFinal[i] = temporario[i];
    }      
      return quadroFinal;
   }//fim do metodo CamadaEnlaceDadosReceptoraControleDeErroCRC

/* ***************************************************************
* Metodo: CamadaEnlaceDadosReceptoraControleDeErroCodigoDeHamming
* Funcao: realiza o controle de erro codigo de hamming
* Parametros: quadro
* Retorno: int []
*************************************************************** */ 
   
  public static int[] CamadaEnlaceDadosReceptoraControleDeErroCodigoDeHamming (int quadro []) {
    //implementacao do algoritmo para VERIFICAR SE HOUVE ERRO
    boolean pass = true;
    int [] newQuadro = new int[quadro.length - tamanhoBitsDeControle(quadro)];

    for(int i = 0, j = 0; i < quadro.length; i++){
      if(potenciaDeDois(i+1)){
        if(quadro[i] !=  somaParidade(i, quadro, false)){
          System.out.println("quadro danifcado");
          pass = false;
        }
      }else{
        newQuadro[j] = quadro[i];
        j++;
      }
    }
    if(pass)
      System.out.println("quadro sem erro");
  
      return newQuadro;
  
    }
  

 


/**
 * ------------------- |||
 * funcoes auxiliares  |||
 * ------------------- |||
 *                     |||
 *                     VVV
 *                      V
 * /

 
  
/* ***************************************************************
* Metodo: xor
* Funcao: realiza xor com o vetor recebido
* Parametros: inteiro
* Retorno: int []
*************************************************************** */ 
   
  private static int [] xor(int []resto, int[] gerador){
    int [] retornoXor = new int[resto.length];
    for(int i = 1, j = 0; i < resto.length; i++, j++){
      if(resto[i] == gerador[i])
        retornoXor[j] = 0;
      else
        retornoXor[j] = 1;
    }
    return retornoXor;
  } 
 
/* ***************************************************************
* Metodo: getSubarray
* Funcao: retorna um array contido dentro do array
* Parametros: inteiro
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
* Funcao: verifica se o inteiro recebido é potencia de dois
* Parametros: inteiro
* Retorno: int []
*************************************************************** */

  public static boolean potenciaDeDois(int num){
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
* Funcao: realiza soma da paridade
* Parametros: inteiro
* Retorno: int
*************************************************************** */

  public static int somaParidade(int posicao, int [] quadro, boolean pass){
    int contBits = 0;
    int paridade = 0;
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

  /**
   * metodo pra verificar se quadro tem erro e retorna um boolean
   * paridade par
   * @param quadro
   * @return 
   */
  public static boolean quadroSemErroParidadePar(int [] quadro){
    int contBits = 0;
    int [] quadroFinal = new int[quadro.length - 1];
    for(int i = 0; i <quadro.length; i++){
      if(quadro[i] == 1) contBits++;
      if(i < quadroFinal.length)
        quadroFinal[i] = quadro[i];
    }
    if(contBits%2 == 0)
      return true;
    else
      return false;
  }


  /**
   * metodo pra verificar se quadro tem erro e retorna um boolean
   * paridade impar
   * @param quadro
   * @return
   */
  public static boolean quadroSemErroParidadeImpar(int [] quadro){
    int contBits = 0;
    int [] quadroFinal = new int[quadro.length - 1];
    for(int i = 0; i <quadro.length; i++){
      if(quadro[i] == 1) contBits++;
      if(i < quadroFinal.length)
        quadroFinal[i] = quadro[i];
    }
    if(contBits%2 == 0)
      return false;
    else
      return true;
  }


  /**
   * metodo pra verificar se quadro tem erro e retorna um boolean
   * crc
   * @param quadro
   * @return
   */
  public static boolean quadroSemErroCrc(int [] quadro){
    int polinomio = 32;
    int [] gerador =      {1,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1,0,0,0,1,1,1,0,1,1,0,1,1,0,1,1,1};
    int [] geradorMenor = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    boolean ok = true;
    int [] resto = getSubArray(quadro, 0, polinomio);

    for(int i = polinomio; i < quadro.length; i++){
      resto[resto.length-1] = quadro[i];
      if(resto[0] == 0){
        resto = xor(resto, geradorMenor);
      }else{
        resto = xor(resto, gerador);
      }
    }
    
    for(int i : resto){
      if(i == 1)
        ok = false;        
    }
    if(!ok)
      return false;
    else
      return true;
  } 

  /**
   *  metodo pra verificar se quadro tem erro e retorna um boolean
   * hamming
   * @param quadro
   * @return
   */
  public static boolean quadroSemErroHamming(int [] quadro){
    boolean pass = true;
    for(int i = 0, j = 0; i < quadro.length; i++){
      if(potenciaDeDois(i+1)){
        if(quadro[i] !=  somaParidade(i, quadro, false)){
          System.out.println("quadro danifcado");
          pass = false;
        }
      }else{
      }
    }

    if(pass){
      
      return true;
    }else{
      return false;
    }
      
  }

  /**
   * metodo que conta quantos bits de controle tem no array
   * @param quadro
   * @return
   */

  public static int tamanhoBitsDeControle(int [] quadro){
    int count  = 0;
    for(int i = 0; i < quadro.length; i ++){
      if(potenciaDeDois(i+1)){
        count++;
      }
    }
    return count;
  }

}

