package Modele;


	/**
	 * Couleur des pions (noir, blanc)
	 * et Couleur des cellules du plateau (pas de pion, possibilité de jeu)
	 * @author courbis
	 *
	 */
public enum Couleur {BLANC,NOIR,VIDE,HIGHLIGHT;

	public static Couleur getCouleurOpposee(Couleur c)
	{
		if (c.compareTo(BLANC)==0)
				return Couleur.NOIR;
		else
			if (c.compareTo(NOIR)==0)
				return Couleur.BLANC;
			else
				if (c.compareTo(VIDE)==0)
					return Couleur.VIDE;
				else
					return Couleur.HIGHLIGHT;
	}

};
