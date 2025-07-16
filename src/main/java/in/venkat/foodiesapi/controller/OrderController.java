package in.venkat.foodiesapi.controller;

import com.razorpay.RazorpayException;
import in.venkat.foodiesapi.exception.BadRequestException;
import in.venkat.foodiesapi.io.OrderRequest;
import in.venkat.foodiesapi.io.OrderResponse;
import in.venkat.foodiesapi.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService  orderService;

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request) throws RazorpayException {
        OrderResponse response=orderService.createOrderWithPayment(request);
        return response;
    }

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String , String> paymentData){
        orderService.verifyPayment(paymentData,"Paid");
    }

    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getUserOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId){
        if (orderId == null || orderId.isBlank()) {
            throw new BadRequestException("Order ID must not be empty");
        }
        orderService.removeOrder(orderId);

    }


//   This is for admin panel
    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers(){

        return orderService.getOrdersOfAllUsers();
    }

    //   This is for admin panelc
    @PatchMapping("/status/{orderId}")
    public  void updateOrderStatus(@PathVariable String orderId, @RequestParam String status ){
        if (status == null || status.isBlank()) {
            throw new BadRequestException("Status must not be empty");
        }
           orderService.updateOrderStatus(orderId, status);
    }

}
