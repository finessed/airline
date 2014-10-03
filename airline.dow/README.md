# airline.dow

A Clojure library for transforming day-of-week strings published
in airline timetables into lists of days and other data structures.

## Timetable

Using the [Star Alliance][1] definition:

    Frequency of operation: 
    1 = Monday
    7 = Sunday
    X = Except

Therefore a flight every weekday may be scheduled with these forms:

    12345
    X67

## Usage

```clojure
(require 'airline.dow)

(airline.dow/parse "135")
; (1 3 5)

(airline.dow/parse "X67")
; (1 2 3 4 5)
```

## License

Copyright © 2014 DevStopFix

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.


[1]: http://www.staralliance.com/assets/doc/en/services/tools-and-downloads/pdf/StarAlliance.pdf