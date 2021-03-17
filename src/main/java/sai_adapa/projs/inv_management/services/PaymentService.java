package sai_adapa.projs.inv_management.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sai_adapa.projs.inv_management.exceptions.PaymentFailedException;
import sai_adapa.projs.inv_management.model.io.EmailsAndAmount;
import sai_adapa.projs.inv_management.model.io.PaymentResponse;
import sai_adapa.projs.inv_management.model.orders.Orders;
import sai_adapa.projs.inv_management.tools.enums.PaymentStatus;

import java.util.concurrent.Future;

@Service
public class PaymentService {

    final WebClient walletWebClient;
    private OrderService orderService;

    public PaymentService() {//TODO CHANGE TOKEN
        this.walletWebClient = WebClient.builder().baseUrl("http://localhost:765").defaultCookie("JSESSIONID", getAccessToken())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    private String getAccessToken() {
        return "accessToken";
    }

    private PaymentResponse convertStringToObject(String paymentResponseString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(paymentResponseString, PaymentResponse.class);
    }

    private Integer ceilDoubleToInteger(Double k) {
        k = Math.ceil(k);
        return k.intValue();
    }

    private void checkPaymentResponse(PaymentResponse paymentResponse) throws PaymentFailedException {
        if (!paymentResponse.getStatus().equals("success")) {
            throw new PaymentFailedException();
        }

    }


    //todo use more lambdas
    public Future<PaymentStatus> payForOrder(Orders orders, String userEmail, String vendorEmail, Double amount) {

        Integer amountInteger = ceilDoubleToInteger(amount);

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = walletWebClient.method(HttpMethod.POST);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/wallet-service");

        EmailsAndAmount emailsAndAmount = EmailsAndAmount.builder().amount(amountInteger).email(userEmail).secondEmail(vendorEmail).build();

        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(emailsAndAmount);


        Mono<String> walletResponse = headersSpec.exchangeToMono(response -> {

            if (response.statusCode()
                    .equals(HttpStatus.OK)) {
                return response.bodyToMono(String.class);
            } else if (response.statusCode()
                    .is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return response.createException()
                        .flatMap(Mono::error);
            }
        });

        try {
            PaymentResponse paymentResponse = convertStringToObject(walletResponse.block());
            checkPaymentResponse(paymentResponse);
        } catch (JsonProcessingException | PaymentFailedException e) {

            orderService.changeOrderStatus(orders, PaymentStatus.Failed);
            return new AsyncResult<PaymentStatus>(PaymentStatus.Failed);
        }

        orderService.changeOrderStatus(orders, PaymentStatus.Successful);
        return new AsyncResult<PaymentStatus>(PaymentStatus.Successful);

    }
}
