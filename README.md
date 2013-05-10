Colloquor
=========

Build Status : ![Travis Build Status](https://api.travis-ci.org/ehodges/colloquor.png)

A WebRTC based communication hub.

Building
--------

    $ source setupBuild.sh
	$ mvn package

Running
-------

	$ export MONGO_USER=<username>
	$ export MONGO_PASS=<password>
	$ java -jar target/colloquor-0.0.1-SNAPSHOT.jar server dev-config.yml