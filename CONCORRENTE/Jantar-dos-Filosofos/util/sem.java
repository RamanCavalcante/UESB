package util;
import java.util.concurrent.Semaphore;

/****************************************************************
 * Autor: Raman Melo Cavalcante
 * Matricula: 201820754
 * Inicio: 18/09/2021
 * Ultima alteracao: 28/09/2021
 * Nome: sem.java
 * Funcao: classe onde o semaforo 
 * ************************************************************** */

 public class sem {

    public static Semaphore garfo00 = new Semaphore(1);
    public static Semaphore garfo01 = new Semaphore(1);
    public static Semaphore garfo02 = new Semaphore(1);
    public static Semaphore garfo03 = new Semaphore(1);
    public static Semaphore garfo04 = new Semaphore(1);
}
