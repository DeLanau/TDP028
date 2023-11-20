# TDP028 #

## Navigering ## 
- [Appbeskrivning](#Appbeskrivning)
- [Konkurrensanalys](#Konkurrensanalys)
## Info ##

<figure class="video_container">
  <video controls="true" allowfullscreen="true" poster="path/to/poster_image.png">
    <source src="/docs/tdp028_app_demo.mp4" type="video/mp4">
  </video>
</figure>

Betygsambition: betyg 3.

Krav är inte slutliga och kan/kommer utåkas.

Tekniska krav:

- Hantering av stora och små skärmar med olika layouter.
- Bra avvägning mellan användning av Acitivy och Fragments.

Enterprenöriella krav:

- Darkmode UX inställning
- Multispråk-stöd

## Veckoplanering  ##

- v40 : skapa multispråk hantering.
- v41 : byta från activity till fragments. 
- v42 : skapa settings fragment eller button för språk byte.
- v43 : utåka setting fragment eller skapa button för darkmode.
- v44 : skapa fragment focus visual delen.
- v45 : utåka fragment focus med åkning/minskning av tid.
- v46 : skapa buttons för start/stop focus läge. 
- v47 : Milstolpe-5.
- v48 : skapa poäng systemet för focus läge.
- v49 : skapa systemet för aktiviteter.
- v50 : skapa lokalt användare statistik.
- v51-1 : extra tid för att komplettera/ändra små saker.
- v2 : inlämning av projekt.

## Appbeskrivning ##

Kärnpoängen med "Focus & Fun" är att förbättra fokus under studierna,
arbetet eller programmeringssessioner. Men också att ha lite kul efter
en intensiv session. Appen introducerar "fokusläge" som kommer att
blockera de flesta interaktioner med telefonen förutom nödsamtal. För
varje minut som användaren spenderar i "fokusläge". Hen kommer att få
en "fokuspoäng" helt enkelt kallas FP. Varje 60 fp användaren kan få
en kista med slumpmässiga utmaningar. Dessa utmaningar är utformade
för att förlänga fokustiden eller till ge någon hälsosam
aktivitet/utmaning.
 
När en kista öppnas kan användaren bestämma om han vill göra
utmåning/aktivitet eller ej.
 
Några av utmaningarna kan se ut så här:
- 2 gånger på nästa fokussession
- +15/30/45m till nästa fokuspass
 
Vissa aktiviteter kan se ut så här:
 
- Gå en promenad på 15 m
- Läs En Bok I 15m
 
Appen kommer inte att ha ett bestraffningssystem samt en kontroll om
användaren har utfört en aktivitet eller inte. Det är bara roligt,
hälsosamt och effektivt.

## Konkurrensanalys ##

I denna konkurrentanalys kommer appen "Forest: Focus for Productiv-
ity" att analyseras. Appen finns på App store och Google Play Store. App
Store-versionen av appen är ej gratis. Denna app är gjord för att "Hålla
fokus genom att plantera träd!".
Denna applikation valdes på grund av ganska stora utbud av funktioner,
en unik idé med enkel design, ett stort antal positiva recensioner i Google
Play Store samt möjligheten att plantera riktiga träd genom att hålla fokus.

- Måste-krav
  - En användare ska kunna ställa in tidsintervallet för fokussessionen.
  - En användare ska inte kunna gå ur fokusläge eller öppna andra appar som inte är nödsituationer.
  - En användare ska kunna avsluta fokussessionen.
  - En användare ska kunna få poäng för varje n tid som spenderas i fokusläge.
  - En användare ska kunna spendera intjänade poäng.

- Egna-krav
  - En användare ska kunna få slumpmässiga utmaningar eller aktiviteter.
  - En användare ska kunna sätta kort pause på focus session vid behöv.
  - En användare ska kunna tacka nej till utmåning eller aktivitet.
  - En användare ska kunna se egen utmåning och aktivitets statistik.

- Wireframe
  
  ![alt text](/docs/wireframe.png)
  
- Ux-bilder
  
 - ![alt text](/docs/1.png)
 - ![alt text](/docs/2.png)
 - ![alt text](/docs/3.png)
 - ![alt text](/docs/4.png)
