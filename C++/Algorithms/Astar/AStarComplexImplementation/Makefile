# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++20 -Wall

# Source files
SOURCES = MinHeap.cpp AStarAlgorithm.cpp
HEADERS = MinHeap.hpp Node.hpp

# Output executable
TARGET = astar

# Object files
OBJECTS = $(SOURCES:.cpp=.o)

# Build and link
$(TARGET): $(OBJECTS)
	$(CXX) $(CXXFLAGS) $(OBJECTS) -o $(TARGET)

# Compile source files
%.o: %.cpp $(HEADERS)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Clean up generated files
clean:
	rm -f $(TARGET) $(OBJECTS)
