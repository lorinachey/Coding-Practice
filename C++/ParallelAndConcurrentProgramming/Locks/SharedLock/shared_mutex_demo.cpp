/**
 * Demonstrate a shared mutex by having multiple calendar "Readers" and only a
 * few calendar "Writers".
*/
#include <thread>
#include <chrono>
#include <shared_mutex>
#include <vector>
#include <array>

int today = 0;
std::shared_mutex marker;

std::vector<std::string> daysOfWeek = {
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday"
};

void calendar_reader(const int id) {
    for (int i = 0; i < 7; i++) {
        // Use the shared lock so multiple threads can lock to read the date
        marker.lock_shared();
        printf("Reader %d sees today is %s\n", id, daysOfWeek[today].c_str());
        // The sleep makes it easier to see what the threads are doing
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
        marker.unlock_shared();
    }
}

void calendar_writer(const int id) {
    for (int i = 0; i < 7; i++) {
        // Use the exclusive lock because only one writer can write at a time
        marker.lock();
        today = (today + 1) % 7;
        printf("Writer-%d updated date to %s\n", id, daysOfWeek[today].c_str());
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
        marker.unlock();
    }
}

int main() {
    // create ten reader threads ...but only two writer threads
    std::array<std::thread, 10> readers;
    for (unsigned int i=0; i < readers.size(); i++) {
        readers[i] = std::thread(calendar_reader, i);
    }
    std::array<std::thread, 2> writers;
    for (unsigned int i=0; i < writers.size(); i++) {
        writers[i] = std::thread(calendar_writer, i);
    }

    // wait for readers and writers to finish
    for (unsigned int i=0; i < readers.size(); i++) {
        readers[i].join();
    }
    for (unsigned int i=0; i < writers.size(); i++) {
        writers[i].join();
    }
}

