all: run

createBinDir:
	mkdir -p bin

compile: createBinDir
	javac src/Interface.java -cp src/ -d bin/ -g:none

package: compile graphe.jar
	jar cmfv manifest graphe.jar  bin/*.class

run: package
	java -jar graphe.jar

clean:
	rm -rf bin
	rm graphe.jar