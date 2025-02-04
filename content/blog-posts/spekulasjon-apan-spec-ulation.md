:page/title Spekulasjon åpån Spec-ulation
:open-graph/title Spekulasjon åpån Spec-ulation | Mathive Thoughts
:open-graph/description Noen tanker rundt Rich Hickeys Spec-ulation
:open-graph/image /images/og-too-massive.jpeg
:blog-post/tags [:versjonering]
:blog-post/author {person/id :mathias}
:blog-post/published #time/ldt "2024-06-12T09:00:00"
:blog-post/desc

Noen tanker rundt Rich Hickeys "Spec-ulation"

:page/body

# Spekulasjon åpån Spec-ulation

Etter at en kollega foreslo Rich Hickeys foredrag "Spec-ulation" i en tråd, bestemte jeg meg for å se det på forrige læringstorsdag.

<div class="video">
  <iframe width="560" height="315" src="https://www.youtube.com/embed/oyLBGkS5ICk" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
</div>

Fantastisk innsiktsvekkende og gjennomtenkt. Han snakker i stor grad om dependencies, versjonering av kode og hvilke problemer og forbedringer som kan gjøres. Den kan sterkt anbefales.

I samme slenget, der den major versjonen (x.0.0) knekker kontrakten til det pågjeldende biblioteket, faller tankene mine ved første instans på React Router. Et bibliotek som er notorisk for å massakrere kontrakten sin ved hvert major bump, noe som resulterer i store inngrep på koden og stor frustrasjon for de som må oppgradere. Selv om de forrige gang kom med en mjuk oppgraderingsversjon hvor gammel og ny kode kunne leve fritt sammen, er det fortsatt et tydelig tegn på fuldstendig feil tilgangsmåte.

React har også selv vært skyld i flere store nytenkninger, noen som har snudd 180 grader på tidligere varianter. Bare tenk på klassekomponenter og innføringen av React hooks.

Den ene, som klamrer seg til klassekomponenter med `this.setState` og lifecycle-funksjoner som `componentDidMount`, `componentDidUpdate` osv., og den andre, som kasserer disse for vanlige javascript-funksjoner med de _förgrömmade_ hooksene, hvis verste fiende er to av de mest fundamentale bokstavene i programmeringens historie: `if`.

> Oi, det eskalerte litt, gitt 😳

Uansett, disse endringene bringer med seg enorme kognitive forvridninger i nye nevrale retninger og krever store omskrivninger i de pågjeldende løsninger.

Jeg snakker ikke imot forbedringer og det å tenke nytt. Intensjonene bak er helt sikkert gode, med tanke på å forenkle eller fremme et bedre API.

Men for å følge Hickey's tankegang, burde det da ikke ha vært nye løsninger? Det er spektakulært og spennende med nye tanker og idéer, men hvis det krever et nytt major versjonsbump, burde det kanskje ikke være en helt ny greie også?

Tenk en verden hvor biblioteker aldri knakk og du kunne stole på pakkene du brukte i prosjektene dine med en visshet om at det bare ville komme nye features.

Og hvis man følger denne retningen videre, vil det da ikke dytte oss mot et mer modulært pakke-hierarki?

Der React hooks er sin egen pakke, på linje med ReactDOM. Der React Router kun håndterer basal routing, og ved siden av er det en pakke som håndterer datalasting ved route-innlesing.

Vi får derfor adskilt konsepter fra hverandre. Vi får mindre pakker som dermed blir mer stabile. Og ikke minst får vi et mye hyggeligere utviklingsmiljø.

Det er kanskje ønsketenkning og praktisk talt blir det nok aldri realisert i den virkelige verden, men det er absolutt verdt å tenke på, spør du meg 😄
