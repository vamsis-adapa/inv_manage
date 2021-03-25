package sai_adapa.projs.inv_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration

public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        return new JedisConnectionFactory();
    }


}
