# Atomic Objects Demo Makefile
CXX       = g++
CXXFLAGS  = -Wall -std=c++20
TARGET    = atomic_objects_demo
SRC       = atomic_objects_demo.cpp

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) -o $(TARGET)

.PHONY: clean
clean:
	rm -f $(TARGET)