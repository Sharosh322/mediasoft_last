package restaraunt.ex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restaraunt.ex.entity.Visitor;
import restaraunt.ex.repository.VisitorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public void save(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public void remove(Visitor visitor) {
        visitorRepository.remove(visitor);
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }
}
