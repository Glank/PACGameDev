sources = org/pac/games/*.java 
sources+= org/pac/games/physics/*.java 
sources+= org/pac/games/gui/*.java 
example_srcs = examples/*.java
test_srcs = test/*.java

all: pac.jar examples.jar

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

test.jar: pac.jar $(test_srcs)
	mkdir -p bin
	javac -cp pac.jar -Xlint:all -d bin $(test_srcs)
	jar cfm test.jar test/Manifest.txt \
		-C bin test 
	chmod +x test.jar

test: test.jar
	java -jar test.jar

docs: pac.jar examples.jar
	mkdir -p html/docs
	javadoc \
		-d html/docs \
		-sourcepath . \
		-classpath pac.jar:examples.jar \
		-subpackages org.pac:examples

clean:
	rm -f *.class
	rm -rf bin

clean_all: clean
	rm -f pac.jar
	rm -f examples.jar
	rm -f test.jar
	rm -rf docs
