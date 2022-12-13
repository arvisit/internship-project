package by.itacademy.profiler.security;


import by.itacademy.profiler.persistence.model.User;
import by.itacademy.profiler.persistence.repository.UserRepository;
import by.itacademy.profiler.security.jwt.JwtUser;
import by.itacademy.profiler.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username:" + username + "not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername -  loaded username: {} successfully loaded", username);
        return jwtUser;
    }
}
