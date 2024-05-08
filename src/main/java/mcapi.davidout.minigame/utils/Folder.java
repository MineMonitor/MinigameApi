package mcapi.davidout.minigame.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Folder {

    public interface DirectoryCopyCallback {
        void onComplete(boolean succes, File folder);
        void onError(Exception exception);
    }


    public static void copyDirectoryAsync(Plugin plugin, File sourceDirectory, File destinationDirectory, DirectoryCopyCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                copyDirectory(sourceDirectory, destinationDirectory);
                callback.onComplete(true, destinationDirectory);
            } catch (IOException e) {
                callback.onError(e);
            }
        });
    }

    public static void copyDirectory(File sourceDirectory, File destinationDirectory) throws IOException {
        if (!destinationDirectory.exists())
            destinationDirectory.mkdirs();

        File[] files = sourceDirectory.listFiles();

        if(files == null) {
            return;
        }

        for (File file : files) {
            File destinationFile = new File(destinationDirectory, file.getName());

            if(file.isDirectory()) {
                copyDirectory(file, destinationFile);
                continue;
            }

            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }


    }

}
