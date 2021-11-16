# Ressourcen
- [settings](#settings)
- [mensa](#mensa)




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

_Abfrage des Mensaplans für die nächsten zwei Wochen, anhand der übergebenen Mensa ID. 
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
| `description`    | Description | Zusätzliche Informationen zu einem Gericht |
| `foodIcons`    | String[] | Icons zu dem jeweiligen Gericht        |
| `prices`    | Price[] | Preise Gerichts für die verschiedenen Rollen        |
| `thumbnailUrl`    | String &#124; Null | Bild URL des Thumbnails in reduzierter Auflösung       |
| `fullUrl`    | String &#124; Null | Bild URL des Thumbnails in voller Auflösung       |

Sollte keine Bild URL zur Verfügung stehen, wird nach werden `thumbnailUrl` und `fullUrl` wird `null`  zurückgegeben.

`Meal` und `Description`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `name` | String | Name des Gerichts |
| `nameAdditives`    | String | Zusatzstoffe im Gericht als Zeichenkette      |
| `additives`    | AdditiveType[] | Liste der Zusatzstoffe |
| `allergens`    | AdditiveType[] | Liste der Allergene        |

`Price`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `value` | Double &#124; Null| null | Betrag des Preises |
| `role`    | String | Rolle für welche der Preis gilt      |

Sollte es keinen Preis für eine Rolle geben, wird `value` auf `null` gesetzt.

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

## ``/staff``
_Abfrage der verfügbaren Mitarbeiter Orte. Rückgabe erfolgt als Array von `staffLocation`._

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`StaffLocation`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Standorts |
| `id`    | String | Kürzel des Standorts des Zusatzstoffs |

## ``/staff:id``

_Abfrage aller Mitarbeiter eines Standortes anhand der übergebenen ID. Rückgabe erfolgt als Array vpn `Staff`._

Verfügbare Requesttypes: **GET**

### Parameter

| Feld    | Typ    | Beschreibung                                | Art   | Optional |
|---------|--------|---------------------------------------------|-------|----------|
| `id` | String | Kürzel des Standorts (z.b. gm) | Path  | Nein |

### Response

Typ: **JSON**

`Staff`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `name` | String | Name des Mitarbeiters |
| `detailUrl`    | String | URL der zur Detailseite der TH Köln für den entsprechenden Mitarbeiter        |
| `tel`    | String &#124; Null  | Telefonnummer des Mitarbeiters. Gibt `null` zurück wenn keine Telefonnummer hinterlegt ist |
| `email`    | String &#124; Null  | Email des Mitarbeiters. Gibt `null` zurück wenn keine Email hinterlegt ist |


## ``/noticeboard``
_Abfrage der verfügbaren schwarzen Bretter. Rückgabe erfolgt als Array von `NoticeboardFeed`._

Verfügbare Requesttypes: **GET**

### Response

Typ: **JSON**

`NoticeboardFeed`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des schwarzen Bretts |
| `id`    | String | Kürzel des schwarzen Bretts |

## ``/staff:id``

_Abfrage des Feeds eines schwarzen Bretts anhand der übergebenen ID._

Verfügbare Requesttypes: **GET**

### Parameter

| Feld    | Typ    | Beschreibung                                | Art   | Optional |
|---------|--------|---------------------------------------------|-------|----------|
| `id` | String | Kürzel des schwarzen Bretts (z.b. inf) | Path  | Nein |

### Response

Typ: **JSON**

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `title` | String | Name des schwarzen Bretts |
| `description`    | String | Beschreibung des schwarzen Bretts |
| `entries`    | NoticeboardEntry[]  | Telefonnummer des Mitarbeiters. Gibt `null` zurück wenn keine Telefonnummer hinterlegt ist |

#### Zugehörige Objekte

`NoticeboardEntry`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `title` | String | Titel des Eintrags |
| `description`    | String | Beschreibung des Eintrags        |
| `detailUrl`    | String | URL zur Detailseite |
| `published`    | Date | Datum der Veröffentlichung        |