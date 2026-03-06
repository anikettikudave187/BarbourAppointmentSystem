package com.dbApi.dbApi.Controller;

import com.dbApi.dbApi.modelClasses.Reviews;
import com.dbApi.dbApi.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/db/review")
public class ReviewController {
    @Autowired
    ReviewRepo reviewRepo;

    @PostMapping("/addReview")
    public Reviews addReview(@RequestBody Reviews reviews){
        return reviewRepo.save(reviews);
    }

    @GetMapping("/getAll")
    public List<Reviews> getAllReviews(){
        List<Reviews> allReviews=reviewRepo.findAll();
        return allReviews;
    }
}
