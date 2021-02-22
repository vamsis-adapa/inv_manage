package sai_adapa.projs.inv_management.tools;

import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;

@Configuration
public class RequestLogger {
    Logbook logbook = Logbook.create();
}
