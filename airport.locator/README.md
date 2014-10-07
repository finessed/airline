# airport.locator

A [RESTful micro-service](http://martinfowler.com/articles/microservices.html) of
airport locations around the globe.

## Running

To start a web server for the application, run:

    lein ring server
    
To do: instructions for deploying to Jetty.

# Resources provided

This section describes the URL syntax of each resource.

## Airport geo-location

Find the latitude and longitude of an airport.

```
GET /airport/XXX
```

where XXX is an UPPERCASE 3-character IATA airport code. Example:

```
GET /airport/LHR
```

returns:

```json
{"LHR":[51.4775,-0.461389]}
```

## Airport routes

Find the latitude and longitude of pairs of airports along a route
with optional connections or stop-overs.

```
GET /route/XXX,YYY[,ZZZ]...
```

Example:

```
GET /route/LHR,CPH,BLL
```

returns:

```json
{"route":[
    [{"LHR":[51.4775,-0.461389]},{"CPH":[55.617917,12.655972]}],
    [{"CPH":[55.617917,12.655972]},{"BLL":[55.740322,9.151778]}]
    ]}
```

## Other

Any other URL or method other than ```GET``` will return ```HTTP 400 Bad Request```.

# License

Software Copyright © 2014 DevStopFix and Distributed under the Eclipse Public License version 1.0.

Database Copyright © 2014 openflights.org and covered by the [Open Database License](http://opendatacommons.org/licenses/odbl/1.0/).
