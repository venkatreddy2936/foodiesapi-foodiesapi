package in.venkat.foodiesapi.io;

import jdk.jshell.Snippet;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    private double amount;
    private String paymentStatus;
    private String razorpayOrderId;
    private String orderStatus;
    private List<OrderItem> orderedItems;

}
