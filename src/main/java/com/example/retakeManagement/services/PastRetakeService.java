package com.example.retakeManagement.services;

import com.example.retakeManagement.models.PastRetake;
import com.example.retakeManagement.models.User;
import com.example.retakeManagement.repo.PastRetakeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с пересдачами (Past Retakes).
 */
@Service
public class PastRetakeService {
    private final PastRetakeRepo pastRetakeRepo;

    /**
     * Конструктор сервиса.
     * @param pastRetakeRepo Репозиторий для хранения данных о пересдачах.
     */
    public PastRetakeService(PastRetakeRepo pastRetakeRepo) {
        this.pastRetakeRepo = pastRetakeRepo;
    }

    /**
     * Получает все пересдачи для указанного пользователя.
     * @param user Пользователь, для которого необходимо получить пересдачи.
     * @return Список пересдач пользователя.
     */
    public List<PastRetake> findAllByUser(User user){
        return pastRetakeRepo.findAllByUser(user);
    }

    /**
     * Сохраняет информацию о пересдаче.
     * @param pastRetake Информация о пересдаче.
     */
    public void save(PastRetake pastRetake){
        pastRetakeRepo.save(pastRetake);
    }

    /**
     * Удаляет пересдачу по идентификатору.
     * @param id Идентификатор пересдачи.
     */
    public void delete(Integer id){
        pastRetakeRepo.deleteById(id);
    }
}