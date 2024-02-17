package test.de.turnertech.measures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OSGiIT {

    @Test
    void verifyOSGiManifest() {
        assertDoesNotThrow(() -> {
            final String modulePath = System.getProperty("jdk.module.path", "");
            final String classPath = modulePath.contains("biz.aQute.bnd") ? modulePath : System.getProperty("java.class.path", "");

            // We execute using the class path for simplicity.
            final ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", classPath, "aQute.bnd.main.bnd", "verify", "target/measures-1.2.0-SNAPSHOT.jar");
            processBuilder.inheritIO();
            final Process process = processBuilder.start();
            assertEquals(0, process.waitFor(), "OSGi Package Verification failed. Something is wrong with the Manifest.");
        });

    }

}
