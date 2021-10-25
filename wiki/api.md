# settings
_Informationen die bei der initiallen Abfrage der App-Einstellungen benötigt werden._

## ``/settings``

_Abfrage der Optionen für die Einstellungen_

Verfügbare Requesttypes: **GET**

###Response

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

###Response

Typ: **JSON**

`MensaLocation`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Mensa Standorts (z.B. "Gummersbach") |
| `id`    | String | Kürzel des Mensa Standorts (z.b. "gm")        |


## ``/settings/newsfeed``

_Abfrage aller Newsfeeds als Liste vom Typ `Newsfeed`_

Verfügbare Requesttypes: **GET**

###Response

Typ: **JSON**

`Newsfeed`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Newsfeeds (z.B. "Informatik und Ingenieurwissenschaften") |
| `id`    | String | Kürzel des Newsfeeds (z.b. "f10")        |

## ``/settings/noticeboard``

_Abfrage aller schwarzen Bretter als Liste vom Typ `NoticeBoardFeed`_

Verfügbare Requesttypes: **GET**

###Response

Typ: **JSON**

`NoticeBoardFeed`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des schwarzen Brettes (z.B. "Informatik") |
| `id`    | String | Kürzel des schwarzen Brettes (z.b. "inf")        | 

## ``/settings/staff``

_Abfrage aller Personal Standorte als Liste vom Typ `StaffLocation`_

Verfügbare Requesttypes: **GET**

###Response

Typ: **JSON**

`StaffLocation`

| Feld    | Typ    | Beschreibung                                |
|---------|--------|---------------------------------------------|
| `label` | String | Name des Standortes (z.B. "Gummersbach") |
| `id`    | String | Kürzel des Standortes (z.b. "gm")        | 
