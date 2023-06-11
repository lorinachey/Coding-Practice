# Two Threads Demo Makefile
CXX		 = g++
CXXFLAGS = -Wall -std=c++20
TARGET	 = two_threads_demo
SRC		 = two_threads_demo.cpp

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) -o $(TARGET)

.PHONY: clean
clean:
	rm -f $(TARGET)