package fr.calc.classes;

import javax.swing.JButton;

/**
 * Classe des boutons, personnalisables
 */
public class Boutons extends JButton{

	private static final long serialVersionUID = 1L;
	
	public Boutons(String texte) {
		this.setText(texte);
	}
}