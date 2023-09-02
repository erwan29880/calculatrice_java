package fr.calc.classes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Classe de l'affichage graphique de la calculatrice
 * 
 * La calculatrice gère les priorités de parenthèses et les priorités d'opérateurs.
 * 
 * Les boutons comprennent les chiffres de 0 à 9,
 * les quatre opérateurs de base,
 * les parenthèses,
 * un bouton entrée, un bouton clear, un bouton pour effacer le dernier caractère
 * 
 * Les boutons sont personnalisables dans la classe Boutons
 * Les GridLayouts sont personnalisables dans la classe Operateurs
 * 
 * La classe Process traîte la chaîne de caractère
 * La classe Calc appelée dans la classe Process gère le calcul
 * 
 * @author erwan tanguy
 * @version 1.0
 * @date 01 septembre 2023
 */
public class Graphisme extends JFrame implements ActionListener{
	
	
	private static final long serialVersionUID = 1L;
	
	// dimensions fenêtre, padding boutons
	private int windowsWidth = 400, WindowsHeight = 400;
	private byte buttonHpadding = 5, buttonVpadding = 5;
	
	// déclarations des boutons
	private Boutons but1 = new Boutons("1");
	private Boutons but2 = new Boutons("2");
	private Boutons but3 = new Boutons("3");
	private Boutons but4 = new Boutons("4");
	private Boutons but5 = new Boutons("5");
	private Boutons but6 = new Boutons("6");
	private Boutons but7 = new Boutons("7");
	private Boutons but8 = new Boutons("8");
	private Boutons but9 = new Boutons("9");
	private Boutons but0 = new Boutons("0");
	private Boutons butP = new Boutons(".");
	private Boutons butClear = new Boutons("clear");
	private Boutons butEnter = new Boutons("enter");
	private Boutons butP1 = new Boutons("(");
	private Boutons butP2 = new Boutons(")");
	
	// boutons opérateurs
	private Boutons butPlus = new Boutons("+");
	private Boutons butMoins = new Boutons("-");
	private Boutons butFois = new Boutons("x");
	private Boutons butDiv = new Boutons("/");
	private Boutons butLastChar = new Boutons("<");
	
	// affichage du calcul et du résultat
	private JLabel texte = new JLabel("");
	
	
	public Graphisme() {
		// opérations de base de la fenêtre
		this.setTitle("ma calculatrice");
		this.setSize(this.windowsWidth, this.WindowsHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage("/images/calculatrice.png");
		this.setIconImage(icon);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Panel principal qui va contenir les autres layouts
		JPanel conteneurPrincipal = new JPanel();
		conteneurPrincipal.setBackground(Color.white);
		conteneurPrincipal.setPreferredSize(new Dimension(new Dimension(this.windowsWidth, this.WindowsHeight)));		
		conteneurPrincipal.setLayout(new GridBagLayout());
		
		// personnalisation de la zone de texte
		this.texte.setFont(new Font("serif", Font.BOLD, 30));
		this.texte.setForeground(Color.white);
		this.texte.setBackground(Color.black);
		this.texte.setOpaque(true);
		
		// GridLayouts des boutons
		Operateurs gridChiffres = new Operateurs(5, 3, buttonHpadding, buttonVpadding);
		Operateurs gridOperateurs = new Operateurs(5, 1, buttonHpadding, buttonVpadding); 
		JPanel gridCenter = new JPanel(gridChiffres);
		JPanel gridEast = new JPanel(gridOperateurs);
		
		// dimensions des layouts
		this.texte.setPreferredSize(new Dimension(380, 70));
		gridCenter.setPreferredSize(new Dimension(300, 270));
		gridEast.setPreferredSize(new Dimension(75, 270));
		
		// ----------------------------------------------
		// positionnement des composants
		GridBagConstraints gbc = new GridBagConstraints();
		
		// affichage texte
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weighty = 0.1;
		conteneurPrincipal.add(this.texte, gbc);
		
		// boutons chiffres
		gbc.gridx = 1;
		gbc.gridy = 0;
		conteneurPrincipal.add(gridCenter);
		
		// boutons opérateurs
		gbc.gridx = 1;
		gbc.gridy = 1;
		conteneurPrincipal.add(gridEast);
		
		//----------------------------------------------
		// création des boutons et des actions
		Boutons[] buttons = {but1, but2, but3, but4, but5, but6, but7, but8, but9, but0, butP, butClear, butEnter, butP1, butP2};
		Boutons[] buttonsOperateurs = {butPlus, butMoins, butFois, butDiv, butLastChar};
		
		for (JButton but: buttons) {
			gridCenter.add(but);
			but.setActionCommand(but.getText());
			but.addActionListener(this);
		}
		for (JButton but: buttonsOperateurs) {
			gridEast.add(but);
			but.addActionListener(this);
		}
		
		this.setContentPane(conteneurPrincipal);
		this.setVisible(true);
	}
	
	
	/**
	 * évenements aux clics des boutons
	 */
	public void actionPerformed(ActionEvent arg0) {
	    String actionCommand = ((JButton) arg0.getSource()).getActionCommand();
	    switch(actionCommand) {
	    	case "enter":
	    		Process process = new Process(this.texte.getText());
	    		this.texte.setText(process.go());
	    		break;
	    	case "clear":
	    		this.texte.setText("");
	    		break;
	    	case "<":
	    		String prov = this.texte.getText();
	    		if (prov.length() > 0) {
	    			this.texte.setText(prov.substring(0, prov.length()-1));
	    		}
	    		break;
	    	default:
	    		this.texte.setText(this.texte.getText() + actionCommand);
	    		break;
	    }
	}
}
