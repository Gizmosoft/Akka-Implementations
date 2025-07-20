package edu.northeastern.models;

public class InventoryResponse {
    public final String status;

    public InventoryResponse(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InventoryResponse{" +
                "status='" + status + '\'' +
                '}';
    }
}
