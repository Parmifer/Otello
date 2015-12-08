package Modele;

import java.util.ArrayList;
import Modele.arbre.Tree;

/**
 * modélisation d'un joueur (HUMAIN ou AUTOMATE)
 *
 * @author courbis
 *
 */
public class Joueur {

    private Couleur maCouleur;
    private int couleurAdverse;
    private TypeJoueur monType;
    private Plateau plateau;
    private int monNiveau;
    //NIVEAUDEBUTANT = 0;
    //NIVEAUMOYEN= 1;
    //NIVEAUFORT = 2;
    //NIVEAUEXPERT= 3;

    /**
     * création d'un joueur
     *
     * @param c couleur
     * @param t type : automate ou humain
     * @param p plateau de jeu
     * @param niv niveau de performance du joueur (utile uniquement pour le
     * joueur automatique)
     */
    public Joueur(Couleur c, TypeJoueur t, Plateau p, int niv) // création d'un joueur de couleur donnée (NOIR ou BLANC) et de type donné (HUMAIN ou AUTOMATE)
    // jouant sur le plateau p.
    {
        maCouleur = c;
        plateau = p;
        monType = t;
        monNiveau = niv;
    }

    /**
     * accesseur au type du joueur
     *
     * @return TypeJoueur
     */
    public TypeJoueur getType() {
        return this.monType;
    }

    /**
     * test si le joueur est de type automate
     *
     * @return boolean
     */
    public boolean isAutomate() {
        return this.monType.compareTo(TypeJoueur.AUTOMATE) == 0;
    }

    /**
     * test si le joueur est de type humain
     *
     * @return boolean
     */
    public boolean isHumain() {
        return this.monType.compareTo(TypeJoueur.HUMAIN) == 0;
    }

    /**
     * test si le joueur a les pions noirs
     *
     * @returnboolean
     */
    public boolean isNoir() {
        return this.maCouleur.compareTo(Couleur.NOIR) == 0;
    }

    /**
     * initialisation de la couleur des pions du joueur
     *
     * @param couleur
     */
    public void setColor(Couleur couleur) {
        this.maCouleur = couleur;
    }

    /**
     * accesseur à la couleur des pions du joueur
     *
     * @return Couleur
     */

    public Couleur getCouleur() {
        return this.maCouleur;
    }

    /**
     * calcul des coups possibles du joueur
     *
     * @return ArrayList<Coup>
     */
    public ArrayList<Coup> getListeCoupsPossibles() {
        Couleur[][] matriceDuJeu = this.plateau.getMatrice();
        int dimension;        
        // TODO
        return null;
    }

    /**
     * calcul du meilleur coup à jouer
     *
     * @return Coup
     */
    public Coup joue() {
        // TODO
        // exemple : 
        Coup coup = new Coup(0, 0);
        return coup;

    }

}
