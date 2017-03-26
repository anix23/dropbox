package sda.dropbox.dropbox;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FileListener {

    private ExecutorService executor;

    public ExecutorService getExecutor(int threads) {
        return this.executor = Executors.newFixedThreadPool(threads);
    }

    public void listen(String path) {
        Path p = Paths.get(path);

        try (WatchService watch = p.getFileSystem().newWatchService()) {
            p.register(watch, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key = null;
            while (true) {
                key = watch.take();
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    Path newPath = ((WatchEvent<Path>) watchEvent).context();
                    // Output
                    System.out.println("New path created: " + newPath);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

