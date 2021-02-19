package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.repositories.ClubRepository;
import socialnetwork.mazkzteam.model.service.ClubService;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Override
    public List<Club> getAll() {
        return clubRepository.findAll();
    }

    @Override
    public Club save(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public Club findById(int id) {
        return clubRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            clubRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Club> getClubsByUserCreate(int user_id) {
        return clubRepository.getClubsByFounder_Id(user_id);
    }

    @Override
    public List<Club> getClubByMembersContains(User user) {
        return clubRepository.getClubByMembersContains(user);
    }

    @Override
    public List<Club> getClubsByMembersIsNotContaining(User user) {
        return clubRepository.getClubsByMembersIsNotContaining(user);
    }
}
