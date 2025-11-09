## Memory Optimization
1. File Streaming: I replaced loading the entire file into a single String in memory. Instead, I use `Files.lines()`

2. No Intermediate List: No temporary list that stored every single word from the file. Words are now processed
and counted directly into the final frequency map, but it made the code a little bit less readable

3. Also added explicit character encoding to the file reading so it can read line-by-line 

### Time Comparison

Just refactored version in **lab2** - **402**

Memory optimized version in **lab3** - **218**