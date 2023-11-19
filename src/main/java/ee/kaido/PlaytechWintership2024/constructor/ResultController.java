package ee.kaido.PlaytechWintership2024.constructor;

import ee.kaido.PlaytechWintership2024.repository.CasinoRepository;
import ee.kaido.PlaytechWintership2024.service.ActionDataService;
import ee.kaido.PlaytechWintership2024.service.MatchDataService;
import ee.kaido.PlaytechWintership2024.service.ReportService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ResultController {
    private final MatchDataService matchDataService;
    private final ActionDataService actionDataService;
    private final CasinoRepository casinoRepository;
    private final ReportService reportService;

    public ResultController(MatchDataService matchDataService, ActionDataService actionDataService, CasinoRepository casinoRepository, ReportService reportService) {
        this.matchDataService = matchDataService;
        this.actionDataService = actionDataService;
        this.casinoRepository = casinoRepository;
        this.reportService = reportService;
    }

    @PostConstruct
    public void generateResult() {
        try {
            matchDataService.getMatchData();
            log.info("Got Match Data");
            actionDataService.getActionDataAndPlayGames();
            log.info("Actions done");
            Long casinoBalance = casinoRepository.getCasinoBalance();
            log.info("Got Balance");
            reportService.WriteToFile(casinoBalance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
