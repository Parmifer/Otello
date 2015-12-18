package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Modele.Couleur;
import Modele.Joueur;
import Modele.Plateau;
import javax.swing.JOptionPane;

/**
 * Interface graphique du jeu 
 * mise à jour lorsque le modèle (plateau + joueur) évolue
 * 
 * @author Mme Courbis - Yann Butscher - Lucile Decrozant-Triquenaux
 *
 */
public class InterfaceOthello extends JFrame implements Observer {
	
	private Plateau plateau;				//plateau de jeu
	private int dim;						// dimension du plateau
	
	JFrame jFrame;
	JPanel jeu;
	JPanel menu;

	GridLayout gridLayout;
	int clickCoordX, clickCoordY,boutton;
	public JButton[][] cellulePlateau;
	
	public JButton joueurAUTO=null;			// bouton pour demander au joueur automatique de jouer
	public JButton reset;					// bouton pour réinitialiser le plateau
	
	JLabel score;							// zone d'affichage du score;
	JLabel quiJoue;							// zone d'affichage du joueur actif  
	
	ImageIcon blanc, noir, vide,highlight;	// images associées aux jetons

	/**
	 * création de l'interface graphique d'un plateau de jeu
	 * @param p	plateu de jeu
	 * @param isJoueurAutomatique booléen signalant la présence d'au moins un joueur automatique
	 */
	public InterfaceOthello(Plateau p, boolean isJoueurAutomatique){
	
		this.plateau=p;
		dim=p.getDim();
		p.addObserver(this);
		
		//
		jFrame = new JFrame();
		jFrame.setTitle("Othello");
		jFrame.setSize(500, 450);
		
		jeu = new JPanel();
		jeu.setSize(300, 300);
		
		menu = new JPanel();
		menu.setSize(300, 50);

		jFrame.getContentPane().add( jeu, BorderLayout.CENTER );
		jFrame.getContentPane().add( menu, BorderLayout.SOUTH );

		gridLayout = new GridLayout(dim,dim);
		jeu.setLayout(gridLayout);

		cellulePlateau = new JButton[dim][dim];
		URL url = InterfaceOthello.class.getResource("blanc.GIF");
		blanc = new ImageIcon(url);
		url = InterfaceOthello.class.getResource("noir.GIF");
		noir = new ImageIcon(url);
		url = InterfaceOthello.class.getResource("vide.GIF");
		vide = new ImageIcon(url);
		url = InterfaceOthello.class.getResource("highlight.GIF");
		highlight = new ImageIcon(url);
		
		for (int i = 0; i < p.getDim(); i++) 
		{	for (int j = 0; j < p.getDim(); j++) 
			{	JButton jButTemp = new JButton();
				cellulePlateau[i][j]=jButTemp;
				cellulePlateau[i][j].setBackground(new Color(0,128,0));
				setIconCellulePlateau(i,j);
				jeu.add(cellulePlateau[i][j]);
			}
		}
	
		quiJoue = new JLabel("C'est au tour de NOIR.");
		menu.add(quiJoue);

		if (isJoueurAutomatique)
		{	joueurAUTO = new JButton("Jeu automatique");
			menu.add(joueurAUTO);
		}
		
		
		reset = new JButton("reset");
		menu.add(reset);

		score = new JLabel("");
		menu.add(score);
		
		jFrame.setVisible(true); 
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null); 
		

	}
	/**
	 * mise à jour d'une cellule du plateau
	 * @param i numéro de ligne
	 * @param j numéro de colonne
	 */
	
	private void setIconCellulePlateau(int i, int j){
		Couleur [][] matrice = plateau.getMatrice();
		if (matrice[i][j] ==  Couleur.BLANC)
			cellulePlateau[i][j].setIcon(blanc);
		else if ( matrice[i][j] == Couleur.NOIR)
			cellulePlateau[i][j].setIcon(noir);
		else
			cellulePlateau[i][j].setIcon(vide);
	}
	
	/**
	 * affichage du plateau 
	 * 
	 */
	public void paintComponentMatrice(){
		
		Couleur [][] matrice=plateau.getMatrice();
		for (int i=0; i<dim;i++)
			for (int j=0; j<dim;j++)
				{	if (matrice[i][j] == Couleur.BLANC) cellulePlateau[i][j].setIcon(blanc);
					else if (matrice[i][j] == Couleur.NOIR) cellulePlateau[i][j].setIcon(noir);
					else  if (matrice[i][j] == Couleur.HIGHLIGHT) cellulePlateau[i][j].setIcon(highlight);
					else cellulePlateau[i][j].setIcon(vide);
				}
		
		
	}
	
	/**
	 * mise à jour de l'interface quand modification du modèle (classe Plateau) 
	 *
	 */
	public void update(Observable o, Object obj){
		
		// appel de paintComponentMatrice
		// appel de setScore
		// appel de setTour
		// exemple :
		paintComponentMatrice();
		// ou appel de la fenêtre de fin de partie
		
	}
	
	/**
	 * initialisation de la zone de score
	 */
	public void setScore (){
		score.setText("BLANCS : " + plateau.getNbBlancs() + " / NOIRS : " + plateau.getNbNoirs());
	}
	
	/**
	 * Initialisation de la zone d'information sur le joueur qui doit jouer
	 * ou sur le joueur qui doit passer son tour
	 */
	public void setTour()
        {
            Joueur prochainJoueur = plateau.changeTourJoueur();
            String couleurJoueurActif = (plateau.aQuiLeTour().getCouleur() == Couleur.NOIR) ? "NOIR" : "BLANC";
            String couleurJoueurEnAttente = (plateau.aQuiLeTour().getCouleur() == Couleur.NOIR) ? "BLANC" : "NOIR";
            if(prochainJoueur == null)
            {
                this.finDePartie();
            }
            else if (plateau.getPasseSonTour())
            {
                JOptionPane.showMessageDialog(this, couleurJoueurEnAttente + " doit passer son tour.", "Attention", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                quiJoue.setText("C'est au tour de " + couleurJoueurActif + ".");                
            }
	}
	

	/**
	 * Affichage de l'interface graphique de fin de partie avec le score et 
	 * le gagnant ou l'annonce de partie ex-aequo
	 */
	public void finDePartie()
        {
            String message;
            int scoreBlanc, scoreNoir;
            int nombreDeCases = plateau.getDim() * plateau.getDim();
            int nombreDeBlancs = plateau.getNbBlancs();
            int nombreDeNoirs = plateau.getNbNoirs();
            
            
            if(nombreDeBlancs > nombreDeNoirs)
            {
                scoreNoir = nombreDeNoirs;
                scoreBlanc = nombreDeCases - nombreDeNoirs;
                message = "BLANC a gagné la partie !\nLe score est de " + scoreBlanc + " à " + scoreNoir + ".";                
                
            }
            else if(nombreDeBlancs < nombreDeNoirs)
            {
                scoreBlanc = nombreDeBlancs;
                scoreNoir = nombreDeCases - nombreDeBlancs;
                message = "NOIR a gagné la partie !\nLe score est de " + scoreNoir + " à " + scoreBlanc + "."; 
            }
            else
            {
                message = "Le match est nul !\nLes deux joueurs sont ex-aequo.";
            }
            
            JOptionPane.showMessageDialog(this, message, "Fin de la partie", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * accesseur au bouton reset
	 * @return JButton
	 */
	public JButton getBoutonReset() {
		return reset;
	}
	/**
	 * accesseur au bouton d'activation du joueur automatique
	 * @return JButton
	 */
	public JButton getBoutonJoueurAuto() {
		
		return joueurAUTO;
	}
	/**
	 * accesseur à une cellule du plateau
	 * @param lig ligne
	 * @param col colonne
	 * @return JButton
	 */
	public JButton getBoutonPlateau(int lig, int col) {
		
		return cellulePlateau[lig][col];
	}

}
