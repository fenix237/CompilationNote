package CompilationNote;

import java.util.Scanner;

public class DFA {

    // Déclaration des propriétés du DFA
    private int[] Q;  // Ensemble des états
    private int q0;   // Etat initial
    private int[][] š; // Fonction de transition
    private int[] F;  // Ensemble des états finaux

    // Constructeur pour initialiser le DFA avec les propriétés données
    public DFA(int[] Q, int q0, int[][] š, int[] F) {
        this.Q = Q;
        this.q0 = q0;
        this.š = š;
        this.F = F;
    }

    // Méthode pour reconnaître un mot donné
    public boolean reconnaîtreMot(String mot) {
        int q = q0; // Initialiser l'état actuel avec l'état initial q0
        for (int i = 0; i < mot.length(); i++) {
            char ui = mot.charAt(i); // Récupérer le caractère à la position i dans le mot
            int uiIndex = ui - 'a'; // Supposant que le DFA reconnaît des lettres minuscules
            q = š[q][uiIndex]; // Calculer le nouvel état en utilisant la fonction de transition
        }
        // Vérifier si l'état final q appartient à l'ensemble des états finaux F
        for (int state : F) {
            if (q == state) {
                return true; // Le mot est reconnu
            }
        }
        return false; // Le mot n'est pas reconnu
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Définir les propriétés du DFA
        int[] Q = {0, 1, 2}; // Ensemble des états {q0, q1, q2}
        int q0 = 0; // Etat initial q0
        int[][] š = {{1, 0}, {1, 2}, {2, 2}}; // Fonction de transition š(q, ui)
        int[] F = {1}; // Ensemble des états finaux {q1}
        
        // Créer une instance du DFA
        DFA dfa = new DFA(Q, q0, š, F);

        // Saisir le mot à reconnaître
        System.out.print("Entrez le mot à reconnaître : ");
        String mot = scanner.nextLine();

        // Vérifier si le mot est reconnu par le DFA
        boolean estReconnu = dfa.reconnaîtreMot(mot);

        // Afficher le résultat
        if (estReconnu) {
            System.out.println("Le mot est reconnu par le DFA.");
        } else {
            System.out.println("Le mot n'est pas reconnu par le DFA.");
        }

        // Fermer le scanner
        scanner.close();
    }
}
