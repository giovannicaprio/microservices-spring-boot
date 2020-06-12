
/**
 * CLASSE RESPONSÁVEL POR MONITORAR UM DIRETÓRIO E TOMAR AÇÕES SEMPRE QUE O MESMO FOR MODIFICADO
 */

package com.eureka.sales.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;
import java.util.List;


public class WatchServiceSales {

    private static final String watchedDir = System.getProperty("user.dir") + "/src/main/resources/data/in/";
    public void doProcess()
            throws IOException {

        // (1) Create a new watch service
        WatchService watchService = FileSystems.getDefault().newWatchService();
        System.out.println("Watch service sales at: " + watchedDir );

        Path dir = Paths.get(watchedDir);

        WatchKey watchKey = dir.register(watchService,
                ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);			

        INFINITE_WHILE_LOOP:
        while(true) {

            try {
                watchService.take();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                break INFINITE_WHILE_LOOP;
            }

            List<WatchEvent<?>> eventList = watchKey.pollEvents();

            EVENT_FOR_LOOP:
            for(WatchEvent<?> genericEvent: eventList) {
                if (genericEvent.kind() == OVERFLOW) {
                    continue EVENT_FOR_LOOP; // next event
                }
                else if (genericEvent.kind() == ENTRY_CREATE) {

                    System.out.println("Path event kind: " + genericEvent.kind());

                    Path filePath = (Path) genericEvent.context();
                    
                    //VERIFY EVERYTIME A FILE IS CREATES
                    //copy the entry file to another folder

                    //reads file data
                    FileHandler filehandler = new FileHandler();
                    filehandler.readFile(watchedDir + filePath.toString(), filePath.getFileName().toString());

                }else{
                    System.out.println("Evento não parametrizado para ações do tipo: " + genericEvent.kind());
                }

				
            } // end EVENT_FOR_LOOP (for a watch key)

            boolean validKey = watchKey.reset();
            //System.out.println("Watch key reset.");

            if (! validKey) {
                break INFINITE_WHILE_LOOP;
            }
			
        } // end, INFINITE_WHILE_LOOP

        watchService.close();
        System.out.println("Watch service closed.");

    } // doProcess()
}