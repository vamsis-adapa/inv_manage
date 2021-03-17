package sai_adapa.projs.inv_management.tools;

import org.springframework.stereotype.Component;
import org.zalando.logbook.Logbook;

@Component
public class RequestLogger {
    Logbook logbook = Logbook.create();
}
