package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private final int NMAX = 100;
	private final int TMAX = 8;
	
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane boxControlloPartita;

    @FXML
    private TextField txtTentativiRimasti;
    // numero tentativi ancora da provare

    @FXML
    private HBox boxControlloTentativi;

    @FXML
    private TextField txtTentativo;
    // tentativo inserito dall'utente

    @FXML
    private TextArea txtMessaggi;

    @FXML
    void handleNuovaPartita(ActionEvent event) {
    	// Gestisce l'inizio di una nuova partita
    	
    	
    	// Logica del gioco
    	this.segreto = (int)(Math.random()*NMAX)+1;
    	this.tentativiFatti = 0;
    	this.inGioco = true;

    	// Gestione Interfaccia
    	boxControlloPartita.setDisable(true); // DISABILITO disable true --> sei disabilitato
    	boxControlloTentativi.setDisable(false); // ABILITO disable false --> non sei disabilitato
    	txtMessaggi.clear();
    	txtTentativiRimasti.setText(Integer.toString(this.TMAX));
    }

    @FXML
    void handleProvaTentativo(ActionEvent event) {
    	
    	// Legggi il tentativo
    	String stringaTentativo = txtTentativo.getText();
    	
    	// Controlla se è valido
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(stringaTentativo);
    	}catch(NumberFormatException e) {
    		// Stringa inserita non è un numero valido
    		
    		txtMessaggi.appendText("Non è un numero valido\n");
    		return;
    	}
    	if(tentativo<0||tentativo>100) {
    		txtMessaggi.appendText("Non è un numero valido\n");
    		return;
    	}    		
    	
    	tentativiFatti++;

    	// Controlla se ha indovinato
    	// --> fine partita
    	if(tentativo==segreto) {
    		txtMessaggi.appendText("Complimenti, hai indovinato in "+tentativiFatti+" tentativi\n");
    		
    		boxControlloPartita.setDisable(false);
    		boxControlloTentativi.setDisable(true);
    		this.inGioco = false;
    		return;
    	}
    	
    	// Verifica se ha esaurito i tentaivi
    	// --> fine partita
    	
    	if(tentativiFatti==TMAX) {
    		txtMessaggi.appendText("Hai PERSO, il numero segreto era: "+segreto+"\n");
    		
    		boxControlloPartita.setDisable(false);
    		boxControlloTentativi.setDisable(true);
    		this.inGioco = false;
    		return;
    	}
    	
    	// Informa se era troppo altro/troppo basso
    	// --> stampa messaggio
    	if(tentativo<segreto) {
    		txtMessaggi.appendText("Tentativo troppo BASSO\n");
    	} else {
    		txtMessaggi.appendText("Tentativo troppo ALTO\n");
    	}
    	
    	// Aggiornare interfaccia numero tenntativi
    	txtTentativiRimasti.setText(Integer.toString(TMAX-tentativiFatti));

    }

    @FXML
    void initialize() {
        assert boxControlloPartita != null : "fx:id=\"boxControlloPartita\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativiRimasti != null : "fx:id=\"txtTentativiRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
        assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";

    }
}
