package ru.practicum.statisticclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.statisticdto.EndpointHit;
import ru.practicum.statisticdto.ViewStats;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient {

    private static final String API_PREFIX_HIT = "/hit";
    private static final String API_PREFIX_STATS = "/stats";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestTemplate rest;
    private final String serverUrl;

    public StatsClient(@Value("${statistic-service.url}") String serverUrl) {
        this.rest = new RestTemplate();
        this.serverUrl = serverUrl;
    }


    public List<ViewStats> getStats(String start,
                                    String end,
                                    boolean unique,
                                    String[] uris) {

        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "unique", unique,
                "uris", uris
        );
        String path = serverUrl + API_PREFIX_STATS + "?start={start}&end={end}&uris={uris}&unique={unique}";


        ResponseEntity<List<ViewStats>> serverResponse = rest.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, parameters);
        return serverResponse.getBody();
    }

    public void createInfo(EndpointHit endpointHit) {
        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(endpointHit);
        rest.exchange(serverUrl + API_PREFIX_HIT, HttpMethod.POST, requestEntity, Object.class);
    }
}
