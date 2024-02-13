# JaQuizes Uživatelská Dokumentace

## 1. Úvod

JaQuizes je konzolová aplikace napsaná v jazyce Java.
Pomocí této aplikace si uživatelé mohou procvičovat svoje znalosti pomocí odpovídání na kvízové otázky v různých kategoriích.

## 2. Instalace a Spuštění

### 2.1. Stažení Aplikace

Aplikaci můžete stáhnout ze [https://github.com/Ads36/JaQuizes](https://github.com/Ads36/JaQuizes)

### 2.2. Instalace Java Runtime Environment (JRE)

Jestliže JRE není na vašem počítači nainstalováno, můžete si jej stáhnout z [oficiální stránky Javy](https://www.oracle.com/java/technologies/javase-jre8-downloads.html).

### 2.3. Spuštění Aplikace

Po stažení a instalaci JRE spusťte aplikaci dvojitým kliknutím na soubor "JaQuizes.jar".
Alternativně lze aplikaci spustit příkazem `java -jar JaQuizes.jar` ve složce s tímto souborem.

Pro pokročilé uživatele: tato aplikace používá Maven, <https://maven.apache.org/>

## 3. Navigace v Aplikaci

Po spuštění aplikace vyberete počet otázek, kategorie a kvíz začne. Výběr se provadí napsáním požadovaného čísla nebo písmena do konzole a stisknutím klávesy ENTER, u odpovědí na otázky lze odpovídat i číslem pořadí odpovědi.

Po zodpovězení požadovaného počtu otázek se vypíše celkové skóre za tento kvíz společně se skórem přes všechny kategorie.

Poté dostanete na výběr, jestli chcete další kvíz, změnit kategorie otázek nebo vypsat výsledky za více kvízů.

## 4. Formát otázek

Načítané otázky jsou v souboru questions.txt.
Formát otázky vypadá následovně:

kategorie  
otázka  
správná odpověď  
odpověď2  
odpověď3  
.  
.  
.  
odpověďX  

Otázky od sebe musí být odděleny aspoň jedním prázdným řádkem.

### 4.1 Přidání otázky

Otázka se jednoduše přidá připsáním na začátek/konec souboru s otázkami, nezpomeňte dodržet správný formát.

## 5. Závěrem

Pro případné dotazy můžete psát na email <cervenkaadamek@seznam.cz>.
