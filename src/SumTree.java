/**
 * Created by Gregory on 2/19/14.
 */
public class SumTree extends CompleteBinaryTree<Integer>
{
    SumTree()
    {
        super();
    }

    int compareSum(int index)
    {
        if (hasChildren(index))
        {
            Integer sum = 0;

            if (hasLeft(index))
                sum += leftValue(index);

            if (hasRight(index))
                sum += rightValue(index);

            //System.out.println("value(index): " + value(index) + " sum: " + sum + " value(index) - sum: " + (value(index) - sum));

            return value(index) - sum;
        }
        else
        {
            return 0;
        }
    }

    public void insert(Integer value)
    {
        super.insert(value);
        siftUp(size);
    }

    public void changeValue(int index, Integer value)
    {
        setValue(index, value);
        siftUp(index);
        siftDown(index);
    }

    protected void siftUp(int index)
    {
        while (hasParent(index) && compareSum(parentIndex(index)) < 0)
        {
            //System.out.println("compareSum(parentIndex(index)): " + compareSum(parentIndex(index)));

            setParent(index, value(index) + (hasSibling(index) ? siblingValue(index) : 0));

            index = parentIndex(index);
        }

        if (hasParent(index))
            if (compareSum(parentIndex(index)) > 0)
                siftDown(parentIndex(index));
    }

    protected void siftDown(int index)
    {
        int difference = compareSum(index);

        //System.out.println("difference: " + difference);

        if (difference > 0)
        {
            if (hasLeft(index) && hasRight(index))
            {
                int leftDifference = difference / 2;
                int rightDifference = difference - leftDifference;
                setLeft(index, leftValue(index) + leftDifference);
                setRight(index, rightValue(index) + rightDifference);
                siftDown(leftIndex(index));
                siftDown(rightIndex(index));
            }
            else if (hasLeft(index) && !hasRight(index))
            {
                setLeft(index, value(index));
            }
            else if (!hasLeft(index) && hasRight(index))
            {
                setRight(index, value(index));
            }
        }
    }
}
