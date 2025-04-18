package part2

class RedBlackTree<T : Comparable<T>> {

    var root: Node<T>? = null

    enum class Color {
        RED, BLACK
    }

    data class Node<T>(
        var data: T,
        var color: Color,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
        var parent: Node<T>? = null
    )

    fun insert(data: T) {
        val newNode = Node(data, Color.RED)
        root = bstInsert(root, newNode)
        fixViolation(newNode)
    }

    private fun bstInsert(root: Node<T>?, newNode: Node<T>): Node<T>? {
        if (root == null) return newNode

        if (newNode.data < root.data) {
            root.left = bstInsert(root.left, newNode)
            root.left!!.parent = root
        } else {
            root.right = bstInsert(root.right, newNode)
            root.right!!.parent = root
        }
        return root
    }

    private fun fixViolation(node: Node<T>) {
        var currNode = node
        while (currNode.parent?.color == Color.RED) {
            var parent = currNode.parent!!
            val grandparent = parent.parent!!

            if (parent == grandparent.left) {
                val uncle = grandparent.right
                if (uncle?.color == Color.RED) {
                    // Case 1: Parent and uncle are red
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandparent.color = Color.RED
                    currNode = grandparent
                } else {
                    if (currNode == parent.right) {
                        // Case 2: Parent is red, uncle is black, and current node is right child
                        rotateLeft(parent)
                        currNode = parent
                        parent = currNode.parent!!
                    }
                    // Case 3: Parent is red, uncle is black, and current node is left child
                    rotateRight(grandparent)
                    parent.color = Color.BLACK
                    grandparent.color = Color.RED
                    currNode = parent
                    break
                }
            } else {
                val uncle = grandparent.left
                if (uncle?.color == Color.RED) {
                    // Case 1: Parent and uncle are red
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandparent.color = Color.RED
                    currNode = grandparent
                } else {
                    if (currNode == parent.left) {
                        // Case 2: Parent is red, uncle is black, and current node is left child
                        rotateRight(parent)
                        currNode = parent
                        parent = currNode.parent!!
                    }
                    // Case 3: Parent is red, uncle is black, and current node is right child
                    rotateLeft(grandparent)
                    parent.color = Color.BLACK
                    grandparent.color = Color.RED
                    currNode = parent
                    break
                }
            }
        }
        root!!.color = Color.BLACK
    }

    private fun rotateLeft(node: Node<T>) {
        val rightChild = node.right!!
        node.right = rightChild.left
//        if (rightChild.left != null) {
            rightChild.left?.parent = node
//        }
        rightChild.parent = node.parent
        if (node.parent == null) {
            root = rightChild
        } else if (node == node.parent!!.left) {
            node.parent!!.left = rightChild
        } else {
            node.parent!!.right = rightChild
        }
        rightChild.left = node
        node.parent = rightChild
    }

    private fun rotateRight(node: Node<T>) {
        val leftChild = node.left!!
        node.left = leftChild.right
//        if (leftChild.right != null) {
            leftChild.right?.parent = node
//        }
        leftChild.parent = node.parent
        if (node.parent == null) {
            root = leftChild
        } else if (node == node.parent!!.right) {
            node.parent!!.right = leftChild
        } else {
            node.parent!!.left = leftChild
        }
        leftChild.right = node
        node.parent = leftChild
    }

    fun inorderTraversal(node: Node<T>? = root, result: MutableList<T> = mutableListOf()): List<T> {
        if (node != null) {
            inorderTraversal(node.left, result)
            result.add(node.data)
            inorderTraversal(node.right, result)
        }
        return result
    }

    fun remove(data: T) {
        var node = findNode(data) ?: return
        var fixNeeded = node.color == Color.BLACK

        if (node.left != null && node.right != null) {
            val successor = findMinNode(node.right!!)
            node.data = successor.data
            node = successor
            fixNeeded = node.color == Color.BLACK
        }

        // Fix the tree before detaching the node
        if (fixNeeded) {
            fixRemoval(node)
        }

        val child = node.left ?: node.right
        val parent = node.parent

        // Detach the node from the tree
        child?.parent = parent
        if (parent == null) {
            root = child
        } else {
            if (node === parent.left) {
                parent.left = child
            } else {
                parent.right = child
            }
        }
    }

    private fun fixRemoval(node: Node<T>) {
        var current: Node<T>? = node
        while (current != root && current!!.color == Color.BLACK) {
//            current ?: break
            val parent = current.parent!!

            val isLeftChild = parent.left === current
            var sibling = (if (isLeftChild) parent.right else parent.left)!!

            // Case 1: Sibling is red
            if (sibling.color == Color.RED) {
                sibling.color = Color.BLACK
                parent.color = Color.RED
                if (isLeftChild) rotateLeft(parent) else rotateRight(parent)
                sibling = (if (isLeftChild) parent.right else parent.left)!!
            }

            // Check sibling's children colors
            val siblingLeftColor = if (sibling.left != null) sibling.left!!.color else Color.BLACK
            val siblingRightColor = if (sibling.right != null) sibling.right!!.color else Color.BLACK

            if (siblingLeftColor == Color.BLACK && siblingRightColor == Color.BLACK) {
                // Case 2: Sibling and both nephews are black
                sibling.color = Color.RED
                current = parent
            } else {
                if (isLeftChild) {
                    // Case 3: Sibling black, left nephew red, right nephew black
                    if (siblingRightColor == Color.BLACK) {
                        sibling!!.left!!.color = Color.BLACK
                        sibling.color = Color.RED
                        rotateRight(sibling)
                        // Re-fetch sibling after rotation and restart loop
                        continue
                    }
                    // Case 4: Sibling black, right nephew red
                    sibling!!.color = parent.color
                    parent.color = Color.BLACK
                    sibling.right!!.color = Color.BLACK
                    rotateLeft(parent)
                } else {
                    // Case 3: Sibling black, right nephew red, left nephew black
                    if (siblingLeftColor == Color.BLACK) {
                        sibling!!.right!!.color = Color.BLACK
                        sibling.color = Color.RED
                        rotateLeft(sibling)
                        // Re-fetch sibling after rotation and restart loop
                        continue
                    }
                    // Case 4: Sibling black, left nephew red
                    sibling!!.color = parent.color
                    parent.color = Color.BLACK
                    sibling.left!!.color = Color.BLACK
                    rotateRight(parent)
                }
                current = root // Exit loop after Case 4
            }
        }
        current!!.color = Color.BLACK
    }

    private fun findMinNode(node: Node<T>): Node<T> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    fun find(data: T): T? {
        return findNode(data)?.data
    }

    private fun findNode(data: T): Node<T>? {
        var current = root
        while (current != null) {
            when {
                data < current.data -> current = current.left
                data > current.data -> current = current.right
                else -> return current
            }
        }
        return null
    }
}