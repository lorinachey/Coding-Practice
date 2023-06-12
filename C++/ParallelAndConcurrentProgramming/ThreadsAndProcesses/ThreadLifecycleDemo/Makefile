# Thread Lifecycle Makefile
CXX      = g++
CXXFLAGS = -Wall -std=c++20
TARGET   = thread_lifecycle_demo
SRC      = thread_lifecycle_demo.cpp

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) -o $(TARGET)

.PHONY: clean
clean:
	rm -f $(TARGET)