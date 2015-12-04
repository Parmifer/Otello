package testArbre;

import arbre.Tree;

/**
 *
 * @author Yann Butscher - Lucile Decrozant-Triquenaux
 */
public class Test {

    static int minPrises = 1;
    static int maxPrises = 15;

    /**
     * Programme de test, se lance et va afficher les différents essais des classes et méthodes du projet
     *
     * @param args Arguments passés au programme, inutilisés dans notre cas
     */
    public static void main(String[] args) {
        // Création d'un arbre 3*3 
        // Parcours de celui-ci en profondeur
        Test._afficherTitre(1, 3, 3);
        Tree<Info> monArbre = genererArbreInfo(3, 3);
        System.out.println("----- deepthSearch() ------");
        System.out.println("---- Résultat attendu -----");
        System.out.println("1 10 100 101 102 11 110 111 112 12 120 121 122");
        System.out.println("---- Résultat obtenu ------");
        monArbre.deepthSearch();

        // Création d'un arbre 5*2
        // Parcours de celui-ci en profondeur
        Test._afficherTitre(2, 5, 2);
        Tree<Info> monArbre2 = genererArbreInfo(5, 2);
        System.out.println("----- deepthSearch() ------");
        System.out.println("---- Résultat attendu -----");
        System.out.println("1 10 100 1000 10000 10001 1001 10010 10011 101 1010 10100 10101 1011 10110 10111 11 110 1100 11000 11001 1101 11010 11011 111 1110 11100 11101 1111 11110 11111");
        System.out.println("---- Résultat obtenu ------");
        monArbre2.deepthSearch();

        // Création d'un arbre 4*2
        // Parcours de celui-ci en largeur
        Test._afficherTitre(3, 4, 2);
        Tree<Info> monArbre3 = genererArbreInfo(4, 2);
        System.out.println("------ widthSearch() ------");
        System.out.println("---- Résultat attendu -----");
        System.out.println("1 10 11 100 101 110 111 1000 1001 1010 1011 1100 1101 1110 1111");
        System.out.println("---- Résultat obtenu ------");
        monArbre3.widthSearch();

        // Création d'un arbre random 4*3
        // Parcours de celui-ci en profondeur puis en largeur
        Test._afficherTitre(4, 4, 3);
        Tree<Info> monArbre4 = genererArbreInfoRandom(4, 3);
        System.out.println("----- deepthSearch() ------");
        System.out.println("---- Résultat obtenu ------");
        monArbre4.deepthSearch();
        System.out.println("");
        System.out.println("------ widthSearch() ------");
        System.out.println("---- Résultat obtenu ------");
        monArbre4.widthSearch();
    }

    private static int _maxiVal(Tree<Info> arbre) {
        int heuristique = 0;

        if (arbre.isFeuille()) {
            heuristique = arbre.getRacine().getValue();
        } else {
            int nombreDeFils = arbre.getNbFils();
            for (int i = 0; i < nombreDeFils; i++) {
                heuristique = Math.max(heuristique, _miniVal(arbre.getFils(i)));
            }
        }

        return heuristique;
    }

    private static int _miniVal(Tree<Info> arbre) {
        int heuristique = 0;

        if (arbre.isFeuille()) {
            heuristique = arbre.getRacine().getValue();
        } else {
            int nombreDeFils = arbre.getNbFils();
            for (int i = 0; i < nombreDeFils; i++) {
                heuristique = Math.min(heuristique, _maxiVal(arbre.getFils(i)));
            }
        }

        return heuristique;
    }

    public static Tree<Info> minMax(Tree<Info> arbre) {
        int heuristique = 0;

        int nombreDeFils = arbre.getNbFils();
        for (int i = 0; i < nombreDeFils; i++) {
            heuristique = Math.max(heuristique, _maxiVal(arbre.getFils(i)));
        }

        Tree<Info> noeudARetourner;
        int indexFils = 0;
        do {
            noeudARetourner = arbre.getFils(indexFils++);
        } while (indexFils < nombreDeFils && heuristique != noeudARetourner.getRacine().getValue());
        noeudARetourner.getRacine().imprim();

        return noeudARetourner;
    }

    /**
     * Génère un arbre à partir de la racine
     *
     * @param profondeur Profondeur de l'arbre désirée
     * @param largeur Largeur de l'arbre désirée
     * @return L'arbre généré
     */
    public static Tree<Info> genererArbreInfo(int profondeur, int largeur) {
        // En cas de profondeur 0, on retourne un arbre vide
        if (profondeur < 1) {
            return new Tree<>();
        } else {
            // On génère la racine.
            Info racine = new Info(1);
            Tree<Info> arbreRacine = new Tree<>(racine, profondeur, largeur);

            // Si la profondeur est supérieure à 1, on va créer des fils à l'arbre
            if (profondeur > 1) {
                arbreRacine = genererArbreFilsInfo(profondeur - 1, largeur, arbreRacine);
            }

            return arbreRacine;
        }
    }

    /**
     * Génère un arbre à partir de la racine en le remplissant avec des nombres aléatoires
     *
     * @param profondeur Profondeur de l'arbre désirée
     * @param largeur Largeur de l'arbre désirée
     * @return L'arbre généré
     */
    public static Tree<Info> genererArbreInfoRandom(int profondeur, int largeur) {
        // En cas de profondeur 0, on retourne un arbre vide
        if (profondeur < 1) {
            return new Tree<>();
        } else {
            // On génère la racine à partir d'un nombre aléatoire
            int nombreAleatoire = Test.minPrises + (int) (Math.random() * ((Test.maxPrises - Test.minPrises) + Test.minPrises));
            Info racine = new Info(nombreAleatoire);
            Tree<Info> arbreRacine = new Tree<>(racine, profondeur, largeur);

            // Si la profondeur est supérieure à 1, on va créer des fils à l'arbre
            if (profondeur > 1) {
                arbreRacine = genererArbreFilsInfoRandom(profondeur - 1, largeur, arbreRacine);
            }

            return arbreRacine;
        }
    }

    /**
     * Génère un fils dans un arbre existant
     *
     * @param profondeur Profondeur de l'arbre désirée
     * @param largeur Largeur de l'arbre désirée
     * @param pere Père de l'arbre que l'on souhaite générer
     * @return L'arbre père modifié avec le nouvel arbre fils
     */
    public static Tree<Info> genererArbreFilsInfo(int profondeur, int largeur, Tree<Info> pere) {
        // Pour chaque branche définie par largeur, on va créer un arbre fils
        for (int i = 0; i < largeur; i++) {
            // Création d'un fils
            Info nouveauNoeudFils = new Info(pere.getRacine().getValue() * 10 + i);
            pere.addFils(nouveauNoeudFils);

            // Si la profondeur est supérieure à 1, 
            // on génère un nouveau fils dans l'arbre qui vient tout juste d'être créé
            if (profondeur > 1) {
                Tree<Info> nouvelArbreFils = pere.getFils(i);
                genererArbreFilsInfo(profondeur - 1, largeur, nouvelArbreFils);
            }
        }

        return pere;
    }

    /**
     * Génère un fils dans un arbre existant, contenant des nombres aléatoires
     *
     * @param profondeur Profondeur de l'arbre désirée
     * @param largeur Largeur de l'arbre désirée
     * @param pere Père de l'arbre que l'on souhaite générer
     * @return L'arbre père modifié avec le nouvel arbre fils
     */
    public static Tree<Info> genererArbreFilsInfoRandom(int profondeur, int largeur, Tree<Info> pere) {
        // Pour chaque branche définie par largeur, on va créer un arbre fils
        for (int i = 0; i < largeur; i++) {
            // Création d'un fils
            int nombreAleatoire = Test.minPrises + (int) (Math.random() * ((Test.maxPrises - Test.minPrises) + Test.minPrises));
            Info nouveauNoeudFils = new Info(nombreAleatoire);
            pere.addFils(nouveauNoeudFils);

            // Si la profondeur est supérieure à 1, 
            // on génère un nouveau fils dans l'arbre qui vient tout juste d'être créé
            if (profondeur > 1) {
                Tree<Info> nouvelArbreFils = pere.getFils(i);
                genererArbreFilsInfoRandom(profondeur - 1, largeur, nouvelArbreFils);
            }
        }

        return pere;
    }

    /**
     * Affiche en console le titre correspondant à un test
     *
     * @param numero Numéro du test
     * @param largeur Largeur de l'arbre créé pourle test
     * @param profondeur Profondeur de l'arbre créé pour le test
     */
    private static void _afficherTitre(int numero, int largeur, int profondeur) {
        System.out.println("");
        System.out.println("");
        System.out.println("******************************************************");
        System.out.println("---------------------------");
        System.out.println("----- Arbre " + numero + " : " + largeur + " x " + profondeur + " -----");
        System.out.println("---------------------------");
        System.out.println("***************************");
    }
}
