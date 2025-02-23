:page/title En ny tilgang til Redux actions
:open-graph/title En ny tilgang til Redux actions | Mathive Thoughts
:open-graph/description Med inspirasjon fra Clojure's kjernefunksjoner, hvordan kan vi forenkle Redux actions for å øke lesbarhet, gjenbrukelighet og vedlikehold, samt senke kognitiv belast?
:open-graph/image /images/og-too-massive.jpeg
:blog-post/tags [:actions :fp]
:blog-post/author {:person/id :mathias}
:blog-post/published #time/ldt "2024-05-15T09:00:00"
:blog-post/desc

Med inspirasjon fra Clojure's kjernefunksjoner, hvordan kan vi forenkle Redux actions for å øke lesbarhet, gjenbrukelighet og vedlikehold, samt senke kognitiv belast?

:page/body

# En ny tilgang til Redux actions

> "The unavoidable price of reliability is simplicity" – C.A.R. Hoare

De siste seks årene som React-utvikler har jeg skrevet mange Redux actions. Snesevis. For hver ny feature har det fulgt som oftest flere nye actions, såsom `addOrder`, `updateDescription`, `deleteEverlastingDespair` osv.

Legg til denne tingen i denne listen, oppdater dette feltet på denne tingen i denne listen, slett denne tingen her.

Hver enkelt med nøyaktig samme logikk bare _litt_ forskjellig: hvor og hva i staten som må endres.

## The greia

Så hvor er det jeg vil med dette? Jo, i mitt dykk ned i Clojure og på en læringstorsdag med min kollega ✨ [Christian](https://cjohansen.no/) ✨, viste han meg en mye enklere løsning. Med Clojure's innebygde kjernefunksjoner kan man slippe avsted med så få som **tre** gjenbrukbare actions for å oppdatere staten 🤯.

> "Wow" – Alle, sannsynligvis

Funksjonene jeg vil frempeke her er `assoc-in` og `update-in`. De brukes til å tilknytte og oppdatere verdier i en datastruktur (tenk objekt i js). Med disse kan man enkelt endre hva du vil – selv i et dypt objekt.

Ta denne strukturen f.eks.:

```javascript
const kvittering = {
  id: 10254,
  tilsynsobjekter: [
    {
      id: 4237,
      kontrollpunkter: [
        {
          id: 93284,
          observasjoner: [
            {
              id: 8385,
              beskrivelse: "Kua har dæva 😢",
            },
          ],
        },
      ],
    },
  ],
};
```

Vanligvis for å endre beskrivelsen på observasjonen ville man gjøre noe lignende:

```javascript
const changeBeskrivelse = (kvittering, updatedObservasjon) => {
  return {
    ...kvittering,
    tilsynsobjekter: kvittering.tilsynsobjekter.map((tobj) => ({
      ...tobj,
      kontrollpunkter: tobj.kontrollpunkter.map((kp) => ({
        ...kp,
        observasjoner: kp.observasjoner.map((obs) =>
          obs.id !== updatedObservasjon.id
            ? obs
            : {
                ...obs,
                beskrivelse: updatedObservasjon.beskrivelse,
              }
        ),
      })),
    })),
  };
};
```

Dette er jo helt hårreisende. Når man slenger det sammen med alle de andre actions og logiske knuterier i et projekt blir det ganske mange linjer kode. Og det vil øke kompleksiteten og den kognitive belasten.

Jeg har lenge lurt på, hvis det er mulig eller i det hele tatt verdt det, hvordan man kan forbedre og forenkle slik kode.

## The Solvation

Det er da her `assoc-in` og `update-in` kommer for å redde dagen. Jeg har gitt et forsøk på å implementere de to funksjonene i javascript:

```javascript
/**
 * @param obj Objektet som skal oppdateres
 * @param path Stien til hva som skal forbindes med verdien
 * @param value Verdien som skal forbindes til stien i objektet
 * @return Ny shallow kopi av objektet
 */
const associateIn = (obj, path, value) => {
  if (path.length === 0) {
    return value;
  }

  const copy = Array.isArray(obj) ? [...obj] : { ...obj };

  const [firstPath, ...restPath] = path;

  if (!copy[firstPath] || typeof copy[firstPath] !== "object") {
    copy[firstPath] = {};
  }

  copy[firstPath] = associateIn(copy[firstPath], restPath, value);

  return copy;
};
```

```javascript
/**
 * @param obj Objektet som skal oppdateres
 * @param path Stien til hva som skal oppdateres
 * @param updateFn En funksjon som tar verdien for enden av stien og returnerer oppdatert verdi
 * @param args Valgfrie ekstra parametre som blir sendt med inn til updateFn
 */
const updateIn = (obj, path, updateFn, ...args) => {
  if (path.length === 0) {
    return updateFn(obj, ...args);
  }

  const copy = Array.isArray(obj) ? [...obj] : { ...obj };

  const [firstPath, ...restPath] = path;

  copy[firstPath] = updateIn(copy[firstPath], restPath, updateFn, ...args);

  return copy;
};
```

For å gjøre samme handling som eksemplet over, kan vi bruke `updateIn`, hvor:

- Første argument er her kvittering, men vil praktisk være hele state-objektet.
- Andre argument er her stien til observasjonens beskrivelse (der `0` er indeksen i listene).
- Tredje argument er en funksjon som får inn verdien fra enden av stien og returnere en ny verdi som du setter.

```javascript
updateIn(
  kvittering, // 1
  [
    "tilsynsobjekter",
    0,
    "kontrollpunkter",
    0,
    "observasjoner",
    0,
    "beskrivelse",
  ], // 2
  (beskrivelse) => updatedBeskrivelse // 3
);
```

`updateIn` vil da traversere objektet ned til det endelige feltet definert i stien og kalle oppdateringsfunksjonen med feltets verdi. Her er noen fler eksempler:

```javascript
updateIn({ frukter: ["banan", "eple", "appelsin"] }, ["frukter"], (frukter) => [
  ...frukter,
  "kunnskapens",
]);
// => { frukter: ['banan', 'eple', 'appelsin', 'kunnskapens']}

updateIn({ navn: "Gjert" }, ["navn"], (navn) => navn + " Steinbukk");
// => { navn: 'Gjert Steinbukk' }

updateIn(["Helt"], [1], (hvaErDette) => [...hvaErDette, "utrolig!"]);
// => ['Helt', 'utrolig!']
```

`assoc-in` funker likedan, men der den bare overskriver verdien istedenfor.

```javascript
associateIn({ forfatter: { kred: 15 } }, ["forfatter", "kred"], 16);
// => { forfatter: { kred: 16 } }

associateIn(
  {
    "🦎": [
      { del: "hode", type: "frisk" },
      { del: "arm", side: "venstre", type: "frisk" },
      { del: "arm", side: "høyre", type: "avstumpet" },
      { del: "ben", side: "venstre", type: "frisk" },
      { del: "ben", side: "høyre", type: "frisk" },
    ],
  },
  ["🦎", 2],
  { del: "arm", side: "høyre", type: "frisk" }
);
/* => {
  "🦎": [
    { del: "hode", type: "frisk" },
    { del: "arm", side: "venstre", type: "frisk" },
    { del: "arm", side: "høyre", type: "frisk" },
    { del: "ben", side: "venstre", type: "frisk" },
    { del: "ben", side: "høyre", type: "frisk" },
  ]
} */
```

Med disse funksjonene blir alle actions derfor en lek å skrive! Man trenger ikke tenke på hvordan man skal implementere en action. Disse funksjonene er helt generelle som kan brukes til ethvert scenario og tar dermed al kognitiv belast vekk. Og du skjærer bort et stort antall linjer kode.

Skal du legge til tilsynsdato til kvitteringen:

```javascript
associateIn(state, ["selectedKvittering", "tilsynsdato"], "2024-05-14");
```

Skal du knytte et bilde til kvitteringen:

```javascript
updateIn(state, ["selectedKvittering", "bildeIds"], (bildeIds) => [
  ...bildeIds,
  "825d3e97-3535-4679-8295-38be573b5087",
]);
```

Det blir ikke enklere enn det 😌

> "Men vent litt... Du sa vi bare trengte tre actions. Hvor blei det av de?"

Jo, observante leser, vi kan lage noen wrappere rundt så vi får noe lignende:

```javascript
const update = (state, payload) =>
  associateIn(state, payload.path, payload.value);

const addToList = (state, payload) =>
  updateIn(state, payload.path, (list) => [...list, payload.value]);

const removeFromList = (state, payload) =>
  updateIn(state, payload.path, (list) =>
    list.slice(payload.index, payload.index + 1)
  );
```

Ved å bruke `associateIn` og `updateIn` i våre prosjekter, kan vi redusere mengden kode betydelig, samtidig som vi forbedrer lesbarheten og vedlikeholdbarheten. Innsikten jeg har fått gjennom min korte tid med Clojure har vært, rett og slett, mindblowing. Det har åpnet for en enklere verden der den kognitive belasten er markant redusert. Dette innlegget er bare en liten del av den overflod av tekniske godsaker jeg har oppdaget gjennom Clojure, og ikke minst takket være veiledningen fra Christian.

---

```javascript
updateIn(leseren, ["hjerne", "celler"], (celler) => celler++);
```
