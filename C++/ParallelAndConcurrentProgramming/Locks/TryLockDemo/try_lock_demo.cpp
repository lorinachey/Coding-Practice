/**
 * Two shoppers adding items to a shared notepad
 * 
 * Using the try_lock instead of a regular lock resulted in the program running
 * about twice as fast!
 */
#include <thread>
#include <mutex>
#include <chrono>

const int NUM_ITEMS_ON_NOTEPAD = 20;

unsigned int items_on_notepad = 0;

std::mutex pencil;

void shopper(const char* name) {
    unsigned int items_to_add =  0;

    // Keep the number of items below a certain value
    while (items_on_notepad <= NUM_ITEMS_ON_NOTEPAD) {
        // Use a try lock to free up a thread if there's already a hold on the lock
        if (items_to_add && pencil.try_lock()) {
            items_on_notepad += items_to_add;
            printf("%s added %u item(s) to the notepad.\n", name, items_to_add);
            items_to_add = 0;
            // Use this sleep to represent the time spent writing on the notepad
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
            pencil.unlock();
        } else {
            // Look for other items to buy - let this sleep represent time spent looking
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            items_to_add++;
            printf("%s found items to buy.\n", name);
        }
    }
}

int main() {
    std::thread barron(shopper, "Barron");
    std::thread olivia(shopper, "Olivia");
    auto start_time = std::chrono::steady_clock::now();
    barron.join();
    olivia.join();
    auto end_time = std::chrono::steady_clock::now();
    auto elapsed_time = std::chrono::duration_cast<std::chrono::milliseconds>(
        std::chrono::steady_clock::now() - start_time).count();
    printf("Elapsed Time: %.2f seconds\n", elapsed_time/1000.0);
}