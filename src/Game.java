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

        String[] board = new String[11];
        board[0] = "    ------>     1       2       3";
        board[1] = "   0 [Début] - [Exo] - [Exo] - [Exo] ";
        board[2] = "        /                          \\ ";
        board[3] = " 12 [Boutique]                    [Bonus] 4";
        board[4] = "       |                             |";
        board[5] = " 11 [Malus]                        [Exo] 5";
        board[6] = "       |                             |";
        board[7] = "  10 [Exo]                        [Malus] 6";
        board[8] = "        \\                          /";
        board[9] = "          [Bonus] - [Exo] -  [Exo]";
        board[10] ="             9        8        7";
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
        int positionp1 = 0;
        int positionp2 = 0;
        int p1stars = 0;
        int p2stars = 0;
        clearScreen();
        cursor(0,0);
        while(!gagne && tourActuel <= maxTour){
            printart(board);
            printstatus(tourActuel, p1name, p2name, p1coins, p2coins, maxTour, positionp1, positionp2);
            println("1. Lancer le dé");
            println("2. Jouer un objet");
            String entreeUtilisateur = choix();
            if(equals(entreeUtilisateur, "1")){

                /* Mouvement sur le plateau */
    
                int lancer = nombreAlea(1, 6);
                /* On déduit qui joue */
                if(tourActuel % 2 == 0){
                    positionp2 = bouger(positionp2, lancer);
                } else {
                    positionp1 = bouger(positionp1, lancer);
                }
            } else if(equals(entreeUtilisateur, "2")){
                println("Avancement du tour");
            }
            tourActuel++;
            gagne = verifierFinDuJeu(tourActuel, maxTour);
            clearScreen();
            cursor(0,0);

        }
        clearScreen();
        cursor(0,0);
        String gagnant = determinerGagnant(p1name, p2name, p1coins, p2coins, p1stars, p2stars);
        println("Merci d'avoir joué!");
        println("Gagnant de la partie : " + gagnant);
        String a = readString();
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

    void printstatus(int tourActuel, String p1name, String p2name, int p1coins, int p2coins, int maxTour, int positionp1, int positionp2){
        println("\nTour actuel : " + tourActuel + "/" + maxTour);
        if(tourActuel % 2 == 0){
            println("A " + p2name + " de jouer!");
        } else {
            println("A " + p1name + " de jouer!");
        }
        println("\n - Pièces - ");
        println(p1name + " : " + p1coins);
        println(p2name + " : " + p2coins);
        println("\n - Position - ");
        println(p1name + " : " + positionp1);
        println(p2name + " : " + positionp2);
    }

    String chooseName(String user){
        clearScreen();
        cursor(0,0);
        println(user + " : Entrez votre nom");
        return readString();
    }

    boolean verifierFinDuJeu(int tourActuel, int maxTour){
        return tourActuel > maxTour;
    }

    void testVerifierFinDuJeu(){
        assertTrue(verifierFinDuJeu(16, 15));
        assertFalse(verifierFinDuJeu(15,15));
        assertFalse(verifierFinDuJeu(2, 15));
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

    int bouger(int position, int lancer){
        position = position + lancer;
        /* Le plateau fait 12 cases ; on ne doit pas dépasser 12. Si on dépasse 12, on repasse a 1 (et non a 0) car on fonctionne avec un numéro de case */
        if(position > 12){
            position = position - 11;
        }
        return position;
    }

    void testBouger(){
        assertEquals(3, bouger(0, 3));
        assertEquals(2, bouger(11, 2));
    }

    String determinerGagnant(String p1name, String p2name, int p1coins, int p2coins, int p1stars, int p2stars){
        if(p1stars == p2stars){
            if(p1coins > p2coins){
                return p1name;
            } else {
                return p2name;
            }
        } else if(p1stars > p2stars){
            return p1name;
        } else {
            return p2name;
        }
    }

    void testDeterminerGagnant(){
        assertEquals("J1", determinerGagnant("J1", "J2", 90, 20, 2, 1));
        assertEquals("J1", determinerGagnant("J1", "J2", 90, 20, 1, 1));
        assertEquals("J2", determinerGagnant("J1", "J2", 60, 80, 1, 1));
    }
}