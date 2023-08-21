# 第七题，反转数字

<pre>
<code>
class Solution {
    public int reverse(int x) {
        int prevRev = 0, rev = 0;
        while (x != 0) {
            // 取出x最低位添加到rev最高位
            rev = rev * 10 + x % 10;
            // 判断溢出
            if ((rev - x % 10) / 10 != prevRev) {
                return 0;
            }
            prevRev = rev;
            x = x / 10;
        }
        return rev;
    }
}
</code>
</pre>

### 重点: 题目要求 "Given a signed 32-bit integer x, return x with its digits reversed.Assume the environment does not allow you to store 64-bit integers (signed or unsigned)."