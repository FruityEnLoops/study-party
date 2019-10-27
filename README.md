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

Jeu éducatif basé sur le concept de Mario Party.

Le but est d'être le premier joueur a acheter une étoile a la fin du plateau. Le plateau est fait de cases qui peuvent faire perdre des pièces ou en gagner au joueur, ou qui font faire un exercice selon lequel on gagne plus ou moins de pièces (selon la réussite).
- Pour avancer sur le plateau, le joueur lance le dès, puis avance x pas.
- Pour acheter une étoile, il faut avoir 15 pièces ou plus et arriver a la fin du plateau. Si le joueur n'a pas asser de pièces, alors il lance un dé de nouveau mais recule x pas.

- Les exercices consistent en des épreuves de différentes matières (Maths, Anglais, Francais). Le pourcentage de bonne réponse défini la réussite a l'épreuve et en conséquence le nombre de pièces données (e.g. : 3/6 = 50% = 0.5 * max_coins)

- 2 joueurs en tour par tour.

### Loot table

Chaque case est tel que :

| Type de case | Effet                         |
| ------------ | ----------------------------- |
| Bonus        | +3                            |
| Malus        | -3                            |
| Exercice     | ((Bonnes réponses / Total questions) * Max pièces par cases) + 3 |


### Idée de plateau

Fais pour un terminal de 80x24 caractères.

```
                                      80 chars
<------------------------------------------------------------------------------>
|                                                                              | 1
|  [Debut] - [B] - [M] - [E] - [E] - [B] - [E] - [M] - [E] - [B] - [M] - [S]   | 2
|                               |                       |                      | 3
|                              [B] - [E] - [E] - [M] - [E]                     | 4
|                                                                              | 5
|                                                                              | 6
|  Tour au $JOUEUR                                                             | 7
|  Joueur 1 : $p1coins                                                         | 8
|  Joueur 2 : $p2coins                                                         | 9
|  Tour n°$tour                                                                | 10
|                                                                              | 11
|  [Action possibles]                                                          | 12
```

### Taches a faire

#### Base du jeu

- Menu (Jouer, quitter, options? (> nombre max de tours, limites, conditions de victoire), choisir le nom des joueurs)
- Interface de jeu : plateau, choix du joueur pendant un tour, affichage des points / statut de la partie (numéro du tour / nombre max de tours)
- Interface exercices : questions, affichage

#### Additions possibles

- Animations (lancement de dé, victoire / perte?)
- ASCII Art par joueur, plutôt que de les catégoriser par un nom (personnages?)
- Effets sonores?