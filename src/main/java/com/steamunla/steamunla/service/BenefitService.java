package com.steamunla.steamunla.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.Benefit;
import com.steamunla.steamunla.repository.BenefitRepository;

@Service
public class BenefitService {

    private final BenefitRepository benefitRepository;

    public BenefitService(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    // =========================
    // LISTAR POR JUEGO
    // =========================

    public List<Benefit> getByGameId(Long gameId) {
        return benefitRepository.findByGameId(gameId);
    }

    // =========================
    // LISTAR ACTIVOS
    // =========================

    public List<Benefit> getActiveBenefits() {
        return benefitRepository.findByActiveTrue();
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    public Benefit getById(Long id) {
        return benefitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefit no encontrado"));
    }

    // =========================
    // GUARDAR
    // =========================

    public Benefit save(Benefit benefit) {
        return benefitRepository.save(benefit);
    }

    // =========================
    // ELIMINAR
    // =========================

    public void delete(Long id) {
        benefitRepository.deleteById(id);
    }
}