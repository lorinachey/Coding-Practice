/**
 * Demonstrate a race condition with this code that
 * determines how many bags of chips to buy for the party.
 * 
 * Notice that re-running the program can result in different
 * outputs for the number of chips to buy depending on which of
 * the threads gets executed first.
 */
#include <thread>
#include <mutex>
#include <cstdint>  // Include this header for int64_t

unsigned int bags_of_chips = 1;  // start with one on the list
std::mutex pencil;

void cpu_work(int64_t workUnits) {
    int64_t x = 0;
    for (int64_t i = 0; i < workUnits*1000000; i++) {
        x++;
    }
}

void barron_shopper() {
    cpu_work(1);  // simulate doing a bit of work first
    std::scoped_lock<std::mutex> lock(pencil);
    bags_of_chips *= 2;
    printf("Barron DOUBLED the bags of chips.\n");
}

void olivia_shopper() {
    cpu_work(1);  // simulate doing a bit of work first
    std::scoped_lock<std::mutex> lock(pencil);
    bags_of_chips += 3;
    printf("Olivia ADDED 3 bags of chips.\n");
}

int main() {
    std::thread shoppers[10];
    for (int i = 0; i < 10; i += 2) {
        shoppers[i] = std::thread(barron_shopper);
        shoppers[i+1] = std::thread(olivia_shopper);
    }
    for (auto& s : shoppers) {
        s.join();
    }
    printf("We need to buy %u bags_of_chips.\n", bags_of_chips);
}