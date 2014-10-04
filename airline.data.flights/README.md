# airline.data.flights

A Clojure library that generates a realistic schedule of flight data
from an airlines daily timetable.

## Input

The program requires an Airlines schedule in CSV format,
and the time-zones of the Airports serviced.

### Schedule Input File

A CSV file in this format:

```
"Source","Destination","Miles","Leave","Arrive","Carrier","Flight","Plane","Days","Stops","Meal","Notes"
AAL,CPH,148,06:25,07:10,SK,1228,M81,X67,0,N,""
```

Notes:

* the ```Arrive``` field may have a ```+1``` suffix to indicate next day
* a header row is assumed and skipped
* column names are not important

### Airports Input File

[Open Flights](http://openflights.org/data.html) have a database of ~7,000 
airports. Please donate if you use their data.

Input format:

```
_,_,_,_,IATA,_,_,_,_TZ
609,"Kastrup","Copenhagen","Denmark","CPH","EKCH",55.617917,12.655972,17,1,"E"
```

Therefore København airport ```CPH``` is one hour ahead of UTC.

```clojure
{ 'CPH' 1 }
```

## Output

```
id,2014-10-06,AAL,SK1228,2014-10-06T06:25:00+01:00,CPH,2014-10-06T07:10:00+01:00
id,2014-10-07,AAL,SK1228,2014-10-07T06:25:00+01:00,CPH,2014-10-07T07:10:00+01:00
id,2014-10-08,AAL,SK1228,2014-10-08T06:25:00+01:00,CPH,2014-10-08T07:10:00+01:00
id,2014-10-09,AAL,SK1228,2014-10-09T06:25:00+01:00,CPH,2014-10-09T07:10:00+01:00
id,2014-10-10,AAL,SK1228,2014-10-10T06:25:00+01:00,CPH,2014-10-10T07:10:00+01:00
```

* A 63-bit identifier to be used as a surrogate key
* Day of flight
* Departure Airport or City IATA code
* Flight number
* Scheduled Departure date-time
* Destination IATA
* Scheduled Arrival date-time

## Build

```
git clone ...
lein uberjar
```

## Usage

Generate 90 days of sample data, zipped:

```
java -jar target/airline.data.flights-0.2.0-standalone.jar \
    airports.dat \
    StarAlliance200905.csv \
    2014-10-04 \
    90 | gzip -c > /tmp/flights-2014-10-04.csv.zip
```

Four parameters are required:

1. airport data csv file
2. schedule data csv file
3. date of first flight in format ````yyyy-mm-dd````
4. number of days of data to generate (e.g. 90)

# Notes

1. The library generates departure and arrival times in the correct 
time-zones of the airports, but ignores daylight saving adjustments.

# Libraries used

1. CSV reading by [clojure-csv](https://github.com/davidsantiago/clojure-csv)
2. [airline.dow](https://github.com/devstopfix/airline/tree/master/airline.dow)


# License

Copyright © 2014 Devstopfix

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
