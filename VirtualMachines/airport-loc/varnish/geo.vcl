backend geo {
    .host = "127.0.0.1";
    .port = "8065";
}

backend jetty {
    .host = "127.0.0.1";
    .port = "8080";
}

sub vcl_recv {
    if (req.url ~ "^/geo/") {
        set req.backend = geo;
    } else {
        set req.backend = jetty;
    }
}
