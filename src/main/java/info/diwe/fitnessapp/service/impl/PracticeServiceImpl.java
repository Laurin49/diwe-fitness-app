package info.diwe.fitnessapp.service.impl;

import info.diwe.fitnessapp.exception.ResourceNotFoundException;
import info.diwe.fitnessapp.model.Practice;
import info.diwe.fitnessapp.repository.PracticeRepository;
import info.diwe.fitnessapp.service.PracticeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticeServiceImpl implements PracticeService {

    private PracticeRepository practiceRepository;

    public PracticeServiceImpl(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    @Override
    public List<Practice> readPractices() {
        return practiceRepository.findAll();
    }

    @Override
    public Optional<Practice> readPractice(Long id) {
        Optional<Practice> optPractice = practiceRepository.findById(id);
        if (!optPractice.isPresent()) {
            throw new ResourceNotFoundException("Practice mit id: " + id + " not found ...");
        }
        return optPractice;
    }

    @Override
    public Practice createPractice(Practice practice) {
        Practice result = practiceRepository.save(practice);
        return result;
    }

    @Override
    public Practice updatePractice(Practice practice) {
        Optional<Practice> optPractice = practiceRepository.findById(practice.getId());
        if (!optPractice.isPresent()) {
            throw new ResourceNotFoundException("Practice mit id: " + practice.getId() + " not found ...");
        }
        Practice result = practiceRepository.save(practice);
        return result;
    }

    @Override
    public void deletePractice(Long id) {
        Optional<Practice> optPractice = practiceRepository.findById(id);
        if (!optPractice.isPresent()) {
            throw new ResourceNotFoundException("Practice mit id: " + id + " not found ...");
        }
        practiceRepository.delete(optPractice.get());
    }
}
