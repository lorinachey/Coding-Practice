/**
 * Measure the Speedup from Parallelizing the Recursive Sum Algorithm
 * 
 * Use multiple evaluation runs to improve accuracy of the measurement.
*/
#include <chrono>
#include <cstdint>
#include <future>

uint64_t sequential_sum(unsigned int lo, unsigned int hi) {
    uint64_t sum = 0;
    for (auto i = lo; i < hi; i++) {
        sum += i;
    }
    return sum;
}

uint64_t parallel_sum(unsigned int lo, unsigned int hi, unsigned int depth=0) {
    uint64_t sum = 0;

    // base case threshold
    if (depth > 3) {
        sum = sequential_sum(lo, hi);
        return sum;
    } else {
        // get the middle index for splitting
        auto mid = (hi + lo) / 2;

        // use a future to get the left portion asyncronously
        auto left = std::async(std::launch::async, parallel_sum, lo, mid, depth+1);
        auto right = parallel_sum(mid, hi, depth+1);

        return left.get() + right;
    }
}

int main() {
    const int NUM_EVAL_RUNS = 10;
    const int SUM_VALUE = 1000000;

    printf("Evaluating Sequential Implementation...\n");\
    std::chrono::duration<double> sequential_time(0);
    auto sequential_result = sequential_sum(0, SUM_VALUE);  // "warm up" run
    for (int i = 0; i < NUM_EVAL_RUNS; i++) {
        auto start_time = std::chrono::high_resolution_clock::now();
        sequential_sum(0, SUM_VALUE);
        sequential_time += std::chrono::high_resolution_clock::now() - start_time;
    }
    sequential_time /= NUM_EVAL_RUNS;

    printf("Evaluating Parallel Implementation...\n");
    std::chrono::duration<double> parallel_time(0);
    auto parallel_result = parallel_sum(0, SUM_VALUE);  // "warm up" run
    for (int i = 0; i < NUM_EVAL_RUNS; i++) {
        auto start_time = std::chrono::high_resolution_clock::now();
        parallel_sum(0, SUM_VALUE);
        parallel_time += std::chrono::high_resolution_clock::now() - start_time;
    }
    parallel_time /= NUM_EVAL_RUNS;

    // Display sequential and parallel results for comparison
    if (sequential_result != parallel_result) {
        printf("ERROR: Result mismatch!\n  Sequential Result = %lu\n  Parallel Result = %lu\n",
            sequential_result, parallel_result);
    }
    printf("Average Sequential Time: %.1f ms\n", sequential_time.count()*1000);
    printf("Average Parallel Time: %.1f ms\n", parallel_time.count()*1000);
    printf("  - Speedup: %.2f\n", sequential_time/parallel_time);
    printf("  - Efficiency %.2f%%\n", 100*(sequential_time/parallel_time)/std::thread::hardware_concurrency());
}
