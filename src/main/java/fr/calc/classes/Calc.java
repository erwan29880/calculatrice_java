package fr.calc.classes;

import java.util.ArrayList;

/**
 * 
 * 
 * @author erwan tanguy
 * @version 1.0
 * @date 01 septembre 2023
 */
public class Calc {

	private String texte;
	
	public Calc(String texte) {
		this.texte = texte;
	}
	
	/**
	 * @param arr arraylist à afficher pour contrôle
	 */
	public void getMyArray(ArrayList<String> arr) {
		for (int i = 0; i < arr.size(); i++) {
			System.out.print(arr.get(i));
		}
		System.out.println();
	}
	
	/**
	 * @param le caractère à tester
	 * @param la position dans la chaîne de caractère
	 * @return true si le caractère n'est pas un opérateur
	 */
	private Boolean testOperateur(char test, int pos) {
		if (test == '+' || test == '-' || test == 'x' || test == '/') {
			return false;
		}
		return true;
	}
	
	/**
	 * Séparation chiffres/nombres et opérateurs
	 * @return une liste avec nombres et opérateurs séparés
	 */
	private ArrayList<String> textToList() {
		ArrayList<String> myArray = new ArrayList<String>();
		String prov = "";  // chaîne pour concaténer les chiffres en nombres
		
		// parcourir la chaîne de caractère
		for (int i = 0; i < this.texte.length(); i++) {
			
			// si ce n'est pas un opérateur
			if(testOperateur(this.texte.charAt(i), i)) {
				prov += this.texte.charAt(i);
				
				// gestion de la fin de chaîne
				if (i == this.texte.length()-1) myArray.add(prov);
			}
			else {
				myArray.add(prov);
				myArray.add(Character.toString(this.texte.charAt(i)));
				prov = "";
			}
		}
		return myArray;
	}
	
	/**
	 * sépare la chaîne selon la priorité des opérateurs puis calcule par sous-cahînes
	 * @param arr la liste avec nombres et opérateurs séparés
	 * @return le résultat du calcul sous forme de chaîne de caractère
	 */
	private String calcul(ArrayList<String> arr) {
		int inc = 0;
		int len = arr.size();
		
		// priorité au calcul des multiplications
		// parcourir la liste tant qu'il reste un signe x
		while (inc < len) {
			if (arr.get(inc).charAt(0) == 'x') {
				// récupérer l'opérateur, le nombre avant et le nombre après
				String[] prov = new String[]{arr.get(inc-1), arr.get(inc), arr.get(inc+1)};
				
				// supprimer l'opérateur et ajouter le résultat du calcul
				arr.remove(inc);
				arr.add(inc, calculate(prov));
				
				// supprimer les nombres
				arr.remove(inc-1);
				arr.remove(inc);
				
				// mettre à jour la taille de la liste et ré-initialiser l'incrément
				len = arr.size();
				inc = 0;
			}
			else inc++;
		}
	
		// calcul des fractions
		inc = 0;
		len = arr.size();
		while (inc < len) {
			if (arr.get(inc).charAt(0) == '/') {
				String[] prov = new String[]{arr.get(inc-1), arr.get(inc), arr.get(inc+1)};
				arr.remove(inc);
				arr.add(inc, calculate(prov));
				arr.remove(inc-1);
				arr.remove(inc);
				len = arr.size();
				inc = 0;
			}
			else inc++;
		}
		
		// additions et soustractions
		inc = 0;
		len = arr.size();
		while (inc < len) {
			if (arr.get(inc).charAt(0) == '+' || arr.get(inc).charAt(0) == '-') {
				String[] prov = new String[]{arr.get(inc-1), arr.get(inc), arr.get(inc+1)};
				arr.remove(inc);
				arr.add(inc, calculate(prov));
				arr.remove(inc-1);
				arr.remove(inc);
				len = arr.size();
				inc = 0;
			}
			else inc++;
		}
		return arr.get(0);
	}
	
	/**
	 * calcule avec un nombre, un opérateur, un nombre sous forme de liste
	 * @param arg
	 * @return  le résultat du calcul sous forme de chaîne de caractère
	 */
	private String calculate(String[] arg) {
		String el1 = arg[0];       // nombre
		String operateur = arg[1]; // opérateur
		String el2 = arg[2];	   // nombre
		double res = 0.0, a = 0.0, b = 0.0;
		
		// caster string en décimal
		try {
			a = Double.parseDouble(el1);
			b = Double.parseDouble(el2);
		}
		catch(Exception e) {
			System.out.println("calculate -> chaine non convertible en float");
		}
		
		// récupérer l'opérateur
		switch(operateur) {
			case "x":
				res = a*b;
				break;
			case "/":
				res = a/b;
				break;
			case "+":
				res = a+b;
				break;
			case "-":
				res = a-b;
				break;
			default:
				System.out.println("calculate -> erreur d'opérateur switch");
				break;
		}
		return String.valueOf(res);
	}
	
	/**
	 * méthode principale
	 * @return le calcul sous forme de chaîne de caractère
	 */
	public String go() {
		ArrayList<String> myArray = textToList();
		return calcul(myArray);
	}	
}