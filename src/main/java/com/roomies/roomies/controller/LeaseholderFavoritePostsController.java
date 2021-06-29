package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Leaseholder;
import com.roomies.roomies.domain.service.LeaseholderService;
import com.roomies.roomies.resource.LeaseholderResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LeaseholderFavoritePostsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LeaseholderService leaseholderService;

    @Operation(tags = {"leaseholders_favouritePosts"})
    @PostMapping("leaseholders/{leaseholderId}/posts/{postId}")
    public LeaseholderResource assignLeaseholderPost(@PathVariable Long leaseholderId,@PathVariable Long postId){
        return convertToResource(leaseholderService.assignLeaseholderPost(leaseholderId,postId));
    }

    @Operation(tags = {"leaseholders_favouritePosts"})
    @DeleteMapping("leaseholders/{leaseholderId}/posts/{postId}")
    public LeaseholderResource unassignLeaseholderPost(@PathVariable Long leaseholderId,@PathVariable Long postId){
        return convertToResource(leaseholderService.unAssignLeaseholderPost(leaseholderId,postId));
    }

    private LeaseholderResource convertToResource(Leaseholder entity){
        return mapper.map(entity,LeaseholderResource.class);
    }

}
