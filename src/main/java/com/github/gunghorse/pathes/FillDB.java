package com.github.gunghorse.pathes;

import com.github.gunghorse.pathes.sessions.CreatorRepository;
import com.github.gunghorse.pathes.sessions.SessionRepository;
import com.github.gunghorse.pathes.user.*;
import com.github.gunghorse.pathes.quests.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FillDB implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CreatorRepository creatorRepository;
    /*@Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private QuestPointRepository questPointRepository;
*/
    @Override
    public void run(String... args) throws Exception {

        // remove this lines to make your database longer than one session
        userRepository.deleteAll();
        questRepository.deleteAll();
        sessionRepository.deleteAll();
        creatorRepository.deleteAll();

        User dimonium = new User("Dimonium-239", "1234");
        User darkStalker = new User("DarkstalkeR", "1234");

        Quest kingsWay = new Quest("Droga królewska",
                "Piękne zabytki po drodze od Barbakana do Wawela");

        kingsWay.setCreator(dimonium);
        darkStalker.startQuestSession(kingsWay);
        userRepository.saveAll(Arrays.asList(dimonium, darkStalker));
        questRepository.save(kingsWay);

        //##################################


/*
        Quest kingsWay = new Quest("Droga królewska",
                "Piękne zabytki po drodze od Barbakana do Wawela",
                "Dimonium-239");

        questRepository.save(kingsWay);
        kingsWay = questRepository.findByTitle("Droga królewska").get(0);

        QuestPoint barbakan = new QuestPoint("visible",
                "Barbakan",
                "Został wzniesiony w latach 1498–1499 za panowania króla Jana Olbrachta w obawie przed najazdem " +
                        "wołosko-tureckim zagrażającym Krakowowi po klęsce bukowińskiej. Inspiracją do tej decyzji były " +
                        "dwa barbakany w Toruniu (Starotoruński z 1429 r. i Chełmiński z 1449 r.), których możliwości " +
                        "obronne skłoniły króla do budowy „takowej fortalicji” w Krakowie. Jan Olbracht osobiście położył " +
                        "tam kamień węgielny pod budowę i przekazał na ten cel 100 grzywien.",
                new GeoJsonPoint(50.065514, 19.941617), null);

        QuestPoint bramaFloreanska = new QuestPoint("visible",
                "Brama Floreanska",
                "Średniowieczna brama z basztą, położona na Starym Mieście w Krakowie u końca ulicy " +
                        "Floriańskiej, przy skrzyżowaniu z ulicą Pijarską. Stanowi pozostałość po dawnych murach " +
                        "miejskich. Jest jedną z ośmiu krakowskich bram obronnych obok Sławkowskiej, Grodzkiej, Wiślnej, " +
                        "Mikołajskiej, Rzeźniczej (na Gródku), Szewskiej, Nowej i Pobocznej.",
                new GeoJsonPoint(50.064861, 19.941389), null);

        QuestPoint kosciolMariacki = new QuestPoint("visible",
                "Koscioł Mariacki",
                "Jeden z największych i najważniejszych, po archikatedrze wawelskiej, kościołów Krakowa, " +
                        "od 1962 roku posiadający tytuł bazyliki mniejszej. Należy do najbardziej znanych zabytków Krakowa i Polski. " +
                        "Jest kościołem gotyckim, budowanym w XIV i XV wieku. Położony jest przy północno-wschodnim " +
                        "narożniku Rynku Głównego, na Placu Mariackim. Kościół znajduje się na trasie Małopolskiej " +
                        "Drogi św. Jakuba z Sandomierza do Tyńca.",
                new GeoJsonPoint(50.061667, 19.939167), null);

        QuestPoint sukiennice = new QuestPoint("visible",
                "Sukiennice",
                "Sukiennice podlegały przez wieki wielu przemianom i ich obecny kształt w niczym nie " +
                        "przypomina dawnych sukiennic. Już w roku 1257 książę Bolesław Wstydliwy przy lokacji Krakowa " +
                        "zobowiązał się postawić kamienne kramy sukienne. Stanowiły one podwójny rząd kramów, tworzących " +
                        "jakby uliczkę pośrodku Rynku. Sukiennice w tej postaci przetrwały do połowy XIV stulecia. ",
                new GeoJsonPoint(50.061667, 19.937222), null);

        barbakan.setQuestID(kingsWay.getId());
        bramaFloreanska.setQuestID(kingsWay.getId());
        kosciolMariacki.setQuestID(kingsWay.getId());
        sukiennice.setQuestID(kingsWay.getId());
        questPointRepository.saveAll(Arrays.asList(barbakan, bramaFloreanska, kosciolMariacki, sukiennice));

        barbakan = questPointRepository.findByQuestIDAndTitleAndLocation(kingsWay.getId(),
                barbakan.getTitle(), barbakan.getLocation()).get(0);

        bramaFloreanska = questPointRepository.findByQuestIDAndTitleAndLocation(kingsWay.getId(),
                bramaFloreanska.getTitle(), bramaFloreanska.getLocation()).get(0);

        kosciolMariacki = questPointRepository.findByQuestIDAndTitleAndLocation(kingsWay.getId(),
                kosciolMariacki.getTitle(), kosciolMariacki.getLocation()).get(0);

        sukiennice = questPointRepository.findByQuestIDAndTitleAndLocation(kingsWay.getId(),
                sukiennice.getTitle(), sukiennice.getLocation()).get(0);

        kingsWay.addQuestPoint(barbakan);
        kingsWay.addQuestPoint(bramaFloreanska);
        kingsWay.addQuestPoint(kosciolMariacki);
        kingsWay.addQuestPoint(sukiennice);

        questPointRepository.saveAll(Arrays.asList(barbakan, bramaFloreanska, kosciolMariacki, sukiennice));
        questRepository.save(kingsWay);

        SessionRel stalkerSession = new SessionRel(darkStalker, kingsWay);
        SessionRel dimoniumSession = new SessionRel(dimonium, kingsWay);
        dimoniumSession.setEnded(true);

        sessionRepository.saveAll(Arrays.asList(stalkerSession, dimoniumSession));

        GeoJsonPoint location = new GeoJsonPoint(50.065514001, 19.941617002);
        //Distance distance = new Distance(0.3, Metrics.KILOMETERS);
        //List<QuestPoint> gr = questPointRepository.findByLocationNear(location, 100);
        //System.out.println(gr);
*/
    }
}
