package com.CK;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        String s = "aaaaaaabbcccc";
        Solution solution = new Solution();
        System.out.println(solution.reorganizeString(s));
    }
}


//Greedy
class Solution {
        public String reorganizeString(String S) {
            // Create map of each char to its count
            Map<Character, Integer> map = new HashMap<>();
            for (char c : S.toCharArray()) {
                int count = map.getOrDefault(c, 0) + 1;
                // Impossible to form a solution
                if (count > (S.length() + 1) / 2) return "";
                map.put(c, count);
            }
            // Greedy: fetch char of max count as next char in the result.
            // Use PriorityQueue to store pairs of (char, count) and sort by count DESC.
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
            for (char c : map.keySet()) {
                pq.add(new int[] {c, map.get(c)});
            }

            // Build the result.
            StringBuilder sb = new StringBuilder();
            while (!pq.isEmpty()) {
                int[] first = pq.poll();
                if (sb.length() == 0 || first[0] != sb.charAt(sb.length() - 1)) {
                    sb.append((char) first[0]);
                    if (--first[1] > 0) {
                        pq.add(first);
                    }
                } else {
                    int[] second = pq.poll();
                    sb.append((char) second[0]);
                    if (--second[1] > 0) {
                        pq.add(second);
                    }
                    pq.add(first);
                }
            }
            return sb.toString();
        }

}

class Solution2 {
    public String reorganizeString(String S) {
        int N = S.length();
        int[] counts = new int[26];
        for (char c: S.toCharArray()) counts[c-'a'] += 100;
        for (int i = 0; i < 26; ++i) counts[i] += i;
        //Encoded counts[i] = 100*(actual count) + (i)
        Arrays.sort(counts);

        char[] ans = new char[N];
        int t = 1;
        for (int code: counts) {
            int ct = code / 100;
            char ch = (char) ('a' + (code % 100));
            if (ct > (N+1) / 2) return "";
            for (int i = 0; i < ct; ++i) {
                if (t >= N) t = 0;
                ans[t] = ch;
                t += 2;
            }
        }

        return String.valueOf(ans);
    }
}