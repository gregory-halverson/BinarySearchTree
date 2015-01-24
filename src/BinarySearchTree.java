import javax.swing.tree.MutableTreeNode;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Gregory on 3/6/14.
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> implements Comparable<BinarySearchTree<K, V>>
{
    private BinarySearchTree<K, V> left = null;
    private BinarySearchTree<K, V> right = null;
    private K key = null;
    private V value = null;

    BinarySearchTree()
    {
        left = null;
        right = null;
        key = null;
        value = null;
    }

    BinarySearchTree(BinarySearchTree<K, V> left, BinarySearchTree<K, V> right, K key, V value)
    {
        this.left = left;
        this.right = right;
        this.key = key;
        this.value = value;
    }

    public boolean hasLeft()
    {
        return left != null;
    }

    public boolean hasRight()
    {
        return right != null;
    }

    public BinarySearchTree<K, V> getLeft()
    {
        return left;
    }

    public BinarySearchTree<K, V> getRight()
    {
        return right;
    }

    public K getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }

    protected void setLeft(BinarySearchTree<K, V> left)
    {
        this.left = left;
    }

    protected void setRight(BinarySearchTree<K, V> right)
    {
        this.right = right;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    public void insert(K key, V value)
    {
        if (this.key == null)
        {
            this.key = key;
            this.value = value;
        }
        else
            {
            int comparison = this.key.compareTo(key);

            if (comparison > 0)
            {
                if (left == null)
                    left = new BinarySearchTree<K, V>(null, null, key, value);
                else
                    left.insert(key, value);
            }
            else if (comparison == 0)
            {
                this.value = value;
            }
            else if (comparison < 0)
            {
                if (right == null)
                    right = new BinarySearchTree<K, V>(null, null, key, value);
                else
                    right.insert(key, value);
            }
        }
    }

    public V find(K key)
    {
        V result = null;

        if (this.key == null)
        {
            result = null;
        }
        else
        {
            int comparison = this.key.compareTo(key);

            if (comparison > 0)
            {
                if (left == null)
                    result = null;
                else
                    result = left.find(key);
            }
            else if (comparison == 0)
            {
                result = value;
            }
            else if (comparison < 0)
            {
                if (right == null)
                    result = null;
                else
                    result = right.find(key);
            }
        }

        return result;
    }

    public int height()
    {
        if (isEmpty())
            return 0;
        else if (isLeaf())
            return 1;
        else
            return Math.max((left == null) ? 0 : left.height(), (right == null) ? 0 : right.height()) + 1;
    }

    public boolean isEmpty()
    {
        return key == null;
    }

    public boolean isLeaf()
    {
        return left == null && right == null;
    }

    @Override
    public int compareTo(BinarySearchTree<K, V> other)
    {
        return key.compareTo(other.getKey());
    }

    public String printData()
    {
        return key + " (" + value + ")";
    }

    @Override
    public String toString()
    {
        /*
        StringWriter outputString = new StringWriter();
        PrintWriter output = new PrintWriter(outputString);

        Queue<BinarySearchTree<K, V>> queue = new LinkedList<BinarySearchTree<K, V>>();

        queue.add(this);

        while (!queue.isEmpty())
        {
            BinarySearchTree<K, V> node = queue.remove();

            output.println(node.getKey() + " (" + node.getValue() + ")");

            if (node.hasLeft())
                queue.add(node.getLeft());

            if (node.hasRight())
                queue.add(node.getRight());
        }

        return outputString.toString();
        */
        
        CompleteBinaryTree<String> output_tree = new CompleteBinaryTree<String>();

        // Level order traversal

        Queue this_level = new LinkedList<BinarySearchTree<K, V>>();
        ArrayList<BinarySearchTree<K, V>> next_level = new ArrayList<BinarySearchTree<K, V>>();
        int level_size = 1;

        // Start queue with root node
        this_level.add(this);

        // Level order traversal by queue
        while (!this_level.isEmpty())
        {
            // Pop current node from queue
            BinarySearchTree<K, V> current_node = (BinarySearchTree<K, V>)this_level.remove();

            if (current_node.isEmpty())
            {
                // Fill in dummy nodes in the output tree
                next_level.add(new BinarySearchTree<K, V>());
                next_level.add(new BinarySearchTree<K, V>());
                output_tree.insert("");
            }
            else if (current_node.isLeaf())
            {
                // Insert symbol and probability to output tree



                next_level.add(new BinarySearchTree<K, V>());
                next_level.add(new BinarySearchTree<K, V>());

                output_tree.insert(current_node.printData());
            }
            else
            {
                // Insert accumulated probability into output tree and traverse Huffman tree


                output_tree.insert(current_node.printData());

                if (current_node.getLeft() == null)
                    next_level.add(new BinarySearchTree<K, V>());
                else
                    next_level.add(current_node.getLeft());

                if (current_node.getRight() == null)
                    next_level.add(new BinarySearchTree<K, V>());
                else
                    next_level.add(current_node.getRight());
            }

            if (this_level.isEmpty())
            {
                // Check if next level is empty
                boolean empty_level = true;
                for (int i = 0; i < next_level.size(); i++)
                    if (!next_level.get(i).isEmpty())
                        empty_level = false;

                // Queue level
                if (!empty_level)
                {
                    for (int i = 0; i < next_level.size(); i++)
                    {
                        this_level.add(next_level.get(i));
                    }

                    next_level.clear();

                    level_size *= 2;
                }
            }
        }

        //return output_tree.getOutputWidthTree().printTree();

        // Render output
        return output_tree.printTree();
    }
}