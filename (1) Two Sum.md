# 第一题，寻找数组中和为 target 的两个数
## 暴力方法
<pre><code>
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++){
            
                for (int j = i + 1; j < nums.length; j++){
                    if (nums[i] + nums[j] == target){
                        return new int[]{i, j};
                    }
                }
            
        }
        return new int[2];
    }
}
</code></pre>

## 优化版本
<pre><code>
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (numMap.containsKey(complement)) {
                return new int[]{numMap.get(complement), i};
            }
            numMap.put(nums[i], i);
        }
        return new int[2];
    }
}
</code></pre>

## 重点: 通过 HashMap 储存补集，查找已经遍历的数的补集