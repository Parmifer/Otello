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

/**
 * Controleur de l'application réceptionnant les événements de l'interface et
 * activant les méthodes adéquates du modèle
 *
 * @author courbis
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
     * Ecouteur du controleur et activation des actions associées
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Si clic sur le bouton Reset
        if (e.getSource() == ihm.getBoutonReset())
        {
            // Réinitialisation de la partie
            this.plateau.reset();
            this.nombreDeClicBoutonAuto = 1;

        } 
        // JOUEUR BOT
        // Gestion des actions du joueur automatique   
        else if (e.getSource() == ihm.getBoutonJoueurAuto() && plateau.aQuiLeTour().isAutomate())
        {                    
            // Première sollicitation : Affichage des coups possibles (avec pions de type HIGHLIGHT)
            if(nombreDeClicBoutonAuto == 1)
            {
                ArrayList<Coup> coupsPossibles = plateau.aQuiLeTour().getListeCoupsPossibles();
                for (Iterator<Coup> i = coupsPossibles.iterator(); i.hasNext();)
                {
                    Coup unCoup = i.next();
                    plateau.setValue(unCoup.getLigne(), unCoup.getColonne(), Couleur.HIGHLIGHT);
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
                    // TODO
                    
                // Sinon, affichage d'un message d'erreur
                    // TODO
                    
                // Reset de la boucle de clic.
                nombreDeClicBoutonAuto = 1;
                
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
                    // TODO
                // Changement de joueur
                ihm.setTour();
            }
            // Sinon, affichage message d'erreur
            else
            {
                afficheMessageErreur("Ce coup n'est pas valide.");
            }
        }
    }

    /**
     * Affichage d'une fenêtre de message d'erreur
     *
     * @param messageTexte message d'erreur
     */
    private void afficheMessageErreur(String messageTexte) {
        JLabel message;

        message = new JLabel(messageTexte);
        JFrame frame = new JFrame("ERREUR");
        frame.getContentPane().add(message, BorderLayout.NORTH);
        frame.setPreferredSize(new Dimension(450, 80));
        frame.setLocation(100, 100);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

}
