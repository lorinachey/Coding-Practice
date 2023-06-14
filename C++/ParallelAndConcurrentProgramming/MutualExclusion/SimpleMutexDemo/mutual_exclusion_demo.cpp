/**
 * Two shoppers are writing to a shared shopping list
*/
#include <thread>
#include <mutex>
#include <chrono>

const unsigned int WAIT_TIME_MILLISECONDS = 500;
unsigned int item_count = 0;
std::mutex item_lock;

void shopper() {
    for (int i = 0; i < 10; i++) {
        printf("Shopper represented by thread id: %d\n", std::this_thread::get_id());
        std::this_thread::sleep_for(std::chrono::milliseconds(WAIT_TIME_MILLISECONDS));
        // Isolate the lock to the smallest possible critical section
        item_lock.lock();
        item_count++;
        item_lock.unlock();
    }
}

int main() {
    std::thread barron(shopper);
    std::thread olivia(shopper);
    barron.join();
    olivia.join();
    printf("We'll need %u items.\n", item_count);
}