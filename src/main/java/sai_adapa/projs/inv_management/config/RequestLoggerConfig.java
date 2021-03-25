package sai_adapa.projs.inv_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;
import org.zalando.logbook.json.JsonHttpLogFormatter;

@Configuration
public class RequestLoggerConfig {

    @Bean
    public Logbook generateLogbook() {
        Logbook logbook = Logbook.builder()

                .sink(new DefaultSink(
                        new DefaultHttpLogFormatter(),
                        new DefaultHttpLogWriter()
                ))
                .build();
        return logbook;
    }
}
