# Barrier Demo Makefile
CXX		 = g++
CXXFLAGS = -Wall -std=c++17 -pthread
TARGET	 = barrier_demo
SRC		 = barrier_demo.cpp

# Need the boost library for barriers
LDLIBS	 = -lboost_thread -lboost_system 

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) $(LDLIBS) -o $(TARGET)

.PHONY: clean
clean:
	rm -f $(TARGET)