package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Review;
import com.roomies.roomies.domain.service.PostService;
import com.roomies.roomies.domain.service.ReviewService;
import com.roomies.roomies.resource.ReviewResource;
import com.roomies.roomies.resource.SaveReviewResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReviewsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ReviewService reviewService;

    @Operation(tags = {"reviews"})
    @GetMapping("/post/{postId}/reviews")
    public Page<ReviewResource> getAllReviewsByPostId(@PathVariable Long postId, Pageable pageable){
        Page<Review> reviewPage = reviewService.getAllReviewsByPostId(postId,pageable);
        List<ReviewResource> resources = reviewPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable, resources.size());
    }

    @Operation(tags = {"reviews"})
    @GetMapping("/reviews/{reviewId}")
    public ReviewResource getReviewById(@PathVariable Long reviewId){
        return convertToResource(reviewService.getReviewById(reviewId));
    }

    @Operation(tags = {"reviews"})
    @PostMapping("/profile/{profileId}/posts/{postId}/reviews")
    public ReviewResource createReview(
            @PathVariable(name = "profileId") Long profileId,
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestBody SaveReviewResource resource){
        return convertToResource(reviewService.createReview(postId,profileId,convertToEntity(resource)));
    }

    @Operation(tags = {"reviews"})
    @PutMapping("/reviews/{reviewId}")
    public ReviewResource updateReview(@PathVariable Long reviewId, @Valid @RequestBody SaveReviewResource resource){
        return convertToResource(reviewService.updateReview(reviewId,convertToEntity(resource)));
    }

    @Operation(tags = {"reviews"})
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        return reviewService.deleteReview(reviewId);
    }

    private Review convertToEntity(SaveReviewResource resource) {
        return mapper.map(resource, Review.class);
    }

    private ReviewResource convertToResource(Review entity) {
        return mapper.map(entity, ReviewResource.class);
    }

}