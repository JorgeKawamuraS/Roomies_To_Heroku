package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Conversation;
import com.roomies.roomies.domain.service.ConversationService;
import com.roomies.roomies.resource.ConversationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class ConversationsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ConversationService conversationService;

    @Operation(summary = "Get Conversations", description = "Get All Conversations by Pages",tags = {"conversations"})
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "All Conversations returned",content = @Content(mediaType ="application/json" ))
    })
    @GetMapping("/profiles/{profileId}/conversations")
    public Page<ConversationResource> getAllConversationsBySenderId(@PathVariable Long profileId, Pageable pageable){
        Page<Conversation> conversationPage = conversationService.getAllConversationsByProfileId(profileId,pageable);
        List<ConversationResource> resources = conversationPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources,pageable,resources.size());
    }

    @Operation(tags = {"conversations"})

    @PostMapping("profiles/{profileSenderId}/profiles/{profileReceiverId}/conversations")
    public ConversationResource createConversation(
            @PathVariable (name = "profileSenderId") Long profileSenderId,
            @PathVariable (name ="profileReceiverId") Long profileReceiverId) {
        return convertToResource(conversationService.createConversation(profileSenderId,profileReceiverId));
    }

    @Operation(tags = {"conversations"})
    @DeleteMapping("/conversations/{conversationId}")
    public ResponseEntity<?> deleteConversation(@PathVariable Long conversationId){
        return conversationService.deleteConversation(conversationId);
    }

    private ConversationResource convertToResource(Conversation entity){
        return mapper.map(entity, ConversationResource.class);
    }


}
