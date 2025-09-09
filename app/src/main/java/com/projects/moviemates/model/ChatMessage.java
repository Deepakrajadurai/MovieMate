package com.projects.moviemates.model;

import java.util.Date; // For timestamp

public class ChatMessage {
    private String messageId;
    private String senderId;    // ID of the user who sent the message
    private String senderName;  // Display name of the sender
    private String receiverId;  // ID of the user who should receive the message (for 1-to-1 chat)
    private String messageText;
    private long timestamp;     // Timestamp of when the message was sent (e.g., System.currentTimeMillis())
    private boolean isSentByCurrentUser; // Helper field for UI to determine message alignment

    // Default constructor (required for Firebase Realtime Database/Firestore, etc.)
    public ChatMessage() {
    }

    // !!!! ADD THIS CONSTRUCTOR !!!!
    // Constructor for simple UI representation as used in ChatActivity/ChatViewModel
    public ChatMessage(String messageText, boolean isSentByCurrentUser) {
        this.messageText = messageText;
        this.isSentByCurrentUser = isSentByCurrentUser;
        this.timestamp = new Date().getTime(); // Default timestamp to now
        // Other fields (messageId, senderId, senderName, receiverId) will be null or default
        // This is suitable for a mock UI where these details aren't immediately needed
        // or are populated differently for bot vs user.
        if (isSentByCurrentUser) {
            this.senderName = "You"; // Or get current user's name
        } else {
            this.senderName = "Bot"; // Or a specific bot name
        }
    }


    // Constructor with essential fields
    public ChatMessage(String messageId, String senderId, String senderName, String receiverId, String messageText, long timestamp, boolean isSentByCurrentUser) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.isSentByCurrentUser = isSentByCurrentUser;
    }

    // Constructor without messageId (if it's generated automatically, e.g., by Firebase)
    public ChatMessage(String senderId, String senderName, String receiverId, String messageText, boolean isSentByCurrentUser) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.timestamp = new Date().getTime(); // Set current time
        this.isSentByCurrentUser = isSentByCurrentUser;
    }


    // Getters
    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isSentByCurrentUser() {
        return isSentByCurrentUser;
    }

    // Setters
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSentByCurrentUser(boolean sentByCurrentUser) {
        isSentByCurrentUser = sentByCurrentUser;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderName='" + senderName + '\'' +
                ", messageText='" + messageText + '\'' +
                ", timestamp=" + new Date(timestamp) +
                '}';
    }
}