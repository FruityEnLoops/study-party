class game extends Program{

    /* Résumé des fonctions
    cleanscreen() : Affiche 100 retours a la ligne (permet donc d'"effacer" l'écran)
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

        /* Menu */
        
        cleanscreen();
        boolean fin = false;
        String entreeUtilisateur = ""; /* Pourquoi un String et non un int? parce que si on tappe un String durant le readInt, on a une exception et le programme s'arrête */
        printart(menuart);
        println("1. Jouer");
        println("2. Quitter");
        while(!fin){
            entreeUtilisateur = readString();
            if(equals(entreeUtilisateur,"1") || equals(entreeUtilisateur,"2")){
                fin = true;
            }
        } /* on sort du while si un choix valide est entré ; sinon on boucle. */
        cleanscreen();
        if(equals(entreeUtilisateur,"1")){
            game();
        } else if(equals(entreeUtilisateur,"2")){
            println("Quitter");
        }
    }

    void game(){
        int tourActuel = 0;
        /* Listes joueurs = {playerName,playerCoins} */
        String[] player1 = new String[]{"","0"};
        String[] player2 = new String[]{"","0"};
        player1[1] = chooseName("Joueur 1");
        player2[1] = chooseName("Joueur 2");
        println("P1 : " + player1[1]);
        println("P2 : " + player2[1]);
    }

    void printart(String[] list){
        for(int i = 0; i < length(list); i++){
            println(list[i]);
        }
    }

    void cleanscreen(){
        print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    String chooseName(String user){
        cleanscreen();
        println(user + " : Entrez votre nom");
        return readString();
    }
}