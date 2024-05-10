package mcapi.davidout.minigame.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Folder {

    public interface DirectoryCopyCallback {
        void onComplete(boolean succes, File folder);
        void onError(Exception exception);
    }


    public static void copyDirectoryAsync(Plugin plugin, File sourceDirectory, File destinationDirectory, List<String> filesToIgnore, DirectoryCopyCallback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                copyDirectory(sourceDirectory, destinationDirectory, filesToIgnore);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    callback.onComplete(true, destinationDirectory);
                }, 2);
            } catch (IOException e) {
                callback.onError(e);
            }
        });
    }

    public static void copyDirectory(File sourceDirectory, File destinationDirectory, List<String> ignoreFiles) throws IOException {
        if (!destinationDirectory.exists())
            destinationDirectory.mkdirs();

        File[] files = sourceDirectory.listFiles();

        if(files == null) {
            return;
        }

        for (File file : files) {
            File destinationFile = new File(destinationDirectory, file.getName());
            if(ignoreFiles.contains(file.getName())) {
                continue;
            }

            if(file.isDirectory()) {
                copyDirectory(file, destinationFile, ignoreFiles);
                continue;
            }

            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
