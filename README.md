# Study Party

```
      ::::::::    :::::::::::    :::    :::        :::::::::     :::   ::: 
    :+:    :+:       :+:        :+:    :+:        :+:    :+:    :+:   :+:  
   +:+              +:+        +:+    +:+        +:+    +:+     +:+ +:+    
  +#++:++#++       +#+        +#+    +:+        +#+    +:+      +#++:      
        +#+       +#+        +#+    +#+        +#+    +#+       +#+        
#+#    #+#       #+#        #+#    #+#        #+#    #+#       #+#         
########        ###         ########         #########        ###  

      :::::::::           :::         :::::::::    :::::::::::    :::   ::: 
     :+:    :+:        :+: :+:       :+:    :+:       :+:        :+:   :+:  
    +:+    +:+       +:+   +:+      +:+    +:+       +:+         +:+ +:+    
   +#++:++#+       +#++:++#++:     +#++:++#:        +#+         +#++:      
  +#+             +#+     +#+     +#+    +#+       +#+           +#+        
 #+#             #+#     #+#     #+#    #+#       #+#           #+#         
###             ###     ###     ###    ###       ###           ###          
```

Par Loïc DEMAY (Groupe D) & Enzo COCCHI (Groupe F).

Jeu éducatif basé sur le concept de Mario Party.

Le but est d'être le joueur avec le plus détoile a la fin de la partie. Le plateau est fait de cases qui peuvent faire perdre des pièces ou en gagner au joueur, ou qui font faire un exercice donnant des pièces lors de bonnes réponses.

### Déroulement du jeu

- Pour avancer sur le plateau, le joueur lance le dès, puis avance x pas.

- Les exercices consistent en des épreuves de différentes matières (Maths, Anglais, Histoire Géographie, Sciences) et donnent 10 pièces en cas de bonne réponse.

- Le type d'exercice est determiné aléatoirement a chaque fois.

- 2 joueurs en tour par tour.

### Condition de victoire

Si un joueur a plus d'étoiles que l'autre, il gagne.
Si les deux joueurs ont le même nombre d'étoiles, alors celui avec le plus de pièces gagne.

### Build

##### Linux

**Requirements**
- Java 8 *SDK*

Lancer compile.sh.

##### Windows
Voir compile.sh. Les builds sont déjà fournis dans le repo.

### Run

**Requirements**
- Java 8

##### Linux
Lancer run.sh.

##### Windows
`cd classes`
`java -cp "..\lib\ap.jar" StudyParty`
