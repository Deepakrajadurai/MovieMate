package com.projects.moviemates.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;
import com.projects.moviemates.model.ChatMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends ViewModel {

    private static final long MOCK_BOT_RESPONSE_DELAY_MS = 1500;

    private final MutableLiveData<List<ChatMessage>> _chatMessages = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<ChatMessage>> chatMessages = _chatMessages;

    private final Handler mockBotHandler = new Handler(Looper.getMainLooper());

    public ChatActivity() {
        // Add initial bot message
        addMessageToList("Hello! How are you feeling today? Tell me what kind of movie you'd like to see.", false);
    }

    private void addMessageToList(String message, boolean isUser) {
        List<ChatMessage> currentMessages = new ArrayList<>(_chatMessages.getValue() != null ? _chatMessages.getValue() : new ArrayList<>());
        currentMessages.add(new ChatMessage(message, isUser)); // Assuming ChatMessage(String, boolean) constructor
        _chatMessages.setValue(currentMessages);
    }

    public void sendMessage(String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            return;
        }
        addMessageToList(messageText.trim(), true);
        triggerMockBotResponse(messageText.trim());
    }

    private void triggerMockBotResponse(String userMessage) {
        mockBotHandler.postDelayed(() -> {
            String botResponse;
            String lowerUserMessage = userMessage.toLowerCase(Locale.ROOT);
            if (lowerUserMessage.contains("funny") || lowerUserMessage.contains("comedy")) {
                botResponse = "I see you're in the mood for a laugh! I'd recommend looking for a comedy like 'Superbad'.";
            } else if (lowerUserMessage.contains("action") || lowerUserMessage.contains("thrilling")) {
                botResponse = "An action-packed adventure sounds great! Have you seen 'John Wick'?";
            } else {
                botResponse = "That sounds interesting. Based on that, I'd suggest checking out the 'Trending' section for some popular options.";
            }
            addMessageToList(botResponse, false);
        }, MOCK_BOT_RESPONSE_DELAY_MS);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mockBotHandler.removeCallbacksAndMessages(null); // Clean up handler
    }
}