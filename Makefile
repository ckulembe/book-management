JAVA = java
JAVAC = javac

SRC = src
DRIVER = postgresql-42.7.11.jar

SOURCES = $(shell find $(SRC) -name "*.java")

class:
	$(JAVAC) -cp .:$(DRIVER) $(SOURCES)

run: class
	$(JAVA) -cp .:$(DRIVER):$(SRC) JDBCMain

clean:
	find $(SRC) -name "*.class" -delete

.PHONY: class run clean