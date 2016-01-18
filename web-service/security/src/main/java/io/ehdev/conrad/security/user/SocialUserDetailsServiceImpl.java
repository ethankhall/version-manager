package io.ehdev.conrad.security.user;

import io.ehdev.conrad.security.database.model.UserModel;
import io.ehdev.conrad.security.database.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

    private final UserModelRepository userModelRepository;

    @Autowired
    public SocialUserDetailsServiceImpl(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        UserModel one = userModelRepository.findOne(UUID.fromString(userId));
        if(null == one) {
            throw new UsernameNotFoundException("Unable to find user " + userId);
        }
        return new UserPrincipal(one);
    }
}
