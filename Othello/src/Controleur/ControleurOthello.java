package Controleur;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Modele.Couleur;
import Modele.Coup;
import Modele.Joueur;
import Modele.Plateau;
import Vue.InterfaceOthello;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * Controleur de l'application réceptionnant les événements de l'interface et
 * activant les méthodes adéquates du modèle
 *
 * @author Mme Courbis - Yann Butscher - Lucile Decrozant-Triquenaux
 *
 */
public class ControleurOthello implements ActionListener {
    
    // Modèle
    Plateau plateau;
    Joueur joueur1;
    Joueur joueur2;
    
    // Vue
    InterfaceOthello ihm;
    
    // Controleur
    int nombreDeClicBoutonAuto;

    /**
     * initialisation du controleur par des liens sur le modèle ( les joueurs et
     * le plateau) et la vue (l'interface graphique)
     *
     * @param p plateau de jeu
     * @param j1 joueur1
     * @param j2 joueur2
     * @param vue interface graphique
     */
    ControleurOthello(Plateau p, Joueur j1, Joueur j2, InterfaceOthello vue) {
        plateau = p;
        joueur1 = j1;
        joueur2 = j2;
        ihm = vue;
        nombreDeClicBoutonAuto = 1;
    }
    
    /**
     * Permet de réinitialiser une partie.
     */
    public void resetJeu() {
        // Réinitialisation de la partie
        this.plateau.reset();
        this.nombreDeClicBoutonAuto = 1;
        if(plateau.aQuiLeTour().getCouleur() == Couleur.BLANC)
        {                
            this.ihm.setTour();
        }

        this.ihm.setScore();
    }
    
    /**
     * Ecouteur du controleur et activation des actions associées
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Si clic sur le bouton Reset
        if (e.getSource() == ihm.getBoutonReset())
        {
            // TODO : Ajouter une confirmation !
            this.resetJeu();
        } 
        // JOUEUR BOT
        // Gestion des actions du joueur automatique   
        else if (e.getSource() == ihm.getBoutonJoueurAuto() && plateau.aQuiLeTour().isAutomate())
        {                    
            // Première sollicitation : Affichage des coups possibles (avec pions de type HIGHLIGHT)
            if(nombreDeClicBoutonAuto == 1)
            {
                ArrayList<Coup> coupsPossibles = plateau.aQuiLeTour().getListeCoupsPossibles();
                int nombreDeCoupsPossibles = coupsPossibles.size();
                for (int i = 0; i < nombreDeCoupsPossibles; i++)
                {
                    plateau.setValue(coupsPossibles.get(i).getLigne(), coupsPossibles.get(i).getColonne(), Couleur.HIGHLIGHT);
                }
                nombreDeClicBoutonAuto++;
            }
            // Deuxième sollicitation : 
            else if(nombreDeClicBoutonAuto == 2)
            {
                // On enlève les highlight.
                plateau.retirerHighlight();
                
                // Affichage du coup demandé par le joueur automatique  
                Coup coupAleatoire = plateau.aQuiLeTour().joue();                
                plateau.setValue(coupAleatoire.getLigne(), coupAleatoire.getColonne(), plateau.aQuiLeTour().getCouleur());   
                
                // Retournement des pions du plateau si le coup demandé est valide
                ArrayList<Coup> aRetourner = plateau.aQuiLeTour().getListePionsARetourner(coupAleatoire);
                int nombreDeRetournements = aRetourner.size();
                for(int i = 0; i < nombreDeRetournements; i++)
                {
                    plateau.setValue(aRetourner.get(i).getLigne(), aRetourner.get(i).getColonne(), plateau.aQuiLeTour().getCouleur());
                }
                    
                // Reset de la boucle de clic.
                nombreDeClicBoutonAuto = 1;
                
                // Affichage des scores
                ihm.setScore(); 
                
                // Changement de joueur
                ihm.setTour();           
            }
        } 
        // JOUEUR HUMAIN
        // Gestion des actions du joueur humain
        else if (plateau.aQuiLeTour().isHumain() && e.getSource() != ihm.getBoutonJoueurAuto()) {
            // Calcul des coordonnées du Coup en fonction du bouton sélectionné sur l'interface.
            JButton jtemp = ((JButton) (e.getSource()));
            int largeur = jtemp.getSize().width;
            int hauteur = jtemp.getSize().height;
            int clickCoordY = jtemp.getX() / largeur + 1;
            int clickCoordX = jtemp.getY() / hauteur + 1;            
            Coup coup = new Coup(clickCoordX - 1, clickCoordY - 1);            
             
            // Si le coup demandé par le joueur humain est valide.
            if(plateau.coupDemande(coup))
            {
                // Affichage de celui-ci.
                plateau.setValue(coup.getLigne(), coup.getColonne(), plateau.aQuiLeTour().getCouleur());
                
                // Retournement des pions du plateau.
                ArrayList<Coup> aRetourner = plateau.aQuiLeTour().getListePionsARetourner(coup);
                int nombreDeRetournements = aRetourner.size();
                for(int i = 0; i < nombreDeRetournements; i++)
                {
                    plateau.setValue(aRetourner.get(i).getLigne(), aRetourner.get(i).getColonne(), plateau.aQuiLeTour().getCouleur());
                }
                
                // Affichage des scores
                ihm.setScore(); 
                
                // Changement de joueur
                ihm.setTour();                
            }
            // Sinon, affichage message d'erreur
            else
            {
                afficheMessageErreur("Ce coup n'est pas valide.");
            }
        }
        
        if(plateau.getFinDePartie())
        {
            this.resetJeu();
        }
    }

    /**
     * Affichage d'une fenêtre de message d'erreur
     *
     * @param messageTexte message d'erreur
     */
    private void afficheMessageErreur(String messageTexte) {
        
        JOptionPane.showMessageDialog(ihm, messageTexte, "ERREUR", JOptionPane.ERROR_MESSAGE);
    }

}
