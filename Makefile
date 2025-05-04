# Variables
JAVAC = javac
JAVA = java
SRC_DIR = src
BIN_DIR = bin
SRC_FILES = $(wildcard $(SRC_DIR)/*.java)
CLASS_FILES = $(SRC_FILES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Default target to compile and run
all: $(BIN_DIR) $(CLASS_FILES)
	@echo "Compilation complete. Run 'make run' to execute the program."

# Rule to create the bin directory if it doesn't exist
$(BIN_DIR):
	mkdir -p $(BIN_DIR)

# Rule to compile .java files into .class files
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	$(JAVAC) -d $(BIN_DIR) $<

# Target to run the program (adjust Main with your actual main class)
run: $(CLASS_FILES)
	$(JAVA) -cp $(BIN_DIR) Main

# Clean up compiled files
clean:
	rm -rf $(BIN_DIR)


