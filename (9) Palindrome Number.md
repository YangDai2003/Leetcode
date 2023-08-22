# 第九题，回文整型数字

<pre>
<code>
    public boolean isPalindrome(int x) {
        // 判断是否只有一位，直接返回true
        if (x < 10 && x >= 0) {
            return true;
        }
        // 判断末尾是否为0，数字是否小于0，直接返回false
        if (x % 10 == 0 || x < 0) {
            return false;
        }
        // 计算回文数
        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x = x / 10;
        }
        return (x == rev || x == rev / 10);
    }
</code>
</pre>

### 重点: 对特殊情况提前判断，由于题目要求不能转成String，否则三行搞定:

<pre>
<code>
String value = String.valueOf(x);
StringBuilder sb = new StringBuilder(value);
return sb.reverse().toString().equals(value);
</code>
</pre>