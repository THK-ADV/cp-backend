# Ressourcen
- [settings](#settings) 
  - [/settings/mensa](#settingsmensa)
  - [/settings/newsfeed](#settingsnewsfeed)
  - [/settings/noticeboard](#settingsnoticeboard)
  - [/settings/staff](#settingsstaff)
- [mensa](#mensa)
    - [/mensa](#mensa)
    - [/settings/mensa:id](#mensaid)
    - [/legend](#legend)



# settings
_Informationen die bei der initiallen Abfrage der App-Einstellungen benötigt werden._

## ``/settings``

_Abfrage der Optionen für die Einstellungen_

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

| Feld              | Typ               | Beschreibung                                    |
|-------------------|-------------------|-------------------------------------------------|
| `mensaLocation`   | MensaLocation[]   | Liste aller Mensa Standorte             |
| `newsFeed`        | Newsfeed[]        | Liste aller Newsfeeds                   |
| `noticeBoardFeed` | NoticeBoardFeed[] | Liste aller Feeds der schwarzen Bretter |
| `staffLocation`   | StaffLocation[]   | Liste aller Personal Standorte      |


## ``/settings/mensa``

_Abfrage aller Mensa Standorte als Liste vom Typ `MensaLocation`_

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`MensaLocation`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Mensa Standorts (z.B. "Gummersbach") |
| `id`    | String | Kürzel des Mensa Standorts (z.b. "gm")        |


## ``/settings/newsfeed``

_Abfrage aller Newsfeeds als Liste vom Typ `Newsfeed`_

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`Newsfeed`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Newsfeeds (z.B. "Informatik und Ingenieurwissenschaften") |
| `id`    | String | Kürzel des Newsfeeds (z.b. "f10")        |

## ``/settings/noticeboard``

_Abfrage aller schwarzen Bretter als Liste vom Typ `NoticeBoardFeed`_

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`NoticeBoardFeed`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des schwarzen Brettes (z.B. "Informatik") |
| `id`    | String | Kürzel des schwarzen Brettes (z.b. "inf")        | 

## ``/settings/staff``

_Abfrage aller Personal Standorte als Liste vom Typ `StaffLocation`_

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`StaffLocation`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Standortes (z.B. "Gummersbach") |
| `id`    | String | Kürzel des Standortes (z.b. "gm")        | 


# mensa
_Informationen zum Angebot der verschiedenen Mensen._

## ``/mensa``

_Abfrage aller Mensa Standorte als Liste vom Typ `MensaLocation`_

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`MensaLocation`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Mensa Standorts (z.B. "Gummersbach") |
| `id`    | String | Kürzel des Mensa Standorts (z.b. "gm")        |


## ``/mensa:id``

_Abfrage des Mensaplans für die nächsten zwei Wochen, anhand der übergebenen Mensa Id. 
Der Mensaplan setzt sich aus einer Liste von `Menu` für die einzelnen Tage zusammen._

Verfügbare Requesttypes: **GET**

### Parameter

| Feld    | Typ    | Beschreibung                                | Art   | Optional |
|---------|--------|---------------------------------------------|-------|----------|
| `id` | String | Kürzel des Mensa Standorts (z.b. gm) | Path  | Nein |
| `withLegend`    | Boolean | Gibt an ob Gerichte mit oder ohne Zusatzinformationen zu Allergenen versehen werden sollen | Query | Ja (Default: _false_) |

### Response

Typ: **JSON**

`Menu`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `date` | Date | Datum des Eintrags |
| `weekday`    | String | Name des Wochentags        |
| `items`    | Item[] | Liste der an dem Tag angebotenen Gerichte |

#### Zugehörige Objekte

`Item`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `category` | String | Kategorie des Gerichts |
| `meal`    | Main | Wichtigsten Informationen eines Gerichts        |
| `description`    | Description | zusätzliche Informationen zu einem Gericht |
| `foodIcons`    | String[] | Icons zu dem jeweiligen Gericht        |
| `prices`    | Price[] | Preise Gerichts für die verschiedenen Rollen        |
| `thumbnailUrl`    | String | Bild URL des Thumbnails in reduzierter Auflösung       |
| `fullUrl`    | String | Bild URL des Thumbnails in voller Auflösung       |

`Meal`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `name` | String | Name des Gerichts |
| `nameAdditives`    | String | Zusatzstoffe im Gericht als Zeichenkette      |
| `additives`    | AdditiveType[] | Liste der Zusatzstoffe |
| `allergens`    | AdditiveType[] | Liste der Allergene        |

`Description`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `name` | String | Name des Gerichts |
| `nameAdditives`    | String | Zusatzstoffe im Gericht als Zeichenkette      |
| `additives`    | AdditiveType[] | Liste der Zusatzstoffe |
| `allergens`    | AdditiveType[] | Liste der Allergene        |

`Price`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `value` | Double | Betrag des Preises |
| `role`    | String | Rolle für welche der Preis gilt      |

## ``/legend``

_Abfrage der Legende der Allergene und Zusatzstoffe für die Mensa. 
Die Legende setzt sich aus einer Liste `Additive` für die einzelnen Allergene und Zusatzstoffe zusammen._

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`Additive`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `deLabel` | String | Bezeichnung auf Deutsch |
| `enLabel`    | String | Bezeichnung auf Englisch       |
| `id`    | Int | ID des Zusatzstoffs |
