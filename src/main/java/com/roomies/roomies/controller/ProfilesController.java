/*package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Profile;
import com.roomies.roomies.domain.service.ProfileService;
import com.roomies.roomies.resource.SaveProfileResource;
import com.roomies.roomies.resource.ProfileResource;
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
@RequestMapping("/api")
public class ProfilesController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profiles")
    public Page<ProfileResource> getAllProfiles(Pageable pageable){
        List<ProfileResource> users = profileService.getAllProfiles(pageable)
                .getContent().stream().map(this::convertToResource)
                .collect(Collectors.toList());
        int usersCount = users.size();
        return new PageImpl<>(users,pageable,usersCount);
    }

    @GetMapping("/paymentMethods/{paymentMethodId}/profiles")
    public Page<ProfileResource> getAllProfilesByPaymentMethodId(@PathVariable Long paymentMethodId, Pageable pageable){
        List<ProfileResource> users = profileService.getAllProfilesByPaymentMethodId(paymentMethodId,pageable)
                .getContent().stream().map(this::convertToResource)
                .collect(Collectors.toList());
        int usersCount = users.size();
        return new PageImpl<>(users,pageable,usersCount);
    }

    @GetMapping("/profile/{profileId}")
    public ProfileResource getProfileById(@PathVariable Long profileId){
        return convertToResource(profileService.getProfileById(profileId));
    }

    @PostMapping("/users/{userId}/plans/{planId}/profiles")
    public ProfileResource createProfile(
            @PathVariable (name = "userId") Long userId,
            @PathVariable (name = "planId") Long planId,
            @Valid @RequestBody SaveProfileResource resource){
        return convertToResource(profileService.createProfile(userId,planId,convertToEntity(resource)));
    }

    @PutMapping("/profiles/{profileId}")
    public ProfileResource updateProfile(@PathVariable Long profileId, @Valid @RequestBody SaveProfileResource resource){
        return convertToResource(profileService.updateProfile(profileId,convertToEntity(resource)));
    }

    @DeleteMapping("/profiles/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long profileId){
        return profileService.deleteProfile(profileId);
    }

    private Profile convertToEntity(SaveProfileResource resource) {
        return mapper.map(resource, Profile.class);
    }

    private ProfileResource convertToResource(Profile entity) {
        return mapper.map(entity, ProfileResource.class);
    }
}
*/