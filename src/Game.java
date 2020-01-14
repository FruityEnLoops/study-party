import extensions.CSVFile;
class StudyParty extends Program{

    final String ROUGE = "\033[31m";
    final String VERT = "\033[32m";
    final String BLEU = "\033[34m";
    final String RESETCOLOR = "\033[0m";
    final String BOLD = "\033[1m";
    final String FBLEU = "\033[44m";

    boolean doubleLancer = false;

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
        board[3] = " 12 [Boutique]                  [+3 Pièces] 4";
        board[4] = "       |                             |";
        board[5] = " 11 [-3 Pièces]                    [Exo] 5";
        board[6] = "       |                             |";
        board[7] = "  10 [Exo]                      [-3 Pièces] 6";
        board[8] = "        \\                          /";
        board[9] = "        [+3 Pièces] - [Exo] -  [Exo]";
        board[10] ="             9        8        7";

        /* Initialisation des questions */

        String[][] questions = toTab(loadCSV("Questions.csv"));

        /* Menu */
        boolean quitter = false;
        while(!quitter){
            clearScreen();
            cursor(0,0);
            boolean fin = false;
            printart(menuart);
            println("");
            println(FBLEU + "1. Jouer" + RESETCOLOR);
            println(FBLEU + "2. Aide" + RESETCOLOR);
            println(FBLEU + "3. Quitter" + RESETCOLOR);
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
                p1.pieces = 999;
                clearScreen();
                cursor(0,0);
                /* Écran de confirmation */
                println("P1 : " + p1.nom);
                println("P2 : " + p2.nom);
                println("Appuyer sur entrée pour démarrer la partie.");
                String attente = readString();
                game(tourActuel, p1, p2, maxTour, board, questions);
            } else if(equals(entreeUtilisateur,"2")){
                clearScreen();
                cursor(0,0);
                aide();
            } else if(equals(entreeUtilisateur,"3")){
                clearScreen();
                quitter = true;
            }
        }
    }

    void game(int tourActuel, Joueur p1, Joueur p2, int maxTour, String[] board, String[][] questions){
        clearScreen();
        cursor(0,0);
        while(tourActuel <= maxTour){
	        Joueur joueur = joueurActuel(tourActuel, p1, p2);
            Joueur autreJoueur = autreJoueur(joueur, p1, p2);
            /* Affichage en deux temps, le plateau puis les infos de la partie */
            printart(board);
            printstatus(tourActuel, p1, p2, maxTour);
            println("1. Lancer le dé");
            println("2. Jouer un objet");
            String entreeUtilisateur = choix();
            if(equals(entreeUtilisateur, "1")){
                lancerEtMouvement(tourActuel, p1, p2, false);
            } else if(equals(entreeUtilisateur, "2")){
                doubleLancer = false;
                if(joueur.inventaire.occupe[0] || joueur.inventaire.occupe[0] || joueur.inventaire.occupe[0]){
                    println("Choisis l'objet à utiliser :");
                    printInventory(joueur.inventaire);
                    choixDansInventaire(joueur, autreJoueur, tourActuel);
                } else {
                    clearScreen();
                    println("Erreur : Aucun objet a jouer");
                    delay(1250);
                }
                lancerEtMouvement(tourActuel, p1, p2, doubleLancer);
            } else if(equals(entreeUtilisateur, "3")){
                joueur.position = 12;
            }
            joueur.pieces = joueur.pieces + actionCase(questions, joueur);
            tourActuel++;
            clearScreen();
            cursor(0,0);
        }
        clearScreen();
        cursor(0,0);
        /* Affichage du gagnant */
        String gagnant = determinerGagnant(p1, p2);
        println("Merci d'avoir joué!");
        println("Gagnant de la partie : " + VERT + gagnant + RESETCOLOR);
        println(FBLEU + " - Appuyer sur Entrée pour revenir au menu principal - " + RESETCOLOR);
        String attente = readString();
    }
    
    String choix(){
        /* Note : cette fonction permet de controller si l'utilisateur entre 1 ou 2.
        On a pas besoin de plus, mais cette fonction sera (peut être) améliorée. */
        boolean fin = false;
        String entreeUtilisateur = ""; /* Pourquoi un String et non un int? parce que si on tappe un String durant le readInt, on a une exception et le programme s'arrête */
        while(!fin){
            entreeUtilisateur = readString();
            if(equals(entreeUtilisateur,"1") || equals(entreeUtilisateur,"2") || equals(entreeUtilisateur,"3")){
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
        println(FBLEU + "\nTour actuel : " + tourActuel + "/" + maxTour + RESETCOLOR);
        if(tourActuel % 2 == 0){
            println("A " + p2.nom + " de jouer!");
        } else {
            println("A " + p1.nom + " de jouer!");
        }
        println(FBLEU + "\n - Pièces - " + RESETCOLOR);
        println(p1.nom + " : " + p1.pieces + " || " + p2.nom + " : " + p2.pieces);
        println(FBLEU + "\n - Position - " + RESETCOLOR);
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
        Joueur j = creerJoueur("Test");
        assertEquals(0, j.pieces);
        assertEquals(0, j.etoiles);
        assertEquals(0, j.position);
    }

    Inventaire creerInventaire(){
        Inventaire i = new Inventaire();
        i.occupe = new boolean[]{false,false,false};
        i.type = new int[]{-1,-1,-1};
        return i;
    }

    void testCreerInventaire(){
        Inventaire i = creerInventaire();
        for(int j = 0; j < 3; j++){
            assertFalse(i.occupe[j]);
        }
        for(int j = 0; j < 3; j++){
            assertEquals(-1, i.type[j]);
        }
    }

    void printInventory(Inventaire i){
        for(int idx = 0; idx < length(i.type); idx++){
            println("  - " + (idx + 1) + " : " + nomObjet(i.type[idx]));
        }
    }

    String nomObjet(int type){
        if(type == 1){
            return "Dé en Or (double ton lancer)";
        } else if(type == 2){
            return "Pickpocket (vole 10 pièces à ton adversaire)";
        } else if(type == -1){
            return "Pas d'objet";
        } else {
            return "Objet invalide";
        }
    }
    
    void lancerEtMouvement(int tourActuel, Joueur p1, Joueur p2, boolean doubleLancer){
        int lancer = nombreAlea(1, 6);
	    if(doubleLancer){
	        lancer = lancer*2;
	    }
        clearScreen();
        cursor(0,0);
        println("Lancer : " + lancer);
        delay(1250);
        joueurActuel(tourActuel, p1, p2).position = bouger(joueurActuel(tourActuel, p1, p2).position, lancer);
	
    }

    Joueur joueurActuel(int tourActuel, Joueur p1, Joueur p2){
        if(tourActuel % 2 == 0){
            return p2;
        } else {
            return p1;
        }
    }

    Joueur autreJoueur(Joueur joueur, Joueur p1, Joueur p2){
        if(joueur == p1) {
            return p2;
        } else  {
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

    int actionCase(String[][] questions, Joueur j){
        // Cette fonction renvoie l'action sur les pieces du joueur selon la case (les cases ne pouvant qu'affecter les pieces du joueur)
        int position = j.position;
        if(position == 4 || position == 9){
            clearScreen();
            cursor(0,0);
            println(VERT + "Bonus!" + RESETCOLOR + " Ajout de 3 pièces.");
            delay(1250);
            return 3;
        } else if(position == 6 || position == 11){
            clearScreen();
            cursor(0,0);
            println(ROUGE + "Malus!" + RESETCOLOR + " Perte de 3 pièces...");
            delay(1250);
            return -3;
        } else if(position == 1 || position == 2 || position == 3 || position == 5 || position == 7 || position == 8 || position == 10){
            return question(questions);
        } else if(position == 12){
            if(!j.inventaire.occupe[0] || !j.inventaire.occupe[0] || !j.inventaire.occupe[0]){
                if(j.pieces > 15){
                    return boutique(j);
                } else {
                    println("Désolé, mais tu n'a pas assez de pièces pour acheter un objet...");
                    delay(1250);
                    return 0;
                }
            } else {
                println("Désolé, mais tu n'a pas de place disponible dans ton inventaire!");
                delay(1250);
                return 0;
            }
        } else {
            return 0;
        }
    }

    int question(String[][] questions){
        int qnumber = nombreAlea(1, 40);
        boolean fin = false;
        String reponse = "";
        while(!fin){
            clearScreen();
            cursor(0,0);
            println("Matière : " + questions[qnumber][1]);
            println("Question :\n" + questions[qnumber][2]);
            println("1. " + questions[qnumber][3]);
            println("2. " + questions[qnumber][4]);
            println("3. " + questions[qnumber][5]);
            reponse = readString();
            if(equals(reponse, "1") || equals(reponse, "2") || equals(reponse, "3")){
                fin = true;
            }
        }
        if(equals(reponse, questions[qnumber][6])){
            clearScreen();
            cursor(0,0);
            println(VERT + "Bonne réponse!" + RESETCOLOR + " +5 pièces");
            delay(1250);
            return 5;
        } else {
            clearScreen();
            cursor(0,0);
            println(ROUGE + "Mauvaise réponse!" + RESETCOLOR + " 0 pièces");
            delay(1250);
            return 0;
        }
    }
    
    void pickpocket(int tourActuel, Joueur joueur, Joueur autreJoueur) {
        //Cet objet permet à son utilisateur de voler 10 pièces à son adversaire   
        if(autreJoueur.pieces<10) { 
            joueur.pieces += autreJoueur.pieces;
            autreJoueur.pieces = 0;
        } else{
            joueur.pieces += 10;
            autreJoueur.pieces -= 10;
        }
    }

    void choixDansInventaire(Joueur joueur, Joueur autreJoueur, int tourActuel) {
        while(true){
            String item = readString();
            if(equals(item, "1")){
                if(joueur.inventaire.occupe[0]){
                    utiliserObjet(joueur, joueur.inventaire.type[0], autreJoueur, tourActuel);
                    joueur.inventaire.occupe[0] = false;
                    joueur.inventaire.type[0] = -1;
                    return;
                } else {
                    println("Pas d'objet dans cet emplacement");
                }
            } else if(equals(item, "2")){
                if(joueur.inventaire.occupe[1]){
                    utiliserObjet(joueur, joueur.inventaire.type[1], autreJoueur, tourActuel);
                    joueur.inventaire.occupe[1] = false;
                    joueur.inventaire.type[1] = -1;
                    return;
                } else {
                    println("Pas d'objet dans cet emplacement");
                }
            } else if(equals(item, "3")){
                if(joueur.inventaire.occupe[2]){
                    utiliserObjet(joueur, joueur.inventaire.type[2], autreJoueur, tourActuel);
                    joueur.inventaire.occupe[2] = false;
                    joueur.inventaire.type[2] = -1;
                    return;
                } else {
                    println("Pas d'objet dans cet emplacement");
                }
            }
        }
    }

    void utiliserObjet(Joueur joueur, int type, Joueur autreJoueur, int tourActuel) {
        if(type == 1) {
            doubleLancer = true;
        
        }
        else if(type == 2){
            pickpocket(tourActuel, joueur, autreJoueur);
        }        
    }

    int boutique(Joueur j){
        println("Attention : vous ne pouvez acheter qu'un seul objet par visite");
        boolean choisi = false;
        println("1. Dé en or   | 15 pièces");
        println("2. Pickpocket | 15 pièces");
        println("3. Étoile     | 25 pièces");
        while(!choisi){
            String objet = readString();
            if(equals(objet, "1") || equals(objet, "2") || equals(objet, "3")){
                if(equals(objet, "3")){
                    if(j.pieces > 25){
                        println("Acheté : Étoile!");
                        j.etoiles = j.etoiles + 1;
                        delay(1250);
                        return -25;
                    } else {
                        println("Pas assez de pièces...");
                    }
                } else {
                    if(j.pieces > 15){
                        println("Acheté : " + nomObjet(toInt(objet)));
                        j.inventaire.occupe[emplacementDisponible(j)] = true;
                        j.inventaire.type[emplacementDisponible(j)] = toInt(objet);
                        delay(1250);
                        return -15;
                    } else {
                        println("Pas assez de pièces...");
                    }
                }
            } else if(equals(objet, "X")){
                choisi = true;
                println("Vous sortez de la boutique");
                delay(1250);
                return 0;
            }
        }
        return -1;
    }

    int emplacementDisponible(Joueur j){
        if(!j.inventaire.occupe[0]){
            return 0;
        } else if(!j.inventaire.occupe[1]){
            return 1;
        } else {
            return 2;
        }
    }

    int toInt(String s){
        return ((int) charAt(s, 0)) - 48;
    }

    void testToInt(){
        assertTrue(1 == toInt("1"));
        assertFalse(1 == toInt("2"));
    }

    void aide(){
        println(FBLEU + "Study Party - Aide" + RESETCOLOR);
        println("");
        println(" Le but du jeu est de gagner en ayant plus d'étoiles ou de pièces que son adversaire! Pour gagner il suffit donc :");
        println("- Soit d'avoir plus d'étoiles que son adversaire");
        println("- Soit d'avoir plus de pièces (en cas d'égalité de nombre d'étoiles)");
        println("");
        println(" Chaque joueur devra avancer sur un plateau a l'aide d'un dé pour avancer et avoir des bonus, malus, acceder a la boutique d'objet (permettant entre autre d'acheter des étoiles ou des objets) et répondre a des questions, donnant des pièces en cas de bonne réponse.");
        println("Bonne chance!");
        println(FBLEU + " - Appuyer sur Entrée pour revenir au menu principal - " + RESETCOLOR);
        String attente = readString();
    }
}
