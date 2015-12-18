package Modele;

import java.util.ArrayList;
import Modele.arbre.Tree;

/**
 * modélisation d'un joueur (HUMAIN ou AUTOMATE)
 *
 * @author Mme Courbis - Yann Butscher - Lucile Decrozant-Triquenaux
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
     * Calcul des coups possibles du joueur.
     *
     * @return ArrayList<?> La liste des coups possibles pour le joueur. Un ArrayList vide si aucun coup n'est possible.
     */
    public ArrayList<Coup> getListeCoupsPossibles() {    
        return this.getListeCoupsPossibles(this.plateau.getMatrice(), this.maCouleur);
    }
    
    /**
     * Retourne les coups possibles pour une configuration donnée.
     * 
     * @param matriceDuJeu Matrice qui représente l'état du plateau du jeu.
     * @param couleurJoueurActif La couleur du joueur actif.
     * 
     * @return ArrayList<?> Une liste des coups possibles. Un ArrayList vide si aucun coup n'est possible.
     */
    public static ArrayList<Coup> getListeCoupsPossibles(Couleur[][] matriceDuJeu, Couleur couleurJoueurActif) {
        // Initialisation de l'ArrayList à retourner
        ArrayList<Coup> coupsPossibles = new ArrayList<>();
        // Puis ses dimension (i x j avec i = j)
        int dimension = matriceDuJeu.length;
        // On parcours les abscisses
        for (int i = 0; i < dimension; i++) {
            // Puis les ordonnées
            for (int j = 0; j < dimension; j++) {
                // Si la case parcourrue est la même que la couleur du joueur...
                if (matriceDuJeu[i][j] == couleurJoueurActif) {
                    // On fait un parcours des cases adjacentes.
                    for (int translationHorizontale = -1; translationHorizontale < 2; translationHorizontale++) {
                        for (int translationVerticale = -1; translationVerticale < 2; translationVerticale++) {
                            // Test pour vérifier qu'on reste dans le cadre de la matrice.
                            if (i + translationHorizontale < dimension && i + translationHorizontale >= 0 && j + translationVerticale < dimension && j + translationVerticale >= 0) {
                                // On regarde si un des cases adjacentes possède un pion de la couleur de mon adversaire.
                                if (matriceDuJeu[i + translationHorizontale][j + translationVerticale] == Couleur.getCouleurOpposee(couleurJoueurActif)) {
                                    // On cherche combien de pions on peux retourner.
                                    int pionRetournePossible = 1;
                                    int abscisse = i + translationHorizontale * (pionRetournePossible + 1);
                                    int ordonnee = j + translationVerticale * (pionRetournePossible + 1);

                                    // On vérifie qu'on ne sors pas du tableau et que le coup à jouer est possible
                                    while (abscisse < dimension && abscisse >= 0 && ordonnee < dimension && ordonnee >= 0 && matriceDuJeu[abscisse][ordonnee] == Couleur.getCouleurOpposee(couleurJoueurActif)) {
                                        pionRetournePossible++;
                                        abscisse = i + translationHorizontale * (pionRetournePossible + 1);
                                        ordonnee = j + translationVerticale * (pionRetournePossible + 1);
                                    }

                                    if (abscisse < dimension && abscisse >= 0 && ordonnee < dimension && ordonnee >= 0 && matriceDuJeu[abscisse][ordonnee] == Couleur.VIDE) {
                                        coupsPossibles.add(new Coup(abscisse, ordonnee));
                                    }
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
     * Récupération de la liste des pions à retourner après qu'un coup ait été
     * joué.
     *
     * @param unCoup Le coup qui vient d'être joué.
     * @param matriceDuJeu Matrice qui représente l'état du plateau du jeu.
     * @param couleurJoueurActif La couleur du joueur actif.
     * 
     * @return ArrayList<?> La liste des pions à retourner (représentés par des coups). 
     */
    public static ArrayList<Coup> getListePionsARetourner(Coup unCoup, Couleur[][] matriceDuJeu, Couleur couleurJoueurActif) {
        // Initialisation de l'ArrayList des coups.
        ArrayList<Coup> pionsARetourner = new ArrayList<>();
        // Puis ses dimension.
        int dimension = matriceDuJeu.length;
        // Variables pour plus de clarté.
        int ligne = unCoup.getLigne();
        int colonne = unCoup.getColonne();
        Couleur couleurJoueurAttente = Couleur.getCouleurOpposee(couleurJoueurActif);

        // On fait un parcours des cases adjacentes.
        for (int translationHorizontale = -1; translationHorizontale < 2; translationHorizontale++) {
            for (int translationVerticale = -1; translationVerticale < 2; translationVerticale++) {
                // Initialisation de l'ArrayList des coups.
                ArrayList<Coup> pionsPotentielsARetourner = new ArrayList<>();

                // Test pour vérifier qu'on reste dans le cadre de la matrice.
                if (ligne + translationHorizontale < dimension && ligne + translationHorizontale >= 0 && colonne + translationVerticale < dimension && colonne + translationVerticale >= 0) {
                    // On regarde si un des cases adjacentes possède un pion de la couleur de mon adversaire.
                    if (matriceDuJeu[ligne + translationHorizontale][colonne + translationVerticale] == couleurJoueurAttente) {
                        // On retourne un premier pion.
                        pionsPotentielsARetourner.add(new Coup(ligne + translationHorizontale, colonne + translationVerticale));

                        // On cherche les autres pions qu'on peux retourner.
                        int pionRetournePossible = 1;
                        int abscisse = ligne + translationHorizontale * (pionRetournePossible + 1);
                        int ordonnee = colonne + translationVerticale * (pionRetournePossible + 1);

                        // On vérifie qu'on ne sors pas du tableau et que le coup à jouer est possible
                        while (abscisse < dimension && abscisse >= 0 && ordonnee < dimension && ordonnee >= 0 && matriceDuJeu[abscisse][ordonnee] == couleurJoueurAttente) {
                            pionsPotentielsARetourner.add(new Coup(abscisse, ordonnee));
                            pionRetournePossible++;
                            abscisse = ligne + translationHorizontale * (pionRetournePossible + 1);
                            ordonnee = colonne + translationVerticale * (pionRetournePossible + 1);
                        }

                        if (abscisse < dimension && abscisse >= 0 && ordonnee < dimension && ordonnee >= 0 && matriceDuJeu[abscisse][ordonnee] == couleurJoueurActif) {
                            pionsARetourner.addAll(pionsPotentielsARetourner);
                        }
                    }
                }
            }
        }

        return pionsARetourner;
    }
    
    /**
     * Récupération de la liste des pions à retourner après qu'un coup ait été
     * joué.
     *
     * @param unCoup Le coup qui vient d'être joué.
     * @return ArrayList<?> La liste des pions à retourner (représentés par des coups).
     */
    public ArrayList<Coup> getListePionsARetourner(Coup unCoup) {        
        return Joueur.getListePionsARetourner(unCoup, this.plateau.getMatrice(), this.maCouleur);
    }

    /**
     * Calcul du meilleur coup à jouer
     *
     * @return Coup
     */
    public Coup joue() {
        Coup coupAJouer = null;
        
        switch(this.monNiveau)
        {
            // NIVEAUDEBUTANT
            case 0:
                coupAJouer = this.joueAleatoire();
                break;             
            // NIVEAUMOYEN
            case 1:
                coupAJouer = getMeilleurCoup(2);
                break;  
            // NIVEAUFORT
            case 2:
                coupAJouer = getMeilleurCoup(4);
                break;  
            // NIVEAUEXPERT
            case 3:
                coupAJouer = getMeilleurCoup(6);
                break;  
        }

        return coupAJouer;
    }
    
    /**
     * Retourne un coup au hasard parmi les coups possibles.
     * 
     * Fonction de coup de l'IA "NIVEAUDEBUTANT".
     *
     * @return Coup Un coup choisi au hasard.
     */
    public Coup joueAleatoire() {
        // Récupération des coups possibles.
        ArrayList<Coup> coupsPossibles = getListeCoupsPossibles();

        // Index d'un coup possible tiré au hasard.      
        int indexAleatoire = (int) (Math.random() * (coupsPossibles.size() - 1));

        return coupsPossibles.get(indexAleatoire);
    }
    
    /**
     * Retourne un le meilleur coup en fonction d'une situation de jeu.
     * 
     * @param profondeur Profondeur de l'arbre qui va déterminer le coup à jouer.
     * Plus la profondeur est grande plus le coup sera pertinent. Valeur minimale : 2.
     * 
     * @return Le meilleur coup à jouer.
     */
    public Coup getMeilleurCoup(int profondeur)
    {    
        // Étape 1 :
        // Construire un arbre en fonction de la situation initiale
        //      - Matrice
        //      - Couleur du joueur
        //      - Couleur du joueur actif
        //      - Les coups possibles
        
        // Création de l'instant.
        Instant instantInitial = new Instant();
        
        // Duplication de la matrice.
        int dim = this.plateau.getDim();     
        
        Couleur[][] tampon = new Couleur[dim][dim];        
        Couleur[][] matrice = this.plateau.getMatrice();
        for(int i = 0; i < dim; i++)
        {
            for(int j = 0; j < dim; j++)
            {
                tampon[i][j] = matrice[i][j];
            }
        }
        
        // Initialisation des valeurs.
        instantInitial.setMatricePlateau(tampon);
        instantInitial.setCouleurJoueurActif(this.maCouleur);
        instantInitial.setCouleurJoueurGagnant(this.maCouleur);
        Tree<Instant> monArbre = Instant.genererArbreInstant(instantInitial, profondeur);
        
        // Étape 2 :
        // Recherche minMax de l'heuristique.
        Tree<?> meilleurNoeud = monArbre.minMax();        
        
        // Étape 3 : 
        // On récupère le coup et on le retourne.
        Coup meilleurCoup = ((Tree<Instant>) meilleurNoeud).getRacine().getCoupAJouer();
        
        // DEBUG
        System.out.println("Meilleur coup : (" + meilleurCoup.getLigne() + "," + meilleurCoup.getColonne() + ")");
        
        return meilleurCoup;
    }    
   
}
