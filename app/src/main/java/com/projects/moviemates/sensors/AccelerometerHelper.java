package com.projects.moviemates.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log; // For logging errors

public class AccelerometerHelper implements SensorEventListener {

    private static final String TAG = "AccelerometerHelper"; // Tag for logging

    public interface MotionListener {
        void onMotionDetected();
        void onIdle();
    }

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MotionListener listener;
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper()); // Renamed for clarity
    private Runnable idleRunnable;

    private static final long IDLE_DELAY_MS = 3000; // 3 seconds
    private static final float SHAKE_THRESHOLD = 3.25f; // Adjust as needed
    // Consider adding a timestamp for the last shake to avoid rapid firing
    // private long lastShakeTime = 0;
    // private static final long MIN_TIME_BETWEEN_SHAKES_MS = 500;


    public AccelerometerHelper(Context context, MotionListener listener) {
        if (context == null) {
            Log.e(TAG, "Context cannot be null");
            // Optionally throw an IllegalArgumentException or handle appropriately
            return;
        }
        this.listener = listener; // Assume listener is not null, or add checks if it can be

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager == null) {
            Log.e(TAG, "SensorManager could not be retrieved.");
            // Handle this case: perhaps notify the listener or disable features
            return;
        }

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer == null) {
            Log.w(TAG, "Accelerometer sensor not available on this device.");
            // Handle this case: perhaps notify the listener or disable features
            // The helper might still function for idling if other sensors were used,
            // but for this specific implementation, motion detection won't work.
        }

        // Initialize the idle runnable
        this.idleRunnable = () -> {
            if (this.listener != null) {
                this.listener.onIdle();
            }
        };
    }

    public void start() {
        if (accelerometer != null && sensorManager != null) {
            // SENSOR_DELAY_UI is appropriate if updates are for UI.
            // Consider SENSOR_DELAY_GAME or SENSOR_DELAY_NORMAL for other use cases.
            boolean registered = sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            if (!registered) {
                Log.e(TAG, "Failed to register accelerometer listener.");
                // Handle registration failure if necessary
                return;
            }
            resetIdleTimer();
        } else {
            Log.w(TAG, "Cannot start AccelerometerHelper: sensor or manager not available.");
        }
    }

    public void stop() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
        // Always try to remove callbacks, even if the sensor wasn't available,
        // to ensure cleanup if it was somehow started partially.
        mainThreadHandler.removeCallbacks(idleRunnable);
    }

    private void resetIdleTimer() {
        mainThreadHandler.removeCallbacks(idleRunnable);
        mainThreadHandler.postDelayed(idleRunnable, IDLE_DELAY_MS);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // A common way to calculate linear acceleration (excluding gravity)
            // This is a simplified approach. A high-pass filter is more robust.
            double currentAcceleration = Math.sqrt(x * x + y * y + z * z);
            double accelerationDelta = Math.abs(currentAcceleration - SensorManager.GRAVITY_EARTH);

            // Simple shake detection logic
            // Consider adding lastShakeTime to prevent multiple rapid detections
            // long currentTime = System.currentTimeMillis();
            // if ((currentTime - lastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MS) {
            if (accelerationDelta > SHAKE_THRESHOLD) {
                // lastShakeTime = currentTime;
                if (listener != null) {
                    // If the listener updates UI, ensure it's on the main thread.
                    // Since onSensorChanged can be frequent, posting every time might be
                    // too much. It's often better if the listener itself handles
                    // main thread posting if it needs to update UI.
                    // For simplicity here, calling directly. If listener updates UI,
                    // it should handle threading.
                    listener.onMotionDetected();
                }
                resetIdleTimer(); // Reset idle timer on any significant motion
            }
            // }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You can log accuracy changes if needed for debugging or specific features.
        // Log.i(TAG, "Accuracy changed for sensor " + sensor.getName() + ": " + accuracy);
    }
}