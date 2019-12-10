class Game extends Program{

    /* Résumé des fonctions
    printart() : Affiche toute les lignes d'une liste (pour les ASCII Arts)
    chooseName(String user) : Permet la saisie du nom du joueur "user" */

    void algorithm(){
        
        /* Initialisation des ASCII Arts dans des listes */

        String[] menuart = new String[15];
        menuart[0] = "      ::::::::    :::::::::::    :::    :::        :::::::::     :::   ::: ";
        menuart[1] = "    :+:    :+:       :+:        :+:    :+:        :+:    :+:    :+:   :+:  ";
        menuart[2] = "   +:+              +:+        +:+    +:+        +:+    +:+     +:+ +:+    ";
        menuart[3] = "  +#++:++#++       +#+        +#+    +:+        +#+    +:+      +#++:      ";
        menuart[4] = "        +#+       +#+        +#+    +#+        +#+    +#+       +#+        ";
        menuart[5] = "#+#    #+#       #+#        #+#    #+#        #+#    #+#       #+#         ";
        menuart[6] = "########        ###         ########         #########        ###          ";
        menuart[7] = "";
        menuart[8] = "      :::::::::           :::         :::::::::    :::::::::::    :::   ::: ";
        menuart[9] = "     :+:    :+:        :+: :+:       :+:    :+:       :+:        :+:   :+:  ";
        menuart[10] = "    +:+    +:+       +:+   +:+      +:+    +:+       +:+         +:+ +:+    ";
        menuart[11] = "   +#++:++#+       +#++:++#++:     +#++:++#:        +#+         +#++:       ";
        menuart[12] = "  +#+             +#+     +#+     +#+    +#+       +#+           +#+        ";
        menuart[13] = " #+#             #+#     #+#     #+#    #+#       #+#           #+#         ";
        menuart[14] = "###             ###     ###     ###    ###       ###           ###          ";

        String[] board = new String[10];
        board[0] = "------>";
        board[1] = "[Début] - [Exo] - [Exo] - [Exo] ";
        board[2] = "    /                          \\ ";
        board[3] = " [Exo]                        [Bonus]";
        board[4] = "   |                             |";
        board[5] = "[Malus]                        [Exo]";
        board[6] = "   |                             |";
        board[7] = " [Exo]                        [Malus]";
        board[8] = "    \\                          /";
        board[9] = "      [Bonus] - [Exo] -  [Exo]";
        
        /* Menu */
        
        clearScreen();
        cursor(0,0);
        boolean fin = false;
        printart(menuart);
        println("1. Jouer");
        println("2. Quitter");
        String entreeUtilisateur = choix();
        clearScreen();
        cursor(0,0);
        if(equals(entreeUtilisateur,"1")){
            int tourActuel = 1;
            int p1coins = 0;
            int p2coins = 0;
            boolean gagne = false;
            clearScreen();
            cursor(0,0);
            println("Partie en combien de tours?");
            int maxTour = readInt();
            String p1name = chooseName("Joueur 1");
            String p2name = chooseName("Joueur 2");
            clearScreen();
            cursor(0,0);
            println("P1 : " + p1name);
            println("P2 : " + p2name);
            println("Appuyer sur entrée pour démarrer la partie.");
            String throwaway = readString();
            game(tourActuel, p1coins, p2coins, gagne, p1name, p2name, maxTour, board);
        } else if(equals(entreeUtilisateur,"2")){
            println("Quitter");
        }
    }

    void game(int tourActuel, int p1coins, int p2coins, boolean gagne, String p1name, String p2name, int maxTour, String[] board){
        clearScreen();
        cursor(0,0);
        while(!gagne && tourActuel <= maxTour){
            printart(board);
            printstatus(tourActuel, p1name, p2name, p1coins, p2coins, maxTour);
            println("1. Lancer le dé");
            println("2. Debug : avancer le tour");
            String entreeUtilisateur = choix();
            if(equals(entreeUtilisateur, "1")){
                println("Lancer le dé");
            } else if(equals(entreeUtilisateur, "2")){
                println("AvaAvancence du tour");
            }
            tourActuel++;
            if(tourActuel > maxTour){
                gagne=true;
            }
            clearScreen();
            cursor(0,0);
        }
    }
    
    String choix(){
        /* Note : cette fonction permet de controller si l'utilisateur entre 1 ou 2.
        On a pas besoin de plus, mais cette fonction peut être améliorée. */
        boolean fin = false;
        String entreeUtilisateur = ""; /* Pourquoi un String et non un int? parce que si on tappe un String durant le readInt, on a une exception et le programme s'arrête */
        while(!fin){
            entreeUtilisateur = readString();
            if(equals(entreeUtilisateur,"1") || equals(entreeUtilisateur,"2")){
                fin = true;
            }
        }
        return entreeUtilisateur;
    }

    void printart(String[] list){
        for(int i = 0; i < length(list); i++){
            println(list[i]);
        }
    }

    void printstatus(int tourActuel, String p1name, String p2name, int p1coins, int p2coins, int maxTour){
        println("\nTour actuel : " + tourActuel + "/" + maxTour);
        if(tourActuel % 2 == 0){
            println("A " + p2name + " de jouer!");
        } else {
            println("A " + p1name + " de jouer!");
        }
        println("\n - Pièces - ");
        println(p1name + " : " + p1coins);
        println(p2name + " : " + p2coins);
    }

    String chooseName(String user){
        clearScreen();
        cursor(0,0);
        println(user + " : Entrez votre nom");
        return readString();
    }

    int nombreAlea(int min, int max){
        if(min > max){
            return -1;
        }
        double alea = random();
        return((int) (alea * (max - min)) + min);
    }

    void testNombreAlea(){
        int test = nombreAlea(4, 9); /* on stocke dans une variable car sinon on execute deux fois la fonction soit deux nombre potentiellement différents */
        assertTrue(test >= 4 && test <= 9);
        assertTrue(nombreAlea(9, 4) == -1);
    }
}