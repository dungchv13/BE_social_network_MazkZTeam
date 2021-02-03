package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Photo;
import socialnetwork.mazkzteam.model.repositories.PhotoRepository;
import socialnetwork.mazkzteam.model.service.PhotoService;

import java.util.List;
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    @Override
    public Photo save(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public Photo findById(int id) {
        return photoRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            photoRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Photo> saveAllPhoto(List<Photo> photoList) {
        return photoRepository.saveAll(photoList);
    }
}
