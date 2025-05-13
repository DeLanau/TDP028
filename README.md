# TDP028 #

## Navigering  
- [Appbeskrivning](#appbeskrivning)  
- [Konkurrensanalys](#konkurrensanalys)  
- [Appdemo](/docs/tdp028_app_demo.mp4)  
- [Kodgenomgång](/docs/tdp028_kodgenomgang.mp4)  
- [Teknisk PM](/docs/pm.org)  

## Info  

**Betygsambition:** Betyg 3.  

Krav är inte slutliga och kan/kommer att utökas.  

**Tekniska krav:**
- Bra avvägning mellan användning av Activities och Fragments.  
- Modulär kod (User/Time Manager).  
- Hantering av bakåtknapp.  
- Användning av egenskriven View (StatsView).  

**Entreprenöriella krav:**
- Dark mode som UX-inställning.  
- Multispråksstöd (svenska/engelska).  

## Veckoplanering  

- v40: Skapa multispråkshantering.  
- v41: Byta från Activities till Fragments.  
- v42: Skapa Settings-fragment eller knapp för språkbyte.  
- v43: Utöka Settings-fragment eller skapa knapp för dark mode.  
- v44: Skapa Fragment för fokusvisualisering.  
- v45: Utöka fokusfragmentet med ökning/minskning av tid.  
- v46: Skapa knappar för att starta/stoppa fokusläge.  
- v47: Milstolpe 5.  
- v48: Skapa poängsystem för fokusläge.  
- v49: Skapa system för aktiviteter.  
- v50: Skapa lokal användarstatistik.  
- v51–v1: Extratid för att komplettera/justera småsaker.  
- v2: Inlämning av projektet.  

## Appbeskrivning  

Kärnsyftet med **"Focus & Fun"** är att förbättra fokus under studier, arbete eller programmering – men också att erbjuda en stunds nöje efter en intensiv session.  

Appen introducerar ett **fokusläge** som blockerar de flesta interaktioner med telefonen, förutom nödsamtal.  

För varje minut i fokusläge tjänar användaren **fokuspoäng (FP)**. Varje 60 FP ger en **kista** med slumpmässiga utmaningar. Dessa utmaningar är designade för att förlänga fokustiden eller främja hälsosamma vanor.  

När en kista öppnas kan användaren välja om hen vill genomföra utmaningen eller inte.  

**Exempel på utmaningar:**
- 2× fokus på nästa session  
- +15/30/45 min till nästa pass  

**Exempel på aktiviteter:**
- Gå en promenad på 15 minuter  
- Läs en bok i 15 minuter  

Appen innehåller inget bestraffningssystem och kontrollerar inte om aktiviteter genomförts. Det handlar om **glädje, hälsa och effektivitet**.  

## Konkurrensanalys  

Här analyseras konkurrentappen **"Forest: Focus for Productivity"**, tillgänglig på App Store och Google Play Store. Appen är inte gratis på App Store.  

Forest är designad för att "hålla fokus genom att plantera träd!". Den valdes för sitt breda funktionsutbud, unika idé, enkla design, positiva recensioner samt möjligheten att plantera riktiga träd genom att fokusera.  

### Måste-krav:
- Användaren ska kunna ställa in tidsintervall för fokus.  
- Användaren ska inte kunna lämna fokusläge eller öppna andra appar (förutom vid nödsituation).  
- Användaren ska kunna avsluta en fokussession.  
- Användaren ska få poäng för varje enhet av tid i fokusläge.  
- Användaren ska kunna spendera intjänade poäng.  

### Egna krav:
- Användaren ska få slumpmässiga utmaningar/aktiviteter.  
- Användaren ska kunna pausa en fokussession kort vid behov.  
- Användaren ska kunna tacka nej till utmaningar/aktiviteter.  
- Användaren ska kunna se sin statistik för utmaningar och aktiviteter.  

### Wireframe  

![alt text](/docs/wireframe.png)  

### UX-bilder  

- ![alt text](/docs/1.png)  
- ![alt text](/docs/2.png)  
- ![alt text](/docs/3.png)  
- ![alt text](/docs/4.png)
