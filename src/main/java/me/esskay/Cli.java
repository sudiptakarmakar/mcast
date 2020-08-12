package me.esskay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;


@Slf4j
@CommandLine.Command(
        name="MultiCastCli",
        description="Command line interface for MultiCast CLI",
        headerHeading="MultiCast CLI Usage:%n%n")
@ToString
public class Cli {

    private static final String DEFAULT_MCAST_ADDR = "224.0.0.1";
    private static final int DEFAULT_MCAST_PORT = 18000;

    @ToString
    public static class Type {
        @CommandLine.Option(
                names = "--receiver",
                required = true)
        public boolean receiver;

        @CommandLine.Option(
                names = "--sender",
                required = true)
        public boolean sender;
    }

    @Getter @Setter
    @CommandLine.ArgGroup(
            exclusive = true,
            multiplicity = "1")
    private Type appType;

    @Getter @Setter
    @CommandLine.Option(names={"-a", "--address"},
            description="IP Address")
    private String address = DEFAULT_MCAST_ADDR;

    @Getter @Setter
    @CommandLine.Option(names={"-p", "--port"},
            description="Port")
    private int port = DEFAULT_MCAST_PORT;
}