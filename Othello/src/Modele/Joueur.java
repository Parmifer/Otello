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
    private Couleur couleurAdverse;
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
        this.maCouleur = c;
        this.couleurAdverse = Couleur.getCouleurOpposee(this.maCouleur);
        this.plateau = p;
        this.monType = t;
        this.monNiveau = niv;
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
        // Initialisation de l'ArrayList à retourner
        ArrayList<Coup> coupsPossibles = new ArrayList<>();
        // On récupère la matrice qui représente le plateau.
        Couleur[][] matriceDuJeu = this.plateau.getMatrice();
        // Puis ses dimension (i x j avec i = j)
        int dimension = this.plateau.getDim();        
        // On parcours les abscisses
        for(int i = 0; i < dimension; i++)
        {
            // Puis les ordonnées
            for(int j = 0; j < dimension; j++)
            {
                // Si la case parcourrue est la même que la couleur du joueur...
                if(matriceDuJeu[i][j] == this.maCouleur)
                { 
                    // On fait un parcours des cases adjacentes.
                    for(int translationHorizontale = -1; translationHorizontale < 2; translationHorizontale++)
                    {
                        for(int translationVerticale = -1; translationVerticale < 2; translationVerticale++)
                        {
                            // On regarde si un des cases adjacentes possède un pion de la couleur de mon adversaire.
                            if(matriceDuJeu[i+translationHorizontale][j+translationVerticale] == this.couleurAdverse)
                            {
                                System.out.println("Pion adverse en : (" + (i + translationHorizontale) + ";" + (j + translationVerticale) + ")");
                                // On cherche combien de pions on peux retourner.
                                int pionRetournePossible = 1;
                                int abscisse = i + translationHorizontale * (pionRetournePossible + 1);
                                int ordonnee = j + translationVerticale   * (pionRetournePossible + 1);
                                
                                // On vérifie qu'on ne sors pas du tableau et que le coup à jouer est possible
                                while (abscisse < 8 && abscisse > 0 && ordonnee < 8 && ordonnee > 0 && matriceDuJeu[abscisse][ordonnee] == this.couleurAdverse)
                                {
                                    pionRetournePossible++;
                                    abscisse = i + translationHorizontale * (pionRetournePossible + 1);
                                    ordonnee = j + translationVerticale   * (pionRetournePossible + 1);
                                }
                                
                                if(abscisse < 8 && abscisse > 0 && ordonnee < 8 && ordonnee > 0 && matriceDuJeu[abscisse][ordonnee] == Couleur.VIDE)
                                {
                                    coupsPossibles.add(new Coup(abscisse, ordonnee));
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return coupsPossibles;
    }

    /**
     * Calcul du meilleur coup à jouer
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
