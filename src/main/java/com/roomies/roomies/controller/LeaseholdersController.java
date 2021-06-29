package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Leaseholder;
import com.roomies.roomies.domain.service.LeaseholderService;
import com.roomies.roomies.resource.LeaseholderResource;
import com.roomies.roomies.resource.SaveLandlordResource;
import com.roomies.roomies.resource.SaveLeaseholderResource;
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
public class LeaseholdersController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private LeaseholderService leaseholderService;

    @Operation(tags = {"leaseholders"})
    @GetMapping("/leaseholders")
    public Page<LeaseholderResource> getAllLeaseHolder(Pageable pageable){
        Page<Leaseholder> leaseholderPage = leaseholderService.getAllLeaseholder(pageable);
        List<LeaseholderResource> resources = leaseholderPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @Operation(tags = {"leaseholders"})
    @GetMapping("/leaseholders/{leaseholderId}")
    public LeaseholderResource getLeaseholderById(@PathVariable Long leaseholderId){
        return convertToResource(leaseholderService.getLeaseholderById(leaseholderId));
    }

    @Operation(tags = {"leaseholders"})
    @PostMapping("/users/{userId}/plans/{planId}/leaseholders")
    public LeaseholderResource createLeaseholder(
            @PathVariable (name = "userId") Long userId,
            @PathVariable (name = "planId") Long planId,
            @Valid @RequestBody SaveLeaseholderResource resource){
        return convertToResource(leaseholderService.createLeaseholder(userId,planId,convertToEntity(resource)));
    }

    @Operation(tags = {"leaseholders"})
    @PutMapping("leaseholders/{leaseholderId}")
    public LeaseholderResource updateLeaseholder(@PathVariable Long leaseholderId,@Valid @RequestBody SaveLeaseholderResource resource){
        return convertToResource(leaseholderService.updateLeaseholder(leaseholderId,convertToEntity(resource)));
    }

    @Operation(tags = {"leaseholders"})
    @DeleteMapping("leaseholders/{leaseholderId}")
    public ResponseEntity<?> deleteLeaseholder(@PathVariable Long leaseholderId){
        return leaseholderService.deleteLeaseholder(leaseholderId);
    }

    private Leaseholder convertToEntity(SaveLeaseholderResource resource){
        return mapper.map(resource,Leaseholder.class);
    }

    private LeaseholderResource convertToResource(Leaseholder entity){
        return mapper.map(entity,LeaseholderResource.class);
    }
}
