package Modele;

public enum TypeJoueur {AUTOMATE(0),HUMAIN(1);

private int codeJoueur;

TypeJoueur(int nb){
	codeJoueur=nb;
}

public int getCodeJoueur(){
	return codeJoueur;
}

};
