import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import part2.RedBlackTree
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import part2.RedBlackTree.Node

class RedBlackTreeTest {

    private fun isRedBlackTreeValid(node: RedBlackTree.Node<Int>?): Boolean {
        if (node == null) return true

        // Check if the node's color is valid
        if (node.color == RedBlackTree.Color.RED && (node.left?.color == RedBlackTree.Color.RED || node.right?.color == RedBlackTree.Color.RED)) {
            return false
        }

        // Recursively check the left and right subtrees
        return isRedBlackTreeValid(node.left) && isRedBlackTreeValid(node.right)
    }

    @Test
    fun `insert and inorder traversal`() {
        val rbTree = RedBlackTree<Int>()
        val values = listOf(10, 20, 30, 15, 25, 5)

        for (value in values) {
            rbTree.insert(value)
        }

        val expectedOutput = "5, 10, 15, 20, 25, 30"
        val actualOutput = rbTree.inorderTraversal().joinToString(", ")
        assertEquals(expectedOutput, actualOutput.trim())
    }

    @Test
    fun `insert and check red-black tree property`() {
        val rbTree = RedBlackTree<Int>()
        val values = listOf(10, 20, 30, 15, 25, 5)

        for (value in values) {
            rbTree.insert(value)
            assertTrue(isRedBlackTreeValid(rbTree.root))
        }
    }

    @Test
    fun `insert and fix violation case 1`() {
        val rbTree = RedBlackTree<Int>()
        val values = listOf(50, 100, 25, 30, 27)

        for (value in values) {
            rbTree.insert(value)
        }

        assertTrue(isRedBlackTreeValid(rbTree.root))
    }

    @Test
    fun `insert and fix violation `() {
        val rbTree = RedBlackTree<Int>()
        val values = listOf(50, 100, 25, 75, 90)

        for (value in values) {
            rbTree.insert(value)
        }

        assertTrue(isRedBlackTreeValid(rbTree.root))
    }

    @Test
    fun `insert and fix violation with leafes 1`() {
        val rbTree = RedBlackTree<Int>()
        val values = listOf(50, 100, 25, 30, 12, 27, 28)

        for (value in values) {
            rbTree.insert(value)
        }

        assertTrue(isRedBlackTreeValid(rbTree.root))
    }

    @Test
    fun `insert and fix violation with leafes`() {
        val rbTree = RedBlackTree<Int>()
        val values = listOf(50, 100, 25, 75, 150, 80, 77)

        for (value in values) {
            rbTree.insert(value)
        }

        assertTrue(isRedBlackTreeValid(rbTree.root))

        for (value in values) {
            rbTree.remove(value)
        }
        assertTrue(rbTree.root == null)
    }

    @Test
    fun deleteNormal() {
        val rbTree = RedBlackTree<Int>()
        rbTree.insert(1)
        rbTree.remove(1)
        assertNull(rbTree.root)

        for (i in 1..100)
            rbTree.insert(i)

        for (i in 30..60)
            rbTree.remove(i)

        for (i in 1..100) {
            if (i in 30..60)
                assertFalse(rbTree.find(i) != null)
            else
                assertTrue(rbTree.find(i) != null)
        }
    }

    @Test
    fun deleteNormalReverse() {
        val rbTree = RedBlackTree<Int>()
        rbTree.insert(100)
        rbTree.remove(100)
        assertNull(rbTree.root)

        for (i in 1..100)
            rbTree.insert(100 - i + 1)

        for (i in 30..60)
            rbTree.remove(100 - i + 1)

        for (i in 1..100) {
            if (i in 30..60)
                assertFalse(rbTree.find(100 - i + 1) != null)
            else
                assertTrue(rbTree.find(100 - i + 1) != null)
        }
    }

    @Test
    fun `test deletion triggering else clause in fixRemoval`() {
        val rbTreeLeft = RedBlackTree<Int>()
        val valuesLeft = listOf(10, 5, 20, 18, 25, 22)
        valuesLeft.forEach { rbTreeLeft.insert(it) }
        rbTreeLeft.remove(18)

        assertTrue(isRedBlackTreeValid(rbTreeLeft.root))

        val rbTreeRight = RedBlackTree<Int>()
        val valuesRight = listOf(50, 55, 45, 47, 40, 43)
        valuesRight.forEach { rbTreeRight.insert(it) }
        rbTreeRight.remove(47)

        assertTrue(isRedBlackTreeValid(rbTreeRight.root))
    }

    @Test
    fun `delete from empty tree` (){
        val rbTree = RedBlackTree<Int>()
        rbTree.remove(1)
        assertNull(rbTree.root)
    }
}