package com.solr.clientwrapper.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.solr.clientwrapper.domain.dto.AdminUserDTO;
import com.solr.clientwrapper.domain.port.api.UserServicePort;
import com.solr.clientwrapper.domain.port.spi.UserPersistencPort;
import com.solr.clientwrapper.infrastructure.entity.User;
import com.solr.clientwrapper.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class DeleteUserTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private UserMapper userMapper;
    private User user;
    private AdminUserDTO userDto;
    
    @Autowired
    @MockBean
    private UserServicePort userServicePort;
    
    @MockBean
    private UserPersistencPort userPersistencePort;
    
    @InjectMocks
    private DeleteUser deleteUser;

	@BeforeEach
    public void init() {
        userMapper = new UserMapper();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        userDto = new AdminUserDTO(user);
        deleteUser = new DeleteUser(userServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(userServicePort).isNotNull();
	}
	
    @Test
    void deleteUserTest() {
    	Mockito.doNothing().when(userServicePort)
    			.deleteUser(DEFAULT_LOGIN);
    	deleteUser.deleteUser(DEFAULT_LOGIN);
    }
    
    @Test
    void removeNonActivatedUserTest() {
    	
    	// testing
    	// System.out.println("user present: "+userPersistencePort.findOneByLogin(DEFAULT_LOGIN).isPresent());
    	
    	Mockito.when(userPersistencePort.findOneByLogin(DEFAULT_LOGIN)
    			.isPresent())
    			.thenReturn(null);
    	deleteUser.removeNonActivatedUser(user);
    }
    
    @Test
    void removeNonActivatedUsersTest() {
    	
    	// testing
    	// System.out.println("user present: "+userPersistencePort.findOneByLogin(DEFAULT_LOGIN).isPresent());
    	
    	Mockito.when(userPersistencePort.findOneByLogin(DEFAULT_LOGIN)
    			.isPresent())
    			.thenReturn(null);
    	deleteUser.removeNotActivatedUsers();
    }
    
}
