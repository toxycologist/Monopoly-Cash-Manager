package pl.kiepura.monopoly.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kiepura.monopoly.repo.PlayerRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PlayerRepo playerRepo;


    public UserDetailsServiceImpl(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerRepo.findByUsername(username);
    }
}