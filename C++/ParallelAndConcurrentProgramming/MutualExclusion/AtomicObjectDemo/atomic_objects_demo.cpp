/**
 * Increment the item count using an atomic object rather than a mutex/lock.
*/
#include <thread>
#include <atomic>

// Define the variable as an atomic type
std::atomic<unsigned int> item_count(0);

void shopper() {
    for (int i = 0; i < 1000000; i++) {
        item_count++;
    }
}

int main() {
    std::thread olivia(shopper);
    std::thread barron(shopper);
    barron.join();
    olivia.join();
    // Use .load() to ensure a synchronized read operation
    printf("We should buy %u items.\n", item_count.load());
}