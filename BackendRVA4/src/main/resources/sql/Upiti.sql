-- izlistati sve artikle
select * 
from artikl;
-- izlistati artikl sa id = 4 
select * 
from artikl 
where id = 4;
-- izlistati artikl sa proizvodjacem "CENTROPROIZVOD" 
select * 
from artikl 
where proizvodjac LIKE 'CENTROPROIZVOD';
-- izlistati nazive artikala koji pocinju slovom P
select naziv 
from artikl 
where naziv LIKE 'P%';
-- izlistati porudzbine sa nazivima dobavljaca
select p.*, d.naziv 
from porudzbina p, dobavljac d 
where p.dobavljac = d.id;
-- izlistati porudzbine sa nazivima dobavljaca kreirane pre 1. marta 
select p.*, d.naziv 
from porudzbina p, dobavljac d 
where p.dobavljac = d.id and p.datum < to_date('01.03.2017', 'dd.mm.yyyy');
-- izlistati sve stavke porudzbine, porudzbine sa id = 4
select * 
from stavka_porudzbine 
where porudzbina = 4;
-- izlistati sve stavke porudzbine, artikla  "Puding jagoda"
select * 
from stavka_porudzbine sp, artikl a 
where sp.artikl = a.id and a.naziv like  'Puding jagoda' ;
-- izlistati sve stavke porudzbine, dobavljaca CENTROPROIZVOD
select sp.*, d.naziv as "Назив добављача" 
from stavka_porudzbine sp, porudzbina p, dobavljac d 
where sp.porudzbina = p.id and p.dobavljac = d.id and d.naziv like 'CENTROPROIZVOD'; 
-- izlistati sve porudzbine, koje imaju artikl "Puding jagoda" i koje su placene
select p.* 
from stavka_porudzbine sp, porudzbina p, artikl a 
where p.id = sp.porudzbina and sp.artikl = a.id and a.naziv like 'Puding jagoda' and p.placeno = true;
-- izlistati porudzbinu (i njene stavke) kreiranu 18.02.2017 na sledeci nacin (prikazati)
select p.id as "ID porudžbine", to_char(p.datum, 'dd.mm.yyyy') as "Datum kreiranja", p.isporuceno as "Datum isporuke", p.placeno as "Plaćeno", a.naziv as "Artikl", sp.kolicina as "Količina", sp.jedinica_mere as "JM", d.naziv as "Dobavljač"
from  porudzbina p, dobavljac d, stavka_porudzbine sp, artikl a
where p.id = sp.porudzbina and sp.artikl = a.id and p.dobavljac = d.id and p.datum = to_date('18.02.2017', 'dd.mm.yyyy');