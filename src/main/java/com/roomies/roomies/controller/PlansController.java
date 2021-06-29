package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Conversation;
import com.roomies.roomies.domain.model.Plan;
import com.roomies.roomies.domain.model.Post;
import com.roomies.roomies.domain.model.Profile;
import com.roomies.roomies.domain.service.PlanService;
import com.roomies.roomies.resource.*;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PlansController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PlanService planService;

    @Operation(tags = {"plans"})
    @GetMapping("/plans")
    public Page<PlanResource> getAllPlans(Pageable pageable){
        Page<Plan> planPage = planService.getAllPlans(pageable);
        List<PlanResource> resources = planPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @Operation(tags = {"plans"})
    @PostMapping("/plans")
    public PlanResource createPlan(@Valid @RequestBody SavePlanResource resource){
        return convertToResource(planService.createPlan(convertToEntity(resource)));
    }

    private Plan convertToEntity(SavePlanResource resource) {
        return mapper.map(resource,Plan.class);
    }

    private PlanResource convertToResource(Plan entity){
        return mapper.map(entity, PlanResource.class);
    }
}
