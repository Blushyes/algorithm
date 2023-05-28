public class BinaryTreeTraver {
    public static void main(String[] args) {
        TreeNode head = BinaryTree.randomTree(7);
        BinaryTree.show(head);
        BinaryTree.traver(head, BinaryTree::pre, "pre");
        BinaryTree.traver(head, BinaryTree::preIt, "preIt");
        BinaryTree.traver(head, BinaryTree::preLeftBranch1, "preLeftBranch1");
        BinaryTree.traver(head, BinaryTree::preLeftBranch2, "preLeftBranch2");
        BinaryTree.traver(head, BinaryTree::in, "in");
        BinaryTree.traver(head, BinaryTree::inIt, "inIt");
        BinaryTree.traver(head, BinaryTree::inLeftBranch, "inLeftBranch");
        BinaryTree.traver(head, BinaryTree::post, "post");
        BinaryTree.traver(head, BinaryTree::postIt, "postIt");
        BinaryTree.traver(head, BinaryTree::postItOneStack, "postItOneStack");
        BinaryTree.traver(head, BinaryTree::level, "level");
        System.out.println("maxWidthMap: " + BinaryTree.maxWidthMap(head));
        System.out.println("maxWidthNoMap: " + BinaryTree.maxWidthNoMap(head));
    }
}