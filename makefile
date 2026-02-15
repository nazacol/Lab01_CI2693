all: compile run

compile:
	kotlinc DegreesOfSeparation.kt -include-runtime -d DegreesOfSeparation.jar

run:
	java -jar DegreesOfSeparation.jar Carlos Ana

clean:
	rm -f DegreesOfSeparation.jar