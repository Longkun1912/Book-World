package com.example.book.service;

import com.example.book.domain.MessageDetails;
import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.Chat;
import com.example.book.entity.Message;
import com.example.book.entity.User;
import com.example.book.repository.ChatRepository;
import com.example.book.repository.MessageRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ModelMapper mapper;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public Chat startAChat(UUID user_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        Optional<Chat> existed_chat = chatRepository.findChatByUsers(current_user.getId(), user_id);
        if (existed_chat.isPresent()){
            System.out.println("Chat0 is present.");
            return existed_chat.get();
        }
        else {
            System.out.println("Chat0 is not present.");
            User other = userRepository.findUserById(user_id).orElseThrow();
            Chat chat = new Chat(current_user, other);
            chat.setId(UUID.randomUUID());
            chat.setCreated_time(LocalDateTime.now());
            return chatRepository.save(chat);
        }
    }

    public List<UserInfoDetails> getFilteredMembers(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        List<UserInfoDetails> member_list = new ArrayList<>();
        List<User> members = userRepository.searchUserByName(username);
        members.remove(current_user);
        System.out.println("Filtered members: " + members.size());
        return memberListResults(current_user,members, member_list);
    }

    public List<UserInfoDetails> getAllMembers(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        List<User> members = userRepository.findAll();
        members.remove(current_user);
        List<UserInfoDetails> memberDetails = new ArrayList<>();
        System.out.println("All members: " + members.size());
        return memberListResults(current_user,members,memberDetails);
    }

    public List<UserInfoDetails> memberListResults(User current_user,List<User> members, List<UserInfoDetails> memberDetails){
        for (User member : members){
            Optional<Chat> chat = chatRepository.findChatByUsers(current_user.getId(), member.getId());
            if (chat.isPresent()){
                System.out.println("Chat1 is present.");
                Message message = messageRepository.getLatestMessageInChat(chat.get());
                if (message == null){
                    message = new Message(current_user, chat.get(), LocalDateTime.now());
                    message.setText("Let's start a chat.");
                    messageRepository.save(message);
                }
                UserInfoDetails converted_member = mapper.map(member, UserInfoDetails.class);
                converted_member.setChat_id(chat.get().getId());
                String limit_message = limitMessageCharacters(message.getText(), 30);
                converted_member.setLatest_message(limit_message);
                memberDetails.add(converted_member);
            }
            else {
                System.out.println("Chat1 is not present.");
            }
        }
        return memberDetails;
    }

    public List<MessageDetails> getMessageInChat(UUID member_id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        List<Message> messages = new ArrayList<>();
        Optional<User> member = userRepository.findUserById(member_id);
        Optional<Chat> chat = chatRepository.findChatByUsers(current_user.getId(),member_id);
        if (chat.isPresent()){
            System.out.println("Chat2 is present.");;
            messages = messageRepository.getMessageInChat(chat.get());
        }
        else {
            System.out.println("Chat2 is not present.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
        List<MessageDetails> messageDetailsList = new ArrayList<>();
        for (Message message : messages){
            LocalDateTime created_time = message.getCreated_time();
            MessageDetails messageDetails = mapper.map(message, MessageDetails.class);
            messageDetails.setLast_updated(formatter.format(created_time));
            messageDetailsList.add(messageDetails);
        }
        System.out.println("Messages in this chat: " + messageDetailsList.size());
        return messageDetailsList;
    }

    public Message saveMessage(UUID member_id, String message_text){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User current_user = userRepository.findUserByEmail(auth.getName()).orElseThrow();
        Optional<Chat> chat = chatRepository.findChatByUsers(current_user.getId(), member_id);
        if (chat.isPresent()){
            Message message = new Message(current_user,message_text, LocalDateTime.now());
            message.setChat(chat.get());
            return messageRepository.save(message);
        }
        else {
            System.out.println("Chat3 is not present.");
            throw new NullPointerException();
        }
    }

    public String limitMessageCharacters(String message, int maxLength) {
        if (message.length() <= maxLength) {
            return message;
        } else {
            return message.substring(0, maxLength) + "...";
        }
    }

    public void deleteChat(UUID chat_id){
        Optional<Chat> existed_chat = chatRepository.findById(chat_id);
        if (existed_chat.isPresent()){
            System.out.println("Chat4 is present.");
            Chat chat = existed_chat.get();
            chatRepository.delete(chat);
        }
        else {
            System.out.println("Chat4 is not present.");
        }
    }

    public Message updateMessage(Integer message_id, String message_text){
        Optional<Message> existing_message = messageRepository.findMessageByID(message_id);
        if (existing_message.isPresent()){
            Message message = existing_message.get();
            System.out.println("Message found with id " + message.getId());
            System.out.println("Message is present with sender " + message.getSender().getUsername());
            message.setText(message_text);
            message.setCreated_time(LocalDateTime.now());
            return messageRepository.save(message);
        }
        else {
            System.out.println("Message is not present");
            return null;
        }
    }

    public void deleteMessage(Integer message_id){
        Optional<Message> existing_message = messageRepository.findMessageByID(message_id);
        existing_message.ifPresent(messageRepository::delete);
    }

}
