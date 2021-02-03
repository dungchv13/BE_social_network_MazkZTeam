package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import socialnetwork.mazkzteam.model.entities.Emote;
import socialnetwork.mazkzteam.model.repositories.EmoteRepository;
import socialnetwork.mazkzteam.model.service.EmoteService;

import java.util.List;

public class EmoteServiceImpl implements EmoteService {

    @Autowired
    private EmoteRepository emoteRepository;

    @Override
    public List<Emote> getAll() {
        return emoteRepository.findAll();
    }

    @Override
    public Emote save(Emote emote) {
        return emoteRepository.save(emote);
    }

    @Override
    public Emote findById(int id) {
        return emoteRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            emoteRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
