package edu.northeastern.models;

public class Response implements OrderCommand {
    public final InventoryResponse response;

    public Response(InventoryResponse response) {
        this.response = response;
    }
}
