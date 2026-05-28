

JAVA = java
JAVAC = javac

MODEL = model/
MODELSRC = $(MODEL)/Author.java \
		$(MODEL)/Title.java \
		$(MODEL)/AuthorIsbn.java
SRC = JDBCMain.java BlankException.java ConnectionDB.java $(MODELSRC)
CLASS = $(SRC:.java=.class)

DRIVER = ./postgresql-42.7.11.jar
RM = rm -f

run: class
	$(JAVA) -cp .:$(DRIVER) JDBCMain

class:
	$(JAVAC) -cp .:$(DRIVER) JDBCMain.java

clean:
	$(RM) $(CLASS)

.PHONY: run class clean