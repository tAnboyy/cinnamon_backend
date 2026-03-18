package com.cinnamon.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class MenuService {

    public List<Map<String, Object>> getMenuItems() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("menuItems").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        return documents.stream().map(doc -> {
            Map<String, Object> data = new HashMap<>(doc.getData());
            data.put("id", doc.getId());
            return data;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> createMenuItem(Map<String, Object> item) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        item.remove("id");
        DocumentReference docRef = db.collection("menuItems").document();
        docRef.set(item).get();
        Map<String, Object> result = new HashMap<>(item);
        result.put("id", docRef.getId());
        return result;
    }

    public Map<String, Object> updateMenuItem(String id, Map<String, Object> item) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        item.remove("id");
        db.collection("menuItems").document(id).set(item).get();
        Map<String, Object> result = new HashMap<>(item);
        result.put("id", id);
        return result;
    }

    public void deleteMenuItem(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection("menuItems").document(id).delete().get();
    }
}
