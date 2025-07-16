package in.venkat.foodiesapi.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponse {
    private  String id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;
}
