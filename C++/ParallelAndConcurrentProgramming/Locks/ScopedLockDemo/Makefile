# Deadlock Demo Makefile
CXX		 = g++
CXXFLAGS = -Wall -std=c++20
TARGET	 = scoped_lock_demo
SRC		 = scoped_lock_demo.cpp

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) -o $(TARGET)

.PHONY: clean
clean:
	rm -f $(TARGET)