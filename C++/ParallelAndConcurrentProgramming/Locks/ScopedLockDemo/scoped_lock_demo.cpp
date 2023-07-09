/**
 * Two dining philosophers are thinking and eating sushi but only one set of
 * chopsticks is available. This could cause a deadlock. One way to avoid deadlock
 * would be to order the chopsticks in priority so that both philosphers try to get
 * the first chopstick each time. The other way (and what's demonstrated here) is the
 * use of a scoped lock.
*/

#include <thread>
#include <mutex>

int sushi_count = 5000;

void philosopher(std::mutex &first_chopstick, std::mutex &second_chopstick) {
    while (sushi_count > 0) {
        std::scoped_lock(first_chopstick, second_chopstick);
        if (sushi_count) {
            sushi_count--;
        }
    }
    // Scoped lock will release locks automagically
}

int main() {
    std::mutex chopstick_a, chopstick_b;
    std::thread barron(philosopher, std::ref(chopstick_a), std::ref(chopstick_b));
    std::thread olivia(philosopher, std::ref(chopstick_b), std::ref(chopstick_a));
    barron.join();
    olivia.join();
    printf("The philosophers are done eating.\n");
}