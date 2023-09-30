package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("Filter is running...");
            //토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능.
            if(token != null && !token.equalsIgnoreCase("null")){
                //userId 가져오기. 위조된 경우 예외처리 된다.
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID : "  + userId);
                //인증완료; SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
                //스프링이 userId를 찾을수 있는 이유가 여기에 있다.
                //UsernamePasswordAuthenticationToken을 생성할때 생성자의 첫번째 매개변수가 AuthenticationPriscipal이다.
                //AuthenticationPrincipal에 스트링형의 userId를 넣어줬다. 또 이 오브젝트를 SecurityContext에 등록했다.
                //스프링은 컨트롤러메서드를 부를때 @AuthenticationPrinciPal어노테이션이 있다는 것을 알고 있고
                //SecurityContextHolder에서 SecurityContext::Authentication 즉 UsernamdePasswordAuthenticationToken
                //오브젝트를 가져온다, 이 오브젝트에서 AuthenticationPrinciPal을 가져와 컨트롤러 메서드에 넘겨준다,
                //AuthenticationPrincipal를 String형의 오브젝트로 지정했기 때문에 컨트롤러의 @AuthenticationPrincipal형으로
                //String을 사용해야 한다는 것을 미리 알고 있다.

            }
        }catch (Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request){
        //Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
