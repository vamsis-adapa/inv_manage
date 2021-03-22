package sai_adapa.projs.inv_management.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sai_adapa.projs.inv_management.exceptions.PaymentFailedException;
import sai_adapa.projs.inv_management.model.enums.PaymentStatus;
import sai_adapa.projs.inv_management.model.io.EmailsAndAmount;
import sai_adapa.projs.inv_management.model.io.PaymentResponse;
import sai_adapa.projs.inv_management.model.items.Item;
import sai_adapa.projs.inv_management.model.orders.Orders;

import java.util.Locale;
import java.util.concurrent.Future;

@Service
@Slf4j
public class PaymentService {

    final WebClient walletWebClient;
    private OrderService orderService;
    private String baseUrl;
    private String resourcePath;

    {
        baseUrl = "https://316278416b16.ngrok.io";
        resourcePath = "/items";//"/wallet-service";
    }


    public PaymentService() {
        this.walletWebClient = WebClient.builder().baseUrl(baseUrl).defaultCookie("JSESSIONID", getAccessToken())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    //TODO CHANGE TOKEN
    private String getAccessToken() {
        return "Y29veW9PVFQtb3R0QGdtYWlsLmNvbQ==";
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
        try {
            if (!paymentResponse.getStatus().equals("successful")) {
                throw new PaymentFailedException();
            }
        } catch (NullPointerException e) {
            throw new PaymentFailedException();
        }

    }

    public PaymentStatus payForOrder(Orders orders, String userEmail, String vendorEmail, Double amount) {
        Integer amountInteger = ceilDoubleToInteger(amount);
        EmailsAndAmount emailsAndAmount = EmailsAndAmount.builder().amount(amountInteger).email(userEmail).secondEmail(vendorEmail).build();


        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", getAccessToken());
            HttpEntity<EmailsAndAmount> request = new HttpEntity<EmailsAndAmount>(emailsAndAmount, headers);
            PaymentResponse paymentResponse = restTemplate.postForObject(baseUrl + resourcePath, request, PaymentResponse.class);
            checkPaymentResponse(paymentResponse);

        } catch (HttpClientErrorException | PaymentFailedException e) {
            orderService.changeOrderStatus(orders, PaymentStatus.Failed);
            return PaymentStatus.Failed;
        }
        orderService.changeOrderStatus(orders, PaymentStatus.Successful);
        return PaymentStatus.Successful;

    }

    //TODO use more lambdas
    //TODO also handle async func properly
    public Future<PaymentStatus> payForOrderWebClient(Orders orders, String userEmail, String vendorEmail, Double amount) {

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
            System.out.println(paymentResponse);

            checkPaymentResponse(paymentResponse);
        } catch (JsonProcessingException | PaymentFailedException e) {

            orderService.changeOrderStatus(orders, PaymentStatus.Failed);
            return new AsyncResult<PaymentStatus>(PaymentStatus.Failed);
        }
        orderService.changeOrderStatus(orders, PaymentStatus.Successful);
        return new AsyncResult<PaymentStatus>(PaymentStatus.Successful);
    }
}
