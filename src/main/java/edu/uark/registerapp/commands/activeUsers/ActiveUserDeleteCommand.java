package edu.uark.registerapp.commands.activeUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {
    @Override
    public void execute(){
        // Supposed to add functionality for validating Employee request object?
        // Not sure where I'm supposed to find this employee object or why it matters

        // Query and delete the active user
        final Optional<ActiveUserEntity> queriedActiveUser =
            this.activeUserRepository.findBySessionKey(this.currentSessionKey);
        
        if (queriedActiveUser.isPresent()){
            this.activeUserRepository.delete(queriedActiveUser.get());
        }
        else{
            throw new NotFoundException("ActiveUserEntity");
        }
    }
    
    // Properties
    private String currentSessionKey;
    public void setCurrentSessionKey(String key){
        this.currentSessionKey = key;
    }

    @Autowired
    private ActiveUserRepository activeUserRepository;
}