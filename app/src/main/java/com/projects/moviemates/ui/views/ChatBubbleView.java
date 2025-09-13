// File: src/main/java/com/projects/moviemates/ui/views/ChatBubbleView.java
package com.projects.moviemates.ui.views;

// ALL NECESSARY IMPORTS ADDED HERE
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout; // The import that fixes the main error
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.projects.moviemates.R;


public class ChatBubbleView extends LinearLayout {
    private TextView messageText;
    private boolean isSender = false;

    // This constructor is needed for creating the view in code
    public ChatBubbleView(Context context) {
        this(context, null);
    }

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
        // Padding values in pixels
        int horizontalPadding = (int) (16 * getResources().getDisplayMetrics().density);
        int verticalPadding = (int) (8 * getResources().getDisplayMetrics().density);
        messageText.setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);

        addView(messageText);
    }

    public void setMessage(String text) {
        messageText.setText(text);
    }

    public void setIsSender(boolean sender) {
        isSender = sender;
        // Re-apply styles based on sender status
        LayoutParams params = (LayoutParams) messageText.getLayoutParams();
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
    }
}