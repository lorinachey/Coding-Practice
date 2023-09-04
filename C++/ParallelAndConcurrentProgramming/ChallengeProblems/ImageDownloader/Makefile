# Download Images Solution Makefile
CXX      = g++
CXXFLAGS = -Wall -pthread
TARGET   = download_images
SRC      = download_images.cpp
LDLIBS   = -lcurl

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) $(LDLIBS) -o $(TARGET)

.PHONY: clean
clean:
	rm -f $(TARGET)