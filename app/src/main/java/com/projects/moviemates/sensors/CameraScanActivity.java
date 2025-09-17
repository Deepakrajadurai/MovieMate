package com.projects.moviemates.sensors;

import com.projects.moviemates.R;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
// ... other necessary imports

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraScanActivity extends AppCompatActivity {

    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan); // Create this layout with a PreviewView

        previewView = findViewById(R.id.camera_preview_view); // Add PreviewView with this ID in your XML
        cameraExecutor = Executors.newSingleThreadExecutor();
        textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        startCamera();
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        //.setTargetResolution(new Size(1280, 720)) // Adjust as needed
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, imageProxy -> {
                    @androidx.camera.core.ExperimentalGetImage
                    android.media.Image mediaImage = imageProxy.getImage();
                    if (mediaImage != null) {
                        InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                        // Pass image to an ML Kit Vision API
                        textRecognizer.process(image)
                                .addOnSuccessListener(visionText -> {
                                    // Task completed successfully
                                    String extractedText = visionText.getText();
                                    // TODO: Process the extractedText (e.g., clean it, look for titles)
                                    // TODO: If a likely title is found, trigger TMDB API search
                                    // Log.d("CameraScanActivity", "Text: " + extractedText);

                                    // Example: if (isLikelyMovieTitle(extractedText)) { searchTmdb(extractedText); }
                                })
                                .addOnFailureListener(e -> {
                                    // Task failed with an exception
                                    // Log.e("CameraScanActivity", "Text recognition failed", e);
                                })
                                .addOnCompleteListener(task -> {
                                    imageProxy.close(); // IMPORTANT: Close the ImageProxy
                                });
                    }
                });

                cameraProvider.unbindAll(); // Unbind use cases before rebinding
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);

            } catch (Exception e) {
                // Log.e("CameraScanActivity", "Use case binding failed", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // You'll need to add a method like this to call the TMDB API
    // private void searchTmdb(String query) { ... }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        if (textRecognizer != null) {
            textRecognizer.close();
        }
    }
}
