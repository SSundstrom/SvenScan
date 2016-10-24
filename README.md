# DAT255 Project: SvenScan
## Team: get_rect(+3)

* Felix Jansson
* Jesper Lindström
* Lisa Larsson
* Maija Happonen
* Rebecca Finne
* Samuel Håkansson
* Simon Sundström

## Product Vision
> For newly arrived refugees who wants to learn Swedish words, the SvenScan is a language teaching application, that by the use of augmented reality helps with teaching words encountered in user’s everyday life. Unlike learning from written words, our product lets the user listen to the words pronunciation and track the user’s learning progress.

## Resurser
* Scrum board: [Trello Board](https://trello.com/b/DwtLc2Xl/svenscan)
* Social contract: [Social contract.md](https://github.com/SSundstrom/SvenScan/blob/master/Docs/SocialContract.md)
* Reflection, D1, D2. See "Docs" folder.
* Gitinspector (Appendix A i readme.md)

## Teknisk information
### Kodkvalité
Projektet har analyserats med hjälp av ett externt program som heter FindBugs. Detta program markerar potentiella buggar i koden som bör lösas. Utvecklingsgruppen har följt såpass god kodpraxis att det inte existerar några buggar enligt FindBugs, se appendix B.

### Kodtester
Applikationens funktionalitet kretsar kring komplexa system från tredjepart, så som textigenkänning, kamera och kommunikation med databas. Detta medför att enhetstester är svåra att genomföra. De enhetstester som finns gäller därav främst en mindre mängd funktionalitet som är byggd av oss och inte tredjepart.
Androids utvecklingsmiljö tillhandahåller integrations tester för att verifiera att applikationen fungerar enligt önskemål när en användare interagerar med applikationen. Tyvärr var det såpass snävt med tid och vi lyckades inte få dessa tester att fungera.

### Mjukvarudesign
![Overview](http://i.imgur.com/YYUSrUJ.png)
Applikationens kodbas är uppdelad i paket beroende på vilket lager eller typ av klass det är. Vi eftersträvar att dölja tekniska detaljer utåt sett, med hjälp av “interfaces” och “repository pattern”. Applikationen använder idag Googles realtidsdatabas Firebase för att dela data mellan alla användare, men tack vare “repository pattern” behöver man inte känna till denna detaljen när man utvecklar applikationen.
Overview

### Översikt
Svenscans kodbas är bygg på Java och Android, med Gradle som byggverktyg. Det är byggt kring flertal tredjepartsbibliotek såsom [Squarecamera](https://github.com/boxme/SquareCamera/tree/master/squarecamera/src/main/java/com/desmond/squarecamera) för att hantera kameran. [Tess-Two](https://github.com/rmtheis/tess-two) i kombination med ett svenskt språkpaket tillhandahåller textigenkänning. [Firebase](https://firebase.google.com) från Google har använts som applikationens databas. Interaktion med Firebase sker igenom “Repository Pattern”, vilken döljer den specifika databas-implementationen gentemot resten av applikationen.

**Applikationens innehåller följande vyer:**
* Kamera (`ScanActivity`) - applikationens “entry point”, där man dels kommer åt övriga delar via menyknappar och dels kan skanna ett ord.
* Visa ord (`ShowWordActivity`) - visar ordet som har skannats eller via åtkomst från Favoriter-vyn.
* Lägg till ord (`AddWordActivity`)
* Mini-spel (`GameActivity`) - ett spel som gör det möjligt att träna på association mellan ord och bild utan att skanna ord.
* Startvyn (`StartActivity`) - frågar om tillåtelse att använda kamera och lagring, innan kameran startar.
* Guide / hjälp (`HelpActivity`) - en stegvis guide som visar hur man ska använda applikationen.
* Mina favoriter (`FavoriteListView`) - en lista med favoritmarkerade ord.
* Tillfälligt dold: Mina framsteg (`MyPageActivity`) - ska innehålla mer detaljerad information om användarens nivå, poäng och “achievements”. Dock tillfälligt dold då vi inte anser att det är nödvändigt att ha en vy för endast poäng (till följd av att “achievements” inte blev klara inför deadline).

### User stories
**Avklarade**

* #0: As a user I want to see an application, so that I know my developer isn't slacking off
* #1: As a person who's learning swedish I want to scan i.e furniture and see its swedish word on the device.
* #2: As a person who's learning swedish I want to be able to hear what a word sounds like, so that I can practice the pronunciation.
* #3: As a swedish learning user I want to be able to favorite words I encounter, so that I can view the difficult words again later on.
* #5: As a new user I want to learn how the app works, so that I can use the app with ease. The help should be comprehendable by anyone who does not understand swedish or english.
* #6: As a user I want to be able to repeat the sound of a saved word whenever I want, so that I can practice it's pronunciation.
* #7: As a swedish mentor I would like to create new words/objects.
* #10: As a swedish learning user I would like to have a static play mode, where I can see a word and pick the answer from X image alternatives, so that I can play without moving.
* #17: As a user I would like to hear the word I've scanned, so that I can learn the pronunciation.
* #18: As a user I want my favorite words to be saved even when I close the application
* #19: As a user I want my list of favorite words to contain a picture of the object, the word and the possibilty to hear its pronunciation again.
* #20: As a swedish mentor I would like to record the pronunciation of words that I create
* #21: As a user I would like to be able to hear the correct sound for the word
* #22: As a user I would like to see the correct picture for the word if such a picture exists
* #23: As a swedish mentor I would like to submit a picture of the words I create
* #25: As a user I would like to be told more nicely when a word doesn't exist
* #26: As a user I would like to understand the interface of each part of the app
* #27. As a user i want a completion look which tells how many right or wrongs I had in the game.
* #28: As a user I want to be able to earn points when playing the word game
* #29: As a user I would like less swedish at the end of a gameround.
* #30: As a user I would like to be able to hear the word when I am playing the game

**Kvarstående user stories**

* #11: As a user I want pictures taken by users to be of decent quality.
* #12: As a user I want to be able to store pictures of my own objects, to connect my own pictures to the word.
* #13: As a user I think the application is more fun if there are various achievements to complete.
* #16: As a user I don't want to create words that already exist because I don't like excise
* #25: As a user I would like to be able to set new images and sounds for different words.

## Appendix
### A. Gitinspector
![Gitinspector](http://i.imgur.com/aXQTSyj.png)

### B. FindBugs
![Findbugs](http://i.imgur.com/FZpPTpk.png)
