package com.example.gesemp.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //On vérifie si c'est le resresh token qui est solicité oubien le access token
        if (request.getServletPath().equals("/refreshToken")){
            filterChain.doFilter(request, response);
        }
        //On recupère le token dans le header Authorization
        String authorizationToken= request.getHeader("Authorization");
        if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
            try{
                //On retire le prefixe Bearer
                String jwt= authorizationToken.substring(7);
                //On charge l'algorithme qui sera utilisé pour décoder ce token
                Algorithm algorithm= Algorithm.HMAC256("monsecret");
                //On build l'algorithme dans verifer de type JWTVerifier
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                //On extrait le JWT décodé dans un objet DecodedJWT
                DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                //On extrait les ids de ce JWT décodé notamment le subject et le claim "roles"
                String email= decodedJWT.getSubject();
                String[] roles= decodedJWT.getClaim("roles").asArray(String.class);

                //On cree une authentification user de type UsernamePasswordAuthenticationToken
                Collection<GrantedAuthority> authorities= new ArrayList<>();
                for (String r:roles){
                    authorities.add(new SimpleGrantedAuthority(r));
                }
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(email,null, authorities);

                //On modifie le context avec une authentication d'utilisateur
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                //Enfin on passe au filtre suivant
                filterChain.doFilter(request, response);
            }
            catch (Exception e){
                response.setHeader("error_message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }
}
