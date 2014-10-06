# org.openflights.airports

A Clojure library designed to transform data from [openflights.org](http://openflights.org/data.html).

## Usage

### Make a table of IATA code to latitude,longitude

    lein run -m org.openflights.airports/run-extract-positions ~/etc/openflights.org/airports.dat > iata3-airport-locations.csv 

## License

Copyright © 2014 DevStopFix

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
