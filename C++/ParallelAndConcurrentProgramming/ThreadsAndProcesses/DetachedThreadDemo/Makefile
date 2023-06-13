# Detached Thread Demo Makefile
CXX      = g++
CXXFLAGS = -Wall -std=c++20
TARGET   = detached_thread_demo
SRC      = detached_thread_demo.cpp

# This line defines a dependency relationship, stating that 
# the target file ($(TARGET)) depends on the source file ($(SRC)).
# It means that the target file needs to be rebuilt if the source file 
# has changed or does not exist.
$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) -o $(TARGET)

.PHONY clean
clean:
	rm -f $(TARGET)