package com.example.retakeManagement.services;

import com.example.retakeManagement.models.Cabinet;
import com.example.retakeManagement.repo.CabinetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с кабинетами.
 */
@Service
public class CabinetService {
    private final CabinetRepo cabinetRepo;

    /**
     * Конструктор класса CabinetService.
     *
     * @param cabinetRepo репозиторий для работы с кабинетами
     */
    @Autowired
    public CabinetService(CabinetRepo cabinetRepo) {
        this.cabinetRepo = cabinetRepo;
    }

    /**
     * Найти кабинет по номеру.
     *
     * @param number номер кабинета
     * @return найденный кабинет или null, если кабинет не найден
     */
    public Cabinet findByNumber(Integer number){
        return cabinetRepo.findByNumber(number).orElse(null);
    }

    /**
     * Получить все кабинеты.
     *
     * @return список всех кабинетов
     */
    public List<Cabinet> findAll(){
        return cabinetRepo.findAll();
    }

    /**
     * Найти кабинет по идентификатору.
     *
     * @param id идентификатор кабинета
     * @return найденный кабинет или null, если кабинет не найден
     */
    public Cabinet findById(Integer id){
        return cabinetRepo.findById(id).orElse(null);
    }

    /**
     * Сохранить кабинет.
     *
     * @param cabinet кабинет для сохранения
     */
    public void save(Cabinet cabinet){
        cabinetRepo.save(cabinet);
    }

    /**
     * Обновить информацию о кабинете.
     *
     * @param cabinet новая информация о кабинете
     * @param id идентификатор кабинета для обновления
     */
    public void update(Cabinet cabinet, Integer id){
        Cabinet cabinet1 = cabinetRepo.findById(id).orElse(null);
        cabinet1.setId(cabinet.getId());
        cabinet1.setNumber(cabinet.getNumber());
        cabinetRepo.save(cabinet1);
    }

    /**
     * Удалить кабинет по идентификатору.
     *
     * @param id идентификатор кабинета для удаления
     */
    public void delete(Integer id){
        cabinetRepo.deleteById(id);
    }
}