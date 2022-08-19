package com.fpolydatn.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author HangNT
 */
@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final OAuth2AuthorizedClientService authorizedClientService;

    private OAuth2AuthorizedClient createClient(OAuth2AuthenticationToken token){
        return authorizedClientService.loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(), token.getPrincipal().getName());
    }

    private HttpHeaders createHeader(OAuth2AuthenticationToken token){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION,
                String.format("Bearer %s",
                        createClient(token).getAccessToken().getTokenValue()));
        return headers;
    }

    private URI getEndpoint(OAuth2AuthenticationToken token){
        return URI.create(
                createClient(token).getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUri()
        );
    }
    public MyUserDetails getUserDetails(OAuth2AuthenticationToken token){
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<String> request = new RequestEntity<>("", createHeader(token),
                HttpMethod.GET, getEndpoint(token));

        ResponseEntity<MyUserDetails> response = restTemplate.exchange(request, MyUserDetails.class);
        return response.getBody();
    }
}
