
package Modele;

import Modele.arbre.Heuristiquable;
import Modele.arbre.Tree;
import java.util.ArrayList;

/**
 *
 * @author Yann Butscher - Lucile Decrozant-Triquenaux
 */
public class Instant implements Heuristiquable
{
    private Coup coupAJouer;
    private Couleur couleurJoueurGagnant;
    private Couleur couleurJoueurActif;
    private Couleur[][] matricePlateau; 
    private int nombreBlancs;
    private int nombreNoirs;

    public Coup getCoupAJouer() {
        return coupAJouer;
    }

    public void setCoupAJouer(Coup coupAJouer) {
        this.coupAJouer = coupAJouer;
        this.jouerCoup();
    }

    public Couleur getCouleurJoueurGagnant() {
        return couleurJoueurGagnant;
    }

    public void setCouleurJoueurGagnant(Couleur couleurJoueurGagnant) {
        this.couleurJoueurGagnant = couleurJoueurGagnant;
    }   

    public Couleur getCouleurJoueurActif() {
        return couleurJoueurActif;
    }

    public void setCouleurJoueurActif(Couleur couleurJoueurActif) {
        this.couleurJoueurActif = couleurJoueurActif;
    }

    public Couleur[][] getMatricePlateau() {
        return matricePlateau;
    }

    public void setMatricePlateau(Couleur[][] matricePlateau) {
        this.matricePlateau = matricePlateau;
    }    
    
    @Override
    public int getHeuristique()
    {       
        this.compterCouleurs();
        int heuristique;
        
        if(this.couleurJoueurActif == this.couleurJoueurGagnant)
        {
            heuristique = couleurJoueurActif == Couleur.BLANC ? nombreBlancs - nombreNoirs : nombreNoirs - nombreBlancs; 
        }
        else
        {
            heuristique = couleurJoueurActif == Couleur.NOIR ? nombreBlancs - nombreNoirs : nombreNoirs - nombreBlancs;
        }
        
        System.out.println("Heuristique : " + heuristique + " [Coup associé : (" + this.coupAJouer.getLigne() + "," + this.coupAJouer.getColonne() + ")]");
        
        return heuristique;
    }    
    
    /**
     * Joue le coup enregistré sur le plateau et retourne les pions si besoin.
     */
    public void jouerCoup()
    {
        // On joue le coup.
        this.matricePlateau[this.coupAJouer.getLigne()][this.coupAJouer.getColonne()] = this.couleurJoueurActif;

        // Retournement des pions du plateau.
        ArrayList<Coup> aRetourner = Joueur.getListePionsARetourner(this.coupAJouer, this.matricePlateau, this.getCouleurJoueurActif());
        int nombreDeRetournements = aRetourner.size();
        for(int i = 0; i < nombreDeRetournements; i++)
        {
            this.matricePlateau[aRetourner.get(i).getLigne()][aRetourner.get(i).getColonne()] = this.couleurJoueurActif;
        }
    }
    
    private void compterCouleurs()
    {
        this.nombreNoirs = 0;
        this.nombreBlancs = 0;
        int tailleMatrice = this.matricePlateau.length;
        
        for(int i = 0; i < tailleMatrice; i++)
        {
            for(int j = 0; j < tailleMatrice; j++)
            {
                if(this.matricePlateau[i][j] == Couleur.BLANC)
                {
                    nombreBlancs++;                   
                }
                else if(this.matricePlateau[i][j] == Couleur.NOIR)
                {
                    nombreNoirs++;                    
                }
            }
        }
    }    
    
     /**
     * Génère un arbre à partir de la racine
     *
     * @param profondeur Profondeur de l'arbre désirée
     * @param instantInitial Représente l'état du jeu au moment de la création de l'arbre.
     * @return L'arbre généré
     */
    public static Tree<Instant> genererArbreInstant(Instant instantInitial, int profondeur) {
        // En cas de profondeur 0, on retourne un arbre vide (Deux niveaux minimum)
        if (profondeur <= 1) {
            return new Tree<>();
        }
        else            
        {
            // Création de l'arbre Racine.
            Tree<Instant> arbreRacine = new Tree<>(instantInitial);            
            
            arbreRacine = Instant.genererArbreFilsInstant(profondeur - 1, arbreRacine);
            
            return arbreRacine;
        }        
    }

    /**
     * Génère un fils dans un arbre existant
     *
     * @param profondeur Profondeur de l'arbre désirée
     * @param pere Père de l'arbre que l'on souhaite générer
     * @return L'arbre père modifié avec le nouvel arbre fils
     */
    private static Tree<Instant> genererArbreFilsInstant(int profondeur, Tree<Instant> pere)
    {  
        // Couleur du joueur actif.
        Couleur couleurJoueurActif = pere.getRacine().getCouleurJoueurActif();
        // Matrice de l'instant père.
        Couleur[][] matricePere = pere.getRacine().getMatricePlateau();
        // On récupère les coups à jouer.
        ArrayList<Coup> coupsPossibles = Joueur.getListeCoupsPossibles(matricePere, couleurJoueurActif);
        
        // Si aucun coup n'est possible.
        if(coupsPossibles.isEmpty())
        {
            // On calcule les coups possibles pour le joueur qui va jouer deux fois.
            coupsPossibles = Joueur.getListeCoupsPossibles(matricePere, Couleur.getCouleurOpposee(couleurJoueurActif));
            // Si la partie n'est pas finie.
            if(!coupsPossibles.isEmpty())
            {
                // Pour chaque coup possible, on créé un noeud contenant un instant différent.
                for(Coup unCoupPossible : coupsPossibles)
                {
                    // Création d'un nouvel instant avec de nouvelles données.
                    Instant nouvelInstant = new Instant();
                    
                    // Duplication de la matrice.
                    int dim = pere.getRacine().getMatricePlateau().length;
                    Couleur[][] tampon = new Couleur[dim][dim];        
                    Couleur[][] matrice = pere.getRacine().getMatricePlateau();
                    for(int i = 0; i < dim; i++)
                    {
                        for(int j = 0; j < dim; j++)
                        {
                            tampon[i][j] = matrice[i][j];
                        }
                    }                    
                    nouvelInstant.setMatricePlateau(tampon);
                    // On copie la couleur du joueur actif.
                    // Elle ne sera pas changée puisque l'autre joueur a passé son tour.
                    nouvelInstant.setCouleurJoueurActif(pere.getRacine().getCouleurJoueurActif());  
                    // On met la couleur du joueur devant gagner.
                    nouvelInstant.setCouleurJoueurGagnant(pere.getRacine().getCouleurJoueurGagnant());                                  
                    // On met le nouveau coup possible.
                    nouvelInstant.setCoupAJouer(unCoupPossible);                       
                    // Association à l'arbre père (et création du noeud fils).
                    pere.addFils(nouvelInstant);

                    // Si la profondeur est supérieure à 1, 
                    // on génère un nouveau fils dans l'arbre qui vient tout juste d'être créé
                    if (profondeur > 1) {
                        Tree<Instant> nouvelArbreFils = pere.getFils(pere.getNbFils() - 1);
                        genererArbreFilsInstant(profondeur - 1, nouvelArbreFils);
                    }
                }
            }
        }
        else
        {
            // Pour chaque coup possible, on créé un noeud contenant un instant différent.
            for(Coup unCoupPossible : coupsPossibles)
            {
                // Création d'un nouvel instant avec de nouvelles données.
                Instant nouvelInstant = new Instant();
                
                // Duplication de la matrice.
                int dim = pere.getRacine().getMatricePlateau().length;
                Couleur[][] tampon = new Couleur[dim][dim];        
                Couleur[][] matrice = pere.getRacine().getMatricePlateau();
                for(int i = 0; i < dim; i++)
                {
                    for(int j = 0; j < dim; j++)
                    {
                        tampon[i][j] = matrice[i][j];
                    }
                }                    
                nouvelInstant.setMatricePlateau(tampon);
                // On copie la couleur du joueur actif.
                nouvelInstant.setCouleurJoueurActif(pere.getRacine().getCouleurJoueurActif());
                // On copie la couleur du joueur devant gagner.
                nouvelInstant.setCouleurJoueurGagnant(pere.getRacine().getCouleurJoueurGagnant());     
                // On met le nouveau coup possible.
                nouvelInstant.setCoupAJouer(unCoupPossible);
                // On change la couleur du joueur actif.
                nouvelInstant.setCouleurJoueurActif(Couleur.getCouleurOpposee(pere.getRacine().getCouleurJoueurActif()));
                // Association à l'arbre père (et création du noeud fils).
                pere.addFils(nouvelInstant);

                // Si la profondeur est supérieure à 1, 
                // on génère un nouveau fils dans l'arbre qui vient tout juste d'être créé
                if (profondeur > 1) {
                    Tree<Instant> nouvelArbreFils = pere.getFils(pere.getNbFils() - 1);
                    genererArbreFilsInstant(profondeur - 1, nouvelArbreFils);
                }
            }
        }
        
        return pere;
    }
}
