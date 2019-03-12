package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;

public class NumeroModel {
	
	private final int NMAX = 100;
	private final int TMAX = 8;
	
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
	
	
	public NumeroModel() {
		this.inGioco = false;
	}
	
	/**
	 * Avvia nuova partita
	 */
	public void newGame() {
    	// Gestisce l'inizio di una nuova partita
		// Logica del gioco
    	this.segreto = (int)(Math.random()*NMAX)+1;
    	this.tentativiFatti = 0;
    	this.inGioco = true;
	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * @param t il tentativo
	 * @return 1 se il tentativo è troppo alto, -1 se è troppo basso, 0 se l'utente ha indovinato
	 */
	public int tentativo(int t) {
		
		// Controllare sse la partita è in corso		
		if(!inGioco) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		// Controllare se l'input è nel range corretto
		if(!tentativoValido(t)) {
//			throw new InvalidParameterException("Devi inserire un numero"
//					+ "tra 1 e "+ NMAX);
			throw new InvalidParameterException(String.format("Devi inserire un numero"
					+ "tra %d e %d", 1, NMAX));
		}
		
		// Gestione del tentativo
		this.tentativiFatti++;
		if(this.tentativiFatti==this.TMAX) {
			// La partita è finita perchè ho esaurito i tentativi
			this.inGioco = false;
		}
		
		if(t == this.segreto) {
			// ho indovinato
			this.inGioco = false;
			return 0;
		}
		else if(t > this.segreto) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	public boolean tentativoValido(int t) {
		if(t<1 || t>NMAX) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isInGioco() {
		return inGioco;
	}

	public void setInGioco(boolean inGioco) {
		this.inGioco = inGioco;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public void setSegreto(int segreto) {
		this.segreto = segreto;
	}

	public int getTMAX() {
		return TMAX;
	}

	public int getSegreto() {
		return segreto;
	}
	
	

}
