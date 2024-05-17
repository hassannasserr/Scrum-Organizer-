package com.example.datastrucure;

import java.util.HashSet;
import java.util.Set;

class BSTNode {
    UserStory data;
    BSTNode left, right;

    public BSTNode(UserStory data) {
        this.data = data;
        left = right = null;
    }
}

public class BinarySearchTree {
    BSTNode root;

    public void insert(UserStory data) {
        root = insertRec(root, data);
    }

    private BSTNode insertRec(BSTNode root, UserStory data) {
        if (root == null) {
            root = new BSTNode(data);
            return root;
        }
        if (data.story.compareTo(root.data.story) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.story.compareTo(root.data.story) > 0) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }

    public Set<UserStory> inOrderTraversal() {
        Set<UserStory> result = new HashSet<>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(BSTNode root, Set<UserStory> result) {
        if (root != null) {
            inOrderRec(root.left, result);
            result.add(root.data);
            inOrderRec(root.right, result);
        }
    }
}
