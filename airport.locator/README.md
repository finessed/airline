# airport.locator

A [RESTful micro-service](http://martinfowler.com/articles/microservices.html) of
airport locations around the globe. Supports JSONP.

## Build

Build:

    lein uberjar

## Run

To start a web server on port 8065 for the application, run:

    java -jar airport.locator-0.7.1-standalone.jar 8065

# Resources provided

This section describes the URL syntax of each resource.

## Airport geo-location

Find the latitude and longitude of an airport.

```
GET /geo/airport/XXX
```

where XXX is an UPPERCASE 3-character IATA airport code. Example:

```
GET /geo/airport/LHR
```

returns:

```json
{"airport":"LHR",
 "location":[51.4775,-0.461389]}
```

## Airport destinations

Get a list of all the airports that are destinations for a given airport.

```
GET /geo/airport/BLL/destinations
```

returns:

```json
{ "airport":"BLL",
  "location":[55.740322,9.151778],
  "destinations":[
    {"airport":"FRA","location":[50.026421,8.543125]},
    {"airport":"MUC","location":[48.353783,11.786086]}] }
```

## Flight routes

Find the latitude and longitude of pairs of airports along a route
with optional connections or stop-overs. Maximum of 16 airport codes.

```
GET /geo/route/XXX,YYY[,ZZZ]...
```

Example:

```
GET /geo/route/LHR,CPH,BLL
```

returns:

```json
{"route":[
    [{"dept": "LHR", "dept-loc":[51.4775,-0.461389]},{"dest": "CPH", "dest-loc":[55.617917,12.655972]}],
    [{"dept": "CPH", "dept-loc":[55.617917,12.655972]},{"dest": "BLL", "dest-loc":[55.740322,9.151778]}]
    ]}
```

## Other

Any other URL or method other than ```GET``` will return ```HTTP 400 Bad Request```.

# License

Software Copyright © 2014 DevStopFix and Distributed under the Eclipse Public License version 1.0.

Database Copyright © 2014 openflights.org and covered by the [Open Database License](http://opendatacommons.org/licenses/odbl/1.0/).
