# 第八题，字符串转整型

<pre>
<code>
    public int myAtoi(String str) {
        str = str.trim();
        final int len = str.length();
        if (len == 0) {
            return 0;
        }

        int index = 0;
        boolean isNegative = false;

        if (str.charAt(index) == '-') {
            isNegative = true;
            ++index;
        } else if (str.charAt(index) == '+') {
            ++index;
        }

        int result = 0;
        while (index < len && isDigit(str.charAt(index))) {
            // 通过-'0'将char转成int: 比如 5的ASCII=53 0的ASCII为48 53-48=5
            int digit = str.charAt(index) - '0';

            // 避免溢出
            if (result > (Integer.MAX_VALUE / 10) || (result == (Integer.MAX_VALUE / 10) && digit > 7)) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            // 进位
            result = (result * 10) + digit;
            ++index;
        }

        return isNegative ? -result : result;
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
</code>
</pre>

### 重点: 对ASCII码的运用和char类型的了解