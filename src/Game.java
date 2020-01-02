import extensions.CSVFile;
class Game extends Program{

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

        /* Initialisation des questions */

        CSVFile questionscsv = loadCSV("Questions.csv");
        String[][] questions = toTab(questionscsv);


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
            /* Initialisation des variables de la partie */
            int tourActuel = 1;
            clearScreen();
            cursor(0,0);
            /* Définition des options de la partie par l'utilisateur */
            println("Partie en combien de tours?");
            int maxTour = readInt(); /* Il faudra remplacer ce readInt a terme par un readString */
            Joueur p1 = creerJoueur(chooseName("Joueur 1"));
            Joueur p2 = creerJoueur(chooseName("Joueur 2"));
            clearScreen();
            cursor(0,0);
            /* Écran de confirmation */
            println("P1 : " + p1.nom);
            println("P2 : " + p2.nom);
            println("Appuyer sur entrée pour démarrer la partie.");
            String attente = readString();
            game(tourActuel, p1, p2, maxTour, board, questions);
        } else if(equals(entreeUtilisateur,"2")){
            println("Quitter");
        }
    }

    void game(int tourActuel, Joueur p1, Joueur p2, int maxTour, String[] board, String[][] questions){
        clearScreen();
        cursor(0,0);
        while(tourActuel <= maxTour){
            /* Affichage en deux temps, le plateau puis les infos de la partie */
            printart(board);
            printstatus(tourActuel, p1, p2, maxTour);
            println("1. Lancer le dé");
            println("2. Jouer un objet");
            String entreeUtilisateur = choix();
            if(equals(entreeUtilisateur, "1")){
                lancerEtMouvement(tourActuel, p1, p2);
            } else if(equals(entreeUtilisateur, "2")){
                printInventory(joueurActuel(tourActuel, p1, p2).inventaire);
                println("Menu pour jouer un objet a faire");
                /* Choisir l'objet */
                lancerEtMouvement(tourActuel, p1, p2);
            }
            joueurActuel(tourActuel, p1, p2).pieces = joueurActuel(tourActuel, p1, p2).pieces + actionCase(joueurActuel(tourActuel, p1, p2).positon, questions, joueurActuel(tourActuel, p1, p2));
            tourActuel++;
            clearScreen();
            cursor(0,0);
        }
        clearScreen();
        cursor(0,0);
        /* Affichage du gagnant */
        String gagnant = determinerGagnant(p1, p2);
        println("Merci d'avoir joué!");
        println("Gagnant de la partie : " + gagnant);
        String attente = readString();
    }
    
    String choix(){
        /* Note : cette fonction permet de controller si l'utilisateur entre 1 ou 2.
        On a pas besoin de plus, mais cette fonction sera (peut être) améliorée. */
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

    /* Affiche le contenu d'une liste. Utilisé pour afficher des textes plus "graphiques" */
    void printart(String[] list){
        for(int i = 0; i < length(list); i++){
            println(list[i]);
        }
    }

    /* Affiche l'etat de la parti en cours : le tour actuel, qui joue, les pieces, la position des joueurs */
    void printstatus(int tourActuel, Joueur p1, Joueur p2, int maxTour){
        println("\nTour actuel : " + tourActuel + "/" + maxTour);
        if(tourActuel % 2 == 0){
            println("A " + p2.nom + " de jouer!");
        } else {
            println("A " + p1.nom + " de jouer!");
        }
        println("\n - Pièces - ");
        println(p1.nom + " : " + p1.pieces + " || " + p2.nom + " : " + p2.pieces);
        println("\n - Position - ");
        println(p1.nom + " : " + p1.position + " || " + p2.nom + " : " + p2.position);
    }

    /* Controle de saisie basique pour de l'entrée de nom (peut être utilisé pour d'autres contextes) */
    String chooseName(String user){
        clearScreen();
        cursor(0,0);
        println(user + " : Entrez votre nom");
        return readString();
    }

    /* Donne un nombre aléatoire entre le min et le max. Renvoi -1 si saisie invalide de min/max */
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

    /* Calcule la position après un déplacement */
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

    /* Determine qui a le plus d'étoiles, ou qui a le plus de pieces si les deux joueurs ont le même nombre d'étoiles */
    String determinerGagnant(Joueur p1, Joueur p2){
        if(p1.etoiles == p2.etoiles){
            if(p1.pieces > p2.pieces){
                return p1.nom;
            } else {
                return p2.nom;
            }
        } else if(p1.etoiles > p2.etoiles){
            return p1.nom;
        } else {
            return p2.nom;
        }
    }

    void testDeterminerGagnant(){
        Joueur j1 = creerJoueur("J1");
        Joueur j2 = creerJoueur("J2");
        j1.pieces = 90;
        j2.pieces = 20;
        j1.etoiles = 2;
        j2.etoiles = 1;
        assertEquals("J1", determinerGagnant(j1, j2));
        j2.pieces = 20;
        j1.etoiles = 1;
        assertEquals("J1", determinerGagnant(j1, j2));
        j1.pieces = 60;
        j2.pieces = 80;
        assertEquals("J2", determinerGagnant(j1, j2));
    }

    Joueur creerJoueur(String nom){
        Joueur j = new Joueur();
        j.nom = nom;
        j.pieces = 0;
        j.etoiles = 0;
        j.position = 0;
        j.inventaire = creerInventaire();
        return j;
    }

    void testCreerJoueur(){
        // giga flemme
    }

    Inventaire creerInventaire(){
        Inventaire i = new Inventaire();
        i.occupe = new boolean[]{false,false,false};
        i.type = new int[]{-1,-1,-1};
        return i;
    }

    void printInventory(Inventaire i){
        for(int idx = 0; idx < length(i.type); idx++){
            println("Emplacement " + idx + " : " + nomObjet(i.type[idx]));
        }
    }

    String nomObjet(int type){
        if(type == 1){
            return "Objet 1";
        } else if(type == 2){
            return "Objet 2";
        } else if(type == -1){
            return "Pas d'objet";
        } else {
            return "Objet invalide";
        }
    }
    
    void lancerEtMouvement(int tourActuel, Joueur p1, Joueur p2){
        int lancer = nombreAlea(1, 6);
        bouger(joueurActuel(tourActuel, p1, p2).position, lancer);
    }

    Joueur joueurActuel(int tourActuel, Joueur p1, Joueur p2){
        if(tourActuel % 2 == 0){
            return p2;
        } else {
            return p1;
        }
    }

    String[][] toTab(CSVFile csv){
        String[][] tab = new String[rowCount(csv)][columnCount(csv)];
        for(int i = 0; i < rowCount(csv); i++){
            for(int j = 0; j < columnCount(csv); j++){
                tab[i][j] = getCell(csv, i, j);
            }
        }
        return tab;
    }

    int actionCase(int position, String[][] questions, Joueur j){
        if(position == 4 || position == 9){
            println("Bonus! Ajout de 3 pièces.");
            return 3;
        } else if(position == 6 || position == 11){
            println("Malus! Perte de 3 pièces...");
            return -3;
        } else if(position == 1 || position == 2 || position == 3 || position == 5 || position == 7 || position == 8 || position == 10){
            return question(questions);
        } else if(position == 12){
            // return boutique(j);
        } else {
            return 0;
        }
    }

    int question(String[][] questions){
        int qnumber = alea(1, 40);
        boolean fin = false;
        while(!fin){
            clearScreen();
            println("Question :\n" + questions[qnumber][2]);
            println("1. " + questions[qnumber][3]);
            println("2. " + questions[qnumber][4]);
            println("3. " + questions[qnumber][5]);
            String reponse = readString();
            if(equals(reponse, "1") || equals(reponse, "2") || equals(reponse, "3")){
                fin = true;
            }
        }
        if(equals(reponse, questions[qnumber][6])){
            println("Bonne réponse! +5 pièces");
            return 5;
        } else {
            println("Mauvaise réponse! 0 pièces");
            return 0;
        }
    }
}