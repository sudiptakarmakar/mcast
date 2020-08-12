package me.esskay;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

import java.net.InetAddress;

@Slf4j
public class App {

    public static void main(String[] args) throws Exception {

        // Signal.handle(new Signal("INT"), signal -> {
        //     log.info("Received INT signal");
        //     System.exit(0);
        // });
        //
        // Signal.handle(new Signal("TERM"), signal -> {
        //     log.info("Received TERM signal");
        //     System.exit(0);
        // });

        final Cli cli = CommandLine.populateCommand(new Cli(), args);
        log.info("{}", cli);
        System.setProperty("java.net.preferIPv4Stack", "true");

        if (cli.getAppType().receiver) {
            final Thread receiver = new Thread(new Receiver(InetAddress.getByName(cli.getAddress()), cli.getPort()));
            receiver.setName("Receiver");
            receiver.start();

        } else if (cli.getAppType().sender) {
            final Thread sender = new Thread(new Sender(InetAddress.getByName(cli.getAddress()), cli.getPort()));
            sender.setName("Sender");
            sender.start();
        }
    }
}
