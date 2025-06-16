package com.springboot.car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable()) 
		.authorizeHttpRequests(authorize -> authorize
				
				.requestMatchers("/api/user/signup").permitAll()
				.requestMatchers("/api/user/token").authenticated()
				.requestMatchers("/api/user/details").authenticated()
				
				.requestMatchers("/api/car/all").permitAll()
				.requestMatchers("/api/car/add/{ownerId}").hasAnyAuthority("CAROWNER")
				.requestMatchers("/api/car/search/price").permitAll()
				.requestMatchers("/api/car/filter").permitAll()
				.requestMatchers("/api/car/available").permitAll()
				
				
				.requestMatchers("/api/customer/add").permitAll()
				.requestMatchers("/api/customer/update/{id}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/get-one").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/delete/{id}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/get-all").hasAnyAuthority("MANAGER","STAFF")
				.requestMatchers("/api/customer/search").hasAnyAuthority("MANAGER","STAFF")
				
				
				
				.requestMatchers("/api/carowner/get-all").hasAnyAuthority("MANAGER")				
				.requestMatchers("/api/carowner/add").permitAll()
				.requestMatchers("/api/carowner/update/{id}").hasAuthority("CAROWNER")
				.requestMatchers("/api/carowner/get-one").hasAuthority("CAROWNER")
				.requestMatchers("/api/carowner/delete/{id}").hasAuthority("CAROWNER")
				.requestMatchers("/api/carowner/search").hasAnyAuthority("MANAGER")	
				
				
				
				.requestMatchers("/api/customer/book/car/{customerId}/{carId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/booking/update/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/booking/cancel/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/view/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/book/car/{carId}").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/car/book/customer/{customerId}").hasAnyAuthority("MANAGER")
				
				
				.requestMatchers("/api/payment/add/{customerId}/{bookingId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/payment/update/{paymentId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/payment/cancel/{paymentId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/payment/view/{paymentId}").hasAnyAuthority("CUSTOMER", "MANAGER")
				.requestMatchers("/api/payment/get-all").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/payment/customer/{customerId}").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/payment/booking/{bookingId}").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/payment/search").hasAnyAuthority("MANAGER")

				
				
				.requestMatchers("/api/review/add/{customerId}/{carId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/review/rating").hasAnyAuthority("MANAGER","STAFF")
				
				
				
				.requestMatchers("/api/manager/add").permitAll()
				
				.anyRequest().authenticated() 
				)
		 .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		 .httpBasic(Customizer.withDefaults()); 
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {  
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) 
			throws Exception {
		  return auth.getAuthenticationManager();
	 }
}
