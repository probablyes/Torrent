# Torrent
Torrent network written in Java 8

Sieć torrent służąca do ściągania i udostępniania plików w obrębie jednej sieci. Pracuje w dwóch wersjach:  
- host2host (H2H): możliwość wymiany plików przez dwa hosty podłączone do sieci  
- multihost (MH): wymiana plików przez wiele podłączonych do sieci hostów  
  
  
Program wykorzystuje protokół TCP i zapewnia kilka funkcjonalności (dla H2H i MH):  
- dostęp do listy plików na konkretnym hoście razem z sumamy kontrolnymi MD5 plików  
- pobieranie plików z innego hosta, tzn ściąganie pliku z wybranego hosta o wybranej nazwie  
- przesyłanie plików na wybrany host, tzn wrzucamy plik ze swojego dysku na wybranego hosta  
- każdy komputer działający w sieci może udostepnić raport ze swojego działania przez swój własny serwer WWW (dostęp przez przeglądarke)

Projekt działa na systemie linux. Wkrótce zostanie spakowany do jara w celu łatwiejszego uruchamiania...    
Przykładowe dane wymagane do podania przez użytkownika po uruchomieniu:  
[siezka] home/test  
[wersja] MH  
[port na ktorym chcę pracowac] 60001  
[nazwa hosta z ktorym chce sie polaczyc] localhost  
[port na ktorym nasluchuje wybrany host] 60004  
