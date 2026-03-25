package ASM.model;

/**
 * Enum dinh nghia cac trang thai cua ban sao tai lieu
 */
public enum CopyStatus {
    GOOD("Con tot"),           // Ban sao con tot
    DAMAGED("Hu hong"),        // Ban sao bi hu hong
    LOST("Mat");               // Ban sao bi mat
    
    private final String description;
    
    // Constructor
    CopyStatus(String description) {
        this.description = description;
    }
    
    // Lay mo ta trang thai
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return description;
    }
}
