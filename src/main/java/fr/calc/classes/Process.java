package fr.calc.classes;

/**
 * processing de la chaîne de caractère
 * principalement pour règles de priorités pour les parenthèses
 */
public class Process {

	private String texte;
	private int len;
	
	/**
	 * @param la chaîne de caractère récupéré de l'affichage
	 */
	public Process(String texte) {
		this.texte = texte;
		this.len = this.texte.length();
	}
		
	/**
	 * fonction utilitaire pour fonctions de vérifications initiales
	 * @param ch parenthèses ou opérateurs
	 * @return le compte de ch dans la chaîne de caractère
	 */
	private int checkChar(char ch) {
		int compte = 0;
		for (int i=0; i < this.len ; i++) {
			if (this.texte.charAt(i) == ch) {
				compte++;
			}
		}
		return compte;
	}
	
	/**
	 * vérification initiale : même nombre de parenthèses ouvrantes et fermantes
	 * @return true si le nombre de parenthèses ouvrantes 
	 * est le même que le nombre de parenthèses fermantes
	 */
	private Boolean checkParenthesis() {
		int po = checkChar('('), pf = checkChar(')');
		return po == pf;
	}
		
	/**
	 * vérification initiale : il y a au moins un opérateur
	 * @return true si il n'y a pas d'opérateur
	 */
	private Boolean checkOperateurs() {
		int check = checkChar('+') + checkChar('-') + checkChar('x') + checkChar('/'); 
		return check == 0;
	}
	
	
	/**
	 * comptage des parenthèses ouvrantes pour éviter une boucle infinie
	 * @param tex la chaîne à tester
	 * @return le nombre d'occurences de parenthèses ouvrantes
	 */
	private int compteParentheses(String tex) {
		int c = 0;
		for (int i=0; i<tex.length();i++) {
			if (tex.charAt(i) == '(') c++;
		}
		return c;
	}
	
	/**
	 * calcule selon la priorité des parenthèses
	 * envoie des sous-chaînes à la classe Calc
	 * @return le résultat sous forme d'une chaîne
	 */
	private String processAndCalculate() {
		// nombre de parenthèses ouvrantes
		int cpte = compteParentheses(this.texte);
		// po pf indices Parenthèse Ouvrante ou Parenthèse Fermante
		int po = 0, pf = 0, inc = 0;
		
		// prioriser les calculs entre parenthèses
		if (cpte > 0) {
			while (inc < cpte) {
				for (int i = 0; i < this.texte.length(); i++) {
					
					// prioriser les parenthèses intérieures à d'éventuelles autres parenthèses
					if (this.texte.charAt(i) == '(') po = i;
					
					if (this.texte.charAt(i) == ')') {
						pf = i+1;
						
						// découpage
						String subs = this.texte.substring(po, pf);
						
						// calcul
						Calc calc = new Calc(subs.substring(0+1, subs.length()-1));
						String res = calc.go();

						// remplacement par la valeur calculée
						this.texte = this.texte.replace(subs, res);  // test, éviter boucle infinie
						break;
					}
				}
				inc++;
			}	
		}	
		// calcul de l'expression finale sans parenthèse
		Calc calcFinal = new Calc(this.texte);
		String resFinal = calcFinal.go();
		return resFinal;
	}
	
	/**
	 * méthode principale
	 * @return le résultat sous forme de chaîne de caractère
	 */
	public String go() {
		// vérifications initiales
		if (!checkParenthesis() || checkOperateurs()) return "error"; 
		// découpage de chaîne et calcul
		try {
			return processAndCalculate();
		}
		catch(Exception e) {
			return "error";
		}
    } 	
}