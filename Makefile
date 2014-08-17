compile:
	cd .. && \
	javac -cp perp/jai_core.jar:perp/jai_codec.jar:perp/ perp/*.java

run:
	cd .. && \
	    java -cp perp/jai_core.jar:perp/jai_codec.jar:perp/:. perp/PerpTrace

all:
	compile
	run
