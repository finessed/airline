#!/bin/sh -e

SMOKE_TEST_URL="http://localhost/airport/LHR"

SRC="/vagrant"

PROVISIONED_AT="/etc/vm_provision_at_timestamp"
PROVISIONED_ON="/etc/vm_provision_on_timestamp"
date > "$PROVISIONED_AT"

echo "Update system"

apt-get update
apt-get -y upgrade

echo "Installing packages"

apt-get -y install default-jre
apt-get -y install nginx
apt-get -y install varnish


echo "Start micro-service"

java -jar "/home/vagrant/airport.locator.jar" 8065 &

echo "Configure Nginx"

service nginx stop
unlink /etc/nginx/sites-enabled/default
cp "$SRC/airport.conf" /etc/nginx/sites-enabled/airport.conf
service nginx start

echo "Configure Varnish"

service varnish stop
cp "$SRC/varnish.vcl" /etc/varnish/default.vcl
service varnish start


date > "$PROVISIONED_ON"
echo "Finished."
echo ""

echo "Smoke test:"
curl --silent "$SMOKE_TEST_URL"
echo ""
curl --write-out "%{http_code}\n" --silent --output /dev/nul "$SMOKE_TEST_URL"
echo ""
