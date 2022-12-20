package util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
Autor....: Raman Melo Cavalcante
Matricula: 201820754
Inicio...: 13 de agosto de 2021
Alteracao: 18 de agosto de 2021
Nome.....: Controller.java
Funcao...: Classe onde é feita toda alteração de imagem, e impressão de texto
na tela
=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
public class Controller implements Initializable {

    @FXML
    private ImageView idPai;

    @FXML
    private ImageView idPrimeiroFilho;

    @FXML
    private ImageView idSegundoFillho;

    @FXML
    private ImageView idTerceiroFilho;

    @FXML
    private ImageView idPrimeiroNeto;

    @FXML
    private ImageView idSegundoNeto;

    @FXML
    private ImageView idBisNeto;

    @FXML
    private Button btnStart;

		private Threads threadPai;

		@FXML
    private Label  idLabelPai;

		Image baby1, baby2, kid1, kid2, kid3, kid4, boy1, boy2, boy3, boy4, 
		adult1, adult2, adult3, veio1, veio2, death; /*Aqui estou declarando variaveis do tipo image 
		com o caminho de cada imagem que vai ser setada
		*/
		ImageView parent;
		int idade, timeLife;
/* ***************************************************************
* Metodo: startThread
* Funcao: acao do botao btnStart dando start na thread pai
* Parametros: ...
* Retorno: void
*************************************************************** */
		@FXML
    void startThread(ActionEvent event) {
      Threads pai = new Threads(this);
			pai.start();//inicia a thread pai
			btnStart.setDisable(true);//desabilito o btnStart logo depois de ser apertado
    }

/* ***************************************************************
* Metodo: initialize
* Funcao: inicializa instaciando as imagens
* Parametros: ...
* Retorno: descricao do valor retornado
*************************************************************** */
		@Override
		public void initialize(URL location, ResourceBundle resources) {//aqui inicialize instanciando todas as imagens
			 baby1 = new Image("/img/baby1.gif");
			 baby2 = new Image("/img/baby2.gif");
			 kid1 = new Image("/img/kid/kid1.gif");
			 kid2 = new Image("/img/kid/kid2.jpg");
			 kid3 = new Image("/img/kid/kid3.gif");
			 kid4 = new Image("/img/kid/kid4.gif");
			 boy1 = new Image("/img/young/young1.gif");
			 boy2 = new Image("/img/young/young2.gif");
			 boy3 = new Image("/img/young/young3.gif");
			 boy4 = new Image("/img/young/young4.jpg");
			 adult1 = new Image("/img/veio/adulto.png");
			 adult2 = new Image("img/veio/veio.jpg");
			 adult3 = new Image("img/veio/veio2.jpg");
			 veio1 = new Image("img/veio/veio3.gif");
			 veio2 = new Image("img/veio/veio4.jpg");
			 death = new Image("/img/death/death.jpg");
		}
/* ***************************************************************
* Metodo: writeLabel
* Funcao: escreve numa label o ano atual 
* Parametros: parent do tipo label, ano do tipo int
* Retorno: void
*************************************************************** */
		public void writeLabel(Label parent, int ano){
			idLabelPai.setText("cls");//limpa o que foi impresso anteriormente
			idLabelPai.setText(String.valueOf(ano+" anos"));//imprime o que o ano na tela 
		}
/* ***************************************************************
* Metodo: changeImage
* Funcao: faz a troca de imagens no ImageView recebido como parameto
* Parametros: parent do tipo ImageView, idade do tipo int
* Retorno: void
*************************************************************** */
		public void changeImage( ImageView parent, int idade){//	
				if(idade == 0){
					parent.setVisible(true);
				}else if(idade == 3){
					parent.setImage(baby2);
				}else if(idade == 5){
					parent.setImage(kid2);
				}else if(idade == 10){
					parent.setImage(kid4);
				}else if(idade == 15){
					parent.setImage(kid3);
				}else if(idade == 18){
					parent.setImage(boy1);
				}else if(idade == 20){
					parent.setImage(boy2);
				}else if(idade == 23){
					parent.setImage(boy3);
				}else if(idade == 29){
					parent.setImage(boy4);
				}else if(idade == 35){
					parent.setImage(adult1);
				}else if(idade == 45){
					parent.setImage(adult2);
				}else if(idade == 65){
					parent.setImage(adult3);
				}else if(idade == 70){
					parent.setImage(veio1);
				}else if(idade == 80){
					parent.setImage(veio2);
				}else if(idade == -1){//quando este metodo recebe como parametro -1 troca a imagem para morto
					parent.setImage(death);
				}	
		}		
/*******************************************************************
***************** getters e setters imageView **********************
********************************************************************/
		public ImageView getIdPai() {
			return idPai;
		}
		public void setIdPai(ImageView idPai) {
			this.idPai = idPai;
		}
		public ImageView getIdPrimeiroFilho() {
			return idPrimeiroFilho;
		}
		public void setIdPrimeiroFilho(ImageView idPrimeiroFilho) {
			this.idPrimeiroFilho = idPrimeiroFilho;
		}
		public ImageView getIdSegundoFillho() {
			return idSegundoFillho;
		}
		public void setIdSegundoFillho(ImageView idSegundoFillho) {
			this.idSegundoFillho = idSegundoFillho;
		}
		public ImageView getIdTerceiroFilho() {
			return idTerceiroFilho;
		}
		public void setIdTerceiroFilho(ImageView idTerceiroFilho) {
			this.idTerceiroFilho = idTerceiroFilho;
		}
		public ImageView getIdPrimeiroNeto() {
			return idPrimeiroNeto;
		}
		public void setIdPrimeiroNeto(ImageView idPrimeiroNeto) {
			this.idPrimeiroNeto = idPrimeiroNeto;
		}
		public ImageView getIdSegundoNeto() {
			return idSegundoNeto;
		}
		public void setIdSegundoNeto(ImageView idSegundoNeto) {
			this.idSegundoNeto = idSegundoNeto;
		}
		public ImageView getIdBisNeto() {
			return idBisNeto;
		}
		public void setIdBisNeto(ImageView idBisNeto) {
			this.idBisNeto = idBisNeto;
		}		
/*******************************************************************
***************** getters e setters label***************************
********************************************************************/	
	public Label getIdLabelPai() {
		return idLabelPai;
	}

	public void setIdLabelPai(Label idLabelPai) {
		this.idLabelPai = idLabelPai;
	}
}
