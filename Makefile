all: package

createBinDir:
	mkdir -p bin

compile: createBinDir
	javac src/Interface.java -cp src/ -d bin/ -g:none

package: compile
	jar cmfv manifest graphe.jar  bin/*.class

run: package
	java -jar graphe.jar

doc:
	javadoc src/* -d doc/

clean:
	rm -rf bin
	rm -rf doc
	rm graphe.jar