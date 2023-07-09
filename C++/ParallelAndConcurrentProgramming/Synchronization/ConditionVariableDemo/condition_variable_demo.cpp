/**
 * Condition Variable Demo - hungry people will be waiting for their turn to
 * take some soup. A condition variable will be used to notify the threads when
 * they can check for the lock again. This allows for a thread to release the lock
 * and automatically try re-aquiring it when the condition is met.
*/
#include <thread>
#include <condition_variable>
#include <mutex>

const int NUM_OF_HUNGRY_PEOPLE = 5;

int soup_servings = 10;

std::mutex slow_cooker_lid;
std::condition_variable soup_taken;

void hungry_person(int id) {
    int put_lid_back = 0;
    while (soup_servings > 0) {
        // Simulates picking up the lid to the slow cooker
        std::unique_lock<std::mutex> lid_lock(slow_cooker_lid);

        while ((id != soup_servings % NUM_OF_HUNGRY_PEOPLE) && soup_servings > 0) {
            // It's not this thread's turn to take soup
            put_lid_back++;
            // Wait on the soup taken cond variable until a notify occurs
            soup_taken.wait(lid_lock);
        }
        if (soup_servings > 0) {
            soup_servings--;
            lid_lock.unlock();
            soup_taken.notify_all();
        }
    }
    printf("Id %d put the lid back %d times.\n", id, put_lid_back);
}

int main() {
    std::thread hungry_threads[NUM_OF_HUNGRY_PEOPLE];
    for (int i = 0; i < NUM_OF_HUNGRY_PEOPLE; i++) {
        hungry_threads[i] = std::thread(hungry_person, i);
    }

    for (auto &hungry_thread : hungry_threads) {
        hungry_thread.join();
    }
}


