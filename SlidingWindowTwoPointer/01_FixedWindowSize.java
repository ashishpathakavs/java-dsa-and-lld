/*
================================================================================
                    FIXED SIZE SLIDING WINDOW PATTERNS
================================================================================

Window size is constant (given as K in problem).

Key Template:
- Add element at pointer end.
- Check if window size is complete using end - start + 1 == k.
- Extract/process result when the window is complete.
- Slide the window by removing the outgoing element at start and incrementing start++.

Time: O(n) | Space: O(1) or O(k)

================================================================================
*/

#include <bits/stdc++.h>
using namespace std;

/*
PROBLEM 1: Maximum Sum of Subarray of Size K
─────────────────────────────────────────────
Find the maximum sum of any contiguous subarray of size k.

Input: arr = [2, 1, 5, 1, 3, 2], k = 3
Output: 9 (subarray [5, 1, 3])

Time: O(n) | Space: O(1)
*/
class Solution {
    public int maxSumSubarrayOfSizeK(int[] arr, int k) {
        int n = arr.length;
        if (n < k) return -1;
        
        int start = 0;
        int windowSum = 0;
        int maxSum = Integer.MIN_VALUE;
        
        for (int end = 0; end < n; end++) {
            windowSum += arr[end]; // Add incoming element
            
            if (end - start + 1 == k) {
                maxSum = Math.max(maxSum, windowSum);
                windowSum -= arr[start]; // Remove outgoing element
                start++;
            }
        }
        return maxSum;
    }
}


/*
PROBLEM 2: First Negative in Every Window of Size K
────────────────────────────────────────────────────
Print the first negative number in every window of size k.
If no negative exists, print 0.

Input: arr = [12, -1, -7, 8, -15, 30, 16, 28], k = 3
Output: [-1, -1, -7, -15, -15, 0]

Approach: Use deque to store indices of negative numbers

Time: O(n) | Space: O(k)
*/

class Solution {
    static List<Integer> firstNegInt(int arr[], int k) {
        int n = arr.length;
        
        int start = 0;
        Deque<Integer> q = new ArrayDeque<>();
        List<Integer> ans = new ArrayList<>();
        
        for (int end = 0; end < n; end++) {
            // Add current element's index if negative
            if (arr[end] < 0) {
                q.offerLast(end);
            }
            
            // Window is complete
            if (end - start + 1 == k) {
                if (q.isEmpty()) {
                    ans.add(0);
                } else {
                    ans.add(arr[q.peekFirst()]);
                }
                
                // Remove elements outside window
                if (!q.isEmpty() && q.peekFirst() == start) {
                    q.pollFirst();
                }
                start++;
            }
        }
        return ans;
    }
}


/*
PROBLEM 3: Count Distinct Elements in Every Window of Size K
─────────────────────────────────────────────────────────────
Count the number of distinct elements in every window of size k.

Input: arr = [1, 2, 1, 3, 4, 2, 3], k = 4
Output: [3, 4, 4, 3]
Explanation: 
  Window [1,2,1,3] → 3 distinct (1, 2, 3)
  Window [2,1,3,4] → 4 distinct (1, 2, 3, 4)
  ...

Time: O(n) | Space: O(k)
*/
import java.util.*;

class Solution {
    ArrayList<Integer> countDistinct(int arr[], int k) {
        int n = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> ans = new ArrayList<>();
        
        int start = 0;
        for (int end = 0; end < n; end++) {
            int key = arr[end];
            map.put(key, map.getOrDefault(key, 0) + 1); // Add incoming element
            
            if (end - start + 1 == k) { // Window is complete
                ans.add(map.size());
                
                // Shrink the window / remove outgoing element
                int startKey = arr[start];
                if (map.get(startKey) == 1) {
                    map.remove(startKey);
                } else {
                    map.put(startKey, map.get(startKey) - 1);
                }
                
                start++;
            }
        }
        return ans;
    }
}


/*
PROBLEM 4: Maximum of All Subarrays of Size K (Sliding Window Maximum)
──────────────────────────────────────────────────────────────────────
Find maximum element in every window of size k.

Input: arr = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
Output: [3, 3, 5, 5, 6, 7]

Approach: Use monotonic decreasing deque (stores indices)
- Front of deque is always the maximum
- Remove elements smaller than current (they can never be max)

Time: O(n) | Space: O(k)
*/
import java.util.*;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) return new int[0];
        
        Deque<Integer> dq = new ArrayDeque<>(); // Monotonic decreasing
        int[] ans = new int[n - k + 1];
        int start = 0;
        
        for (int end = 0; end < n; end++) {
            // Remove smaller elements (they can never be maximum)
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[end]) {
                dq.pollLast();
            }
            
            dq.offerLast(end);
            
            if (end - start + 1 == k) { // Window is complete
                ans[start] = nums[dq.peekFirst()];
                
                // Remove elements outside the current window
                if (dq.peekFirst() == start) {
                    dq.pollFirst();
                }
                start++;
            }
        }
        return ans;
    }
}



/*
PROBLEM 5: Find All Anagrams in a String (LeetCode 438)
───────────────────────────────────────────────────────
Given strings s and p, find all start indices of p's anagrams in s.

Input: s = "cbaebabacd", p = "abc"
Output: [0, 6]
Explanation: 
  "cba" at index 0 is anagram of "abc"
  "bac" at index 6 is anagram of "abc"

Time: O(n) | Space: O(1) - only 26 characters
*/
import java.util.*;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int n = s.length(), k = p.length();
        if (n < k) return ans;
        
        int[] pCount = new int[26];
        int[] sCount = new int[26];
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }
        
        int start = 0;
        for (int end = 0; end < n; end++) {
            sCount[s.charAt(end) - 'a']++; // Add incoming character
            
            if (end - start + 1 == k) { // Window is complete
                if (Arrays.equals(pCount, sCount)) {
                    ans.add(start);
                }
                sCount[s.charAt(start) - 'a']--; // Remove outgoing character
                start++;
            }
        }
        return ans;
    }
}


/*
PROBLEM 6: Permutation in String (LeetCode 567)
───────────────────────────────────────────────
Check if s2 contains a permutation of s1.

Input: s1 = "ab", s2 = "eidbaooo"
Output: true (s2 contains "ba" which is permutation of "ab")

Time: O(n) | Space: O(1)
*/
import java.util.*;

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int k = s1.length(), n = s2.length();
        if (n < k) return false;
        
        int[] count1 = new int[26];
        int[] count2 = new int[26];
        for (char c : s1.toCharArray()) {
            count1[c - 'a']++;
        }
        
        int start = 0;
        for (int end = 0; end < n; end++) {
            count2[s2.charAt(end) - 'a']++; // Add incoming character
            
            if (end - start + 1 == k) { // Window is complete
                if (Arrays.equals(count1, count2)) {
                    return true;
                }
                count2[s2.charAt(start) - 'a']--; // Remove outgoing character
                start++;
            }
        }
        return false;
    }
}


/*
PROBLEM 7: Subarray Product Less Than K - Count (LeetCode 713)
──────────────────────────────────────────────────────────────
Count subarrays where product of all elements < k.

Input: nums = [10, 5, 2, 6], k = 100
Output: 8
Subarrays: [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]

Note: This uses VARIABLE window but included here as it counts subarrays

Time: O(n) | Space: O(1)
*/
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0;
        
        int product = 1;
        int count = 0;
        int start = 0;
        
        for (int end = 0; end < nums.length; end++) {
            product *= nums[end];
            
            // Shrink window while product >= k
            while (product >= k) {
                product /= nums[start];
                start++;
            }
            
            // All subarrays ending at 'end' index with start >= 'start' are valid
            count += end - start + 1;
        }
        return count;
    }
}


/*
PROBLEM 8: Average of All Subarrays of Size K
─────────────────────────────────────────────
Calculate average of every contiguous subarray of size k.

Input: arr = [1, 3, 2, 6, -1, 4, 1, 8, 2], k = 5
Output: [2.2, 2.8, 2.4, 3.6, 2.8]

Time: O(n) | Space: O(1)
*/
class Solution {
    public double[] findAverages(int[] arr, int k) {
        int n = arr.length;
        double[] result = new double[n - k + 1];
        double windowSum = 0;
        int start = 0;
        
        for (int end = 0; end < n; end++) {
            windowSum += arr[end]; // Add incoming element
            
            if (end - start + 1 == k) { // Window is complete
                result[start] = windowSum / k;
                windowSum -= arr[start]; // Remove outgoing element
                start++;
            }
        }
        return result;
    }
}


/*
PROBLEM 9: Contains Duplicate II (LeetCode 219)
───────────────────────────────────────────────
Check if array contains duplicates within distance k.

Input: nums = [1,2,3,1], k = 3
Output: true (nums[0] == nums[3], and 3 - 0 <= 3)

- k+1 length sliding window

Time: O(n) | Space: O(k)
*/

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        int start = 0;
        
        for (int end = 0; end < nums.length; end++) {
            if (window.contains(nums[end])) {
                return true;
            }
            window.add(nums[end]);
            
            // Slide window: once index distance exceeds k, remove element at start
            if (end - start + 1 == k+1) {
                window.remove(nums[start]);
                start++;
            }
        }
        return false;
    }
}


/*
PROBLEM 10: Minimum Difference Between Largest and Smallest in Three Moves
──────────────────────────────────────────────────────────────────────────
You can change at most 3 elements to any value.
Find minimum difference between max and min after changes.

Input: nums = [5,3,2,4]
Output: 0 (change 3 elements to make all same)

Approach: Sort, then find min difference in sliding window of size n-3

Time: O(n log n) | Space: O(1)
*/

class Solution {
    public int minDifference(int[] nums) {
        int n = nums.length;
        if(n <= 3){
            return 0;
        }

        Arrays.sort(nums);
        int ans = Integer.MAX_VALUE;

        // nums[n-4] - nums[0]
        // nums[n-3] - nums[1]
        // nums[n-2] - nums[2]
        // nums[n-1] - nums[3]
        for(int i=0; i<=3; i++){
            ans = Math.min(ans, nums[n-4+i] - nums[i]);
        }
        return ans;
    }
}


/*
================================================================================
                              SUMMARY TABLE
================================================================================

+──────────────────────────────────+─────────────────+────────────────────────+
| Problem                          | Data Structure  | Key Insight            |
+──────────────────────────────────+─────────────────+────────────────────────+
| Max/Min Sum of Size K            | Variable        | Add/remove at edges    |
| First Negative in Window         | Deque           | Store negative indices |
| Count Distinct in Window         | HashMap         | Track frequencies      |
| Max of Sliding Window            | Monotonic Deque | Keep potential maxes   |
| Find Anagrams                    | Array[26]       | Compare freq arrays    |
| Permutation in String            | Array[26]       | Same as anagrams       |
| Contains Duplicate II            | HashSet         | Window as set          |
+──────────────────────────────────+─────────────────+────────────────────────+

================================================================================
*/

