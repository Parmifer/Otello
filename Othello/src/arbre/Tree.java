package arbre;

import java.util.ArrayList;

/**
 * Implémentation d'un arbre Naire générique
 *
 * @author Anne-lise Courbis - Yann Butscher - Lucile Decrozant-Triquenaux
 *
 * @param <T> Fixé lors de la création de l'arbre, contenu de chaque noeud de l'arbre
 */
public class Tree<T> {

    private T _racine;
    private ArrayList<Tree<T>> _fils;
    private Tree<T> _pere;
    private int _largeur;
    private int _profondeur;

    /**
     * Crée d'un arbre vide
     */
    public Tree() {
        this._racine = null;
        this._pere = null;
        this._fils = new ArrayList<>();
        this._largeur = 0;
        this._profondeur = 0;
    }

    /**
     * Crée un arbre avec une racine donnée
     *
     * @param noeudRacine Racine de l'arbre
     * @param profondeur Profondeur de l'arbre
     * @param largeur Largeur de l'arbre
     */
    public Tree(T noeudRacine, int profondeur, int largeur) {
        this._racine = noeudRacine;
        this._pere = null;
        this._fils = new ArrayList<>();
        this._largeur = largeur;
        this._profondeur = profondeur;
    }

    /**
     * Crée un arbre avec une racine donnée, racine étant un arbre
     *
     * @param noeudRacine
     * @param arbrePere
     */
    public Tree(T noeudRacine, Tree<T> arbrePere) {
        this._racine = noeudRacine;
        this._pere = arbrePere;
        this._fils = new ArrayList<>();
    }

    /**
     * Initialise la racine d'un arbre
     *
     * @param noeudRacine
     */
    public void setRacine(T noeudRacine) {
        this._racine = noeudRacine;
    }

    /**
     * Retourne le noeud racine de l'arbre
     *
     * @return Le noeud racine
     */
    public T getRacine() {
        return this._racine;
    }

    /**
     * Ajoute un fils à l'arbre
     *
     * @param noeudFils Le noeud à ajouter
     */
    public void addFils(T noeudFils) {
        Tree<T> arbreFils = new Tree<>(noeudFils, this);
        this._fils.add(arbreFils);
    }

    /**
     * Retourne le noeud fils correspondant à l'index passé en paramètre
     *
     * @param indexFils L'index du noeud à retourner par rapport à son père
     * @return Tree<> Le noeud corespondant à l'index
     */
    public Tree<T> getFils(int indexFils) {
        return this._fils.get(indexFils);
    }

    /**
     * Retourne le nombre de fils appartenants à l'arbre
     *
     * @return int Nombre de fils existants dan l'arbre
     */
    public int getNbFils() {
        return this._fils.size();
    }

    public int getLargeur() {
        return _largeur;
    }

    public int getProfondeur() {
        return _profondeur;
    }

    public ArrayList<Tree<T>> getFils() {
        return _fils;
    }

    /**
     * Teste si noeud est une feuille
     *
     * @return boolean True si le noeud est une feuille, false sinon.
     */
    public boolean isFeuille() {
        return this.getNbFils() == 0;
    }

    /**
     * Retourne le noeud père
     *
     * @return Tree<> Le noeud père
     */
    public Tree<T> getPere() {
        return this._pere;
    }

    /**
     * Parcourt en profondeur de l'arbre
     */
    public void deepthSearch() {
        // Affichage de la valeur contenue dans le noeud
        System.out.print(this.getRacine());
        int nombreDeFils = this.getNbFils();
        // On parcourt les _fils en _profondeur
        for (int i = 0; i < nombreDeFils; i++) {
            this.getFils(i).deepthSearch();
        }
    }

    /**
     * Parcourt en largeur de l'arbre
     */
    public void widthSearch() {
        // Création de la liste qui sert à stocker les noeuds qu'on parcourt
        ArrayList<Tree<T>> noeudsReorganises = new ArrayList();
        // On stocke le premier noeud, soit l'arbre dans lequel on se trouve
        noeudsReorganises.add(this);
        // Tant qu'il va rester des choses à afficher dans la liste, on continue notre traitement
        do {
            // On récupère le premier noeud stocké dans la liste pour l'afficher
            Tree<T> arbre = noeudsReorganises.get(0);
            System.out.print(this.getRacine());
            // On enlève le noeud de la liste (il a déjà été affiché, on n'en a plus besoin)
            noeudsReorganises.remove(0);
            // Si l'arbre a des fils, on va les stocker à leur tour dans la liste pour les afficher par la suite
            if (!arbre.isFeuille()) {
                for (int i = 0; i < arbre.getNbFils(); i++) {
                    noeudsReorganises.add(arbre.getFils(i));
                }
            }
        } while (!noeudsReorganises.isEmpty());
    }
}
