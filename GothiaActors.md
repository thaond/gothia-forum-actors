# Introduktion till projektet Gothia Forum Actors #
Nedan följer en beskrivning av projektet Gothia Actor som har utförts av VGR IT åt Gothia Forum.

Projektet Gothia Forum Actors är det projekt som utvecklat mjukvaran bakom siten http://www.gothiaforum.com.

Systemet är byggt enligt Öppna Programs Referensarkitekturens, för mer om den se [Introduktion till Referensarkitektur](http://code.google.com/p/oppna-program/wiki/Introduktion_till_RA)

# Innehåll #


![http://gothia-forum-actors.googlecode.com/svn/wiki/images/gothiaforum-site.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/gothiaforum-site.png)

# Detta är Gothia Forum #

Gothia Forum har kommit till för att underlätta och driva det kliniska forskningssamarbetet mellan sjukvård, akademi och forskningsföretag i Västra Götaland. Vi vill i och med denna tjänst bygga upp en virtuell mötesplats och resurs för alla som arbetar med klinisk forskning.

Gothia Forum är en mötesplats och en frivillig resurs för alla som arbetar med klinisk forskning i regionen. Forskare inom Västra Götalandsregionen samt svenska och utländska forskningsföretag kan vända sig till oss. Ett centralt begrepp i Gothia Forum är `aktörer`. Se förklaring nedan angående vad som menas med en `aktör`.

**Vi ger service inom fyra områden:**

  * Information

En viktig del av Gothia Forums verksamhet är att samla och sprida information om pågående klinisk forskning inom VGR. Vi vill också sprida kunskap om utbudet av resurser för den kliniska forskningen som finns inom regionen och inom våra samarbetsorganisationer och företag.

  * Kvalitetsstöd

Gothia Forum vill verka för god kvalitet inom en kliniska forskningen och därför arbetar vi för att samordna resurser för kvalitetskontroll. Dessutom erbjuder Gothia Forum i samarbete med läkemedelsindustrin utbildningar inom GCP och deltar i utvecklingen av nya utbildningar.

  * Projektstöd

Genom Gothia Forum kan du få kontakter som kan hjälpa till med formaliteterna kring din forskning. Det kan handla om att upprätta forskningskontrakt och ansökningar av olika slag. Vi kan också erbjuda hjälp att hitta prövare inom olika discipliner och patienter via olika databaser inom VGR.

  * Strategisk utveckling

Den kliniska forskningen i Västra Götalandsregionen är beroende av att det sker en kontinuerlig utveckling av de resurser som behövs för forskningen. För att Sverige och VGR skall kunna hålla en fortsatt internationell hög standard är den utvecklingen sker i samarbete med alla inblandade aktörer. Gothia Forum har möjlighet att identifiera, besluta och genomföra strategiska utvecklingsinsatser.

# Vad är en aktör? #

En aktör är organisation eller ett företag som vill synas på Gothia Forums nätverk av aktörer. En aktör kan skapa en profil på Gothia Forums webbportal där de kan beskriva sina tjänster och sin verksamhet. Med hjälp av söktjänsten som är kopplat till presentationerna i nätverket skall det bli enklare att hitta olika aktörer som är verksamma inom klinisk forskning/prövning. Besökare skall få en överblick över vilka resurser som finns tillgängliga och snabbt hitta kontaktuppgifter till en aktör.

# Portlettar #
Gothia Actors består totalt av sex portletar. Söktjänst för Gothia Forum som består av tre portletar. Utöver de finns en för att skapa upp aktörer, en för att ge en rikare upplevelse av aktörs presentationen och en i kontrollpanelen för att konfigurera vilka artiklar (texter) som skall visas i portletarna och i temat.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/menubar.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/menubar.png)

## Actors Search ##
Denna portlet kalas för Actors Search. Denna portlet har funktionen autocomplete på de taggar som finns på aktörs presentationerna. Den visar upp träffra av sökningen som ett litet ”kort” av presentationen. Detta kort är en artikel i Liferay med en speciell mall, mer om detta senare.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/actrosSearchmedauto.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/actrosSearchmedauto.png)

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/hits.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/hits.png)

## Actors Search thin client ##
Det finns en portlet som ligger på förstasidan den har ingen egen funktionalitet förutom att att autocompleta på taggar och skickar vidare sökningen till Actors Search portleten. Denna portlet kallas för Actors Search thin client.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/frontpagesearch.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/frontpagesearch.png)

## Actors Search Result ##
Den sista portleten va söktjänsten är Actors Search Result det den gör är att den visar presentationen av en aktör. Den visar upp presentationen som är en artikel i Liferay med en speciell mall mer om detta senare.   På presentationen visas de taggar som är satt på aktören dessa går att klicka på. Då görs en ny sökning på denna tagg och träffaran av sökningen presenteras. Det finns även kontakt uppgifter till den person som är ansvarig för aktören på presentationen.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/pressentationactor.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/pressentationactor.png)

## Actors Form ##
Utöver själva sök-tjänsten finns det även en portlet för att mata in aktörer i ett formulär. Denna portlet är den största och mest komplicerade av de sex. Den visas bara när man är inloggad som en användare på en gömd sida som heter _Vår Sida_ med URL:en var-sida. Om man går till url för _vår sida_ och inte är inloggad skall den vara konfigurerad så att den inte visas. Om användaren inte har skapat någon presentation uppmanas den till att antingen skapa en eller att ansluta till en befintlig aktör.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/anlutellerskapa.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/anlutellerskapa.png)

### Skapa ny presentation ###

Om en användare väljer att skapa en presentation så kommer denne till ett formulär där den får fylla i dess uppgifter. Formuläret har validering som kontrollerar att de obligatoriska fält är ifyllda och att telefonnummer är giltiga osv. Det finns en Liferay komponent kallad liferay-ui:asset-tags-selector. Där går det skapa nya helt egna taggar. Den autocompletear på de taggar som redan finns i portalen och det går även välja bland de som finns in en lista.


![http://gothia-forum-actors.googlecode.com/svn/wiki/images/form.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/form.png)

När användaren har klickat vidare så kommer denne till nästa steg. Att ladda upp en logotyp till presentationen.  Efter det så visas en förhandsgranskning av presentationen. Det går att redigera den igen om det är något om skall ändras. Eller så går det att skicka iväg den för godkännande.  När den skickas iväg så startar ett workflow. Detta innebär att den eller de som är administratörer på Gothia Forum får ett mail om att ett nytt workflow-ärende har kommit. Då kan administratören gå in och granska presentationen som är en artikel. Om administratören tycker innehållet ser okej ut kan han godkänna den. Annars kan han avslå artikeln. Han har även möjlighet att redigera artikeln och sen godkänna den. När en artikel har blivit godkänd så går det att hitta och se den i söktjänsten. I dagsläget skickas inget e-post meddelande till ägaren av aktörspresentationen. För workflowet använder vi kaleo-web som är Liferays egna workflow engine. För att detta skal fungera måste ett wolkflow som heter Singel Approver slås på för artiklar i Liferay detta görs i kontrollpanelen.

## Show Similar Actors ##
Det finns en portlet för att visa liknande aktörer. Denna portlet skall finnas på samma sida som Actors Search Result portleten. Det den gör är att den visar aktörer som har en likhet med den aktör som för tillfället visas i Actors Search Result portleten. Detta gör den genom att Actors Search Result portleten skickar taggarna för aktören men public render parameters. Som läses in av Show Similar Actors som gör en sökning mot Solr och får där igenom en aktörer att visa.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/liknande.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/liknande.png)

## Settings ##
Denna portlets funktion är att konfigurera vilka artiklar som ska visas på olika vyer i portletarna i Gothia Actors projektet. Även i temat finns det artiklar som går att konfigurera på detta sätt. Anledning till att det är byggt så här är att det ska gå att byta de texter som visas på ett enkelt sätt. Genom att byta ID:t till ett annat så kan texterna enkelt ändras. Den använder något som kallas för expando fält i Liferay. Expando fälten är satta på gruppen för gothiaforum.com. Sen plockas ID:t till artikeln upp genom dessa expando fält på de ställen där artikeln ska visa. Det finns tre sådana konfigurerbara  artiklar i Actors Search en som alltid visas en som bara visas när ingen sökning är gjord och en när sökningen inte ger några träffar.

# Arkitektur av Gothia Actors #

## Söktjänsten och Workflow ##

### Solr ###
Solr-web är en integration mellan Solr och Liferay. Gothia Actors använder Solr-web genom Liferay. För att göra sökfrågor mot Solr (för mer om Solr se http://lucene.apache.org/solr/ ). För att använda solr i Liferay behövs war filen för Solr-web deployas in och ange adressen till Solr server. Då ersätts standard sökmotorn i Liferay mot Solr.  För att göra sökningar mot solr är det bara att använda Liferays vanliga api för sökningar. Solr-web ser även till att all data som skall vara sökbar indexeras i Solrs index. War filen för Solr-web finns på Liferays kundzon att hämta. Detta kan bara de som är registrerade kunder hos Liferay göra.

### Kaleo ###
Kaleo  är Liferays egna workflow engine. Som också denna går att finna på Liferays kundzon som en .war fil. I detta projekt används den för att få workflow hantering på artiklar. Så att när en artikel har skapats så går den inte direkt att publicera. Den måste först godkännas genom en arbetsflöde av en administratör. Först när den har blivit godkänd så blir den synlig för besökarna genom söktjänsten.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/gothia-actors.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/gothia-actors.png)

## En aktörs uppbyggnad ##
En aktör består av flera av Liferays standard entiteter. En aktör har en organisation där användaren är medlem av organisationen. Organisationen kan ha flera medlemmar om det är fler användare som har anslutit sig till aktören.

![http://gothia-forum-actors.googlecode.com/svn/wiki/images/actor.png](http://gothia-forum-actors.googlecode.com/svn/wiki/images/actor.png)

# Artiklar, strukturer och mallar i Liferay #
Efter som att en presentation av en aktör är en artikel i Liferay kan den visas på många olika sätt på olika platser i portalen beroende på vilken mall vi väljer att visa den med. Som när vi visar sökträffarna (dom små röda korten) så används en mall  GF\_ACTOR\_LIST och när vi visar presentationen så används en annan mall GF\_ACTOR\_TEMPLATE.  Det finns även en mall som heter GF\_MORE\_LIKE\_THIS och den används när liknande aktörer visas.
Strukturer i Liferay är de fält som finns i en artikel. Artiklar och mallar är kopplade till strukturer. För att alla artiklar som används för att presentera en aktör används struktur GF\_SERVICE.


# Instruktioner för att sätta upp Gothia Actros #

## Sätta upp Liferay med Tomcat ##

För att sätta upp Liferay följ länken nedan.
http://code.google.com/p/oppna-program/wiki/Anvisningar_Utvecklingsmilo_Portlet

## Bygga Gothia Actors ##
För att bygga gothia så används maven för mer om detta se
http://code.google.com/p/oppna-program/wiki/Anvisningar_Utvecklingsmiljo#Bygg_och_skapa_eclipse-projektfiler_f%C3%B6r_systemet

## Produktionssätta Portlets ##

Hela Gothia Forum projektet består totalt av 9 stycken war filer inklusive denna för Gothia Actors.

  * Gothia Actors
  * Gothia Theme (CSS markup)
  * Solr
  * Kaleo
  * Custom Velocity Rools RSS Hook
  * Googel Maps Portlet
  * Gothia Layouts
  * Hotell Liferay Hooks
  * Web Form Portlet

## Övrigt ##

Kan vara bra att känna till http://code.google.com/p/oppna-program/wiki/regler_och_riktlinjer