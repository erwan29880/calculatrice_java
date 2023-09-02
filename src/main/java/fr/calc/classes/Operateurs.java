package fr.calc.classes;

import java.awt.GridLayout;

/**
 * Classe pour les GridLayouts, personnalisables
 */
public class Operateurs extends GridLayout {

	private static final long serialVersionUID = 1L;

	public Operateurs(int rows, int columns, byte hPadding,byte vPadding) {
		this.setRows(rows);
		this.setColumns(columns);
		this.setHgap(hPadding);
		this.setVgap(vPadding);
	}
}