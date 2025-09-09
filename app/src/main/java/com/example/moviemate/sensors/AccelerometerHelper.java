// File: src/main/java/com/example/moviemate/sensors/AccelerometerHelper.java
package com.example.moviemate.sensors;
// ... imports for Sensor, SensorManager, etc.

public class AccelerometerHelper implements SensorEventListener {

    public interface MotionListener {
        void onMotionDetected();
        void onIdle();
    }

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MotionListener listener;
    private Handler idleHandler = new Handler(Looper.getMainLooper());
    private Runnable idleRunnable;

    private static final long IDLE_DELAY_MS = 3000; // 3 seconds
    private static final float SHAKE_THRESHOLD = 3.25f; // Adjust as needed

    public AccelerometerHelper(Context context, MotionListener listener) {
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        idleRunnable = () -> listener.onIdle();
    }

    public void start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        resetIdleTimer();
    }

    public void stop() {
        sensorManager.unregisterListener(this);
        idleHandler.removeCallbacks(idleRunnable);
    }

    private void resetIdleTimer() {
        idleHandler.removeCallbacks(idleRunnable);
        idleHandler.postDelayed(idleRunnable, IDLE_DELAY_MS);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        double acceleration = Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;

        if (acceleration > SHAKE_THRESHOLD) {
            listener.onMotionDetected();
            resetIdleTimer();
        }
    }
    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}

// File: src/main/java/com/example/moviemate/sensors/LocationHelper.java
package com.example.moviemate.sensors;
// ... imports for Location, Geocoder, FusedLocationProviderClient etc.

public class LocationHelper {
    public interface LocationListener {
        void onRegionDetermined(String region); // e.g., "US", "DE"
    }

    private FusedLocationProviderClient fusedLocationClient;
    private Context context;

    public LocationHelper(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void requestLocation(LocationListener listener) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // In a real app, you'd request permission here. For now, assume it's granted.
            listener.onRegionDetermined("US"); // Default
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        String countryCode = addresses.get(0).getCountryCode();
                        listener.onRegionDetermined(countryCode);
                    } else {
                        listener.onRegionDetermined("US"); // Default
                    }
                } catch (IOException e) {
                    listener.onRegionDetermined("US"); // Default on error
                }
            } else {
                listener.onRegionDetermined("US"); // Default
            }
        });
    }
}