# 第三题，找出字符串中最长不重复的子串

<pre>
<code>
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet();
        int max = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            while(!set.add(s.charAt(right))) {
                set.remove(s.charAt(left++));
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}
</code>
</pre>

### 重点: 使用 HashSet 保存已经遍历过的字符