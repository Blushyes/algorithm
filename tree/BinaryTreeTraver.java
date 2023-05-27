public class BinaryTreeTraver {
    public static void main(String[] args) {
        TreeNode head = BinaryTree.randomTree(5);
        BinaryTree.show(head);
        BinaryTree.traver(head, BinaryTree::pre, "pre");
        BinaryTree.traver(head, BinaryTree::in, "in");
        BinaryTree.traver(head, BinaryTree::post, "post");
        BinaryTree.traver(head, BinaryTree::preIt, "preIt");
        BinaryTree.traver(head, BinaryTree::inIt, "inIt");
        BinaryTree.traver(head, BinaryTree::postIt, "postIt");
        BinaryTree.traver(head, BinaryTree::postItOneStack, "postItOneStack");
        BinaryTree.traver(head, BinaryTree::level, "level");
        System.out.println("maxLevelNodes: " + BinaryTree.findMaxLevelNodes(head));
    }
}