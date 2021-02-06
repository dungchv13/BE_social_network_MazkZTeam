package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Emote;
import socialnetwork.mazkzteam.model.repositories.EmoteRepository;
import socialnetwork.mazkzteam.model.service.EmoteService;

import java.util.List;
@Service
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


    @Override
    public boolean disliked(int post_id, int user_id) {
        try{
            emoteRepository.deleteEmoteByPost_idAndUser_id(post_id,user_id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
