package in.venkat.foodiesapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.venkat.foodiesapi.exception.BadRequestException;
import in.venkat.foodiesapi.io.FoodRequest;
import in.venkat.foodiesapi.io.FoodResponse;
import in.venkat.foodiesapi.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/foods")
@AllArgsConstructor
@CrossOrigin("*")
public class FoodController {

    private  final FoodService foodService;

    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString, @RequestPart("file")MultipartFile file){
//        this is for my sake
//        System.out.println("==== RAW foodString ====");
        System.out.println(foodString);
        ObjectMapper objectMapper=new ObjectMapper();
        FoodRequest request=null;
        try{
            request =objectMapper.readValue(foodString , FoodRequest.class);
        } catch (JsonProcessingException ex){
          throw new BadRequestException("Invalid JSON format for food data");

        }
        FoodResponse response=foodService.addFood(request, file);
        return response;
    }
    @GetMapping
    public List<FoodResponse> readFoods(){
        return foodService.readFoods();
    }

    @GetMapping("/{id}")
   public  FoodResponse readFood(@PathVariable String id){
        return  foodService.readFood(id);
   }


   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public  void deleteFood(@PathVariable String id){
        foodService.deleteFood(id);
    }
}
