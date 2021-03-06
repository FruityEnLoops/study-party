import extensions.CSVFile;
import extensions.File;

class StudyParty extends Program{
    
    final String[][] questions = toTab(loadCSV("Questions.csv"));

    int effet = 0;
    final String ROUGE = "\033[31m";
    final String VERT = "\033[32m";
    final String JAUNE = "\033[93m";
    final String RESETCOLOR = "\033[0m";
    final String FBLEU = "\033[44m";
    final String BOLD = "\033[1m";

    void algorithm(){

        /* Menu */
        boolean quitter = false;
        while(!quitter){
            clearScreen();
            cursor(0,0);
            boolean fin = false;
            printart("menuart.txt");
            delay(350);
            println("");
            println(FBLEU + "1. Jouer" + RESETCOLOR);
            delay(50);
            println(FBLEU + "2. Aide" + RESETCOLOR);
            delay(50);
            println(FBLEU + "3. Crédits" + RESETCOLOR);
            delay(50);
            println(FBLEU + "4. Quitter" + RESETCOLOR);
            String entreeUtilisateur = choixmenu();
            clearScreen();
            cursor(0,0);
            if(equals(entreeUtilisateur,"1")){
                /* Initialisation des variables de la partie */
                int tourActuel = 1;
                clearScreen();
                cursor(0,0);
                /* Définition des options de la partie par l'utilisateur */
                println("Partie en combien de tours?");
                boolean entreeCorrecte = false;
                int maxTour = readIntMaisMieux();
                Joueur p1 = creerJoueur(chooseName("Joueur 1"));
                Joueur p2 = creerJoueur(chooseName("Joueur 2"));
                clearScreen();
                cursor(0,0);
                /* Écran de confirmation */
                println("P1 : " + p1.nom);
                println("P2 : " + p2.nom);
                println("Appuyer sur entrée pour démarrer la partie.");
                String attente = readString();
                game(tourActuel, p1, p2, maxTour);
            } else if(equals(entreeUtilisateur,"2")){
                clearScreen();
                cursor(0,0);
                aide();
            } else if(equals(entreeUtilisateur, "3")){
                clearScreen();
                cursor(0,0);
                credits();
            } else if(equals(entreeUtilisateur,"4")){
                clearScreen();
                quitter = true;
            }
        }
    }

    void game(int tourActuel, Joueur p1, Joueur p2, int maxTour){
        clearScreen();
        cursor(0,0);
        while(tourActuel <= maxTour){
            effet = 0;
	        Joueur joueur = joueurActuel(tourActuel, p1, p2);
            Joueur autreJoueur = autreJoueur(joueur, p1, p2);
            /* Affichage en deux temps, le plateau puis les infos de la partie */
            printart("board.txt");
            afficherSurCarte(p1, 1);
            afficherSurCarte(p2, 2);
            cursor(11, 0);
            printstatus(tourActuel, p1, p2, maxTour);
            println("\nAppuyer sur entrée pour lancer le dé.");
            String entreeUtilisateur = readString();
            lancerEtMouvement(tourActuel, p1, p2, false);    
            joueur.pieces = joueur.pieces + actionCase(joueur);
            tourActuel++;
            if(effet == 1){
                tourActuel = tourActuel - 1;
            } else if(effet == 2){
                if(autreJoueur.pieces < 10){
                    joueur.pieces = joueur.pieces + autreJoueur.pieces;
                    autreJoueur.pieces = 0;
                    effet = 0;
                } else {
                    joueur.pieces = joueur.pieces + 10;
                    autreJoueur.pieces = autreJoueur.pieces - 10;
                    effet = 0;
                }
            }
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
    
    String choixmenu(){
        boolean fin = false;
        String entreeUtilisateur = ""; /* Pourquoi un String et non un int? parce que si on tappe un String durant le readInt, on a une exception et le programme s'arrête */
        while(!fin){
            entreeUtilisateur = readString();
            if(equals(entreeUtilisateur,"1") || equals(entreeUtilisateur,"2") || equals(entreeUtilisateur,"3") || equals(entreeUtilisateur,"4")){
                fin = true;
            }
        }
        return entreeUtilisateur;
    }

    // Permet d'afficher un ASCII art
    void printart(String filename){
        File fichier = newFile(filename);
        String ligne = "";
        ligne = readLine(fichier);
        while(ligne != null){
            println(ligne);
            ligne = readLine(fichier);
        }
    }

    // Permet d'afficher un ASCII art, mais avec comme origine un emplacement sur le terminal
    void printart(String filename, int x, int y){
        File fichier = newFile(filename);
        String ligne = "";
        int i = 0;
        ligne = readLine(fichier);
        while(ligne != null){
            cursor(x + i, y);
            println(ligne);
            ligne = readLine(fichier);
            i++;
        }
    }

    /* Affiche l'etat de la parti en cours : le tour actuel, qui joue, les pieces, la position des joueurs */
    void printstatus(int tourActuel, Joueur p1, Joueur p2, int maxTour){
        print(FBLEU + "\nTour actuel : " + tourActuel + "/" + maxTour);
        if(tourActuel % 2 == 0){
            println(" // A " + p2.nom + " de jouer!" + RESETCOLOR);
        } else {
            println(" // A " + p1.nom + " de jouer!" + RESETCOLOR);
        }
        println(FBLEU + "\n - $$$ - " + RESETCOLOR);
        println(p1.nom + " : " + JAUNE + p1.pieces + RESETCOLOR + " || " + p2.nom + " : " + JAUNE + p2.pieces + RESETCOLOR);
        println(FBLEU + "\n - ★★★ - " + RESETCOLOR);
        println(p1.nom + " : " + JAUNE + p1.etoiles + RESETCOLOR + " || " + p2.nom + " : " + JAUNE + p2.etoiles + RESETCOLOR);
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
        return j;
    }

    void testCreerJoueur(){
        Joueur j = creerJoueur("Test");
        assertEquals(0, j.pieces);
        assertEquals(0, j.etoiles);
        assertEquals(0, j.position);
    }

    String nomObjet(int type){
        if(type == 1){
            return "Dé en Or (permet de rejouer)";
        } else if(type == 2){
            return "Pickpocket (vole 10 pièces à ton adversaire)";
        } else {
            return "Objet invalide";
        }
    }

    void testNomObjet(){
        assertEquals("Dé en Or (permet de rejouer)", nomObjet(1));
        assertEquals("Pickpocket (vole 10 pièces à ton adversaire)", nomObjet(2));
        assertEquals("Objet invalide", nomObjet(-1));
    }
    
    void lancerEtMouvement(int tourActuel, Joueur p1, Joueur p2, boolean doubleLancer){
        int lancer = nombreAlea(1, 6);
	    if(doubleLancer){
	        lancer = lancer*2;
	    }
        clearScreen();
        cursor(0,0);
        println("Lancer : " + lancer + "\n");
        printart("dice" + lancer + ".txt");
        delay(1500);
        joueurActuel(tourActuel, p1, p2).position = bouger(joueurActuel(tourActuel, p1, p2).position, lancer);
	
    }

    Joueur joueurActuel(int tourActuel, Joueur p1, Joueur p2){
        if(tourActuel % 2 == 0){
            return p2;
        } else {
            return p1;
        }
    }

    void testJoueurActuel(){
        Joueur j1 = creerJoueur("Joueur 1");
        Joueur j2 = creerJoueur("Joueur 2");
        assertTrue(j1 == joueurActuel(1, j1, j2));
        assertTrue(j2 == joueurActuel(2, j1, j2));
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

    int actionCase(Joueur j){
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
            return question();
        } else if(position == 12){
            if(j.pieces > 15){
                return boutique(j);
            } else {
                println("Désolé, mais tu n'a pas assez de pièces pour acheter un objet...");
                delay(1250);
                return 0;
            }
        } else {
            return 0;
        }
    }

    void testActionCase(){
        Joueur j = creerJoueur("Test");
        j.position = 9;
        assertTrue(3 == actionCase(j));
        j.position = 6;
        assertTrue(-3 == actionCase(j));
    }

    int question(){
        int qnumber = nombreAlea(1, 40);
        boolean fin = false;
        String reponse = "";
        while(!fin){
            clearScreen();
            cursor(0,0);
            println("Matière : " + questions[qnumber][1]);
            printart("question.txt", 3, 40);
            cursor(3,0);
            printart(questions[qnumber][1] + ".txt");
            println("");
            println(BOLD + "Question :\n" + RESETCOLOR + questions[qnumber][2]);
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
            println(VERT + "Bonne réponse!" + RESETCOLOR + " + 10 pièces");
            delay(1250);
            return 10;
        } else {
            clearScreen();
            cursor(0,0);
            println(ROUGE + "Mauvaise réponse!" + RESETCOLOR + " 0 pièces");
            delay(1250);
            return 0;
        }
    }

    int boutique(Joueur j){
        println("Attention : vous ne pouvez acheter qu'un seul objet par visite");
        boolean choisi = false;
        println("1. Dé en or   | 15 pièces");
        println("2. Pickpocket | 15 pièces");
        println("3. Étoile     | 25 pièces");
        println("X. Sortir de la boutique");
        while(!choisi){
            String objet = readString();
            if(equals(objet, "1") || equals(objet, "2") || equals(objet, "3")){
                if(equals(objet, "3")){
                    if(j.pieces > 25){
                        println("Acheté : Étoile!");
                        j.etoiles = j.etoiles + 1;
                        printart("etoile.txt");
                        delay(1250);
                        return -25;
                    } else {
                        println("Pas assez de pièces...");
                    }
                } else {
                    if(j.pieces > 15){
                        println("Acheté : " + nomObjet(stringToInt(objet)));
                        if(equals(objet, "1")){
                            print(JAUNE);
                            printart("dés.txt");
                            print(RESETCOLOR);
                            effet = 1;
                        } else {
                            printart("pickpocket.txt");
                            effet = 2;
                        }
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
    
    void aide(){
        println(FBLEU + "Study Party - Aide" + RESETCOLOR);
        println("");
        println(" Le but du jeu est de gagner en ayant plus d'étoiles ou de pièces que son adversaire! Pour gagner il suffit donc :");
        println("- Soit d'avoir plus d'étoiles que son adversaire");
        println("- Soit d'avoir plus de pièces (en cas d'égalité de nombre d'étoiles)");
        println("");
        delay(500);
        println(" Chaque joueur devra avancer sur un plateau a l'aide d'un dé pour avancer et avoir des bonus, malus, acceder a la boutique d'objet (permettant entre autre d'acheter des étoiles ou des objets) et répondre a des questions, donnant des pièces en cas de bonne réponse.");
        println("");
        delay(500);
        println("Les joueurs peuvent acheter des objets pour gagner un avantage!");
        println("- Pickpocket");
        println("  Voler 10 pièces a son adversaire!");
        println("- Dé en or");
        println("  Permet de rejouer son tour!");
        println("");
        delay(500);
        println("Chaque objet coute 15 pièces. A vous de bien gerer vos pièces pour mettre des batons dans les roues de votre adversaire et gagner!");
        println("");
        delay(500);
        println("Bonne chance!");
        println("");
        println(FBLEU + " - Appuyer sur Entrée pour revenir au menu principal - " + RESETCOLOR);
        String attente = readString();
    }

    int readIntMaisMieux(){
        boolean estCorrect = false;
        while(true){
            String e = readString();
            for(int i = 0; i < length(e); i++){
                if(charAt(e, i) >= '0' && charAt(e, i) <= '9'){
                    estCorrect = true;
                } else {
                    estCorrect = false;
                }
            }
            if(estCorrect){
                return stringToInt(e);
            }
        }
    }

    void credits(){
        println(FBLEU + "Study Party - Crédits" + RESETCOLOR);
        println("");
        println("     Une idée de Loïc DEMAY");
        println("        Programmation :");
        println("          - Loïc DEMAY");
        println("          - Enzo COCCHI");
        println("");
        println("   Projet réalisé dans le cadre");
        println("  du DUT Informatique de l'IUT A");
        println("       de Lille, semestre 1");
        println("");
        println(FBLEU + " - Appuyer sur Entrée pour revenir au menu principal - " + RESETCOLOR);
        String attente = readString();
    }

    void afficherSurCarte(Joueur j, int pnumber){
        print(FBLEU);
        if(j.position == 1){
            cursor(3, 16);
            print("J" + pnumber);
        } else if(j.position == 2){
            cursor(3, 24);
            print("J" + pnumber);
        } else if(j.position == 3){
            cursor(2, 38);
            print("J" + pnumber);
        } else if(j.position == 4){
            cursor(4, 30);
            print("J" + pnumber);
        } else if(j.position == 5){
            cursor(6, 33);
            print("J" + pnumber);
        } else if(j.position == 6){
            cursor(8, 30);
            print("J" + pnumber);
        } else if(j.position == 7){
            cursor(10, 38);
            print("J" + pnumber);
        } else if(j.position == 8){
            cursor(9, 24);
            print("J" + pnumber);
        } else if(j.position == 9){
            cursor(9, 14);
            print("J" + pnumber);
        } else if(j.position == 10){
            cursor(8, 12);
            print("J" + pnumber);
        } else if(j.position == 11){
            cursor(6, 17);
            print("J" + pnumber);
        } else if(j.position == 12){
            cursor(4, 16);
            print("J" + pnumber);
        }
        print(RESETCOLOR);
    }
}
