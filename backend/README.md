# Application GESEMP

GESEMP est une application simplifiée de gestion d'employé

## Architecture
GESEMP utilise une architecture client serveur basée sur les
frameworks [Angular](https://angular.io/) côté client et [Spring boot](https://spring.io/projects/spring-boot) côté serveur.

<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/aqgo.png">

## Conception UML
Les règles de gestion de GESEMP :
- un employé appartient à un département ;
- un département est constitué de plusieurs employés ;
- il existe un ou plusieurs administrateur(s) qui gèrent les
  départements, les employés et les rôles.


Ci-dessous les diagrammes de cas d'utilisation et de classe
l'application :

### a- Diagramme de cas d'utilisation
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/se1f.jpeg">

### b- Diagramme de classe
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/g80y.jpeg">

## Quelques captures d'écran
### interface de connexion
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/cjo1.jpeg">

### Accueil administrateur
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/trqb.jpeg">

### Ajouter un employé
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/qji7.jpeg">

### Liste d'employés
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/bw0s.jpeg">

### Détails d'un employé
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/7yui.jpeg">

### Liste des départements
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/i5k2.jpeg">

## Exécution de l'application 
Après avoir clôné le projet, vous pouvez le lancer de 2 manières
en local ou via des containers dockeur.

### Exécution en local
Ce projet utilise la version 8 de Java, pour télécharger les dépendances du pom.xml vous pourrez
utilisez la commande suivante sur le terminal de votre IDE preféré 😊:

```
    ./mvnw dependency:resolve
```
# ⚠️ Note : Vous devez disposer d'un SGBD MySql installé sur votre ordinateur ... Vous y créerez une BD nommée "gesemp" avant la prochaine étape.

Ensuite il suffit de lancer l'application via votre IDE ou
via la commande suivante :

```
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Exécution via dockeur

### Dockeur: qu'est-ce que c'est ? 🤷🏽‍♂
<img width="1042" height="300" alt="gesemp-screenshot" src="https://www.docker.com/wp-content/uploads/2022/05/Docker_Temporary_Image_Google_Blue_1080x1080_v1.png">

[Docker](https://www.docker.com/) est une plate-forme conçue pour aider les développeurs à créer, partager et exécuter des applications modernes.
Docker utilise un système de containeurs permettant d'alléger l'utilisation des ressources locales
il est possible d'utiliser des stacks complexes sans empiéter de manière prépondérante sur l'environnement local.
Un containeur est un processus isolé qui tourne sur notre ordinateur. 
Ce système de containeurisation facilite grandement le partage et le déploiement des applications web modernes.
Celles utilisant une architecture micro services.

### lancer les containeurs gesemp :
Conformément à notre archtecture notre application possède 3 grandes parties :
 - partie Front : Angular
 - partie Back : Spring Boot
 - Base de données : MySql

il est question d'exécuter ces parties sous forme de containers
pour ce faire vous devez installé [Dockeur desktop](https://www.docker.com/products/docker-desktop/)
et lancez la commande suivante via le terminal de votre IDE :

```
 dockeur compose up
```

Et laissez la magie opérer 🧞‍♂️ ✨

### Visualisation sur Docker desktop
Vous pouvez voir les containeurs gesemp qui tournent 
<img width="1042" alt="gesemp-screenshot" src="https://zupimages.net/up/22/43/369g.png">
