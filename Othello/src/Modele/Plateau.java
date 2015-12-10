package Modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * classe Plateau qui regroupe: (1) le matrice du jeu (matrice de Couleurs) (2)
 * les liens vers les joueurs (3) la validation du coup demandé (4) le
 * retournement des pions (5) la détection de la fin de partie (aucun des 2
 * joueurs ne peut jouer) (6) le calcul du joueur actif
 *
 * @author courbis
 *
 */
public class Plateau extends Observable {

    private Couleur[][] matrice; 				// matrice de jeu
    private int nbblancs; 	 					// nombre de blancs présents
    private int nbnoirs; 	  					// nombre de noirs présents 
    private int dim;		 					// dimension du matrice
    private Joueur joueurActif; 				// joueur qui doit jouer
    private Joueur joueurEnAttente;				// joueur en attente de jouer
    private boolean finDePartie = false;			// gestion de la fin de partie quand aucun des joueurs ne peut jouer
    private boolean passeSonTour = false;			// gestion du tour quand un joueur est obligé de passer son tour

    /**
     * création d'un matrice de dimension donnée
     *
     * @param d dimension du matrice (d doit être paire)
     */
    public Plateau(int d) {

        dim = d;
        matrice = new Couleur[dim][dim];
        int milieu = d / 2;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrice[i][j] = Couleur.VIDE;
            }
        }

        matrice[milieu - 1][milieu - 1] = Couleur.BLANC;
        matrice[milieu][milieu] = Couleur.BLANC;
        matrice[milieu][milieu - 1] = Couleur.NOIR;
        matrice[milieu - 1][milieu] = Couleur.NOIR;

        nbblancs = 2;
        nbnoirs = 2;
        passeSonTour = false;
        finDePartie = false;

    }

    /**
     * Initialisation en début de partie du joueur actif (pions noirs) et du
     * joueur en attente (pions blancs)
     *
     * @param j1 joueur
     * @param j2 joueur
     */
    public void setJoueurActif(Joueur j1, Joueur j2) {
        joueurActif = j1;
        joueurEnAttente = j2;
    }

    /**
     * accesseur dà la dimension du matrice
     *
     * @return dint
     */
    public int getDim() {
        return dim;
    }

    /**
     * accesseur à la matrice représentant le matrice de jeu
     *
     * @return Couleur[][]
     */
    public Couleur[][] getMatrice() {
        return matrice;
    }

    /**
     * nombre de pions blancs présents sur le matrice
     *
     * @return int
     */
    public int getNbBlancs() {
        return nbblancs;
    }

    /**
     * acesseur nombre de pions noirs présents sur le matrice
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
     * réinitialisation du matrice
     */
    public void reset() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrice[i][j] = Couleur.VIDE;
            }
        }
        int milieu = dim / 2;

        matrice[milieu - 1][milieu - 1] = Couleur.BLANC;
        matrice[milieu][milieu] = Couleur.BLANC;

        matrice[milieu][milieu - 1] = Couleur.NOIR;
        matrice[milieu - 1][milieu] = Couleur.NOIR;

        nbblancs = 2;
        nbnoirs = 2;

        finDePartie = false;
        passeSonTour = false;
        setChanged();
        notifyObservers();
    }

    /**
     * initialisation d'une cellule du matrice
     *
     * @param lig numéro de ligne
     * @param col numéro de colonne
     * @param couleur couleur du pion
     */
    public void setValue(int lig, int col, Couleur couleur) {
        Couleur lastCouleur = matrice[lig][col];
        matrice[lig][col] = couleur;
        //System.out.println("mise a jour du matrice en " + lig + " - " + col);
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
     * Vérifie si le coup demandé est valide
     *
     * @param coup à vérifier
     * @return boolean
     */
    public boolean coupDemande(Coup coup) {
        boolean estValide = false;

        ArrayList<Coup> coupsPossibles = joueurActif.getListeCoupsPossibles();
        for (Iterator<Coup> i = coupsPossibles.iterator(); i.hasNext();) {
            Coup unCoup = i.next();
            if (coup.getColonne() == unCoup.getColonne() && coup.getLigne() == unCoup.getLigne())
            {
                estValide = true;
            }
        }

        return estValide;
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
     * Changement du joueur actif en fonction des coups possibles.
     * S'il n'y a plus de coups possibles, la fonction retourne null.
     *
     * @return Joueur Le joueur qui va devoir jouer.
     */
    public Joueur changeTourJoueur() {
        // Si le prochain joueur peut jouer.
        if (joueurEnAttente.getListeCoupsPossibles().size() > 0)
        {
            // On échange les deux joueurs
            Joueur tmp = joueurEnAttente;
            joueurEnAttente = joueurActif;
            joueurActif = tmp;

            return joueurActif;
        } 
        else 
        {
            return null;
        }
    }

    /**
     * Retire tous les HIGHLIGHT de la matrice.
     */
    public void retirerHighlight() {
        // On parcours les abscisses
        for (int i = 0; i < dim; i++) {
            // Puis les ordonnées
            for (int j = 0; j < dim; j++) {
                // Si la case parcourrue est un HIGHLIGHT...
                if (matrice[i][j] == Couleur.HIGHLIGHT) {
                    setValue(i, j, Couleur.VIDE);
                }
            }
        }
    }
}
