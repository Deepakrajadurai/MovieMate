// File: src/main/java/com/example/moviemate/ui/views/ChatBubbleView.java
package com.projects.moviemates.ui.views;
// ... imports

public class ChatBubbleView extends LinearLayout {
    private TextView messageText;
    private boolean isSender = false;

    public ChatBubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChatBubbleView, 0, 0);
        try {
            isSender = a.getBoolean(R.styleable.ChatBubbleView_isSender, false);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        messageText = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if (isSender) {
            params.gravity = Gravity.END;
            messageText.setBackgroundResource(R.drawable.chat_bubble_sender);
            messageText.setTextColor(Color.WHITE);
        } else {
            params.gravity = Gravity.START;
            messageText.setBackgroundResource(R.drawable.chat_bubble_receiver);
            messageText.setTextColor(Color.BLACK);
        }
        messageText.setLayoutParams(params);
        messageText.setPadding(32, 16, 32, 16);
        addView(messageText);
    }

    public void setMessage(String text) {
        messageText.setText(text);
    }
}