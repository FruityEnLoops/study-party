# Study Party
Jeu éducatif basé sur le concept de Mario Party.

Le but est d'être le premier joueur a acheter une étoile a la fin du plateau. Le plateau est fait de cases qui peuvent faire perdre des pièces ou en gagner au joueur, ou qui font faire un exercice selon lequel on gagne plus ou moins de pièces (selon la réussite).
- Pour avancer sur le plateau, le joueur lance le dès, puis avance x pas.
- Pour acheter une étoile, il faut avoir 15 ou plus pièces et arriver a la fin du plateau. Si le joueur n'a pas asser de pièces, alors il lance un dé de nouveau mais recule x pas.

- Les exercices consistent en des épreuves de différentes matières (Maths, Anglais, Francais). Le pourcentage de bonne réponse défini la réussite a l'épreuve et en conséquence le nombre de pièces données (e.g. : 3/6 = 50% = 0.5 * max_coins)

### Loot table

Chaque case est tel que :

| Type de case | Effet                         |
| ------------ | ----------------------------- |
| Bonus        | +3                            |
| Malus        | -3                            |
| Exercice     | (Bonnes réponses / Total questions) * Max pièces par cases |