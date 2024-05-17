#include <iostream>
#include <string>
using namespace std;
void display();
class Node {
public:
    int data;
    Node* next;

    // Constructor
    Node(int id) {
        data = id;
        next = nullptr;
    }
};

// Linked List class
class LinkedList {
private:
    Node* head;

public:
    // Constructor
    LinkedList() {
        head = nullptr;
    }
    // Function to clear the linked list
    void clearLinkedList(LinkedList& list) {
        Node* current = list.head;
        while (current != nullptr) {
            Node* next = current->next;
            delete current;
            current = next;
        }
        list.head = nullptr;
    }

    // Function to add a node to the linked list
    void addNode(int id) {
        Node* newNode = new Node(id);
        if (head == nullptr) {
            head = newNode;
        } else {
            Node* temp = head;
            while (temp->next != nullptr) {
                temp = temp->next;
            }
            temp->next = newNode;
        }
    }

    // Function to display the linked list
    void display() {
        Node* temp = head;
        while (temp != nullptr) {
            cout << temp->data << " ";
            temp = temp->next;
        }
        cout << endl;
    }

    // Function to check if the linked list is empty
    bool isEmpty() {
        return head == nullptr;
    }
};

// Binary Search Tree Node
class TreeNode {
public:
    int id;
    int difficulty;
    TreeNode* left;
    TreeNode* right;

    // Constructor
    TreeNode(int id, int difficulty) {
        this->id = id;
        this->difficulty = difficulty;
        left = nullptr;
        right = nullptr;
    }
};

// Binary Search Tree class
class BST {
private:
    TreeNode* root;

    // Helper function to insert a node recursively
    TreeNode* insertRec(TreeNode* node, int id, int difficulty) {
        // If the tree is empty, create a new node
        if (node == nullptr) {
            return new TreeNode(id, difficulty);
        }

        // Otherwise, recur down the tree
        if (id < node->id) {
            node->left = insertRec(node->left, id, difficulty);
        } else if (id > node->id) {
            node->right = insertRec(node->right, id, difficulty);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // Helper function to traverse the tree in preorder and store IDs in a linked list
    void preorderTraversal(TreeNode* node, LinkedList& list) {
        if (node != nullptr) {
            list.addNode(node->id);
            preorderTraversal(node->left, list);
            preorderTraversal(node->right, list);
        }
    }
    bool searchRec(TreeNode* node, int id) {
        // If the tree is empty, return false
        if (node == nullptr) {
            return false;
        }

        // If the ID matches the current node's ID, return true
        if (node->id == id) {
            return true;
        }

        // If the ID is smaller than the current node's ID, search in the left subtree
        if (id < node->id) {
            return searchRec(node->left, id);
        }

        // If the ID is larger than the current node's ID, search in the right subtree
        return searchRec(node->right, id);
    }
    void clearBSTRec(TreeNode* node) {
        if (node != nullptr) {
            clearBSTRec(node->left);
            clearBSTRec(node->right);
            delete node;
        }
    }
    // Helper function to remove a node recursively
    TreeNode* removeRec(TreeNode* root, int key) {
        // Base case: If the tree is empty
        if (root == nullptr) {
            return root;
        }

        // Recur down the tree
        if (key < root->id) {
            root->left = removeRec(root->left, key);
        } else if (key > root->id) {
            root->right = removeRec(root->right, key);
        } else {
            // Node with only one child or no child
            if (root->left == nullptr) {
                TreeNode* temp = root->right;
                delete root;
                return temp;
            } else if (root->right == nullptr) {
                TreeNode* temp = root->left;
                delete root;
                return temp;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            TreeNode* temp = minValueNode(root->right);

            // Copy the inorder successor's content to this node
            root->id = temp->id;
            root->difficulty = temp->difficulty;

            // Delete the inorder successor
            root->right = removeRec(root->right, temp->id);
        }
        return root;
    }

    // Helper function to find the minimum value node in a subtree
    TreeNode* minValueNode(TreeNode* node) {
        TreeNode* current = node;

        // Loop down to find the leftmost leaf
        while (current && current->left != nullptr) {
            current = current->left;
        }

        return current;
    }

public:
    // Constructor
    BST() {
        root = nullptr;
    }
    // Function to clear the binary search tree
    void clearBST(BST& tree) {
        clearBSTRec(tree.root);
        tree.root = nullptr;
    }

    bool search(int id) {
        return searchRec(root, id);
    }
    // Function to insert a node
    void insert(int id, int difficulty) {
        root = insertRec(root, id, difficulty);
    }

    // Function to traverse the tree in preorder and store IDs in a linked list
    void preorderTraversal(LinkedList& list) {
        preorderTraversal(root, list);
    }

    // Function to remove a node with the given ID
    void remove(int id) {
        root = removeRec(root, id);
    }

    // Function to check if the binary search tree is empty
    bool isEmpty() {
        return root == nullptr;
    }
};
BST userStories;
LinkedList completedTasks;
void productowner(){
    cout<<"Hello, Product owner "<<endl;
    cout<<"Please enter the id of the task then the difficulty "<<endl;
    cout<<"if you want to exit in any time just enter 0 "<<endl;
    while(true){
        int id;cin>>id;
        if(id==0){
            break;
        }
        int difficulty;
        cin>>difficulty;
        userStories.insert(id,difficulty);
    }
    display();
}
void developer(){
    if(userStories.isEmpty()){
        cout<<"no user stories to show"<<endl;
        display();
    }
    cout<<"if you want to show the available user stories enter 1"<<endl;
    cout<<"if you want to exit enter 2"<<endl;
    int select;cin>>select;
    if (select==1){
    LinkedList list;
    userStories.preorderTraversal(list);
    list.display();
    cout<<"if you want to choose any user story enter it is number"<<endl;
    cout<<"if you want to exit enter 2"<<endl;
    int x;
    cin>>x;
    if(x==0){
        display();
    }
    else{
      if(userStories.search(x)){
          completedTasks.addNode(x);
          userStories.remove(x);
          cout<<"DONE"<<endl;
          display();
      }
      else{
          cout<<"Valid id"<<endl;
          developer();
      }
    }
    }
    else if(select == 2) {
        display();
    } else{
        cout<<"Enter a valid number"<<endl;
        developer();
    }

}
void productmanager(){
cout<<"Hello Manager"<<endl;
cout<<"Now you can view if there is a completed task or not "<<endl;
cout<<endl;
cout<<"if ypu want to know if there is a completed tasks and view it enter 1"<<endl;
cout<<"if you want to exit enter 2"<<endl;
int x;
cin>>x;
if(x==2){
    display();
}
else if(x==1){
    if(completedTasks.isEmpty()){
        cout<<"NO tasks for view"<<endl;
        display();
    }
    else{
        completedTasks.display();
        cout<<"The sprint has ended with these tasks good bay"<<endl;
        completedTasks.clearLinkedList(completedTasks);
        userStories.clearBST(userStories);
        display();
    }
} else{
    cout<<"Not a valid number"<<endl;
    productmanager();
}
}
void display(){
    while(true) {
        cout << "if you are a product owner enter 1" << endl;
        cout << "if you are a  developer enter 2" << endl;
        cout << "if you are a product manager enter 3" << endl;
        int selection;
        cin >> selection;
        if (selection == 1) {
            productowner();
            break;
        }
        else if (selection== 2){
            developer();
            break;
        }
        else if(selection == 3){
            productmanager();
            break;
        }
        else {
            cout<<"Please enter a valid number"<<endl;
        }
    }
}
int main() {
    display();
    return 0;
}
