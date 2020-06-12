
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

import java.io.File;
import java.io.IOException;
import java.util.List;


public class WatchServiceSales {

    //private static final String watchedDir = "/Users/giovannicaprio/Desktop/data/in/";
    //private static final String watchedDir = "/Users/giovannicaprio/Desktop/teste_nt/microservices-spring-boot/spring-eureka-sales/resources/data/in/";
    private static final String watchedDir = System.getProperty("user.dir") + "/src/main/resources/data/in/";
	
    /*public static void main(String [] args)
            throws IOException {

        new WatchServiceSales().doProcess();
    }*/
	
    public void doProcess()
            throws IOException {

        // (1) Create a new watch service
        WatchService watchService = FileSystems.getDefault().newWatchService();
        System.out.println("Watch service sales at: " + watchedDir );


        // (2) Get the directory to be monitored	
        Path dir = Paths.get(watchedDir);

        // (3) Register the directory to be monitored with the watch service
        WatchKey watchKey = dir.register(watchService,
                ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);			

        // (4) Get and process the events that occur
        INFINITE_WHILE_LOOP:
        while(true) {

            // (4a) Wait for the watch key to be signaled of events
            try {
                watchService.take();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                break INFINITE_WHILE_LOOP;
            }

            // (4b) Get and process the events for the watch key
            List<WatchEvent<?>> eventList = watchKey.pollEvents();
            //System.out.println("Process the pending events for the watch key: " + eventList.size());

            EVENT_FOR_LOOP:
            for(WatchEvent<?> genericEvent: eventList) {
                Boolean fileProcesssed = false;
                // Retrieve and process the event kind
                if (genericEvent.kind() == OVERFLOW) {
				
                    //System.out.println("Overflow event");
                    continue EVENT_FOR_LOOP; // next event
                }
                //else if (genericEvent.kind() == ENTRY_CREATE || genericEvent.kind() == ENTRY_MODIFY) {
                else if (genericEvent.kind() == ENTRY_CREATE) {

                     // else, genericEvent.kind() is WatchEvent.Kind<Path>
                    // values: ENTRY_CREATE... 
                    System.out.println("Path event kind: " + genericEvent.kind());

                    // Retrieve the file name associated with the event
                    Path filePath = (Path) genericEvent.context();
                    
                    //VERIFY EVERYTIME A FILE IS CREATES
                    //copy the entry file to another folder

                    //reads file data
                    FileHandler filehandler = new FileHandler();
                    fileProcesssed = filehandler.readFile(watchedDir + filePath.toString(), filePath.getFileName().toString());

                    //deleta, ou não, o aruivo do diretório
                    /*if(fileProcesssed){
                        //deleta o arquivo 
                        File file = new File(watchedDir + filePath.toString());
                        file.delete();
                    }*/

                }else{
                    System.out.println("Evento não parametrizado para ações do tipo: " + genericEvent.kind());
                }

				
            } // end EVENT_FOR_LOOP (for a watch key)

            // (4c) Reset the watch key
            boolean validKey = watchKey.reset();
            //System.out.println("Watch key reset.");

            if (! validKey) {
                System.out.println("Invalid watch key, close the watch service");
                break INFINITE_WHILE_LOOP;
            }
			
        } // end, INFINITE_WHILE_LOOP

        // (5) Close the watch service
        watchService.close();
        System.out.println("Watch service closed.");

    } // doProcess()
}