sources = org/pac/games/*.java 
sources+= org/pac/games/physics/*.java 
sources+= org/pac/games/gui/*.java 
example_srcs = examples/*.java

all: pac.jar examples.jar docs

pac.jar: $(sources)
	mkdir -p bin
	javac -Xlint:all -d bin $(sources)
	jar cf pac.jar \
		-C bin org
	chmod +x pac.jar

examples.jar: pac.jar $(example_srcs)
	mkdir -p bin
	javac -cp pac.jar -Xlint:all -d bin $(example_srcs)
	jar cfe examples.jar examples.BounceEngineExample \
		-C bin examples 
	chmod +x examples.jar

docs: pac.jar examples.jar
	mkdir -p docs
	javadoc \
		-d docs \
		-sourcepath . \
		-subpackages org.pac:examples

clean:
	rm -f *.class
	rm -rf bin

clean_all: clean
	rm -f pac.jar
	rm -f examples.jar
	rm -rf docs
