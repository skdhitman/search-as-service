package com.solr.clientwrapper.rest;

import com.solr.clientwrapper.domain.dto.UserDTO;
import com.solr.clientwrapper.usecase.authority.ReadAuthority;
import com.solr.clientwrapper.usecase.user.CreateUser;
import com.solr.clientwrapper.usecase.user.DeleteUser;
import com.solr.clientwrapper.usecase.user.ReadUser;
import com.solr.clientwrapper.usecase.user.RegisterUser;
import com.solr.clientwrapper.usecase.user.UpdateUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey"));

    private final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    ////######## Without Using Verb Service Layer ######### ////////
	/*
	 * private final UserServicePort userServicePort; private final
	 * AuthorityServicePort authorityServicePort;
	 * 
	 * public PublicUserResource(UserServicePort userServicePort,
	 * AuthorityServicePort authorityServicePort) { this.userServicePort =
	 * userServicePort; this.authorityServicePort = authorityServicePort; }
	 */
    
    ////######## Using Verb Service Layer ######## ////////
    private final CreateUser createUser;
    private final RegisterUser registerUser;
    private final ReadUser readUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;
    
    private final ReadAuthority readAuthority;
    
    

    public PublicUserResource(CreateUser createUser, RegisterUser registerUser, ReadUser readUser,
			UpdateUser updateUser, DeleteUser deleteUser, ReadAuthority readAuthority) {
		this.createUser = createUser;
		this.registerUser = registerUser;
		this.readUser = readUser;
		this.updateUser = updateUser;
		this.deleteUser = deleteUser;
		this.readAuthority = readAuthority;
	}

	/**
     * {@code GET /users} : get all users with only the public informations -
     * calling this are allowed for anyone.
     *
     * @param pageable
     *            the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     *         body all users.
     */
    @GetMapping("/users")
    @Operation(summary = "/users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(Pageable pageable) {
        log.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = readUser.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * Gets a list of all roles.
     * 
     * @return a string list of all roles.
     */
    @GetMapping("/authorities")
    @Operation(summary = "/users", security = @SecurityRequirement(name = "bearerAuth"))
    public List<String> getAuthorities() {

        return readAuthority.getAuthorities();
    }
}
