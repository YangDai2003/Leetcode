# 第二题，将两个链表中的元素相加
### Example: Input: l1 = [2,4,3], l2 = [5,6,4]
### Output: [7,0,8]
### Explanation: 342 + 465 = 807.

<pre>
<code>
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = new ListNode(0);
        int carry = 0; 
        while(l1 != null || l2 != null || carry == 1){
            int sum = 0;
            if(l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            sum += carry;
            carry = sum/10;
            ListNode node = new ListNode(sum % 10);
            curr.next = node;
            curr = curr.next;
        }
        return dummy.next;
    }
}
</code>
</pre>

### 重点: 实现进位，链表从个位逆序存储，对应了加法从个位开始运算