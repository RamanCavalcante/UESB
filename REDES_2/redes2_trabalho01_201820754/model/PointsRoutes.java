package model;
/****************************************************************
 * Autor:...........: Raman Melo Cavalcante
 * Matricula........: 201820754
 * Inicio...........: 10/02/2023
 * Ultima alteracao.: 19/02/2023
 * Nome: ...........: PointsRoutes
 * Funcao...........: guarda todos os pontos do pacotes
 * ***************************************************************/
public class PointsRoutes {

  private Router current, origin, destiny, previous;

  /**
   *
   * @param origin
   * @param destiny
   */
  public PointsRoutes(Router origin,  Router destiny){
    this.origin = origin;
    this.current = origin;//default
    this.destiny = destiny;
  }

  public void setPreviousRoute(Router route){
    this.previous = route;
  }

  public Router getPreviousRoute(){
    return previous;
  }

  public void setCurrentRoute(Router route){
    this.current = route;
  }

  public void setDestiny(Router router){
    this.destiny = router;
  }

  public Router getDestiny(){
    return this.destiny;
  }
  //default
  public void setCurrentRoute(){
    this.current = this.origin;
  }

  public Router getCurrentRoute(){
    return this.current;
  }

  public Router getOriginRoute(){
    return this.origin;
  }
}
