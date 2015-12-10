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
    //modèle

    Plateau plateau;
    Joueur joueur1;
    Joueur joueur2;
    // vue
    InterfaceOthello ihm;

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

    }

    /**
     * Ecouteur du controleur et activation des actions associées
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == ihm.getBoutonReset()) {
            // Réinitialisation de la partie
            this.plateau.reset();

        } // JOUEUR BOT
        else if (e.getSource() == ihm.getBoutonJoueurAuto() && plateau.aQuiLeTour().isAutomate()) {
            // Gestion des actions du joueur automatique : 
            // 1ère sollicitation : Affichage des coups possibles (avec pions de type HIGHLIGHT)
            ArrayList<Coup> coupsPossibles = plateau.aQuiLeTour().getListeCoupsPossibles();
            for (Iterator<Coup> i = coupsPossibles.iterator(); i.hasNext();) {
                Coup unCoup = i.next();
                plateau.setValue(unCoup.getLigne(), unCoup.getColonne(), Couleur.HIGHLIGHT);
            }
            // 2ème sollicitation : affichage du coup demandé par le joueur automatique et retournement des pions du plateau si le coup demandé est valide,
            // sinon, affichage message d'erreur

            // TO DO
            // exemple :
//            Coup coup = plateau.aQuiLeTour().joue();
//            plateau.setValue(coup.getLigne(), coup.getColonne(), Couleur.NOIR);
        } // JOUEUR HUMAIN
        else if (plateau.aQuiLeTour().isHumain() && e.getSource() != ihm.getBoutonJoueurAuto()) {
            // calcul des coordonnées du Coup en fonction du bouton sélectionné sur l'interface
            JButton jtemp = ((JButton) (e.getSource()));
            int largeur = jtemp.getSize().width;
            int hauteur = jtemp.getSize().height;
            int clickCoordY = jtemp.getX() / largeur + 1;
            int clickCoordX = jtemp.getY() / hauteur + 1;
            Coup coup = new Coup(clickCoordX - 1, clickCoordY - 1);
            // gestion des actions du joueur humain: 
            // affichage du coup demandé par le joueur humain si celui-ci est valide et retournement des pions du plateau (sinon, affichage message d'erreur)
            // TO DO	
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
