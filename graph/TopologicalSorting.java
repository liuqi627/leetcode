/**
 lintcode
 题目是要找出这个图的一个topological order，这个图每个点想成一个工序，工序之间有先后依赖。
 输入：图，这图是个list，list的每个元素是是个node，每个node里有自己的label以及他的儿子们。
 输出：topological order， 就是一个node的arraylist
 解题关键：求出每个节点的入度，下一个工序是当前入度为0的工序（当前总是会有一个或多个入度为0的工序），从根节点层序遍历，把入度为0的放到result里，当遍历完了所有的节点的时候，返回result即可
 流程：1.生成入度map 2.找到头结点，建一个queue(linkedlist)，把头结点扔到queue里 3.层序遍历，访问完了(poll)把这个节点扔到result里并把他关联的子节点的入度都减一，把当前入度为0的节点进队列(offer)，直到队列为空。注意，在每次poll完之后，那个被poll完的node的儿子里面总有一个儿子是入度为0的。否则他就不是有向无环图，只有有向无环图(DAG)才有拓扑排序
 参考资料：http://songlee24.github.io/2015/05/07/topological-sorting/
 关于有向无环图(DAG)的解释:
 graph = structure consisting of nodes, that are connected to each other with edges
 
 directed = the connections between the nodes (edges) have a direction: A -> B is not the same as B -> A
 
 acyclic = "non-circular" = moving from node to node by following the edges, you will never encounter the same node for the second time.
 
 A good example of a directed acyclic graph is a tree. Note, however, that not all directed acyclic graphs are trees.
**/

/**
 * Definition for Directed graph.
 * class DirectedGraphNode {
 *     int label;
 *     ArrayList<DirectedGraphNode> neighbors;
 *     DirectedGraphNode(int x) { label = x; neighbors = new ArrayList<DirectedGraphNode>(); }
 * };
 */
public class Solution {
    /**
     * @param graph: A list of Directed graph node
     * @return: Any topological order for the given graph.
     */    
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // write your code here
        ArrayList<DirectedGraphNode> result = new ArrayList<DirectedGraphNode>();
        HashMap<DirectedGraphNode, Integer> map = new HashMap<DirectedGraphNode, Integer>();
        for(DirectedGraphNode node : graph){
            for(DirectedGraphNode inner : node.neighbors){
                if(!map.containsKey(inner)){
                    map.put(inner, 1);
                }
                else{
                    map.put(inner,map.get(inner)+1);
                }
            }
        }
        
        Queue<DirectedGraphNode> queue = new LinkedList<DirectedGraphNode>();
        for(DirectedGraphNode node : graph){
            if(!map.containsKey(node)){
                queue.offer(node);
            }
        }
        
        while(!queue.isEmpty()){
            DirectedGraphNode dgn = queue.poll();
            result.add(dgn);
            for(DirectedGraphNode node : dgn.neighbors){
                map.put(node, map.get(node)-1);
                if(map.get(node) == 0){
                    queue.offer(node);
                }
            }
        }
        return result;
    }
}
