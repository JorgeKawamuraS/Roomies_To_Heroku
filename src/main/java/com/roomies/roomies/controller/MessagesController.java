package com.roomies.roomies.controller;

import com.roomies.roomies.domain.model.Message;
import com.roomies.roomies.domain.service.MessageService;
import com.roomies.roomies.resource.MessageResource;
import com.roomies.roomies.resource.SaveMessageResource;
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
public class MessagesController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MessageService messageService;

    @Operation(tags = {"messages"})
    @GetMapping("/messages")
    public Page<MessageResource> getAllMessages(Pageable pageable){
        List<MessageResource> messages = messageService.getAllMessages(pageable)
                .getContent().stream().map(this::convertToResource)
                .collect(Collectors.toList());
        int messagesCount = messages.size();
        return new PageImpl<>(messages,pageable,messagesCount);
    }

    @Operation(tags = {"messages"})
    @PostMapping("/conversations/{conversationSenderId}/messages")
    public MessageResource createMessage(
            @PathVariable (name = "conversationSenderId") Long conversationSenderId,
            @Valid @RequestBody SaveMessageResource resource){
        return convertToResource(messageService.createMessage(conversationSenderId,convertToEntity(resource)));
    }

    @Operation(tags = {"messages"})
    @PutMapping("/messages/{messageId}")
    public MessageResource updateMessage(@PathVariable Long messageId, @Valid @RequestBody SaveMessageResource resource){
        return convertToResource(messageService.updateMessage(messageId,convertToEntity(resource)));
    }

    @Operation(tags = {"messages"})
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId){
        return messageService.deleteMessage(messageId);
    }

    private Message convertToEntity(SaveMessageResource resource){
        return mapper.map(resource,Message.class);
    }

    private MessageResource convertToResource(Message entity){
        return  mapper.map(entity,MessageResource.class);
    }

}
