/**
 * Created by Gregory on 3/6/14.
 */
public class TestTree
{
    public static void main(String[] args)
    {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<Integer, String>();

        tree.insert(5, "five");
        tree.insert(3, "three");
        tree.insert(7, "seven");
        tree.insert(6, "six");
        tree.insert(8, "eight");
        tree.insert(4, "four");


        System.out.print(tree);
    }
}
