#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define DEGREE 3

typedef int KEY_VALUE;

typedef struct _btree_node {
    KEY_VALUE*  keys;  // 当前结点包含的值
    struct btree_node** childrens;  // 当前结点的子树列表

    int num;    // 当前结点包含的node个数
    int leaf;   // 是不是叶子节点
}btree_node;

typedef struct _btree {
    struct btree_node* root;
    int t;
} btree;

/*
    @param t: 每个btree结点至少有t-1个关键字，至多有2t-1个关键字

*/
btree_node* btree_create_node(int t, int leaf) {
    btree_node* node = (btree_node*)calloc(1, sizeof(btree_node));
    if (node == NULL) {
        assert(0);
    }

    node->leaf = leaf;
    // 
    node->keys = (KEY_VALUE*)calloc(1, (2*t-1)*sizeof(KEY_VALUE));
    node->childrens = (btree_node**)calloc(1, (2*t)*sizeof(btree_node));
    node->num = 0;

    return node;
}






