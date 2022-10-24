package com.example.gesemp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gesemp.models.AppRole;
import com.example.gesemp.models.Employee;
import com.example.gesemp.servicesImpl.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/")
public class SessionController {

    private final EmployeeServiceImpl employeeService;

    @GetMapping("refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //On recupère le token dans le header Authorization
        String authorizationToken= request.getHeader("Authorization");
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
            try {
                //On retire le prefixe Bearer
                String jwt = authorizationToken.substring(7);
                //On charge l'algorithme qui sera utilisé pour décoder ce token
                Algorithm algorithm = Algorithm.HMAC256("monsecret");
                //On build l'algorithme dans verifer de type JWTVerifier
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                //On extrait le JWT décodé dans un objet DecodedJWT
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                //On extrait les ids de ce JWT décodé notamment le subject et le claim "roles"
                String email = decodedJWT.getSubject();

                if (email.equals("bidiasken@gesemp.com")) {
                    User employee= new User("bidiasken@gesemp.com", "", Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
                    List<String> authorithies= new ArrayList<>();
                    employee.getAuthorities().forEach(
                            (r)->{authorithies.add(String.valueOf(new SimpleGrantedAuthority(r.toString())));}
                    );
                    //On genere un nouveau access_token
                    String jwtAccessToken= JWT.create()
                            .withSubject(employee.getUsername())    //username or email
                            .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000)) // temps d'expiration = 5min (en millisecondes)
                            .withIssuer(request.getRequestURL().toString()) // adresse url source de ce token
                            .withClaim("roles", authorithies)
                            .withAudience("frontWebApp", "frontMobileApp")
                            .sign(algorithm);

                    Map<String, String> tokens= new HashMap<>();
                    tokens.put("access_token", jwtAccessToken);
                    tokens.put("refresh_token", jwt);
                    response.setContentType("application/json");

                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }else{
                    Employee employee= employeeService.loadEmployeeByEmail(email);

                    //On genere un nouveau access_token
                    String jwtAccessToken= JWT.create()
                            .withSubject(employee.getEmail())    //username or email
                            .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000)) // temps d'expiration = 5min (en millisecondes)
                            .withIssuer(request.getRequestURL().toString()) // adresse url source de ce token
                            .withClaim("roles", employee.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
                            .withAudience("frontWebApp", "frontMobileApp")
                            .sign(algorithm);

                    Map<String, String> tokens= new HashMap<>();
                    tokens.put("access_token", jwtAccessToken);
                    tokens.put("refresh_token", jwt);
                    response.setContentType("application/json");

                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }

            } catch (Exception e) {
                response.setHeader("error_message", e.getMessage());
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> error= new HashMap<>();
            error.put("message", "refresh token manquant ou incorrect");
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }
}
