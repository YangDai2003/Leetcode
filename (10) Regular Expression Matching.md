# 第十题，正则表达式，hard难度

<pre>
<code>
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
</code>
</pre>

<p>
重点: 使用了一个二维布尔数组 M 来表示字符串 s 的前 i 个字符是否能够匹配模式字符串 p 的前 j 个字符。
通过初始化和归纳规则来填充二维布尔数组 M，最终返回 M[m][n]，即字符串 s 的所有字符是否能够匹配模式字符串 p 的所有字符。
</p>  