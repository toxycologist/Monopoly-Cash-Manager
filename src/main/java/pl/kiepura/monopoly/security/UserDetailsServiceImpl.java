package pl.kiepura.monopoly.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kiepura.monopoly.manager.PlayerManager;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PlayerManager playerManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerManager.findByUsername(username);
    }
}