package ASM.util;

import ASM.model.Document;
import ASM.model.Copy;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Lop quan ly viec luu va doc du lieu tu file
 * Su dung ObjectInputStream va ObjectOutputStream
 */
public class FileManager {
    private static final String DOCUMENT_FILE = "documents.dat";
    private static final String COPY_FILE = "copies.dat";
    
    /**
     * Luu danh sach tai lieu vao file
     */
    public static void saveDocuments(Map<String, Document> documents) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DOCUMENT_FILE))) {
            oos.writeObject(documents);
            System.out.println("Da luu " + documents.size() + " tai lieu vao file.");
        } catch (IOException e) {
            System.err.println("Loi khi luu tai lieu: " + e.getMessage());
        }
    }
    
    /**
     * Doc danh sach tai lieu tu file
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Document> loadDocuments() {
        File file = new File(DOCUMENT_FILE);
        if (!file.exists()) {
            System.out.println("File tai lieu chua ton tai. Tao du lieu moi.");
            return new HashMap<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DOCUMENT_FILE))) {
            Map<String, Document> documents = (Map<String, Document>) ois.readObject();
            System.out.println("Da tai " + documents.size() + " tai lieu tu file.");
            return documents;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Loi khi doc tai lieu: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    /**
     * Luu danh sach ban sao vao file
     */
    public static void saveCopies(Map<String, Copy> copies) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(COPY_FILE))) {
            oos.writeObject(copies);
            System.out.println("Da luu " + copies.size() + " ban sao vao file.");
        } catch (IOException e) {
            System.err.println("Loi khi luu ban sao: " + e.getMessage());
        }
    }
    
    /**
     * Doc danh sach ban sao tu file
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Copy> loadCopies() {
        File file = new File(COPY_FILE);
        if (!file.exists()) {
            System.out.println("File ban sao chua ton tai. Tao du lieu moi.");
            return new HashMap<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(COPY_FILE))) {
            Map<String, Copy> copies = (Map<String, Copy>) ois.readObject();
            System.out.println("Da tai " + copies.size() + " ban sao tu file.");
            return copies;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Loi khi doc ban sao: " + e.getMessage());
            return new HashMap<>();
        }
    }
    
    /**
     * Khoi phuc quan he giua Document va Copy sau khi load tu file
     */
    public static void restoreRelationships(Map<String, Document> documents, 
                                           Map<String, Copy> copies) {
        // Xoa danh sach copies cu trong documents
        documents.values().forEach(doc -> doc.getCopies().clear());
        
        // Thiet lap lai quan he
        copies.values().forEach(copy -> {
            Document doc = documents.get(copy.getDocumentId());
            if (doc != null) {
                doc.addCopy(copy);
            }
        });
        
        System.out.println("Da khoi phuc quan he giua tai lieu va ban sao.");
    }
}
