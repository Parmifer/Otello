package Modele;

/**
 * modélisation d'un coup en terme de ligne et colonne du plateau de jeu
 * @author courbis
 *
 */
public class Coup
{	private int lig;
	private int col;
	/**
	 * création d'un coup 
	 * @param l ligne 
	 * @param c colonne
	 */
	public Coup(int l, int c) 
	{	lig=l;
		col=c;
	}
	public Coup()
	{
		
	}
	
	/**
	 * accesseur à la ligne d'un coup
	 * @return
	 */
	public int getLigne()
	{ 	return(lig);
	}
/**
 * accesseur à la colonne d'un coup
 * @return
 */
	public int getColonne()
	{ 	return(col);
	}
	
}
