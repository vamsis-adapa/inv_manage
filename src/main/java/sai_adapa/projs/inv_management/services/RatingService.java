package sai_adapa.projs.inv_management.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.items.ItemWithRating;
import sai_adapa.projs.inv_management.model.items.Rating;

@Service
@Getter
@Setter
public class RatingService {

    KafkaTemplate<String, ItemWithRating> kafkaTemplate;

    private String address;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, ItemWithRating> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void rateItem(Item item, Rating rating) {
        ItemWithRating itemWithRating = new ItemWithRating(item, rating);

        kafkaTemplate.send("ItemWithRating", itemWithRating);
        return;
    }
}
