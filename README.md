# Application
L'application permet d'accéder à deux activités : Une calculatrice et un lecteur de Flux RSS.
Lorsqu'on lance l'application, un menu s'affiche pour nous permettre de sélectionner l'activité voulu. 


# Calculatrice
La calculatrice contient les différentes touches dans un TableLayout et TableRow, le résultat du calcul est affiché dans un TextView et l'historique est stoqué dans une ScrollView.


## Principes UX

J'ai placé les boutons associés aux chiffres centrés en bas de l'écran de manière à ce qu'ils soient accessibles facilement avec le téléphone dans les mains et que cela soit ergonomique.

Ensuite, les boutons utilisés les plus souvent comme "=" et "AC" sont présents en bas de l'écran pour être accessible facilement.

Enfin, les boutons utilisés le moins souvent sont placés au dessus des chiffres.

Pour éviter de créer un nombre important de boutons, un bouton permet d'accéder à une seconde page de fonctions mathématiques en remplacant les expressions les moins utilisés qui sont placés au dessus des chiffres.

## Structure

L'activité utilise une architecture MVP, composé d'une CalculatorView, d'un CalculatorPresenter et d'un CalculatorModel. 
Une fois sélectionné sur la page principale, une nouvelle activité est crée invoquant la vue, qui crée le présenteur, qui a son tour crée le modèle.

## Evaluate
    
Pour permettre de réaliser les calculs de manière beaucoup plus simple et d'implémenter des opérations plus complexes (Sinus, Log, Sqrt,...), j'ai utilisé la bibliothèque Exp4J que j'ai trouvé en recherchant des bibliothèques adaptées.

## Vérification du format de l'expression

Pour vérifier qu'une expression a un format correcte, on utilise la fonction adaptée de la bibliothèque Exp4j qui permet de le vérifier.
Si ce n'est pas le cas, l'affichage contenant l'expression mathématique est encadrée d'une bordure rouge et le bouton "=" est désactivé.

## Historique

A chaque calcul, l'expression mathématique et le résultat sont ajoutés à l'historique. Le résultat du calcul courant est conservé pour continuer à réaliser des calculs avec.

## Affichage et Responsive

L'application de la calculette a été créée pour les téléphones portables / Tablettes. Elle n'est donc pas adaptée à des écrans qui ont un format très différent comme des ordinateurs, des montres ou des télévisions.  

L'application s'adapte alors au format de l'écran.

## Fonctionnalités supplémentaires

J'ai créé un seul bouton pour les parenthèses "()". Il ajoute une parenthèse "(" lorsqu'il y a autant de "(" que de ")" ou que l'élément précédant est une "(" et ajoute sinon ")".

Il y a un bouton pour effacer un caractère et un bouton pour effacer le résultat courant.

Si l'expression mathématique est trop longue pour être écrite sur une seule ligne, alors elle est séparée sur plusieurs lignes.

J'ai crée un objet "Menu" dans la bar d'action de l'activité pour pouvoir revenir à la page principale.
Lorsqu'il est cliqué, cela termine l'activité courante.

## Tests

J'ai réalisé deux tests, l'un dans un cas nominal et l'autre dans un cas dégénéré.
Le premier test vérifie que le résultat de la fonction de calcul est correcte et ne lève pas d'erreur.
Le second vérifie que la fonction lève une erreur lorque la formule mathématique n'est pas correctement formatée.

## Sécurité de l'application

L'application ne permettant de rentrer que des chiffres et un nombre fini d'instructions mathématiques, il ne devrait pas être possible pour lui de réaliser une attaque par injection. 

De plus, l'application ne lève jamais d'exception sans qu'elles soient récupérées, ce qui permet d'éviter d'éviter les failles du JSE permettant d'injection de code. 

## Problèmes rencontrés

Problème d'internationalisation :
    
    Lors de l'utilisation de la calculatrice, le résultat obtenu après le calcul est converti en String. Cependant bien que cela fonctionnait parfaitement sur le téléphone émulé dans Android Studio (Langue : US), une fois installé sur mon téléphone (Langue : FR), le résultat converti en String utilises alors des "," à la place des "." pour séparer les décimales. Or la bibliothèque Exp4j n'est pas adaptée au format avec des virgules, ce qui crée une erreur de format.

    J'ai donc utilisé une manière différente pour transformer les Doubles en String 

    >    DecimalFormat decimalFormat = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));
    >    String troncatedResult = decimalFormat.format(result);


# Flux RSS

L'activité permettant la lecture d'un Flux RSS est très minimaliste. Elle continent un menu déroulant pour sélectionner la provenance du flux RSS, de l'affiche d'un élément et de boutons pour parcourir les différents éléments du flux.

Lorsqu'on change de flux, on affiche le premier de celui ci. 

A noter : L'ensemble du flux n'est pas sauvegardé mais simplement l'objet demandé, cela n'a pas été fait par manque de temps mais ça peut être rajouté plutôt simplement par la suite. 

## Structure

L'activité utilise une architecture MVP, composé d'une RSSView, d'un RSSPresenter et d'un RSSModel. 
On utilise également un Proxy avec la classe RSSHandler pour traiter la récupération du flux RSS.

Une fois sélectionné sur la page principale, une nouvelle activité est crée invoquant la vue, qui crée le présenteur, qui a son tour crée le modèle.

## Bibliothèques

Pour récupérer le flux RSS, j'ai utilisé la bilbiothèque Volley.

Pour traiter le flux, j'ai utilisé SAX car il est très efficace et plus simple à mettre emplacer qu'avec un parser de DOM.

## Messages Discord

Le Flux "ENSICAEN" permet de récupérer les messages qui ont été postés dans un salon Discord spécifique.

Pour cela, je suis passé par le service Zapier qui permet d'automatiser très simplement des taches entre différents services.

Ce service a alors créé un flux RSS à partir de balises qu'il a récupéré depuis Discord. 

Lien vers le flux RSS : https://zapier.com/engine/rss/17211843/ENSICAEN


## Fonctionnalités supplémentaires

J'ai crée un objet "Menu" dans la bar d'action de l'activité pour pouvoir revenir à la page principale.
Lorsqu'il est cliqué, cela termine l'activité courante.


# Pistes d'amélioration

Bien que j'ai tenté de mettre en pratique des principes d'UX, je n'ai pas passé trop de temps sur le design comme celui des boutons.

Pour les écrans trop larges, on pourrait séparer les boutons en deux parties pour chacune des mains de l'utilisateur.

Pour la calculatrice, je pourrai rajouter certaines opérations et touches comme ",".

On pourrait également faire intéragir les deux applications en ajoutant des notifications du flux RSS lorsqu'on utilise la calculatrice.

Pour l'affichage du Flux RSS, une amélioration du visuelle et de l'UX est nécessaire en mettant par exemple les boutons "Précédant" et "Suivant" en bas de l'écran. Nous pourrions également faire défiler le contenu plutôt que de devoir intéragir pour passer au message suivant.

Enfin les tests présents sont très succincts, il faudrait réaliser des tests fonctionnels et dégénérés pour les différentes fonction du modèle (Notamment pour le Flux RSS).

Il serai également bien de pouvoir choisir la langue de l'application avec un MessageBundle.


# Liens

Gitlab : https://gitlab.ecole.ensicaen.fr/duranton/dev-mobile

J'ai créé un compte GitHub pour pouvoir utiliser les Pages :

Github Pages : https://aa9578425.github.io/CalculatriceDuranton/ 

