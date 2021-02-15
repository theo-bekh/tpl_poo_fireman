# Ensimag 2A POO - TP 2018/19
## Fichiers

- src: contient les classes fournies par les enseignants ainsi que les notres
  -> simulation.Simulateur.java : Contient le main, une classe qui gère la simulation

- cartes: quelques exemples de fichiers de donnees

- bin/gui.jar: archive Java contenant les classes de l'interface graphique. Voir un exemple d'utilisation dans TestInvader.java

- doc: la documentation (API) des classes de l'interface graphique contenues dans gui.jar. Point d'entrée: index.html

- Rapport TP POO.pdf : le rapport

## Utilisation

Compilation : 
```bash
javac -d ./bin -cp ./gui.jar $(find . -name '*.java')
```

Execution : 
```bash
java -classpath ./bin:./gui.jar simulation.Simulateur <nomDeFichier> [options]
```
		Options : -i : pompier intelligent (par defaut)
		          -g : pompier glouton
