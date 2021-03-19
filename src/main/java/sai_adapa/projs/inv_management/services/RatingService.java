package sai_adapa.projs.inv_management.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.io.ItemWithRating;
import sai_adapa.projs.inv_management.model.items.Rating;
import sai_adapa.projs.inv_management.model.users.Users;

@Service
@Getter
@Setter
public class RatingService {

    KafkaTemplate<String, String> kafkaTemplate;

    private String address;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }




    public void rateItem(Item item, Rating rating, Users users) {
        ObjectMapper objectMapper = new ObjectMapper();

        ItemWithRating itemWithRating = new ItemWithRating(item, rating,users);
        try {


            kafkaTemplate.send("Rating", objectMapper.writeValueAsString(itemWithRating));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
