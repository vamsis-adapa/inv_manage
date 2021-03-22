package sai_adapa.projs.inv_management.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.io.ItemWithRating;
import sai_adapa.projs.inv_management.model.items.Rating;
import sai_adapa.projs.inv_management.model.users.Users;

@Service
@Getter
@Setter
public class RatingService {

    KafkaTemplate<String, String> kafkaTemplate;

    private String kafkaAddress;
    private String ratingServiceAddress;
    private String resourceLocation;
    final WebClient walletWebClient;
    {
        ratingServiceAddress = "https://localhost:8080";
        resourceLocation = "rating";

    }

    public RatingService() {
        this.walletWebClient = WebClient.builder().baseUrl(ratingServiceAddress)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void getRating(Long itemId)
    {
        RestTemplate restTemplate = new RestTemplate();



    }



    public void rateItem(Item item, Rating rating, Users users) {
        ObjectMapper objectMapper = new ObjectMapper();

        ItemWithRating itemWithRating = new ItemWithRating(item, rating,users);
        try {

            System.out.println(objectMapper.writeValueAsString(itemWithRating));
            kafkaTemplate.send("Rating", objectMapper.writeValueAsString(itemWithRating));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
