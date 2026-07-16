/*
================================================================================
                VARIABLE SIZE SLIDING WINDOW - LONGEST/MAXIMUM
================================================================================

Find the LONGEST or MAXIMUM subarray/substring satisfying a condition.

Key Template (Expand then Shrink):
- Expand window by moving right pointer
- Shrink window when constraint is violated
- Update maximum after ensuring window is valid

Pattern:
  int left = 0, maxLen = 0;
  for (int right = 0; right < n; right++) {
      // Expand: add arr[right] to window state
      
      while ( window is INVALID ) {
          // Shrink: remove arr[left] from window state
          left++;
      }
      
      // Window is now valid, update answer
      maxLen = max(maxLen, right - left + 1);
  }

Time: O(n) | Space: O(1) or O(k) depending on problem

================================================================================
*/

#include <bits/stdc++.h>
using namespace std;

/*
PROBLEM 1: Longest Substring Without Repeating Characters (LeetCode 3)
───────────────────────────────────────────────────────────────────────
Find length of longest substring without repeating characters.

Input: s = "abcabcbb"
Output: 3 ("abc")

Input: s = "bbbbb"
Output: 1 ("b")

Time: O(n) | Space: O(min(n, alphabet_size))
*/
public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int start = 0;
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int end = 0; end < n; end++) {
            char key = s.charAt(end);
            map.put(key, map.getOrDefault(key, 0) + 1);

            // Window is invalid (contains duplicates) if total elements > unique map keys
            while (end - start + 1 > map.size()) {
                char skey = s.charAt(start);
                if (map.get(skey) == 1) {
                    map.remove(skey);
                } else {
                    map.put(skey, map.get(skey) - 1);
                }
                start++;
            }

            // Window is now valid, update answer
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 2: Longest Substring with At Most K Distinct Characters (LeetCode 340)
──────────────────────────────────────────────────────────────────────────────
Find longest substring with at most k distinct characters.

Input: s = "eceba", k = 2
Output: 3 ("ece")

Input: s = "aa", k = 1
Output: 2 ("aa")

Time: O(n) | Space: O(k)
*/
public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int n = s.length();
        int start = 0;
        int maxLen = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int end = 0; end < n; end++) {
            char key = s.charAt(end);
            map.put(key, map.getOrDefault(key, 0) + 1);

            // Window is invalid when the number of distinct characters exceeds k
            while (map.size() > k) {
                char skey = s.charAt(start);
                if (map.get(skey) == 1) {
                    map.remove(skey);
                } else {
                    map.put(skey, map.get(skey) - 1);
                }
                start++;
            }

            // Window is now valid, update answer
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 3: Longest Repeating Character Replacement (LeetCode 424)
─────────────────────────────────────────────────────────────────
You can replace at most k characters. Find longest substring with same letter.

Input: s = "AABABBA", k = 1
Output: 4 ("AABA" → replace B → "AAAA")

Key Insight: Valid window if (window_size - max_freq) <= k

Time: O(n) | Space: O(26) = O(1)
*/
public int characterReplacement(String s, int k) {
        int n = s.length();
        int[] count = new int[26];
        int start = 0;
        int maxFreq = 0;
        int maxLen = 0;

        for (int end = 0; end < n; end++) {
            char key = s.charAt(end);
            count[key - 'A']++;
            maxFreq = Math.max(maxFreq, count[key - 'A']);

            // Window is invalid when other elements to replace exceed k
            while ((end - start + 1) - maxFreq > k) {
                char skey = s.charAt(start);
                count[skey - 'A']--;
                start++;
                
                // Note: We don't strictly need to recompute maxFreq here because 
                // maxFreq only helps find a larger valid window, which requires a larger maxFreq.
            }

            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 4: Max Consecutive Ones III (LeetCode 1004)
───────────────────────────────────────────────────
Given binary array, return max consecutive 1s if you can flip at most k 0s.

Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
Output: 6 ([1,1,1,0,0,1,1,1,1,1,1] after flipping)

Time: O(n) | Space: O(1)
*/
public int longestOnes(int[] nums, int k) {
        int n = nums.length;
        int start = 0;
        int zeros = 0;
        int maxLen = 0;

        for (int end = 0; end < n; end++) {
            if (nums[end] == 0) {
                zeros++;
            }

            // Window is invalid when the zero count exceeds our budget k
            while (zeros > k) {
                if (nums[start] == 0) {
                    zeros--;
                }
                start++;
            }

            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 5: Fruit Into Baskets (LeetCode 904)
────────────────────────────────────────────
Collect maximum fruits with only 2 types of baskets.
(Same as: Longest subarray with at most 2 distinct elements)

Input: fruits = [1,2,1,2,3]
Output: 4 ([1,2,1,2])

Time: O(n) | Space: O(1) - at most 3 types in map
*/
public int totalFruit(int[] fruits) {
        int n = fruits.length;
        int start = 0;
        int maxFruits = 0;
        Map<Integer, Integer> basket = new HashMap<>();

        for (int end = 0; end < n; end++) {
            int key = fruits[end];
            basket.put(key, basket.getOrDefault(key, 0) + 1);

            // Window is invalid if we have more than 2 distinct fruit types
            while (basket.size() > 2) {
                int skey = fruits[start];
                if (basket.get(skey) == 1) {
                    basket.remove(skey);
                } else {
                    basket.put(skey, basket.get(skey) - 1);
                }
                start++;
            }

            maxFruits = Math.max(maxFruits, end - start + 1);
        }
        return maxFruits;
    }


/*
PROBLEM 6: Longest Subarray of 1's After Deleting One Element (LeetCode 1493)
─────────────────────────────────────────────────────────────────────────────
Delete exactly one element. Find longest subarray of 1's.

Input: nums = [1,1,0,1,1,1]
Output: 5 (delete 0 at index 2)

Similar to Max Consecutive Ones with k=1, but result is (maxLen - 1)

Time: O(n) | Space: O(1)
*/
public int longestSubarray(int[] nums) {
        int n = nums.length;
        int start = 0;
        int zeros = 0;
        int maxLen = 0;

        for (int end = 0; end < n; end++) {
            if (nums[end] == 0) {
                zeros++;
            }

            // Window is invalid if we have more than 1 zero (we can only delete 1)
            while (zeros > 1) {
                if (nums[start] == 0) {
                    zeros--;
                }
                start++;
            }

            maxLen = Math.max(maxLen, end - start + 1);
        }
        
        // We must delete exactly one element, so the result size is maxLen - 1
        return maxLen - 1;
    }


/*
PROBLEM 7: Maximum Erasure Value (LeetCode 1695)
────────────────────────────────────────────────
Find maximum sum of subarray with all unique elements.

Input: nums = [4,2,4,5,6]
Output: 17 ([2,4,5,6])

1 <= nums[i] <= 10

Time: O(n) | Space: O(n)
*/
public int maximumUniqueSubarray(int[] nums) {
        int n = nums.length;
        int start = 0;
        int sum = 0;
        int maxSum = 0;
        Set<Integer> seen = new HashSet<>();

        for (int end = 0; end < n; end++) {
            int key = nums[end];

            // Window is invalid if the element is already present in our unique set
            while (seen.contains(key)) {
                int skey = nums[start];
                seen.remove(skey);
                sum -= skey;
                start++;
            }

            seen.add(key);
            sum += key;
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }


/*
PROBLEM 8: Get Equal Substrings Within Budget (LeetCode 1208)
─────────────────────────────────────────────────────────────
Maximum length substring where cost to change s to t is <= maxCost.
Cost of changing s[i] to t[i] is |s[i] - t[i]|.

Input: s = "abcd", t = "bcdf", maxCost = 3
Output: 3 ("abc" → "bcd" costs 1+1+1 = 3)

Time: O(n) | Space: O(1)
*/
public int equalSubstring(String s, String t, int maxCost) {
        int n = s.length();
        int start = 0;
        int cost = 0;
        int maxLen = 0;

        for (int end = 0; end < n; end++) {
            cost += Math.abs(s.charAt(end) - t.charAt(end));

            // Window is invalid if total transformation cost exceeds our budget limit
            while (cost > maxCost) {
                cost -= Math.abs(s.charAt(start) - t.charAt(start));
                start++;
            }

            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 9: Longest Subarray with Absolute Diff <= Limit (LeetCode 1438)
───────────────────────────────────────────────────────────────────────
Find longest subarray where abs(max - min) <= limit.

Input: nums = [8,2,4,7], limit = 4
Output: 2 ([2,4] or [4,7])

Approach: Use two deques to track max and min in window

Time: O(n) | Space: O(n)
*/
public int longestSubarray(int[] nums, int limit) {
        int n = nums.length;
        Deque<Integer> maxQ = new ArrayDeque<>(); // Monotonic decreasing (stores indices)
        Deque<Integer> minQ = new ArrayDeque<>(); // Monotonic increasing (stores indices)
        int start = 0;
        int maxLen = 0;

        for (int end = 0; end < n; end++) {
            // Maintain max monotonic deque
            while (!maxQ.isEmpty() && nums[end] > nums[maxQ.peekLast()]) {
                maxQ.pollLast();
            }
            maxQ.offerLast(end);

            // Maintain min monotonic deque
            while (!minQ.isEmpty() && nums[end] < nums[minQ.peekLast()]) {
                minQ.pollLast();
            }
            minQ.offerLast(end);

            // Window is invalid if absolute diff between max and min element exceeds limit
            while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > limit) {
                if (maxQ.peekFirst() == start) {
                    maxQ.pollFirst();
                }
                if (minQ.peekFirst() == start) {
                    minQ.pollFirst();
                }
                start++;
            }

            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 10: Longest Nice Subarray (LeetCode 2401)
─────────────────────────────────────────────────
Find longest subarray where bitwise AND of every pair is 0.

Input: nums = [1,3,8,48,10]
Output: 3 ([3,8,48] - no two elements share a bit)

Approach: Track used bits with OR, shrink when AND with new element > 0

Time: O(n) | Space: O(1)
*/
public int longestNiceSubarray(int[] nums) {
        int n = nums.length;
        int start = 0;
        int usedBits = 0;
        int maxLen = 0;

        for (int end = 0; end < n; end++) {
            // Window is invalid if current element shares set bits with existing window
            while ((usedBits & nums[end]) != 0) {
                usedBits ^= nums[start]; // Remove outgoing element's bits using XOR
                start++;
            }

            usedBits |= nums[end]; // Add incoming element's bits
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }


/*
PROBLEM 11: Maximum Points You Can Obtain from Cards (LeetCode 1423)
────────────────────────────────────────────────────────────────────
Pick k cards from either end. Maximize sum.

Input: cardPoints = [1,2,3,4,5,6,1], k = 3
Output: 12 (pick 1, 6, 5)

Approach: Minimize sum of (n-k) length window in middle

Time: O(n) | Space: O(1)
*/
public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int windowSize = n - k;
        int totalSum = 0;
        int windowSum = 0;
        int minWindowSum = Integer.MAX_VALUE;
        int start = 0;

        // Base Case: If we have to pick all cards
        if (windowSize == 0) {
            for (int x : cardPoints) totalSum += x;
            return totalSum;
        }

        for (int end = 0; end < n; end++) {
            totalSum += cardPoints[end];
            windowSum += cardPoints[end];

            // When window matches the needed length (n-k)
            if (end - start + 1 == windowSize) {
                minWindowSum = Math.min(minWindowSum, windowSum);
                windowSum -= cardPoints[start];
                start++;
            }
        }

        return totalSum - minWindowSum;
    }

/*
================================================================================
                              SUMMARY TABLE
================================================================================

+────────────────────────────────────+──────────────────────────────────────────+
| Problem                            | Window Constraint                        |
+────────────────────────────────────+──────────────────────────────────────────+
| Longest Without Repeating          | No duplicate characters                  |
| At Most K Distinct                 | At most K unique characters              |
| Character Replacement              | (size - maxFreq) <= K                    |
| Max Consecutive Ones               | At most K zeros in window                |
| Fruit Into Baskets                 | At most 2 unique elements                |
| After Deleting One Element         | At most 1 zero in window                 |
| Maximum Erasure Value              | All unique elements + track sum          |
| Equal Substrings Within Budget     | Cost to transform <= budget              |
| Absolute Diff <= Limit             | max - min <= limit (use two deques)      |
+────────────────────────────────────+──────────────────────────────────────────+

KEY INSIGHT: For "longest" problems, expand first, then shrink when invalid.

================================================================================
*/

