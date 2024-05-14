package CompilationNote;

import java.util.*;

public class EFermetureCalculator {

    public Set<Integer> eFermeture(int etat, Map<Integer, Set<Integer>> epsilonTransitions) {
        Set<Integer> fermeture = new HashSet<>();
        Stack<Integer> pile = new Stack<>();
        pile.push(etat);
        fermeture.add(etat); // Initialiser e-fermeture(T) à T

        while (!pile.isEmpty()) {
            int etatCourant = pile.pop();

            // Parcourir tous les états u avec un arc de t à u étiqueté epsilon
            if (epsilonTransitions.containsKey(etatCourant)) {
                for (int etatSuivant : epsilonTransitions.get(etatCourant)) {
                    if (!fermeture.contains(etatSuivant)) {
                        fermeture.add(etatSuivant);
                        pile.push(etatSuivant); // Empiler U dans Pile
                    }
                }
            }
        }

        return fermeture;
    }

    // public static void main(String[] args) {
    //     // Exemple d'utilisation
    //     EFermetureCalculator calculator = new EFermetureCalculator();
    //     Map<Integer, Set<Integer>> epsilonTransitions = new HashMap<>();
    //     // Ajoutez vos epsilon-transitions à epsilonTransitions ici

    //     int etatInitial = 0;
    //     Set<Integer> eFermetureEtatInitial = calculator.eFermeture(etatInitial, epsilonTransitions);
    //     System.out.println("e-fermeture de l'état initial : " + eFermetureEtatInitial);
    // }

    // public static void main(String[] args) {
    //     // Création des transitions epsilon
    //     Map<Integer, Set<Integer>> epsilonTransitions = new HashMap<>();
    //     epsilonTransitions.put(0, new HashSet<>(Arrays.asList(1))); // Transition de 0 à 1
    //     epsilonTransitions.put(1, new HashSet<>(Arrays.asList(2))); // Transition de 1 à 2
    //     epsilonTransitions.put(1, new HashSet<>(Arrays.asList(0))); // Transition de 1 à 0 (retour)

    //     // Calcul de l'e-fermeture de l'état initial 0
    //     EFermetureCalculator calculator = new EFermetureCalculator();
    //     int etatInitial = 0;
    //     Set<Integer> eFermetureEtatInitial = calculator.eFermeture(etatInitial, epsilonTransitions);

    //     // Affichage du résultat
    //     System.out.println("e-fermeture de l'état initial " + etatInitial + " : " + eFermetureEtatInitial);
    // }

    public static void main(String[] args) {
        // Création des transitions epsilon
        Map<Integer, Set<Integer>> epsilonTransitions = new HashMap<>();
        epsilonTransitions.put(0, new HashSet<>(Arrays.asList(1))); // Transition de 0 à 1
        epsilonTransitions.put(1, new HashSet<>(Arrays.asList(2))); // Transition de 1 à 2
        epsilonTransitions.put(1, new HashSet<>(Arrays.asList(0, 2))); // Transition de 1 à 0 et 2 (retour)

        // Calcul de l'e-fermeture de chaque état
        EFermetureCalculator calculator = new EFermetureCalculator();
        Map<Integer, Set<Integer>> eFermetures = new HashMap<>();
        for (int etat : epsilonTransitions.keySet()) {
            Set<Integer> eFermetureEtat = calculator.eFermeture(etat, epsilonTransitions);
            eFermetures.put(etat, eFermetureEtat);
        }

        // Affichage du résultat
        for (int etat : eFermetures.keySet()) {
            System.out.println("e-fermeture de l'état " + etat + " : " + eFermetures.get(etat));
        }
    }
}
