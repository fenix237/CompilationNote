package CompilationNote;

import java.util.*;

public class AFNDtoAFD {

    public static Set<String> epsilonFermeture(Set<String> etats, Map<String, Map<Character, Set<String>>> transitions) {
        Set<String> fermeture = new HashSet<>(etats);
        Queue<String> aTraiter = new LinkedList<>(etats);
        
        while (!aTraiter.isEmpty()) {
            String etat = aTraiter.poll();
            if (transitions.containsKey(etat) && transitions.get(etat).containsKey('\u03B5')) {
                for (String etatSuivant : transitions.get(etat).get('\u03B5')) {
                    if (!fermeture.contains(etatSuivant)) {
                        fermeture.add(etatSuivant);
                        aTraiter.add(etatSuivant);
                    }
                }
            }
        }
        
        return fermeture;
    }

    public static Set<String> mouvement(Set<String> etats, char symbole, Map<String, Map<Character, Set<String>>> transitions) {
        Set<String> resultat = new HashSet<>();
        
        for (String etat : etats) {
            if (transitions.containsKey(etat) && transitions.get(etat).containsKey(symbole)) {
                resultat.addAll(transitions.get(etat).get(symbole));
            }
        }
        
        return resultat;
    }
    public static void main(String[] args) {
        // Définir les états, l'alphabet et les transitions de l'AFND
        Set<String> etats = new HashSet<>(Arrays.asList("q0", "q1", "q2"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();
        transitions.put("q0", Map.of('a', new HashSet<>(Arrays.asList("q0", "q1")), 'b', new HashSet<>(Arrays.asList("q0"))));
        transitions.put("q1", Map.of('a', new HashSet<>(Arrays.asList("q2")), 'b', new HashSet<>(Arrays.asList("q2"))));
        transitions.put("q2", Map.of('a', new HashSet<>(Arrays.asList("q2")), 'b', new HashSet<>(Arrays.asList("q2"))));

        // Créer un nouvel automate fini déterministe (AFD)
        Set<Set<String>> nouveauxEtats = new HashSet<>();
        Set<Map<Character, Set<String>>> nouvellesTransitions = new HashSet<>();
        Queue<Set<String>> etatsAFD = new LinkedList<>();
        Set<String> etatInitial = epsilonFermeture(Set.of("q0"), transitions);
        etatsAFD.add(etatInitial);
        nouveauxEtats.add(etatInitial);

        while (!etatsAFD.isEmpty()) {
            Set<String> etatCourant = etatsAFD.poll();
            Map<Character, Set<String>> nouvelleTransition = new HashMap<>();
            for (char symbole : alphabet) {
                Set<String> nouvelEtat = epsilonFermeture(mouvement(etatCourant, symbole, transitions), transitions);
                if (!nouveauxEtats.contains(nouvelEtat)) {
                    etatsAFD.add(nouvelEtat);
                    nouveauxEtats.add(nouvelEtat);
                }
                nouvelleTransition.put(symbole, nouvelEtat);
            }
            nouvellesTransitions.add(nouvelleTransition);
        }

        // Afficher les nouveaux états et les nouvelles transitions de l'AFD
        System.out.println("Nouveaux états de l'AFD : " + nouveauxEtats);
        System.out.println("Nouvelles transitions de l'AFD : " + nouvellesTransitions);
    }

    
}
