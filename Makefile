all: package

createBinDir:
	mkdir bin

compile: createBinDir
	javac src/Interface.java -cp src/ -d bin/ -g:none

package: compile
	jar cmfv manifest graphe.jar  bin/*.class

clean:
	rm -rf bin
	rm graphe.jar