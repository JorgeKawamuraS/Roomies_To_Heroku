package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.PaymentMethod;
import com.roomies.roomies.domain.service.PaymentMethodService;
import com.roomies.roomies.resource.PaymentMethodResource;
import com.roomies.roomies.resource.SavePaymentMethodResource;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.coyote.Response;
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
public class PaymentMethodsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Operation(tags = {"paymentMethods"})
    @GetMapping("/profile/{profileId}/methods")
    public Page<PaymentMethodResource> getAllPaymentMethodsByProfileId(
            @PathVariable (name = "profileId") Long profileId,
            Pageable pageable){
        Page<PaymentMethod> paymentMethodPage = paymentMethodService.getAllPaymentMethodsByProfileId(profileId,pageable);
        List<PaymentMethodResource> resources = paymentMethodPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable, resources.size());
    }

    @Operation(tags = {"paymentMethods"})
    @PostMapping("/methods")
    public PaymentMethodResource createPaymentMethod(@Valid @RequestBody SavePaymentMethodResource resource){
        return convertToResource(paymentMethodService.createPaymentMethod(convertToEntity(resource)));
    }

    @Operation(tags = {"paymentMethods"})
    @PutMapping("/methods/{methodId}")
    public PaymentMethodResource updatePaymentMethod(@PathVariable Long methodId, @Valid @RequestBody SavePaymentMethodResource resource){
        return convertToResource(paymentMethodService.updatePaymentMethod(methodId,convertToEntity(resource)));
    }

    @Operation(tags = {"paymentMethods"})
    @DeleteMapping("/methods/{methodId}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable Long methodId){
        return paymentMethodService.deletePaymentMethod(methodId);
    }

    private PaymentMethod convertToEntity(SavePaymentMethodResource resource){
        return mapper.map(resource,PaymentMethod.class);
    }

    private PaymentMethodResource convertToResource(PaymentMethod entity){
        return mapper.map(entity,PaymentMethodResource.class);
    }

}
