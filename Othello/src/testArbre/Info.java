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

    private int _value;

    public Info(int val) {
        _value = val;
    }

    public Info() {
        _value = -1;
    }

    public void setValue(int val) {
        _value = val;
    }

    public int getValue() {
        return _value;
    }

    /**
     * Affiche en console la valeur de l'objet
     */
    public void imprim() {
        System.out.print(_value + " ");
    }
    
    @Override
    public String toString() {
        String valeur = Integer.toString(this.getValue()) + " ";
        return valeur;
    }
}
