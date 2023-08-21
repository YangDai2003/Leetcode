# 第五题，找到最长回文串

<pre>
<code>
class Solution {
    public String longestPalindrome(String s) {
        // 遍历字符串中每个字符
        int max = 0, idx = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = extend(s, i, i), len2 = extend(s, i, i + 1);
            if (max < Math.max(len1, len2)) {
                idx = (len1 > len2) ? (i - len1 / 2) : (i - len2 / 2 + 1);
                max = Math.max(len1, len2);
            }
        }
        return s.substring(idx, idx + max);
    }
    // 以字符为中心向两边扩展，找到回文字符串最大长度
    private int extend(String s, int i, int j) {
        for (; i >= 0 && j < s.length(); i--, j++)
            if (s.charAt(i) != s.charAt(j))
                break;
        return j - i - 2 + 1;
    }
}
</code>
</pre>
