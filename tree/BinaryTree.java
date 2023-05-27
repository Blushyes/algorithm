import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class BinaryTree {
    // 创建有n个节点的随机二叉树
    public static TreeNode randomTree(int n) {
        // 创建随机内容
        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            nums.add(i + 1);
        }
        Collections.shuffle(nums);

        // map用来记录是否还有孩子节点
        TreeNode head = new TreeNode(nums.remove(0));
        Map<TreeNode, Boolean> map = new HashMap<>();
        map.put(head, true);

        // 加入 n - 1 个节点
        for (int i = 0; i < n - 1; i++) {
            List<Map.Entry<TreeNode, Boolean>> list = new ArrayList<>(map.entrySet().stream().toList());
            Collections.shuffle(list);
            Map.Entry<TreeNode, Boolean> entry = list.get(0);
            TreeNode node = entry.getKey();
            boolean hasChild = entry.getValue();

            // 如果没有孩子那就重新选择
            while (!hasChild) {
                list.remove(0);
                Collections.shuffle(list);
                entry = list.get(0);
                hasChild = entry.getValue();
                node = entry.getKey();
            }

            // 如果有孩子节点才进行创建
            TreeNode child = null;
            if (node.left == null && node.right == null) {  // 如果左右孩子都为空则随机选取一个孩子节点创建新节点
                int randomInt = new Random().nextInt(2);
                child = randomInt == 0 ?
                        (node.left = new TreeNode(nums.remove(0))) :
                        (node.right = new TreeNode(nums.remove(0)));
            } else {    // 否则直接选为空的那个
                child = node.left == null ?
                        (node.left = new TreeNode(nums.remove(0))) :
                        (node.right = new TreeNode(nums.remove(0)));
            }
            map.put(child, true);   // 将孩子节点加入标记map
            if (node.left != null && node.right != null) {  // 如果已经没有孩子节点了那么进行标记false
                map.put(node, false);
            }
        }
        return head;
    }

    public static void traver(TreeNode head, Consumer<TreeNode> traverConsumer, String title) {
        System.out.print(title + ": ");
        traverConsumer.accept(head);
        System.out.println();
    }

    // 先序遍历
    public static void pre(TreeNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.val + " ");
        pre(head.left);
        pre(head.right);
    }

    // 中序遍历
    public static void in(TreeNode head) {
        if (head == null) {
            return;
        }
        in(head.left);
        System.out.print(head.val + " ");
        in(head.right);
    }

    // 后序遍历
    public static void post(TreeNode head) {
        if (head == null) {
            return;
        }
        post(head.left);
        post(head.right);
        System.out.print(head.val + " ");
    }

    // 先序遍历迭代版
    public static void preIt(TreeNode head) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            // 先打印根节点
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");

            // 两个孩子依次入栈
            // 因为先进后出，所以要先进右孩子
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    // 中序遍历迭代版
    public static void inIt(TreeNode head) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (head != null) {
            while (!stack.isEmpty() || head != null) {
                if (head != null) { // 先把所有的左子全压入栈中
                    stack.push(head);
                    head = head.left;
                } else {    // 当压不动了的时候（即head此时为空），则开始出栈并打印，然后来到右孩子处
                    head = stack.pop();
                    System.out.print(head.val + " ");
                    head = head.right;
                }
            }
        }
    }

    // 后序遍历迭代版
    public static void postIt(TreeNode head) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> turnStack = new ArrayDeque<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            // 这里只是进入反转栈，而不是打印
            turnStack.push(node.val);

            // 先序是：center left right
            // 那么将下面步骤反转可以获得：center right left
            // 再将这个翻转可获得：left right center 即为后序遍历
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        // 然后依次取出打印反转栈
        while (!turnStack.isEmpty()) {
            Integer val = turnStack.pop();
            System.out.print(val + " ");
        }
    }

    // 后序遍历迭代版单栈版本
    public static void postItOneStack(TreeNode head) {
        // p追踪每次打印的q，q则按顺序跳动
        TreeNode p = head, q = head;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            // 注意如果q要继续往左需要满足上一次没有遍历到左而且上一次没有遍历到右
            // 特别需要注意上一次没有遍历到右的情况
            if (q.left != null && p != q.left && p != q.right) {
                stack.push(q);
                q = q.left;
            } else if (q.right != null && p != q.right) {
                stack.push(q);
                q = q.right;
            } else {
                System.out.print(q.val + " ");
                p = q;
                q = stack.pop();
            }
        }
    }

    // 层次遍历
    public static void level(TreeNode head) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(head);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    // 找出节点最多的层
    public static int findMaxLevelNodes(TreeNode head) {
        Map<TreeNode, Integer> map = new HashMap<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        map.put(head, 1);
        queue.offer(head);
        int curLevel = 1;
        int curNodes = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (map.get(node) != curLevel) {
                curLevel = map.get(node);
                max = Math.max(max, curNodes);
                curNodes = 1;
            } else {
                curNodes += 1;
            }
            if (node.left != null) {
                queue.offer(node.left);
                map.put(node.left, map.get(node) + 1);
            }
            if (node.right != null) {
                queue.offer(node.right);
                map.put(node.right, map.get(node) + 1);
            }
        }
        return max;
    }

    // 用于获得树的层数
    public static int getTreeDepth(TreeNode root) {
        return root == null ? 0 : (1 + Math.max(getTreeDepth(root.left), getTreeDepth(root.right)));
    }

    private static void writeArray(TreeNode currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
        // 保证输入的树不为空
        if (currNode == null) return;
        // 先将当前节点保存到二维数组中
        res[rowIndex][columnIndex] = String.valueOf(currNode.val);

        // 计算当前位于树的第几层
        int currLevel = ((rowIndex + 1) / 2);
        // 若到了最后一层，则返回
        if (currLevel == treeDepth) return;
        // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        int gap = treeDepth - currLevel - 1;

        // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
        if (currNode.left != null) {
            res[rowIndex + 1][columnIndex - gap] = "/";
            writeArray(currNode.left, rowIndex + 2, columnIndex - gap * 2, res, treeDepth);
        }

        // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
        if (currNode.right != null) {
            res[rowIndex + 1][columnIndex + gap] = "\\";
            writeArray(currNode.right, rowIndex + 2, columnIndex + gap * 2, res, treeDepth);
        }
    }

    public static void show(TreeNode root) {
        if (root == null) System.out.println("EMPTY!");
        // 得到树的深度
        int treeDepth = getTreeDepth(root);

        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        // 作为整个二维数组的宽度
        int arrayHeight = treeDepth * 2 - 1;
        int arrayWidth = (2 << (treeDepth - 2)) * 3 + 1;
        // 用一个字符串数组来存储每个位置应显示的元素
        String[][] res = new String[arrayHeight][arrayWidth];
        // 对数组进行初始化，默认为一个空格
        for (int i = 0; i < arrayHeight; i ++) {
            for (int j = 0; j < arrayWidth; j ++) {
                res[i][j] = " ";
            }
        }

        // 从根节点开始，递归处理整个树
        // res[0][(arrayWidth + 1)/ 2] = (char)(root.val + '0');
        writeArray(root, 0, arrayWidth/ 2, res, treeDepth);

        // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
        for (String[] line: res) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length; i ++) {
                sb.append(line[i]);
                if (line[i].length() > 1 && i <= line.length - 1) {
                    i += line[i].length() > 4 ? 2: line[i].length() - 1;
                }
            }
            System.out.println(sb.toString());
        }
    }
}
