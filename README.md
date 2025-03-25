# Aplicatie e-ticketing
Aplicație de e-ticketing destinată achiziției, gestionării și validării biletelor pentru diverse evenimente.

## Functionalitati
În cadrul aplicației utilizatorii pot să cumpere bilete și pot beneficia și de diferite avantaje în funcție de activitatea pe care o au. Cu fiecare achiziție pe care o face, un utilizator primește un număr de puncte raportat la prețul biletului. La un anumit număr de puncte utilizatorii beneficiază de anumite reduceri, sau chiar de primirea unui bilet gratuit pentru un eveniment. Prețul biletelor este ajustat în funcție de mai multe metrici (după achiziționarea a peste 80% din bilete, biletele devin mai scumpe; prețul unui bilet este mai crescut în ultima zi; dacă vânzările sunt foarte scăzute pentru un anumit eveniment, prețul biletelor se ieftinește). Totodată, aplicația va avea un sistem de statistici (cele mai populare evenimente, cei mai activi utilizatori, categoriile de evenimente cel mai apreciate, sau din contră cel mai puțin căutate).

## Statistici

Aplicația oferă funcționalități pentru analiza datelor despre bilete, având ca scop determinarea evenimentelor cele mai populare, atât în total, cât și pe categorii de vârstă.

1. Listă de evenimente sortate în funcție de popularitate, împreună cu procentul de bilete vândute pentru fiecare eveniment.
2. Listă cu cel mai popular eveniment pentru fiecare grupă de vârstă, pe baza vânzărilor de bilete.
## Sistemul de puncte - descriere
Aplicația încadrează biletele în mai multe intervale de preț:
 - 0 -> 50 => la achizitia unui astfel de bilet utilizatorul primeste 2 puncte
 - 50 -> 100 => la achizitia unui astfel de bilet utilizatorul primeste 5 puncte
 - 100 -> 150 => la achizitia unui astfel de bilet utilizatorul primeste 10 puncte
 - 150 -> 200 => la achizitia unui astfel de bilet utilizatorul primeste 15 puncte
 - 200 -> + => la achizitia unui astfel de bilet utilizatorul primeste 20 puncte

<<<<<<< HEAD
Utilizatorul acumuleaza aceste puncte si poate beneficia de urmatoarele reduceri:
 - 50 de puncte => reducere de 20%
 - 100 de puncte => reducere de 50%
 - 200 de puncte => reducere de 100%
=======
* Make sure that the Github repository is forked under your account / Organization
* Create a new Codespace from your forked repository
* Wait for the Codespace to be up and running
* Check java version
  * ```java -version``` should return 21
  * Validate Java version is properly configured in devcontainer.json
  * If the correct Java version is set in devcontainer.json, but the command returns a different version:
    * Open Command Palette (Ctrl+Shift+P) and run `Rebuild Container`
    * Wait for the container to be rebuilt, and if the Java version is still incorrect, try a full rebuild or set it manually in the terminal:
      * ```sdk default java 21.0.5-ms```
* Make sure that Docker service has been started
    * ```docker ps``` should return no error
* For running all services in docker:
    * Build the docker image of the hello world service
        * ```make build```
    * Start all the service containers
        * ```./start.sh```
* For running / debugging directly in Visual Studio Code
  * Build and run the Spring Boot service
    * ```./gradlew build```
    * ```./gradlew bootRun```
  * Start the MongoDB related services
      * ```./start_mongo_only.sh```
* Use [requests.http](requests.http) to test API endpoints
* Navigation between methods (e.g. 'Go to Definition') may require:
  * ```./gradlew build``` 
>>>>>>> upstream/main

## Ajustare dinamica preturi
    
1. Aplicare discount 20% la evenimente care, cu cel mult 3 zile inainte de ziua evenimentului, inca au sub 50% bilete vandute
   - Nu se mai pot aplica scumpiri ale pretului dupa discount

2. Crestere pret bilet 30% fata de pretul initial, in ziua evenimentului
   - Nu se aplica la evenimentele care au dispus de discount
     
3. Crestere pret bilet 20%, atunci cand s-au vandut peste 80% din bilete 
   - Nu se aplica in ziua evenimentului, daca atunci se atinge pragul 

## Arhitectura
În ceea ce privește arhitectura aplicației, este o aplicație bazată pe arhitectura client-server și are la bază request-uri http (get, post, put, delete).

## Impartirea functionalitatilor 
- `Berca Teodora` - Sistemul de puncte
- `Horga Daria` - Statistici
- `Iscru Bianca` - Ajustare dinamica preturi
