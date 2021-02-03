package io.my.springsecurityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloResources {

    @Autowired(required = true)
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;


    @RequestMapping({"/hello"})

    @Secured({ "ROLE_ADMIN" })
    public ResponseEntity<?>  hello(){
        return ResponseEntity.ok("hello");
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AuthnticationReq req){
        Authentication user = new UsernamePasswordAuthenticationToken(req.getPassword(),req.getPassword());
        Authentication authenticate = authenticationManager.authenticate(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        String s = JwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationRes(s));

    }
}
