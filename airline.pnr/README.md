# airline.pnr

Generate pseudo-random PNR numbers for testing an airline system.

## Usage

``` clojure
(def db "postgresql://clerk:fcb0412@localhost:15432/airline")
(claim-pnr db 0)
; 14QFSO
```

# Warning

The generation of rude words is not prevented.

# License

Copyright © 2014 Devstopfix

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
