package testArbre;

/**
 * Classe pour tester l'implantation d'un arbre Naire générique
 * Cette classe permet de mettre des objets génériques pratiques à afficher 
 * dans les arbres formés à partir de la classe Tree
 *
 * @author Anne-lise Courbis - Lucile Decrozant-Triquenaux
 *
 */
public class Info {

    private int value;

    public Info(int val) {
        value = val;
    }

    public Info() {
        value = -1;
    }

    public void setValue(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    /**
     * Affiche en console la valeur de l'objet
     */
    public void imprim() {
        System.out.print(value + " ");
    }
    
    @Override
    public String toString() {
        String valeur = Integer.toString(this.getValue()) + " ";
        return valeur;
    }
}
