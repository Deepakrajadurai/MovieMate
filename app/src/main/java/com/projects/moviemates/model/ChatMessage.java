package com.projects.moviemates.model;

import java.util.Date;

public class ChatMessage {
    private String messageId;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String messageText;
    private long timestamp;
    private boolean isSentByCurrentUser;

    public ChatMessage() {
    }

    public ChatMessage(String messageText, boolean isSentByCurrentUser) {
        this.messageText = messageText;
        this.isSentByCurrentUser = isSentByCurrentUser;
        this.timestamp = new Date().getTime();
        if (isSentByCurrentUser) {
            this.senderName = "You";
        } else {
            this.senderName = "Bot";
        }
    }

    public ChatMessage(String messageId, String senderId, String senderName, String receiverId, String messageText, long timestamp, boolean isSentByCurrentUser) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    public ChatMessage(String senderId, String senderName, String receiverId, String messageText, boolean isSentByCurrentUser) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.timestamp = new Date().getTime();
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    // !!!! THIS IS THE NEW METHOD THAT FIXES THE ERROR !!!!
    public boolean isUser() {
        return isSentByCurrentUser();
    }
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    // Getters
    public String getMessageId() { return messageId; }
    public String getSenderId() { return senderId; }
    public String getSenderName() { return senderName; }
    public String getReceiverId() { return receiverId; }
    // Renamed this method to avoid conflict with the new one
    public String getMessage() { return messageText; }
    public long getTimestamp() { return timestamp; }
    public boolean isSentByCurrentUser() { return isSentByCurrentUser; }

    // Setters
    public void setMessageId(String messageId) { this.messageId = messageId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public void setReceiverId(String receiverId) { this.receiverId = receiverId; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setSentByCurrentUser(boolean sentByCurrentUser) { isSentByCurrentUser = sentByCurrentUser; }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderName='" + senderName + '\'' +
                ", messageText='" + messageText + '\'' +
                ", timestamp=" + new Date(timestamp) +
                '}';
    }
}