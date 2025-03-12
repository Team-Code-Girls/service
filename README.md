# Aplicatie e-ticketing
Aplicație de e-ticketing destinată achiziției, gestionării și validării biletelor pentru diverse evenimente.

## Functionalitati
În cadrul aplicației utilizatorii pot să cumpere bilete și pot beneficia și de diferite avantaje în funcție de activitatea pe care o au. Cu fiecare achiziție pe care o face, un utilizator primește un număr de puncte raportat la prețul biletului. La un anumit număr de puncte utilizatorii beneficiază de anumite reduceri, sau chiar de primirea unui bilet gratuit pentru un eveniment. Prețul biletelor este ajustat în funcție de mai multe metrici (după achiziționarea a peste 80% din bilete, biletele devin mai scumpe; prețul unui bilet este mai crescut în ultima zi; dacă vânzările sunt foarte scăzute pentru un anumit eveniment, prețul biletelor se ieftinește). Totodată, aplicația va avea un sistem de statistici (cele mai populare evenimente, cei mai activi utilizatori, categoriile de evenimente cel mai apreciate, sau din contră cel mai puțin căutate).

## Sistemul de puncte - descriere
Aplicația încadrează biletele în mai multe intervale de preț:
 - 0 -> 50 => la achizitia unui astfel de bilet utilizatorul primeste 2 puncte
 - 50 -> 100 => la achizitia unui astfel de bilet utilizatorul primeste 5 puncte
 - 100 -> 150 => la achizitia unui astfel de bilet utilizatorul primeste 10 puncte
 - 150 -> 200 => la achizitia unui astfel de bilet utilizatorul primeste 15 puncte
 - 200 -> + => la achizitia unui astfel de bilet utilizatorul primeste 20 puncte

Utilizatorul acumuleaza aceste puncte si poate beneficia de urmatoarele reduceri:
 - 50 de puncte => reducere de 20%
 - 100 de puncte => reducere de 50%
 - 200 de puncte => reducere de 100%

## Arhitectura
În ceea ce privește arhitectura aplicației, este o aplicație bazată pe arhitectura client-server și are la bază request-uri http (get, post, put, delete).

## Impartirea functionalitatilor 
- `Berca Teodora` - Sistemul de puncte
- `Horga Daria` - Statistici
- `Iscru Bianca` - Ajustare dinamica preturi
