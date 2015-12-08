package Controleur;

import Modele.Couleur;
import Modele.Joueur;
import Modele.Plateau;
import Modele.TypeJoueur;
import Vue.InterfaceOthello;

/**
 * Classe principale de lancement d'une partie : (1) création du plateau, des
 * joueurs et de l'interface, (2) association des liens d'écoute de l'interface
 * graphique par le controleur
 *
 * @author courbis
 *
 */
public class Othello {

    private static int DIMENSION = 8; // dimension du plateau de jeu
    private static int NIVEAUDEBUTANT = 0;
    private static int NIVEAUMOYEN = 1;
    private static int NIVEAUFORT = 2;
    private static int NIVEAUEXPERT = 3;

    public static void main(String[] args) {

        // création d'un plateau
        Plateau plateau = new Plateau(DIMENSION);

        //déclaration des joueurs
        Joueur joueur1 = new Joueur(Couleur.NOIR, TypeJoueur.AUTOMATE, plateau, NIVEAUMOYEN);
        Joueur joueur2 = new Joueur(Couleur.BLANC, TypeJoueur.HUMAIN, plateau, NIVEAUDEBUTANT);

        // définition du joueur qui débute (pions noirs, conventionnellement)
        plateau.setJoueurActif(joueur1, joueur2);

        // test si bouton de jeu automatique nécessaire sur l'interface
        boolean isJoueurAuto = joueur1.isAutomate() || joueur2.isAutomate();

        //création de l'IHM du plateau
        InterfaceOthello ihm = new InterfaceOthello(plateau, isJoueurAuto);
        ihm.setScore();
        ihm.setTour();

        // création du controlleur
        ControleurOthello controlleur = new ControleurOthello(plateau, joueur1, joueur2, ihm);

        // association de Listener aux éléments actifs de l'interface
        ihm.getBoutonReset().addActionListener(controlleur);
        for (int i = 0; i < plateau.getDim(); i++) {
            for (int j = 0; j < plateau.getDim(); j++) {
                ihm.getBoutonPlateau(i, j).addActionListener(controlleur);
            }
        }
        if (ihm.getBoutonJoueurAuto() != null) {
            ihm.getBoutonJoueurAuto().addActionListener(controlleur);
        }

    }

}
