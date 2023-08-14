/**
 * Demonstrate the use of a barrier to synchronize operations between
 * the Barron and Olivia shopper threads.
 * 
 * Notice that re-running the program now results in the same amount
 * of chips each time.
 */
#include <thread>
#include <mutex>
#include <cstdint>  // Include this header for int64_t
#include <boost/thread/barrier.hpp>

signed int NUM_OF_SHOPPERS = 10;
unsigned int bags_of_chips = 1;  // start with one on the list
std::mutex pencil;
boost::barrier sync_shoppers(NUM_OF_SHOPPERS);

void cpu_work(int64_t workUnits) {
    int64_t x = 0;
    for (int64_t i = 0; i < workUnits*1000000; i++) {
        x++;
    }
}

void barron_shopper() {
    cpu_work(1);  // simulate doing a bit of work first
    // Have Barron wait to double the bags until after the barrier
    sync_shoppers.wait();
    std::scoped_lock<std::mutex> lock(pencil);
    bags_of_chips *= 2;
    printf("Barron DOUBLED the bags of chips.\n");
}

void olivia_shopper() {
    cpu_work(1);  // simulate doing a bit of work first
    {
        // Using scoped lock so we can reduce the critical
        // section to just this portion so we do not end up
        // holding the lock unnecessarily
        std::scoped_lock<std::mutex> lock(pencil);
        bags_of_chips += 3;
    }
    // We want Olivia to add the bags first
    sync_shoppers.wait();
    printf("Olivia ADDED 3 bags of chips.\n");
}

int main() {
    std::thread shoppers[NUM_OF_SHOPPERS];
    for (int i = 0; i < NUM_OF_SHOPPERS; i += 2) {
        shoppers[i] = std::thread(barron_shopper);
        shoppers[i+1] = std::thread(olivia_shopper);
    }
    for (auto& s : shoppers) {
        s.join();
    }
    printf("We need to buy %u bags_of_chips.\n", bags_of_chips);
}