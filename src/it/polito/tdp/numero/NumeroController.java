package it.polito.tdp.numero;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.numero.model.NumeroModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class NumeroController {
	
	private NumeroModel model;
	
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
    	// Gestione Interfaccia
    	boxControlloPartita.setDisable(true); // DISABILITO disable true --> sei disabilitato
    	boxControlloTentativi.setDisable(false); // ABILITO disable false --> non sei disabilitato
    	txtMessaggi.clear();
    	txtTentativo.clear();
//    	txtTentativiRimasti.setText(Integer.toString(0));
    	
    	// Comunica al modello di iniziare una nuova partita
    	model.newGame();
    }

    @FXML
    void handleProvaTentativo(ActionEvent event) {
    	
    	// Legggi il tentativo
    	String stringaTentativo = txtTentativo.getText();
    	
    	// Controlla se è valido ( il tipo di dato )
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
    	
    	if(!model.tentativoValido(tentativo)) {
    		txtMessaggi.appendText("Range non valido\n");
    	}
    	
    	int risultato = model.tentativo(tentativo);
    	
    	if(risultato == 0){
    		txtMessaggi.appendText("Complimenti, hai indovinato in "+model.getTentativiFatti()+" tentativi\n");
    		
    		boxControlloPartita.setDisable(false);
    		boxControlloTentativi.setDisable(true);
    	} else if(risultato<0) {
    		txtMessaggi.appendText("Tentativo troppo BASSO\n");
    	} else {
    		txtMessaggi.appendText("Tentativo troppo ALTO\n");
    	}
    	
    	// Aggiornare interfaccia numero tenntativi
//    	txtTentativiRimasti.setText(Integer.toString(model.getTentativiFatti()));
    	
    	if(!model.isInGioco()) {
    		// La partita è finita
    		
    		if(risultato!=0) {
    			txtMessaggi.appendText("Hai Perso!");
    			txtMessaggi.appendText("Il numero segreto era: "+ model.getSegreto());

        		boxControlloPartita.setDisable(false);
        		boxControlloTentativi.setDisable(true);
    		}
    			
    	}

    }

    @FXML
    void initialize() {
        assert boxControlloPartita != null : "fx:id=\"boxControlloPartita\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativiRimasti != null : "fx:id=\"txtTentativiRimasti\" was not injected: check your FXML file 'Numero.fxml'.";
        assert boxControlloTentativi != null : "fx:id=\"boxControlloTentativi\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtTentativo != null : "fx:id=\"txtTentativo\" was not injected: check your FXML file 'Numero.fxml'.";
        assert txtMessaggi != null : "fx:id=\"txtMessaggi\" was not injected: check your FXML file 'Numero.fxml'.";
 
    }
	
	public void setModel(NumeroModel model) {
		this.model = model;
		
		// ogni volta che cambia tentativi fatti cambia anche
		// il valore del testo bind fa da collegamento
		// con questa riga di codice posso
		// eliminare tutte le setText di txtTentativiRimasti
		txtTentativiRimasti.textProperty().bind(Bindings.convert(model.tentativiFattiProperty()));
		// bindings.convert trasforma in eventuale stringa
		// (se ciò che metto all'interno è già stringa
		// rimane tale, altrimenti converte in stringa)
	}
}
