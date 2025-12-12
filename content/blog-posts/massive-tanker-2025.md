:page/title 🤯  Massive Tanker i 2025  🤯
:blog-post/tags [:rewind]
:blog-post/author {:person/id :mathias}
:blog-post/published #time/ldt "2025-12-12T09:01:00"
:blog-post/desc

Hva i all verden har jeg drevet med i år?

:page/body

Et år er gått og jeg har brukt Clojure på jobben på fulltid i Team Servering. I
tillegg har jeg sett litt på programmeringsspråket Zig.

I anledning av årsskiftet om noen få uker kommer jeg her med en oppsummering av
det jeg har brukt noe av tiden min på i 2025.

## Bloggposter

### [Følg dopamin-hitsene](/blog-posts/folg-dopamin-hitsene/)
<em>12. desember 2025</em>

### [Ekspert på utviklerverktøyet ditt](/blog-posts/ekspert-paa-utviklerverktoy/)
<em>21. oktober 2025</em>

### [Git repo – En analyse](https://mathivethoughts.no/blog-posts/git-analyse/)
<em>18. juni 2025</em>

### [Terminal kos](https://mathivethoughts.no/blog-posts/terminal-kos/)
<em>22. februar 2025</em>

### [Å animere transisjoner mellom statiske sider](https://mathivethoughts.no/blog-posts/view-transition/)
<em>5. februar 2025</em>

## Clojure

### Chipper Chap's Chateau <small>[Github](https://github.com/boosja/chipper-chaps-chateau)</small>

En variant av Tripp-trapp-tresko i diverse dimensjoner skrevet i ClojureScript
og Replicant.

### Ungandr <small>[Github](https://github.com/boosja/ungandr)</small>

Et uferdig eksperiment i å skrive et spill i terminalen med
[Babashka](https://babashka.org/). Les mer i [Terminal
kos](https://mathivethoughts.no/blog-posts/terminal-kos/).

Du er Ungandr, et avkom av den legendariske Gormungandr, og har blitt vekket av
bråket fra gravearbeidet til et massivt kompleks. I blindt raseri stormer du inn
i komplekset og forårsaker havari.

### Repolyzer <small>[Github](https://github.com/boosja/repolyzer)</small>

Les in git-historikken til et prosjekt i en datomic-database og lag spørringer
for å vise dataene i diverse diagrammer. Les mer i [Git repo – En
analyse](https://mathivethoughts.no/blog-posts/git-analyse/).

Jeg har også gjort et lite stykke arbeid på et
[D3](https://d3js.org/)-bibliotek i ClojureScript, men det er et godt stykke
igjen før (eller hvis 😅) det blir ferdig.

## Zig

I sommer fikk jeg lyst til å lære meg et lav-nivå-språk for å prøve ut
minne-manipulering, spill-utvikling og skrive native applikasjoner. Jeg landet
på Zig og følgende er resultatet:

### zlides <small>[Github](https://github.com/boosja/zlides)</small>

Et slideshow som kjører i terminalen skrevet med Zig. Tar en tekst-fil som
input og genererer et enkelt slideshow du kan navigere deg rundt i.

### zansi <small>[Github](https://github.com/boosja/zansi)</small>

Zansi er et forsøk på å forenkle bruk av ANSI escape koder via
[hiccup](https://github.com/weavejester/hiccup/wiki/Syntax).

```clojure
[:bold " bold " [:fg/blue " blue "]]
;; =>
\x1b[1m bold \x1b[34m blue \x1b[39m\x1b[0m
```

Biblioteket sprang ut av zlides-prosjektet, som inneholder en basal syntaks
highlighter for Zig-kode.

### Zigme <small>[Github](https://github.com/boosja/mal/tree/master/impls/zigme)</small>

Kjenner du til [Make a Lisp](https://github.com/kanaka/mal)? Det er en guide som
hjelper deg med å skrive din egen lisp i hvilket som helst språk. Zigme er
lispen min jeg tukler med iblant.

## Emacs

Da jeg startet på Team Servering i januar byttet jeg til Emacs med
[configgen](https://github.com/magnars/emacsd-reboot) til Christian og Magnar.
En tøff og hard overgang, men jeg føler meg nå mer bekvem og effektiv enn noen
gang før, og jeg har til og med lagt til flere forbedringer til glede for meg
selv og ikke minst teamet.

### Keybindings

Les mer i [Ekspert på utviklerverktøyet ditt](https://parenteser.mattilsynet.io/ekspert-paa-utviklerverktoy/).

#### `C-c C-M-s`

[Pretty-printer](https://github.com/magnars/emacsd-reboot/blob/40b7b515a1d177b9e14b3be7a8e66d2974c5d679/packages/setup-clojure-mode.el#L158-L166)
verdien til top-level def.

#### `C-c M-w`

[Evaluerer og
kopierer](https://github.com/magnars/emacsd-reboot/blob/40b7b515a1d177b9e14b3be7a8e66d2974c5d679/packages/setup-cider.el#L113-L124)
resultatet til utklippstavlen.

#### `C-c C-M-w`

[Evaluerer top-level
form](https://github.com/magnars/emacsd-reboot/blob/40b7b515a1d177b9e14b3be7a8e66d2974c5d679/packages/setup-cider.el#L126-L136)
og kopierer resultatet til utklippstavlen.

#### `C-c P`

Magit er en pakke som gir deg et git-interface i Emacs. `C-c p` lar deg legge
til én medforfatter på committen. En feature Magnar har lagt til. Da jeg begynte
i januar var vi plutselig mange på teamet, og vi satt noen ganger tre eller fire
sammen og parprogrammerte. Jeg la derfor til `C-c P` som lar deg [legge til
flere
medforfattere](https://github.com/magnars/emacsd-reboot/blob/40b7b515a1d177b9e14b3be7a8e66d2974c5d679/packages/setup-magit.el#L138-L141)
på committen.

### Ekstra features

#### Print with `e->map` wrapped

I Matnyttig jobber vi veldig mye med entiteter fra Datomic-databasen. Når man
printer ut datomic-entiteter får man noe liknende på `{:db/id 12345678}`. For
enkelt å kunne printe ut verdiene i tillegg, har vi `e->map` som sørger for
dette, men på en måte så vi unngår sykliske løkker.

Etter 11 måneder med å manuelt wrappe entiteter med `e->map`, fikk jeg heller
Emacs til å [fikse dette for
deg](https://github.com/magnars/emacsd-reboot/blob/40b7b515a1d177b9e14b3be7a8e66d2974c5d679/settings/matnyttig.el#L53-L54).
Helt automagisk!

#### Første-klasses konsepter

I Matnyttig har vi mange første-klasses konsepter som kan identifiseres med et
keyword. For å lettere kunne navigere rundt i koden vår og finne definisjonen på
første-klasses konseptene, utbygde jeg Emacs til å [gjenkjenne
disse](https://github.com/magnars/emacsd-reboot/blob/40b7b515a1d177b9e14b3be7a8e66d2974c5d679/settings/matnyttig.el#L144-L174).
Nå kan vi bruke `goto-definition`-funksjonaliteten i Emacs på keywords som er
identifikator på et første-klasses konsept.
