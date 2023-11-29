package accenture.training.javacoin.dtos;

public class OrderResponse {
    private Long id;

    public OrderResponse(Long id) {
        this.id = id;
    }

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
