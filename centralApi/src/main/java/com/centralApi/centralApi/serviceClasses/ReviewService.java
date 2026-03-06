package com.centralApi.centralApi.serviceClasses;

import com.centralApi.centralApi.integrations.DbApi;
import com.centralApi.centralApi.modelClasses.Reviews;
import com.centralApi.centralApi.requestBodyClasses.ReviewsRb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    DbApi dbApi;

    public Reviews addReview(ReviewsRb reviewsRb){
        return dbApi.callAddReview(reviewsRb);
    }

    public List<Reviews> getAll(){
        return dbApi.callGetAll();
    }
}
