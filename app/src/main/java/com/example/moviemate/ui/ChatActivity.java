// File: app/src/main/java/com/example/moviemate/ui/ChatActivity.java
package com.example.moviemate.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moviemate.databinding.ActivityChatBinding;
import com.example.moviemate.model.ChatMessage;
import com.example.moviemate.ui.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupRecyclerView();

        binding.sendButton.setOnClickListener(v -> sendMessage());

        // Add initial bot message
        addMessage("Hello! How are you feeling today? Tell me what kind of movie you'd like to see.", false);
    }

    private void setupRecyclerView() {
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // New items appear at the bottom
        binding.chatRecyclerView.setLayoutManager(layoutManager);
        binding.chatRecyclerView.setAdapter(chatAdapter);
    }

    private void sendMessage() {
        String messageText = binding.messageInput.getText().toString().trim();
        if (!TextUtils.isEmpty(messageText)) {
            // Add user's message to the chat
            addMessage(messageText, true);
            binding.messageInput.setText("");

            // Trigger mock bot response
            getMockBotResponse(messageText);
        }
    }

    private void addMessage(String message, boolean isUser) {
        chatMessages.add(new ChatMessage(message, isUser));
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        binding.chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
    }

    private void getMockBotResponse(String userMessage) {
        // Simulate network delay and a simple response logic
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            String botResponse;
            if (userMessage.toLowerCase().contains("funny") || userMessage.toLowerCase().contains("comedy")) {
                botResponse = "I see you're in the mood for a laugh! I'd recommend looking for a comedy like 'Superbad'.";
            } else if (userMessage.toLowerCase().contains("action") || userMessage.toLowerCase().contains("thrilling")) {
                botResponse = "An action-packed adventure sounds great! Have you seen 'John Wick'?";
            } else {
                botResponse = "That sounds interesting. Based on that, I'd suggest checking out the 'Trending' section for some popular options.";
            }
            addMessage(botResponse, false);
        }, 1500); // 1.5 second delay
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}