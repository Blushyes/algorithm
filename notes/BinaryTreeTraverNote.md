# 二叉树遍历

## 什么是遍历？

按照某种**特定的规则**、次序依次对每个节点进行访问。

## 为什么要遍历？

可以将二叉树这个半线性化的结构转化为线性结构，对结构进行简化，更方便。

## 有哪些遍历方式？

+ 先序
+ 中序
+ 后序
+ 层次

## 树节点

~~~java
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {}

    public TreeNode(int val) {
        this.val = val;
    }
}
~~~

## 先序

### 递归实现

~~~java
public static void pre(TreeNode head) {
    if (head == null) {
        return;
    }
    visit(head);
    pre(head.left);
    pre(head.right);
}
~~~

+ 一个退化情况处理（递归基）
+ 递归实现左子树和右子树遍历（注意这个是尾递归）

### 迭代实现1

~~~java
public static void preIt(TreeNode head) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(head);
    while (!stack.isEmpty()) {
        // 先打印根节点
        TreeNode node = stack.pop();
        visit(node);

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
~~~

### 迭代实现2（左侧链实现）

~~~java
public static void preLeftBranch1(TreeNode head) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    while (head != null) {  // 沿着左侧链下行
        visit(head);
        if (head.right != null) {
            stack.push(head.right);
        }
        head = head.left;
        // 当左侧链到底，沿着右侧链上行
        if (head == null && !stack.isEmpty()) {
            head = stack.pop();
        }
    }
}
~~~

### 迭代实现3（左侧链实现，清晰版）

~~~java
public static void preLeftBranch2(TreeNode head) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    while (true) {  // 不断地
        visitAloneLeftBranch(head, stack);
        if (stack.isEmpty()) {
            break;
        }
        head = stack.pop();
    }
}

// 沿着左侧链访问
private static void visitAloneLeftBranch(TreeNode head, Deque<TreeNode> stack) {
    while (head != null) {
        visit(head);
        if (head.right != null) {
            stack.push(head.right);
        }
        head = head.left;
    }
}
~~~

## 中序

### 递归实现

~~~java
public static void in(TreeNode head) {
    if (head == null) {
        return;
    }
    in(head.left);
    visit(head);
    in(head.right);
}
~~~

### 迭代实现1

~~~java
public static void inIt(TreeNode head) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    if (head != null) {
        while (!stack.isEmpty() || head != null) {
            if (head != null) { // 先把所有的左子全压入栈中
                stack.push(head);
                head = head.left;
            } else {    // 当压不动了的时候（即head此时为空），则开始出栈并打印，然后来到右孩子处
                head = stack.pop();
                visit(head);
                head = head.right;
            }
        }
    }
}
~~~

### 迭代实现2

~~~java
public static void inLeftBranch(TreeNode head) {
    Deque<TreeNode> stack1 = new ArrayDeque<>();
    Deque<TreeNode> stack2 = new ArrayDeque<>();
    while (head != null) {
        if (head.right != null) {
            stack2.push(head.right);
        }
        stack1.push(head);
        head = head.left;
        if (head == null) {
            while (!stack1.isEmpty()) {
                TreeNode node = stack1.pop();
                visit(node);
            }
            if (!stack2.isEmpty()) {
                head = stack2.pop();
            }
        }
    }
}
~~~

## 后序遍历

### 递归实现

~~~java
public static void post(TreeNode head) {
    if (head == null) {
        return;
    }
    post(head.left);
    post(head.right);
    visit(head);
}
~~~

### 迭代实现1

~~~java
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
~~~

### 迭代实现2

~~~java
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
~~~

## 层次遍历

~~~java
public static void level(TreeNode head) {
    Deque<TreeNode> queue = new ArrayDeque<>();
    queue.offer(head);
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        visit(node);
        if (node.left != null) {
            queue.offer(node.left);
        }
        if (node.right != null) {
            queue.offer(node.right);
        }
    }
}
~~~