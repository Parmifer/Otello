
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

    public int getNombreBlancs() {
        return nombreBlancs;
    }

    public void setNombreBlancs(int nombreBlancs) {
        this.nombreBlancs = nombreBlancs;
    }

    public int getNombreNoirs() {
        return nombreNoirs;
    }

    public void setNombreNoirs(int nombreNoirs) {
        this.nombreNoirs = nombreNoirs;
    }
    
    @Override
    public int getHeuristique()
    {       
        int heuristique;
        
        if(this.couleurJoueurActif == this.couleurJoueurGagnant)
        {
            heuristique = (couleurJoueurActif == Couleur.BLANC) ? nombreBlancs - nombreNoirs : nombreNoirs - nombreBlancs; 
        }
        else
        {
            heuristique = (couleurJoueurActif == Couleur.NOIR) ? nombreBlancs - nombreNoirs : nombreNoirs - nombreBlancs;
        }
                
        return heuristique;
    }    
    
    /**
     * Joue le coup enregistré sur le plateau et retourne les pions si besoin.
     */
    public void jouerCoup()
    {
        // On joue le coup.
        this.matricePlateau[this.coupAJouer.getLigne()][this.coupAJouer.getColonne()] = this.couleurJoueurActif;
        
        if(this.couleurJoueurActif == Couleur.BLANC)
        {
            nombreBlancs++;                   
        }
        else
        {
            nombreNoirs++;                    
        }

        // Retournement des pions du plateau.
        ArrayList<Coup> aRetourner = Joueur.getListePionsARetourner(this.coupAJouer, this.matricePlateau, this.getCouleurJoueurActif());
        int nombreDeRetournements = aRetourner.size();
        for(int i = 0; i < nombreDeRetournements; i++)
        {
            this.matricePlateau[aRetourner.get(i).getLigne()][aRetourner.get(i).getColonne()] = this.couleurJoueurActif;
            
            if(this.couleurJoueurActif == Couleur.BLANC)
            {
                nombreBlancs++;                   
            }
            else
            {
                nombreNoirs++;                    
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
            // On récupère les coups à jouer.
            ArrayList<Coup> prochainsCoupsPossibles = Joueur.getListeCoupsPossibles(instantInitial.getMatricePlateau(), instantInitial.getCouleurJoueurActif());
            // On génère les sous arbres.
            arbreRacine = Instant.genererArbreFilsInstant(prochainsCoupsPossibles, profondeur - 1, arbreRacine);
            
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
    private static Tree<Instant> genererArbreFilsInstant(ArrayList<Coup> coupsPossibles, int profondeur, Tree<Instant> pere)
    {  
        /** INITIALISATION DES VARIABLES **/
        
        // Récupération des données du père
        // Couleurs
        int nombreDeBlancs = pere.getRacine().getNombreBlancs();
        int nombreDeNoirs = pere.getRacine().getNombreNoirs();
        // Couleur du joueur actif.
        Couleur couleurJoueurActif = pere.getRacine().getCouleurJoueurActif();
        // Couleur du joueur qui doit gagner.
        Couleur couleurJoueurGagnant = pere.getRacine().getCouleurJoueurGagnant(); 
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
        
        /** DEBUT DE L'ALGO **/        
        // Si aucun coup n'est possible.
        if(coupsPossibles.isEmpty())
        {
            // On calcule les coups possibles pour le joueur qui va jouer deux fois.
            coupsPossibles = Joueur.getListeCoupsPossibles(matrice, Couleur.getCouleurOpposee(couleurJoueurActif));
            // Si la partie n'est pas finie.
            if(!coupsPossibles.isEmpty())
            {
                // Pour chaque coup possible, on créé un noeud contenant un instant différent.
                for(Coup unCoupPossible : coupsPossibles)
                {                    
                    // Création d'un nouvel instant avec de nouvelles données.
                    Instant nouvelInstant = new Instant();                    
                    // On sauve la matrice.                    
                    nouvelInstant.setMatricePlateau(tampon);
                    // On copie la couleur du joueur actif.
                    // Elle ne sera pas changée puisque l'autre joueur a passé son tour.
                    nouvelInstant.setCouleurJoueurActif(couleurJoueurActif);  
                    // On met la couleur du joueur devant gagner.
                    nouvelInstant.setCouleurJoueurGagnant(couleurJoueurGagnant);
                    // On sauve le nombre de pions pour chaque couleur.
                    nouvelInstant.setNombreBlancs(nombreDeBlancs); 
                    nouvelInstant.setNombreNoirs(nombreDeNoirs); 
                    // On met le nouveau coup possible.
                    nouvelInstant.setCoupAJouer(unCoupPossible);                       
                    // Association à l'arbre père (et création du noeud fils).
                    pere.addFils(nouvelInstant);

                    // Si la profondeur est supérieure à 1, 
                    // on génère un nouveau fils dans l'arbre qui vient tout juste d'être créé
                    if (profondeur > 1) {
                        Tree<Instant> nouvelArbreFils = pere.getFils(pere.getNbFils() - 1);
                        // On récupère les coups à jouer.
                        ArrayList<Coup> prochainsCoupsPossibles = Joueur.getListeCoupsPossibles(nouvelInstant.getMatricePlateau(), nouvelInstant.getCouleurJoueurActif());
                        // On génère les sous arbres.
                        genererArbreFilsInstant(prochainsCoupsPossibles, profondeur - 1, nouvelArbreFils);
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
               
                nouvelInstant.setMatricePlateau(tampon);
                // On copie la couleur du joueur actif.
                nouvelInstant.setCouleurJoueurActif(couleurJoueurActif);
                // On copie la couleur du joueur devant gagner.
                nouvelInstant.setCouleurJoueurGagnant(couleurJoueurGagnant); 
                // On sauve le nombre de pions pour chaque couleur.
                nouvelInstant.setNombreBlancs(nombreDeBlancs); 
                nouvelInstant.setNombreNoirs(nombreDeNoirs); 
                // On met le nouveau coup possible.
                nouvelInstant.setCoupAJouer(unCoupPossible);
                // On change la couleur du joueur actif.
                nouvelInstant.setCouleurJoueurActif(Couleur.getCouleurOpposee(couleurJoueurActif));
                // Association à l'arbre père (et création du noeud fils).
                pere.addFils(nouvelInstant);

                // Si la profondeur est supérieure à 1, 
                // on génère un nouveau fils dans l'arbre qui vient tout juste d'être créé
                if (profondeur > 1) {
                    Tree<Instant> nouvelArbreFils = pere.getFils(pere.getNbFils() - 1);
                    // On récupère les coups à jouer.
                    ArrayList<Coup> prochainsCoupsPossibles = Joueur.getListeCoupsPossibles(nouvelInstant.getMatricePlateau(), nouvelInstant.getCouleurJoueurActif());
                    // On génère les sous arbres.
                    genererArbreFilsInstant(prochainsCoupsPossibles, profondeur - 1, nouvelArbreFils);
                }
            }
        }
        
        return pere;
    }
}
