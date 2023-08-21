# 第六题，Z字形重排字符串
### Example: Input: s = "PAYPALISHIRING", numRows = 4
### Output: "PINALSIGYAHRPI"
### Explanation:
<pre>P     I    N
A   L S  I G
Y A   H R
P     I
</pre>

<pre>
<code>
class Solution {
   public String convert(String s, int nRows) {
        if (nRows == 1) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        int len = s.length();
        int cycleLen = 2 * nRows - 2; // 每个循环的长度

        for (int i = 0; i < nRows; i++) {
            for (int j = i; j < len; j += cycleLen) {
                sb.append(s.charAt(j));

                // 对于非首行和末行的字符，还需要添加斜线上的字符
                if (i != 0 && i != nRows - 1) {
                    int diagonalIdx = j + cycleLen - 2 * i;
                    if (diagonalIdx < len) {
                        sb.append(s.charAt(diagonalIdx));
                    }
                }
            }
        }

        return sb.toString();
    }
}
</code>
</pre>

### 重点: 找出Z字形排列的循环规律