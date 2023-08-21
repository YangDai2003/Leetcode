# 第四题，找出两个有序数组的中位数

<pre>
<code>
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int l1 = nums1.length, l2 = nums2.length;
        // 保证左比右小
        if (l1 > l2) {
            return findMedianSortedArrays(nums2, nums1);
        }
        // 左右下标
        int l = 0, r = l1;
        while (l <= r) {
            int mid1 = (l + r) / 2;
            int mid2 = (l1 + l2 + 1) / 2 - mid1;

            int maxLeft1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1 - 1];
            int minRight1 = (mid1 == l1) ? Integer.MAX_VALUE : nums1[mid1];

            int maxLeft2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2 - 1];
            int minRight2 = (mid2 == l2) ? Integer.MAX_VALUE : nums2[mid2];

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
}
</code>
</pre>

### 重点: 正确分割两个数组，找出左部分最大值，右部分最小值，运用 MergeSort 思想减少了合并数组的复杂度