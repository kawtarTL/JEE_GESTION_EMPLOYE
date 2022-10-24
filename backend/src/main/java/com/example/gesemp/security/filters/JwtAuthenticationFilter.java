package com.example.gesemp.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //Utilisateur essaie de se connaecter
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        String email, password;

        try {
            System.out.println("attemptAuthentication");
            Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            email= requestMap.get("email");
            password= requestMap.get("password");

            if (email.equals("bidiasken@gesemp.com") && password.equals("admin")) {
                System.out.println("Tentativie de connexion administrateur ...");
                authentication=new AdminAuthentication();
                SecurityContext context= SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }else{
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(email, password);
                authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }

        }catch (Exception e){
            Map<String, String> error_message=new HashMap<>();
            error_message.put("message", "Ce compte n'est pas enregistré");
            response.setContentType("application/json");
            try {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                new ObjectMapper().writeValue(response.getOutputStream(), error_message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return authentication;
    }


    //Authentification reussi
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("succesfulAuthentication");
        User user=(User) authResult.getPrincipal();
        System.out.println("user authentifié: "+user);
        List<String> authorities= new ArrayList<>();
        user.getAuthorities().forEach(a->{
            authorities.add(a.toString());
        });
        System.out.println("avec les roles "+authorities);
        //On calcule la signature du jwt grace a un algorithme HMAC256 basé sur secret
        Algorithm algorithm= Algorithm.HMAC256("monsecret");
        String jwtAccessToken= JWT.create()
                .withSubject(user.getUsername())    //username or email
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10 * 60 * 1000)) // temps d'expiration = 5min (en millisecondes)
                                .withIssuer(request.getRequestURL().toString()) // adresse url source de ce token
                                        .withClaim("roles", authorities)
                                                .withAudience("frontWebApp", "frontMobileApp")
                                                        .sign(algorithm);

        //Le refresh token
        String jwtRefreshToken= JWT.create()
                        .withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis()+15*60*1000))
                                        .withAudience("frontWebApp", "frontMobileApp")
                                                .withIssuer(request.getRequestURL().toString())
                                                        .sign(algorithm);

        Map<String, String> tokens= new HashMap<>();
        tokens.put("access_token", jwtAccessToken);
        tokens.put("refresh_token", jwtRefreshToken);
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
