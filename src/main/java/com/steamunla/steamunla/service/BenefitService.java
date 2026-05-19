
package com.steamunla.steamunla.service;

import com.steamunla.steamunla.model.Benefit;
import com.steamunla.steamunla.repository.BenefitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenefitService {

    @Autowired
    private BenefitRepository benefitRepository;

    // LISTAR POR JUEGO
    public List<Benefit> getByGameId(Long gameId) {
        return benefitRepository.findByGameId(gameId);
    }

    // LISTAR ACTIVOS
    public List<Benefit> getActiveBenefits() {
        return benefitRepository.findByActiveTrue();
    }

    // BUSCAR POR ID
    public Benefit getById(Long id) {
        return benefitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit no encontrado"));
    }

    // GUARDAR / CREAR / UPDATE
    public Benefit save(Benefit benefit) {
        return benefitRepository.save(benefit);
    }

    // ELIMINAR
    public void delete(Long id) {
        benefitRepository.deleteById(id);
    }
}