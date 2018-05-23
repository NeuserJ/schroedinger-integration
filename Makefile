JAVAC = javac -encoding latin1
# = /mnt/c/Program\ Files/Java/jdk1.8.0_171/bin/javac.exe
JAR = jar
# = /mnt/c/Program\ Files/Java/jdk1.8.0_171/bin/jar.exe
sources = $(wildcard src/org/schrodinger/*.java) \
	$(wildcard src/org/schrodinger/gui/*.java) \
       	$(wildcard src/org/schrodinger/potential/*.java)

classes = $(patsubst src/%.java,bin/%.class,$(sources))
	# $(sources:.java=.class)

all: Simulation-Schrödinger

Simulation-Schrödinger: $(classes)

clean :
	rm -f -r bin/*

bin/%.class : src/%.java
	$(JAVAC) -d bin -classpath "bin:src:src/commons-math3-3.6.1.jar" $<

jar: $(classes)
	$(JAR) cvf Schrödinger.jar $(classes) 

test:
	echo $(classes)
