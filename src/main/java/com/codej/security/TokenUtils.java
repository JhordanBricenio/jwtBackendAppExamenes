package com.codej.security;

import com.codej.service.Impl.UserDetailServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class TokenUtils {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    private final static String ACCES_TOKEN_SECRET="$2a$10$o9YrobQ1XIDkoByviQDgKOkrAYBbw8./ftLIOcqBhV.JM.mP6YSxG";
    private final static Long ACCES_TOKEN_VALITY_SECOND=2_592_000L;

    public static String createToken(UserDetails user){
        Claims claims=Jwts.claims().setSubject(user.getUsername());
        claims.put("authorities",user.getAuthorities());
        Date now=new Date();
        Date validity=new Date(now.getTime()+ACCES_TOKEN_VALITY_SECOND*1000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes()))
                .compact();

        }
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims=Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username=claims.getSubject();
            List<Map<String,String>> authorities=(List<Map<String,String>>)claims.get("authorities");
            List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
            for (Map<String,String> authority:authorities){
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("authority")));
            }
            UserDetails userDetails=new User(username,"",grantedAuthorities);
            return new UsernamePasswordAuthenticationToken(userDetails,"",grantedAuthorities);

        }catch (JwtException e){
            return null;
        }
    }

}
