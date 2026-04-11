package entity;

/**
 * Entity class ánh xạ với bảng tree
 */
public class Tree {
    private int nodeId;
    private String nodeName;
    private Integer parentId;
    private int level;
    
    // Constructors
    public Tree() {
    }
    
    public Tree(int nodeId, String nodeName, Integer parentId, int level) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
        this.level = level;
    }
    
    public Tree(String nodeName, Integer parentId, int level) {
        this.nodeName = nodeName;
        this.parentId = parentId;
        this.level = level;
    }
    
    // Getters and Setters
    public int getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getNodeName() {
        return nodeName;
    }
    
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    @Override
    public String toString() {
        return String.format("Tree[ID=%d, Name=%s, ParentID=%s, Level=%d]",
                nodeId, nodeName, parentId != null ? parentId : "NULL", level);
    }
}
