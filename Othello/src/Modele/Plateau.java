package Modele;

import java.util.Observable;

/**
 * classe Plateau qui regroupe: (1) le plateau du jeu (matrice de Couleurs) (2)
 * les liens vers les joueurs (3) la validation du coup demandé (4) le
 * retournement des pions (5) la détection de la fin de partie (aucun des 2
 * joueurs ne peut jouer) (6) le calcul du joueur actif
 *
 * @author courbis
 *
 */
public class Plateau extends Observable {

    private Couleur[][] plateau; 				// plateau de jeu
    private int nbblancs; 	 					// nombre de blancs présents
    private int nbnoirs; 	  					// nombre de noirs présents 
    private int dim;		 					// dimension du plateau
    private Joueur joueurActif; 				// joueur qui doit jouer
    private Joueur joueurEnAttente;				// joueur en attente de jouer
    private boolean finDePartie = false;			// gestion de la fin de partie quand aucun des joueurs ne peut jouer
    private boolean passeSonTour = false;			// gestion du tour quand un joueur est obligé de passer son tour

    /**
     * création d'un plateau de dimension donnée
     *
     * @param d dimension du plateau (d doit être paire)
     */
    public Plateau(int d) {

        dim = d;
        plateau = new Couleur[dim][dim];
        int milieu = d / 2;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                plateau[i][j] = Couleur.VIDE;
            }
        }

        plateau[milieu - 1][milieu - 1] = Couleur.BLANC;
        plateau[milieu][milieu] = Couleur.BLANC;
        plateau[milieu][milieu - 1] = Couleur.NOIR;
        plateau[milieu - 1][milieu] = Couleur.NOIR;

        nbblancs = 2;
        nbnoirs = 2;
        passeSonTour = false;
        finDePartie = false;

    }

    /**
     * initialisation en début de partie du joueur actif (pions noirs) et du
     * joueur en attente (pions blancs)
     *
     * @param j1 joueur
     * @param j2 joueur
     */
    public void setJoueurActif(Joueur j1, Joueur j2) {
        // TODO
        // exemple :
        joueurActif = j1;
    }

    /**
     * accesseur dà la dimension du plateau
     *
     * @return dint
     */
    public int getDim() {
        return dim;
    }

    /**
     * accesseur à la matrice représentant le plateau de jeu
     *
     * @return Couleur[][]
     */
    public Couleur[][] getMatrice() {
        return plateau;
    }

    /**
     * nombre de pions blancs présents sur le plateau
     *
     * @return int
     */
    public int getNbBlancs() {
        return nbblancs;
    }

    /**
     * acesseur nombre de pions noirs présents sur le plateau
     *
     * @return int
     */
    public int getNbNoirs() {
        return nbnoirs;
    }

    /**
     * accesseur au booléen de fin de partie
     *
     * @return boolean
     */
    public boolean getFinDePartie() {
        return finDePartie;
    }

    /**
     * accesseur au booléen PasseSonTour
     *
     * @return boolean
     */
    public boolean getPasseSonTour() {
        return passeSonTour;
    }

    /**
     * réinitialisation du plateau
     */
    public void reset() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                plateau[i][j] = Couleur.VIDE;
            }
        }
        int milieu = dim / 2;
        plateau[milieu - 1][milieu - 1] = Couleur.BLANC;
        plateau[milieu][milieu] = Couleur.BLANC;

        plateau[milieu][milieu - 1] = Couleur.NOIR;
        plateau[milieu - 1][milieu] = Couleur.NOIR;

        nbblancs = 2;
        nbnoirs = 2;

        finDePartie = false;
        passeSonTour = false;
        setChanged();
        notifyObservers();
    }

    /**
     * initialisation d'une cellule du plateau
     *
     * @param lig numéro de ligne
     * @param col numéro de colonne
     * @param couleur couleur du pion
     */
    public void setValue(int lig, int col, Couleur couleur) {
        Couleur lastCouleur = plateau[lig][col];
        plateau[lig][col] = couleur;
        //System.out.println("mise a jour du plateau en " + lig + " - " + col);
        if (couleur == Couleur.BLANC) {
            nbblancs++;
            if (lastCouleur == Couleur.NOIR) {
                nbnoirs--;
            }
        } else {
            nbnoirs++;
            if (lastCouleur == Couleur.BLANC) {
                nbblancs--;
            }

        }
        setChanged();
        notifyObservers();
    }

    /**
     * vérifie si le coup demandé est valide
     *
     * @param coup à vérifier
     * @return boolean
     */
    public boolean coupDemande(Coup coup) {
        // TODO
        return true;
    }

    /**
     * accesseur au joueur actif
     *
     * @return Joueur
     */
    public Joueur aQuiLeTour() {
        return joueurActif;

    }

    /**
     * changement du joueur actif en fonction des coups possibles; si plus de
     * coups possibles, la fonction retourne null
     *
     * @return Joueur
     */
    public Joueur changeTourJoueur() {

        //TODO
        return joueurActif;

    }

}
