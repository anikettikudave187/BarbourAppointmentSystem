package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.modelClasses.Reviews;
import com.centralApi.centralApi.requestBodyClasses.ReviewsRb;
import com.centralApi.centralApi.serviceClasses.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/central/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/addReview")
    public Reviews addReview(@RequestBody ReviewsRb reviewsRb){
        return reviewService.addReview(reviewsRb);
    }

    @GetMapping("/getAllReviews")
    public List<Reviews> addReview(){
        return reviewService.getAll();
    }
}
