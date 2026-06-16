package com.pgvet.pago.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import feign.FeignException;

import com.pgvet.pago.client.CitaClient;
import com.pgvet.pago.client.UsuarioClient;
import com.pgvet.pago.dto.CitaDTO;
import com.pgvet.pago.dto.PagoCreateDTO;
import com.pgvet.pago.dto.PagoDTO;
import com.pgvet.pago.dto.UsuarioDTO;
import com.pgvet.pago.model.Pago;
import com.pgvet.pago.repository.PagoRepository;

// Marca esta clase como servicio
@Service
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final UsuarioClient usuarioClient;
    private final CitaClient citaClient;

    public PagoService(PagoRepository pagoRepository, UsuarioClient usuarioClient,
                       CitaClient citaClient) {
        this.pagoRepository = pagoRepository;
        this.usuarioClient = usuarioClient;
        this.citaClient = citaClient;
    }

    // Convierte entidad a DTO de salida
    private PagoDTO convertirADTO(Pago pago) {
        return new PagoDTO(
                pago.getId(),
                pago.getTutorId(),
                pago.getCitaId(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getEstado(),
                pago.getNumeroBoleta(),
                pago.getFechaPago()
        );
    }

    // Convierte DTO de entrada a entidad
    private Pago convertirAEntidad(PagoCreateDTO dto) {
        Pago pago = new Pago();

        pago.setTutorId(dto.getTutorId());
        pago.setCitaId(dto.getCitaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado());
        pago.setNumeroBoleta(dto.getNumeroBoleta());
        pago.setFechaPago(dto.getFechaPago());

        return pago;
    }

    // Valida tutor y cita en otros microservicios (Feign)
    private void validarReferenciasExternas(PagoCreateDTO dto) {
        log.info("Validando referencias externas con Feign");

        log.info("Consultando tutor {} con Feign", dto.getTutorId());
        try {
            UsuarioDTO tutor = usuarioClient.buscarPorId(dto.getTutorId());
            if (tutor == null || tutor.getId() == null) {
                throw new RuntimeException("Usuario no encontrado");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Usuario no encontrado");
            throw new RuntimeException("Usuario no encontrado");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Usuario-Service");
            throw new RuntimeException("No se pudo conectar con Usuario-Service");
        }

        log.info("Consultando cita {} con Feign", dto.getCitaId());
        try {
            CitaDTO cita = citaClient.buscarPorId(dto.getCitaId());
            if (cita == null || cita.getId() == null) {
                throw new RuntimeException("Cita no encontrada");
            }
        } catch (FeignException.NotFound error) {
            log.warn("Cita no encontrada");
            throw new RuntimeException("Cita no encontrada");
        } catch (Exception error) {
            log.warn("No se pudo conectar con Cita-Service");
            throw new RuntimeException("No se pudo conectar con Cita-Service");
        }
    }

    // Listar todos los pagos
    public List<PagoDTO> listar() {
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar pago por ID
    public Optional<PagoDTO> buscarPorId(Long id) {
        return pagoRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Guardar pago nuevo
    public PagoDTO guardar(PagoCreateDTO dto) {
        log.info("Guardando registro");
        validarReferenciasExternas(dto);

        Pago pago = convertirAEntidad(dto);
        Pago pagoGuardado = pagoRepository.save(pago);
        return convertirADTO(pagoGuardado);
    }

    // Actualizar pago
    public PagoDTO actualizar(Long id, PagoCreateDTO dto) {
        log.info("Actualizando registro");

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        validarReferenciasExternas(dto);

        pago.setTutorId(dto.getTutorId());
        pago.setCitaId(dto.getCitaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado(dto.getEstado());
        pago.setNumeroBoleta(dto.getNumeroBoleta());
        pago.setFechaPago(dto.getFechaPago());

        return convertirADTO(pagoRepository.save(pago));
    }

    // Eliminar pago
    public void eliminar(Long id) {
        log.info("Eliminando registro id={}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pagoRepository.delete(pago);
    }
}
