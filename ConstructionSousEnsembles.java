package CompilationNote;

import java.util.*;

public class ConstructionSousEnsembles {

    // Méthode pour calculer l'e-fermeture d'un ensemble d'états
    public Set<Integer> eFermeture(Set<Integer> etats, Map<Integer, Set<Integer>> epsilonTransitions) {
        Set<Integer> fermeture = new HashSet<>();
        Stack<Integer> pile = new Stack<>();

        // Ajouter tous les états de l'ensemble dans la pile
        pile.addAll(etats);

        while (!pile.isEmpty()) {
            int etatCourant = pile.pop();
            fermeture.add(etatCourant);

            // Ajouter tous les états atteignables par epsilon-transition
            if (epsilonTransitions.containsKey(etatCourant)) {
                for (int etatSuivant : epsilonTransitions.get(etatCourant)) {
                    if (!fermeture.contains(etatSuivant)) {
                        pile.push(etatSuivant);
                    }
                }
            }
        }

        return fermeture;
    }

    // Méthode principale pour la construction des sous-ensembles
    public Map<Set<Integer>, Map<Character, Set<Integer>>> constructionSousEnsembles(
            Map<Integer, Map<Character, Set<Integer>>> transitions,
            Map<Integer, Set<Integer>> epsilonTransitions) {

        Map<Set<Integer>, Map<Character, Set<Integer>>> dtran = new HashMap<>();
        Queue<Set<Integer>> fileDetats = new LinkedList<>();

        // Initialiser avec l'e-fermeture de l'état initial
        Set<Integer> etatInitial = eFermeture(Collections.singleton(0), epsilonTransitions);
        fileDetats.add(etatInitial);
        dtran.put(etatInitial, new HashMap<>());

        while (!fileDetats.isEmpty()) {
            Set<Integer> etatActuel = fileDetats.poll();
            // Marquer l'état actuel

            // Vérifier si la map de transitions pour l'état actuel est vide
            Map<Character, Set<Integer>> transitionEtatActuel = transitions.get(etatActuel.iterator().next());
            if (transitionEtatActuel != null && !transitionEtatActuel.isEmpty()) {
                // Pour chaque symbole d'entrée
                for (char symbole : transitionEtatActuel.keySet()) {
                    Set<Integer> etatSuivant = new HashSet<>();
                    // Calculer la transition pour le symbole donné
                    for (int etat : etatActuel) {
                        Set<Integer> transitionsEtat = transitions.get(etat).get(symbole);
                        if (transitionsEtat != null) {
                            etatSuivant.addAll(transitionsEtat);
                        }
                    }
                    // Calculer l'e-fermeture de l'état suivant
                    Set<Integer> eFermetureEtatSuivant = eFermeture(etatSuivant, epsilonTransitions);

                    // Si l'état suivant n'est pas déjà dans Detats, l'ajouter
                    if (!dtran.containsKey(eFermetureEtatSuivant)) {
                        fileDetats.add(eFermetureEtatSuivant);
                        dtran.put(eFermetureEtatSuivant, new HashMap<>());
                    }

                    // Ajouter la transition à Dtran
                    dtran.get(etatActuel).put(symbole, eFermetureEtatSuivant);
                }
            }
        }

        return dtran;
    }

    public static void main(String[] args) {
        // Exemple d'utilisation
        ConstructionSousEnsembles construction = new ConstructionSousEnsembles();
        Map<Integer, Map<Character, Set<Integer>>> transitions = new HashMap<>();
        Map<Integer, Set<Integer>> epsilonTransitions = new HashMap<>();
        Map<Set<Integer>, Map<Character, Set<Integer>>> dtran = construction.constructionSousEnsembles(transitions,
                epsilonTransitions);
        // Afficher le résultat
        System.out.println(dtran);
    }
}
