package com.springsecurity.spring_security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.springsecurity.spring_security.models.Post;
import com.springsecurity.spring_security.models.User;
import com.springsecurity.spring_security.respository.PostRepository;
import com.springsecurity.spring_security.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Slf4j
@SpringBootApplication
public class SpringSecurityApplication {

	private static String SIGNNED_JWT = "zb5yUHCgEBmZ4Q000Xnx0i96akPeqeFcOk21etJR/EC4ndnw3Qz9M/Oy09c2QMpI";

	public static void generateToken(String username){
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(username)
				.issuer("vnedu.vn")
				.issueTime(new Date())
				.expirationTime(new Date())
				.claim("customClaim","custom")
				.build();

		Payload payload = new Payload(jwtClaimsSet.toJSONObject());
		JWSObject jwsObject = new JWSObject(header,payload);

		try {
			jwsObject.sign(new MACSigner(SIGNNED_JWT.getBytes()));
			System.out.println(jwsObject.serialize());
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
		generateToken("admin");
	}



	@Bean
	CommandLineRunner commandLineRunner(PostRepository posts, UserRepository userRepository, PasswordEncoder passwordEncoder){

		return args -> {
			userRepository.save(new User("admin","password","ADMIN"));
			userRepository.save(new User("user","password","USER"));
			posts.save(new Post("Title","Title is a description","TitleAd","Nguyen Trung Kien"));
		};
	}
}
