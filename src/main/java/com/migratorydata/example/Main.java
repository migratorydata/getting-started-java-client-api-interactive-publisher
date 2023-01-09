package com.migratorydata.example;

import com.migratorydata.client.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        Map<String, ScheduledFuture<?>> tasks = new HashMap<>();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        
        // create a MigratoryData client
        final MigratoryDataClient client = new MigratoryDataClient();

        // Define the log listener and verbosity
        client.setLogListener(new MigratoryDataLogListener() {
            private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");

            @Override
            public void onLog(String log, MigratoryDataLogLevel level) {
                String isoDateTime = sdf.format(new Date(System.currentTimeMillis()));
                System.out.println(String.format("[%1$s] [%2$s] %3$s", isoDateTime, level, log));
            }
        }, MigratoryDataLogLevel.DEBUG);

        // attach the entitlement token
        client.setEntitlementToken(Config.token);

        // Define the listener to handle live message, status notifications and
        // interactive publishing events.
        // In your application it is recommended to define a regular class
        // instead of the anonymous class we define here for concision
        client.setListener(new MigratoryDataInteractiveListener() {

            public void onStatus(String status, String info) {
                System.out.println("Got Status: " + status + " - " + info);
            }

            public void onMessage(MigratoryDataMessage message) {
                System.out.println("Got Message: " + message);
            }

            public void onSubscribe(String subject) {
                System.out.println("Got onSubscribe: " + subject + ", start publishing!");

                // When a client subscribes for the first time to subject
                // start a repetitive task  to publish a message every 5 seconds for that subject
                tasks.put(subject, executor.scheduleAtFixedRate(() -> {
                    client.publish(new MigratoryDataMessage(subject, ("Hello with timestamp: " + System.currentTimeMillis()).getBytes()));
                }, 1000, 5000, TimeUnit.MILLISECONDS));
            }

            public void onUnsubscribe(String subject) {
                System.out.println("Got onUnsubscribe: " + subject + ", stop publishing!");

                // When the last client unsubscribes from subject
                // stop the task from publishing for that subject.
                ScheduledFuture<?> task = tasks.remove(subject);
                task.cancel(false);
            }
        });

        client.advertiseInteractiveSubjects(Arrays.asList(Config.wildcardSubject));

        client.setEncryption(Config.encryption);

        // set server to connect to the MigratoryData server
        client.setServers(Config.server);

        // connect to the MigratoryData server
        client.connect();

        // add a shutdown hook to catch CTRL-C and cleanly shutdown this client
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                executor.shutdown();
                client.disconnect();
            }
        });

        // keep program running
        while (true) {
            Thread.sleep(1000);
        }
    }
}