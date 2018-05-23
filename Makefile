JAVAC = /mnt/c/Program\ Files/Java/jdk1.8.0_171/bin/javac.exe
JAR = /mnt/c/Program\ Files/Java/jdk1.8.0_171/bin/jar.exe
sources = $(wildcard src/org/schrodinger/*.java) \
	$(wildcard src/org/schrodinger/gui/*.java) \
       	$(wildcard src/org/schrodinger/potential/*.java)

classes = $(sources:.java=.class)

all: Simulation-Schrödinger

Simulation-Schrödinger: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) -classpath "src:src/commons-math3-3.6.1.jar" $<

jar: $(classes)
	$(JAR) cvf Schrödinger.jar $(classes) 

test:
	echo $(classes)
