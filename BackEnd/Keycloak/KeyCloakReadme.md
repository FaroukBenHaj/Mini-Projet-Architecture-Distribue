# README File for Keycloak and Spring Boot Integration

## Overview
This repository contains a guide and code for integrating Keycloak, an open-source identity and access management tool, with a Spring Boot application. This setup utilizes Spring Security to secure REST APIs using Keycloak as the authentication provider. The instructional content is derived from a comprehensive video tutorial explaining the process in detail.

## Table of Contents
1. Introduction to Keycloak
2. Features of Keycloak
3. Advantages of Using Keycloak
4. Setting Up Keycloak
5. Integrating Keycloak with Spring Boot
6. Testing the Integration
7. Conclusion

## 1. Introduction to Keycloak
Keycloak provides centralized authentication and authorization services, allowing developers to implement security for their applications more easily. It supports protocols like OAuth 2.0 and OpenID Connect, providing features such as single sign-on (SSO), user federation, and role-based access control.

## 2. Features of Keycloak
Some key features include:
- **Single Sign-On (SSO)**: Users authenticate once and gain access to multiple applications.
- **Identity Brokering**: Supports social logins and can act as an identity broker for external identity providers.
- **User Federation**: Connects with existing user databases (e.g., LDAP, Active Directory).
- **Fine-Grained Authorization**: Allows detailed control over user permissions.
- **Centralized Management Console**: Offers an intuitive UI for managing users and roles.

## 3. Advantages of Using Keycloak
- **Open Source**: Free to use and backed by a vibrant community.
- **Versatile**: Works with various applications and authentication protocols.
- **Scalability**: Can handle numerous users effectively, making it suitable for enterprises.
- **Security Features**: Comprehensive options for user management and security policies.
- **Customization**: As an open-source project, it can be tailored to specific organizational needs.

## 4. Setting Up Keycloak
### 4.1. Installation
- Make sure you have Docker installed.
- Use the following command to pull the Keycloak image and start a container:
  ```bash
  docker run -d --name keycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 8080:8080 jboss/keycloak
  ```
- Access the Keycloak admin console via `http://localhost:8080` and log in using `admin/admin`.

### 4.2. Configuration
- Create a new realm (e.g., `mini-projet-architecture-distribue`).
- Set up clients, roles, and users as per your requirements.
- Ensure that you configure clients appropriately, specifying valid redirect URIs and allowed origins.

## 5. Integrating Keycloak with Spring Boot
### 5.1. Dependencies
Include dependencies for Spring Security and OAuth2 Resource Server in your Maven `pom.xml` file:
```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

### 5.2. Configuration
Configure your Spring Boot `application.properties` or `application.yml` to specify Keycloak's issuer URI and JWK set URI for JWT validation:
```yaml
spring:
  security:
    oauth2:
      resource:
        server:
          jwt:
            issuer-uri: http://localhost:8080/realms/mini-projet-architecture-distribue
            jwk-set-uri: http://localhost:8080/realms/mini-projet-architecture-distribue/protocol/openid-connect/certs
```

### 5.3. Security Configuration
Implement a Security Configuration class to define access rules and configure the application as an OAuth 2.0 resource server:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeHttpRequests()
      .anyRequest().authenticated()
      .and()
      .oauth2ResourceServer()
      .jwt();
    return http.build();
  }
}
```

## 6. Testing the Integration
- Once both Keycloak and your Spring Boot application are running, use Postman or a similar tool to test API endpoints.
- Attempt to access protected resources without a token to verify that authorization is enforced.
- Generate a token from Keycloak for a created user and use this token to successfully access the secured endpoints.

## 7. Conclusion
This README provides a structured approach to integrating Keycloak with a Spring Boot application, ensuring secure access to your REST APIs. If you have any questions or need further clarification on any steps, feel free to leave comments or reach out.

By following this guide and utilizing the provided resources, you should be able to achieve a robust security mechanism for your applications that leverage Keycloak for identity management. Happy coding!