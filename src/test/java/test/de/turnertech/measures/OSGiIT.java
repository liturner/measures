package test.de.turnertech.measures;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OSGiIT {

    private String getJarPath() {
        final File targetDir = new File("target");
        final File[] files = targetDir.listFiles((dirName, fileName) -> dirName.getName().equals("target") &&
                fileName.startsWith("measures-") &&
                fileName.endsWith(".jar") &&
                !fileName.contains("javadoc") &&
                !fileName.contains("source"));
        if (files != null && files.length == 1) {
            return files[0].getPath();
        }
        return "Jar-File-Not-Found";
    }

    @Test
    void verifyOSGiManifest() {
        assertDoesNotThrow(() -> {
            final String modulePath = System.getProperty("jdk.module.path", "");
            final String classPath = modulePath.contains("biz.aQute.bnd") ? modulePath : System.getProperty("java.class.path", "");

            // We execute using the class path for simplicity.
            final ProcessBuilder processBuilder = new ProcessBuilder("java", "-cp", classPath, "aQute.bnd.main.bnd", "verify", getJarPath());
            processBuilder.inheritIO();
            final Process process = processBuilder.start();
            assertEquals(0, process.waitFor(), "OSGi Package Verification failed. Something is wrong with the Manifest.");
        });

    }

}
