:page/title Å animere transisjoner mellom statiske sider
:open-graph/title Å animere transisjoner mellom statiske sider | Mathive Thoughts
:open-graph/description Jeg introduserer hvordan man kan animere overganger mellom statiske html-sider
med det nye @view-transition CSS attributet.
:open-graph/image /images/og-too-massive.jpeg
:blog-post/tags [:styles :animation]
:blog-post/author {person/id :mathias}
:blog-post/published #time/ldt "2025-02-05T09:00:00"
:blog-post/desc

Jeg introduserer hvordan man kan animere overganger mellom statiske html-sider
med det nye @view-transition CSS attributet.

:page/body

# Å animere transisjoner mellom statiske sider

<img src="/images/transition.webp" alt="View transition teaser">

I etterkant av JavaZone så jeg en lightning-talk om [nye features i
CSS](https://2024.javazone.no/program/34839cbc-fa7c-4450-b0ae-cd13f43dd86b). Det
var især én av de som fanget oppmerksomheten min. Netop det med View Transitions
(starter 9:03 i videoen).

Jeg har alltid tenkt det ville vært gøy å legge til animasjoner på en side, men
har aldri funnet den rette tiden for å lære hvordan.

Da jeg da så han snakke om å animere side-traverseringer tenkte jeg: "Nå har jeg
sjansen! Dette er akkurat noe for bloggen min ✨".

Så hvor begynner man?

Jo, man legger til disse linjene på top-nivå i CSS-en din:

```css
@view-transition {
  navigation: auto;
}
```

Bare med disse linjene vil det komme en fading effekt når du går fra én side til
en annen – helt automatisk.

> Dette er bra, men går det ikke an å gjøre det enda finere?

Jo da, det går an 😄. Da har vi nemlig `view-transition-name`, som vi kan putte
på enkelte elementer for å spice dem opp litt når vi innleser den neste siden.

Om jeg f.eks. lager en klasse med `view-transition-name`:

```css
.logo-transition {
  view-transition-name: logo;
}
```

og sørger for å legge til klassen på både det store bildet på forsiden og logoen
øverst på siden her, da får vi helt "out-of-the-boks" en veldig fin glidene
overgang. Prøv selv ved å gå frem og tilbake mellom forsiden og en bloggpost
(_du kan trykke på ikonet øverst for å komme til forsiden_)

Dette var enkelt! Med bare to enkle tillegg gjør nettleseren så utrolig mye for
deg. Her en fin transisjon av logoen fra den ene plassen til den andre. Dette
var stas.

### 🚀 Men det kan bli enda finere! 🚀

For å få til noe mer kontrollert og gi deg absolutt frihet, tilbyr de også
pseudo-elementene `::view-transition-old` og `::view-transition-new`. Med disse
kan du granulert legge til akkurat den CSS-en som du kan ønske deg.
`::view-transition-old` bruker du til å animere siden du forlater mens
`::view-transition-new` animerer siden du ankommer.

Med kodesnutten under får jeg siden til å zoome ut og deretter zoome inn på
neste side.

```css
 :root {
   view-transition-name: root;
 }
 
 @keyframes scale-out {
     0% { transform: scale(1); }
     50% { transform: scale(0); }
     100% { transform: scale(0); }
 }
 @keyframes scale-in {
     0% { transform: scale(0); }
     50% { transform: scale(0); }
     100% { transform: scale(1); }
 }
 
 ::view-transition-old(root) {
   animation: 0.4s ease-out both scale-out;
 }
 
 ::view-transition-new(root) {
   animation: 0.4s ease-in both scale-in;
 }
```

Du kan se [resultatet her](/view-transition-1/). Jeg setter
`view-transition-name` på `:root` og definerer mine zoom animasjoner med
`scale`-funksjonen. Disse legger jeg da til på `::view-transition-old` og
`::view-transtion-new` spesifikt målrettet det definerte navnet `root` (kunne
også ha vært en klasse f.eks.).

Med dette er det da elementært å sette opp side-overganger med uanede
muligheter.

Som det siste kan jeg nevne at det også er et [View Transition
API](https://developer.chrome.com/docs/web-platform/view-transitions) som man
kan bruke i javascript der det er mulig å gjøre enda mer. Det blir litt for mye
for denne artiklen å håndtere.

### Oppsummering

- `@view-transition` setter opp hele greia.
- `view-transition-name` spesifiserer hvilket element overgangen skal påvirke.
- `::view-transition-old` kontrollerer overgangen fra den siden du forlater.
- `::view-transition-new` kontrollerer overgangen til den siden du ankommer.

---

#### Ting å merke seg

- Da jeg satt og fiklet med dette, merket jeg at ofte, så viste overgangene seg
  ikke når jeg hadde devtools oppe.
- Featuren understøttes i de fleste nettlesere, men ikke firefox.
