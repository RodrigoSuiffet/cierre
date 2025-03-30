package com.ecolucos.cierre.service;

import com.ecolucos.cierre.entities.DTO.CajaDTO;
import com.ecolucos.cierre.entities.DTO.ExpenseDTO;
import com.ecolucos.cierre.entities.db.Gasto;
import com.ecolucos.cierre.entities.db.Inicial;
import com.ecolucos.cierre.repository.CajaRepository;
import com.ecolucos.cierre.entities.db.Caja;

import com.ecolucos.cierre.repository.GastoRepository;
import com.ecolucos.cierre.util.CajaDTOMapper;
import com.ecolucos.cierre.util.GastosDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Slf4j
@Service
public class CajaService {

    @Autowired
    private CajaRepository cajaRepository;
    @Autowired
    private GastoRepository gastoRepository;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @Autowired
    private CajaDTOMapper cajaDTOMapper;

    @Autowired
    private GastosDTOMapper gastosDTOMapper;


    public Inicial getInicial (String shiftId) {
        Inicial inicial = new Inicial();
        inicial.setValor(Double.parseDouble(cajaRepository.findLastRecuentoByTurno(shiftId)));
       return inicial;

    }

    @Transactional
    public Caja saveCajaCierre(CajaDTO submission) {
        Caja caja = cajaDTOMapper.cajaDTOToCaja(submission);

        // Save the entity
        caja = cajaRepository.save(caja);

        // Save expenses
        if (submission.getGastos() != null && submission.getGastos().getExpenses() != null) {
            for (ExpenseDTO gasto : submission.getGastos().getExpenses()) {
                Gasto gastoDB = gastosDTOMapper.expenseDTOToGasto(gasto);
                gastoDB.setCaja(caja);
                gastoRepository.save(gastoDB);
            };

        }
        log.info("Saved cash register with ID: {}", caja.getId());

        return caja;
    }

    /**
     * Get a cash register by its ID
     */
    public Caja getCajaById(Long id) {
        Optional<Caja> cajaOpt = cajaRepository.findById(id);
        return cajaOpt.orElse(null);
    }


}