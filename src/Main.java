
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        f(4, false);
        String s = "pwwkew";
        System.out.println(lengthOfLongestSubstring(s));
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
    }

    public boolean isMatch(String s, String p) {
        // 边界情况
        if (s == null || p == null) return false;

        int m = s.length();
        int n = p.length();

        // M[i][j] 表示字符串 s 的前 i 个字符是否能够匹配模式字符串 p 的前 j 个字符
        boolean[][] M = new boolean[m + 1][n + 1];

        // 初始化：
        // 1. M[0][0] = true，因为空字符串匹配空模式字符串
        M[0][0] = true;

        // 2. M[i][0] = false（布尔数组的默认值），因为空模式字符串无法匹配非空字符串
        // 3. M[0][j]：模式字符串匹配空字符串 "" 的情况，模式字符串应该是 #*#*#*#*... 的形式，即偶数位置的字符应该是 '*'，所以可以跳过奇数位置的字符，从 2 开始，每次间隔 2。
        // 注意到重复子模式 #* 的长度只有 2，所以我们可以利用 M[0][j - 2] 而不是每次扫描 j 长度来检查是否匹配 #*#*#*#*...
        for (int j = 2; j < n + 1; j += 2) {
            if (p.charAt(j - 1) == '*' && M[0][j - 2]) {
                M[0][j] = true;
            }
        }

        // 归纳规则类似于编辑距离，我们也是从后往前考虑，并且基于模式字符串中的字符
        // 1. 如果 p.charAt(j) == s.charAt(i)，则 M[i][j] = M[i - 1][j - 1]
        //    ######a(i)
        //    ####a(j)
        // 2. 如果 p.charAt(j) == '.'，则 M[i][j] = M[i - 1][j - 1]
        // 	  #######a(i)
        //    ####.(j)
        // 3. 如果 p.charAt(j) == '*'：
        //    1. 如果 p.charAt(j - 1) != '.' && p.charAt(j - 1) != s.charAt(i)，则 b* 被视为空，M[i][j] = M[i][j - 2]
        //       #####a(i)
        //       ####b*(j)
        //    2. 如果 p.charAt(j - 1) == '.' || p.charAt(j - 1) == s.charAt(i)：
        //       ######a(i)
        //       ####.*(j)
        //
        // 	  	 #####a(i)
        //    	 ###a*(j)
        //      2.1 如果 p.charAt(j - 1) 被视为空，则 M[i][j] = M[i][j - 2]
        //      2.2 如果 p.charAt(j - 1) 被视为一个字符，则 M[i][j] = M[i - 1][j - 2]
        //      2.3 如果 p.charAt(j - 1) 被视为多个字符，则 M[i][j] = M[i - 1][j]
        // 总结：从上面可以看出，要得到 M[i][j]，我们需要知道 M 中的前面的元素，即我们需要先计算它们。
        // 这决定了 i 从 1 到 m - 1，j 从 1 到 n + 1

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                char curS = s.charAt(i - 1);
                char curP = p.charAt(j - 1);
                if (curS == curP || curP == '.') {
                    M[i][j] = M[i - 1][j - 1];
                } else if (curP == '*') {
                    char preCurP = p.charAt(j - 2);
                    if (preCurP != '.' && preCurP != curS) {
                        M[i][j] = M[i][j - 2];
                    } else {
                        M[i][j] = (M[i][j - 2] || M[i - 1][j - 2] || M[i - 1][j]);
                    }
                }
            }
        }

        return M[m][n];
    }

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

    // 第六题 字符串Z字形排列
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

    public String longestPalindrome(String s) {
        // 遍历字符串中每个字符
        int max = 0, idx = 0;
        for (int i = 0; i < s.length(); i++) {
            // 分别处理奇数和偶数为中心的回文串
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

    // 分别将两个数组对半分开，考虑奇数偶数，再分别找到左部分的最大值和右部分的最小值，即可得到中位数
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length;
        // 保证左比右小
        if (l1 > l2) {
            return findMedianSortedArrays(nums2, nums1);
        }
        // 左右下标
        int l = 0, r = l1;
        while (l <= r) {
            int mid1 = (l + r) / 2;
            // 通过加 1 ，合并了奇数偶数两种情况，因为整数除法下取整。
            int mid2 = (l1 + l2 + 1) / 2 - mid1;
            System.out.println("mid1: " + mid1 + "mid2: " + mid2);

            int maxLeft1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int minRight1 = (mid1 == l1) ? Integer.MAX_VALUE : nums1[mid1];
            System.out.println(maxLeft1 + "---" + minRight1);

            int maxLeft2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int minRight2 = (mid2 == l2) ? Integer.MAX_VALUE : nums2[mid2];
            System.out.println(maxLeft2 + "---" + minRight2);
            // 满足交叉分割关系
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                if ((l1 + l2) % 2 == 0) {
                    return (double) (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2;
                } else {
                    return (double) Math.max(maxLeft1, maxLeft2);
                }
            } else if (maxLeft1 > minRight2) {
                r = mid1 - 1;
            } else {
                l = mid1 + 1;
            }
        }

        return -1;
    }

    private static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int max = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            while (!set.add(s.charAt(right))) {
                set.remove(s.charAt(left++));
            }
            System.out.println(left + "" + right);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    private static String util(int i) {
        if (i % 3 == 0 && i % 5 == 0) {
            return "FizzBuzz";
        } else if (i % 3 == 0) {
            return "Fizz";
        } else if (i % 5 == 0) {
            return "Buzz";
        } else {
            return i + "";
        }
    }

    public static void f(int n, boolean b) {
        g(n, 7, b);
        if (n > 1) {
            f(n / 2, true);
            System.out.println("10///");
            f(n / 2, b);
            System.out.println("12///");
        }
        if (b) {
            g(n, 15, b);
        }
    }

    public static void g(int n, int m, boolean b) {
        System.out.println(n + "---" + m + "---" + b);
    }
}