class game extends Program{
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

        boolean fin = false;
        int entreeUtilisateur = -1;
        printart(menuart);
        println("1. Jouer");
        println("2. Quitter");
        println("3. Options");
        while(!fin){
            entreeUtilisateur = readInt();
            if(entreeUtilisateur == 1 || entreeUtilisateur == 2 || entreeUtilisateur == 3){
                fin = true;
            }
        } /* on sort du while si un choix valide est entré ; sinon on boucle. */
        if(entreeUtilisateur == 1){
            game();
        } else if(entreeUtilisateur == 2){
            println("Quitter");
        } else if(entreeUtilisateur == 3){
            options();
        }
    }

    void game(){
        println("game");
    }

    void options(){
        println("Un jour peut être");
    }

    void printart(String[] list){
        for(int i = 0; i < length(list); i++){
            println(list[i]);
        }
    }
}