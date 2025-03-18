# Aplicatie e-ticketing
Aplicație de e-ticketing destinată achiziției, gestionării și validării biletelor pentru diverse evenimente.

## Functionalitati
În cadrul aplicației utilizatorii pot să cumpere bilete și pot beneficia și de diferite avantaje în funcție de activitatea pe care o au. Cu fiecare achiziție pe care o face, un utilizator primește un număr de puncte raportat la prețul biletului. La un anumit număr de puncte utilizatorii beneficiază de anumite reduceri, sau chiar de primirea unui bilet gratuit pentru un eveniment. Prețul biletelor este ajustat în funcție de mai multe metrici (după achiziționarea a peste 80% din bilete, biletele devin mai scumpe; prețul unui bilet este mai crescut în ultima zi; dacă vânzările sunt foarte scăzute pentru un anumit eveniment, prețul biletelor se ieftinește). Totodată, aplicația va avea un sistem de statistici (cele mai populare evenimente, cei mai activi utilizatori, categoriile de evenimente cel mai apreciate, sau din contră cel mai puțin căutate).

## Statistici

Aplicația oferă funcționalități pentru analiza datelor despre bilete, având ca scop determinarea evenimentelor cele mai populare, atât în total, cât și pe categorii de vârstă.

1. Listă de evenimente sortate în funcție de popularitate, împreună cu procentul de bilete vândute pentru fiecare eveniment.
2. Listă cu cel mai popular eveniment pentru fiecare grupă de vârstă, pe baza vânzărilor de bilete.

## Arhitectura
În ceea ce privește arhitectura aplicației, este o aplicație bazată pe arhitectura client-server și are la bază request-uri http (get, post, put, delete).

## Impartirea functionalitatilor 
- `Berca Teodora` - Sistemul de puncte
- `Horga Daria` - Statistici
- `Iscru Bianca` - Ajustare dinamica preturi
