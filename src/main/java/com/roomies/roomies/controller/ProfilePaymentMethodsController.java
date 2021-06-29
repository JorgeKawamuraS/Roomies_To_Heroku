package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Profile;
import com.roomies.roomies.domain.repository.ProfileRepository;
import com.roomies.roomies.domain.service.ProfileService;
import com.roomies.roomies.resource.ProfileResource;
import com.roomies.roomies.resource.SaveProfileResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProfilePaymentMethodsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProfileService profileService;

    @Operation(tags = {"profile_paymentMethods"})
    @PostMapping("profiles/{profileId}/methods/{methodId}")
    public ProfileResource assignProfilePaymentMethod(@PathVariable Long profileId,@PathVariable Long methodId){
        return convertToResource(profileService.assignPaymentMethodProfile(methodId,profileId));
    }

    @Operation(tags = {"profile_paymentMethods"})
    @DeleteMapping("profiles/{profileId}/methods/{methodId}")
    public ProfileResource unassignProfilePaymentMethod(@PathVariable Long profileId,@PathVariable Long methodId){
        return convertToResource(profileService.unAssignPaymentMethodProfile(methodId,profileId));
    }

    private Profile convertToEntity(SaveProfileResource resource){
        return mapper.map(resource,Profile.class);
    }

    private ProfileResource convertToResource(Profile entity){
        return mapper.map(entity,ProfileResource.class);
    }

}
