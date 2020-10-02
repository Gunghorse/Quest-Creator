package com.github.gunghorse.questCreator;

import com.github.gunghorse.questCreator.quests.points.*;
import com.github.gunghorse.questCreator.repositories.QuestPointRepository;
import com.github.gunghorse.questCreator.repositories.QuestRepository;
import com.github.gunghorse.questCreator.repositories.QuestStartPointRepository;
import com.github.gunghorse.questCreator.repositories.UserRepository;
import com.github.gunghorse.questCreator.user.*;
import com.github.gunghorse.questCreator.quests.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FillDB implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private QuestPointRepository questPointRepository;
    @Autowired
    private QuestStartPointRepository questStartPointRepository;

    private UserService userService;

    /*
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CreatorRepository creatorRepository;
    @Autowired
    private ChildPointRelRepository childPointRelRepository;
    @Autowired
    private PointQuestRelRepository pointQuestRelRepository;
    */

    @Override
    public void run(String... args) throws Exception {

        // remove this lines to make your database longer than one session
        userRepository.deleteAll();
        questRepository.deleteAll();
        questPointRepository.deleteAll();
        questStartPointRepository.deleteAll();

        userService = new UserService(userRepository);

        /*
        sessionRepository.deleteAll();
        creatorRepository.deleteAll();
        childPointRelRepository.deleteAll();
        pointQuestRelRepository.deleteAll();
         */

        UserDTO dimonium = new UserDTO();
        dimonium.setEmail("dymitr.kuzmin@gmail.com");
        dimonium.setPassword("1234");
        dimonium.setUsername("Dimonium-239");

        UserDTO darkStalker = new UserDTO();
        darkStalker.setEmail("dakr@stalker.com");
        darkStalker.setPassword("1234");
        darkStalker.setUsername("DarkStalker");

        Quest kingsWay = new Quest("Droga królewska",
                "Piękne zabytki po drodze od Barbakana do Wawela");

        User dimoniumUser = userService.registerNewUserAccount(dimonium);
        User darkStalkerUser = userService.registerNewUserAccount(darkStalker);
        kingsWay.setCreator(dimoniumUser);
        darkStalkerUser.startQuestSession(kingsWay);



        QuestStartPoint barbakan = new QuestStartPoint(QuestPointStatus.UNVISITED_VISIBLE,
                "Barbakan",
                "Został wzniesiony w latach 1498–1499 za panowania króla Jana Olbrachta w obawie przed najazdem " +
                        "wołosko-tureckim zagrażającym Krakowowi po klęsce bukowińskiej. Inspiracją do tej decyzji były " +
                        "dwa barbakany w Toruniu (Starotoruński z 1429 r. i Chełmiński z 1449 r.), których możliwości " +
                        "obronne skłoniły króla do budowy „takowej fortalicji” w Krakowie. Jan Olbracht osobiście położył " +
                        "tam kamień węgielny pod budowę i przekazał na ten cel 100 grzywien.",
                new Point(50.065514, 19.941617));

        kingsWay.setStartPoint(barbakan);

        QuestPoint bramaFloreanska = new QuestPoint(QuestPointStatus.UNVISITED_VISIBLE,
                "Brama Floreanska",
                "Średniowieczna brama z basztą, położona na Starym Mieście w Krakowie u końca ulicy " +
                        "Floriańskiej, przy skrzyżowaniu z ulicą Pijarską. Stanowi pozostałość po dawnych murach " +
                        "miejskich. Jest jedną z ośmiu krakowskich bram obronnych obok Sławkowskiej, Grodzkiej, Wiślnej, " +
                        "Mikołajskiej, Rzeźniczej (na Gródku), Szewskiej, Nowej i Pobocznej.",
                new Point(50.064861, 19.941389));
        bramaFloreanska.setQuest(kingsWay);
        bramaFloreanska.addParent(barbakan);

        QuestPoint kosciolMariacki = new QuestPoint(QuestPointStatus.UNVISITED_VISIBLE,
                "Koscioł Mariacki",
                "Jeden z największych i najważniejszych, po archikatedrze wawelskiej, kościołów Krakowa, " +
                        "od 1962 roku posiadający tytuł bazyliki mniejszej. Należy do najbardziej znanych zabytków Krakowa i Polski. " +
                        "Jest kościołem gotyckim, budowanym w XIV i XV wieku. Położony jest przy północno-wschodnim " +
                        "narożniku Rynku Głównego, na Placu Mariackim. Kościół znajduje się na trasie Małopolskiej " +
                        "Drogi św. Jakuba z Sandomierza do Tyńca.",
                new Point(50.061667, 19.939167));
        kosciolMariacki.setQuest(kingsWay);
        kosciolMariacki.addParent(bramaFloreanska);

        QuestPoint sukiennice = new QuestPoint(QuestPointStatus.UNVISITED_VISIBLE,
                "Sukiennice",
                "Sukiennice podlegały przez wieki wielu przemianom i ich obecny kształt w niczym nie " +
                        "przypomina dawnych sukiennic. Już w roku 1257 książę Bolesław Wstydliwy przy lokacji Krakowa " +
                        "zobowiązał się postawić kamienne kramy sukienne. Stanowiły one podwójny rząd kramów, tworzących " +
                        "jakby uliczkę pośrodku Rynku. Sukiennice w tej postaci przetrwały do połowy XIV stulecia. ",
                new Point(50.061667, 19.937222));
        sukiennice.setQuest(kingsWay);
        sukiennice.addParent(kosciolMariacki);

        // Here we make fork
        QuestPoint stAnne = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Kościół św. Anny",
                "Zabytkowa, barokowa kolegiata rzymskokatolicka położona " +
                        "przy ulicy św. Anny 13 w Krakowie. Jest kościołem akademickim Uniwersytetu Jagiellońskiego, " +
                        "a także kościołem parafialnym dla parafii św. Anny. Posługę sprawują księża diecezjalni. ",
                new Point(50.0610393,19.9375758));
        stAnne.setQuest(kingsWay);
        stAnne.addParent(sukiennice);

        QuestPoint uj = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Uniwersytet Jagielloński",
                "Uniwersytet (studium generale) został założony w 1364 roku w Kazimierzu" +
                        " z fundacji Kazimierza III Wielkiego, i składał się początkowo z trzech wydziałów: " +
                        "sztuk wyzwolonych, medycyny i prawa[5]. Był to drugi, po powstałym w 1348 roku " +
                        "Uniwersytecie w Pradze, uniwersytet założony w środkowej Europie. " +
                        "Reaktywowany około 1390 roku przez Jadwigę Andegaweńską. Oficjalny odnowiony w " +
                        "1400 roku w Krakowie przez Władysława II Jagiełłę z fundacji Jadwigi Andegaweńskiej, " +
                        "poszerzony o wydział teologiczny, utworzony dzięki zabiegom Jadwigi w 1397 roku.",
                new Point(50.0609153,19.9355373));

        uj.setQuest(kingsWay);
        uj.addParent(stAnne);

        QuestPoint stFranciszek = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Bazylika Franciszkanów św. Franciszka z Asyżu",
                "W roku 1236 lub 1237 książę Henryk Pobożny sprowadził franciszkanów z Pragi. " +
                        "Kościół został rozbudowany około 1269 roku[2] i następnie w 1279 roku została w nim " +
                        "pochowany książę Bolesław V Wstydliwy wraz z siostrą bł. Salomeą. Zostali tu też pochowani " +
                        "synowie Władysława Łokietka (Stefan i Władysław).\n"+
                        "Wygląd pierwotnego kościoła jest przedmiotem dyskusji. Nie jest wykluczone, " +
                        "że pierwotny kościół był zbudowany na planie krzyża greckiego (równoramiennego) " +
                        "z wieżą na przecięciu naw lub w narożu szczytu nawy północnej i zachodniego ramienia części " +
                        "krzyżowej[2]. Budulcem była cegła (z dodatkiem kamiennych detali). Ok. 1260-1270 dobudowano " +
                        "do niego zakrystię. W 1269 roku kościół konsekrowano pod wezwaniem św. Franciszka z Asyżu. " +
                        "Inicjatorem i głównym inwestorem przebudowy kościoła franciszkanów w Krakowie w końcu " +
                        "lat sześćdziesiątych XIII wieku był książę krakowsko-sandomierski Bolesław Wstydliwy. " +
                        "Zapewne krótko po śmierci księżnej Salomei zrodził się na krakowskim dworze pomysł " +
                        "wzniesienia rodowego mauzoleum, w którym, obok kreowanej na przyszłą świętą księżnej Salomei, " +
                        "w przyszłości miał być pochowany książę Bolesław Wstydliwy i jego żona Kinga",
                new Point(50.0587663,19.9338958));
        stFranciszek.setQuest(kingsWay);
        stFranciszek.addParent(uj);

        QuestPoint wsd = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Wyższe Seminarium Duchowne Archidiecezji Krakowskiej",
                "W 1601 na synodzie diecezjalnym wydano dekret o powstaniu seminarium. " +
                        "Z kolei 20 grudnia 1602 kard. Bernard Maciejowski podejmuje decyzję o erygowaniu seminarium. " +
                        "Początkowo alumni mieszkali na Wawelu, ale z czasem wyodrębniły się trzy seminaria: " +
                        "prowadzone przez księży misjonarzy, akademickie i zamkowe. Jednak 28 września 1901 " +
                        "powstało nowe seminarium diecezjalne przy ul. Podzamcze. Zostało powołane przez biskupa " +
                        "krakowskiego kardynała Jana Puzynę, którego staraniem wzniesiono w latach 1899–1902 " +
                        "neogotycki gmach według projektu Gabriela Niewiadomskiego. Zarząd nowego seminarium stanowili " +
                        "księża diecezjalni. W latach I i II wojny światowej budynek był w rękach okupanta. " +
                        "Lata powojenne pozwoliły na modernizacje obiektu. W roku 1954 diecezja oddała na " +
                        "użytkowanie seminarium budynek przy ul. Piłsudskiego 4 (wówczas Manifestu Lipcowego 4). " +
                        "W kolejnych latach dokonano wielu remontów i modernizacji tak, aby 17 listopada 2001 " +
                        "uroczyście świętować 400-lecie seminarium, które służy wielu pokoleniom kapłanów. " +
                        "13 sierpnia 1991 seminarium odwiedził papież Jan Paweł II.",
                new Point(50.0557958,19.9349901)
                );
        wsd.setQuest(kingsWay);
        wsd.addParent(stFranciszek);

        // Another branch
        QuestPoint stWojciech = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Kościół Świętego Wojciecha",
                "Wedle tradycji w tym miejscu miał głosić kazania św. Wojciech, " +
                        "a na pamiątkę tego wydarzenia miał powstać drewniany kościółek. " +
                        "Podczas badań archeologicznych odsłonięto pod obecną budowlą relikty wcześniejszej, " +
                        "murowanej, z przełomu X i XI wieku. Obecna romańska świątynia powstała w 2. połowie XI " +
                        "lub na początku XII wieku. W 1404 roku, dzięki bp. Piotrowi Wyszowi Radolińskiemu, kościół " +
                        "został prebendą uniwersytecką. W 1453 wygłaszał tu swoje kazania Jan Kapistran. Na początku " +
                        "XVII w. budowla uległa barokizacji: podwyższono ściany kościoła, całość budowli nakryto kopułą, " +
                        "wytynkowano romańskie mury oraz wybudowano od strony zachodniej nowe wejście. Przebudową " +
                        "kierowali prof. Walenty Fontana oraz ks. Sebastian Mirosz. W 1711 roku dobudowano zakrystię, " +
                        "w 1778 – kaplicę bł. Wincentego Kadłubka.",
                new Point(50.0610393,19.9375758));
        stWojciech.setQuest(kingsWay);
        stWojciech.addParent(sukiennice);

        QuestPoint poitraIPawla = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Kościół św. Apostołów Piotra i Pawła",
                "Jest to pierwsza budowla architektury barokowej w Krakowie. " +
                        "Ufundowana została dla jezuitów przez króla Zygmunta III Wazę. " +
                        "Plan kościoła wykonał prawdopodobnie Giovanni de Rossis, plan ten był realizowany od " +
                        "1597 roku – najpierw przez Józefa Britiusa (Giuseppe Brizio), a następnie modyfikowany " +
                        "przez Giovanniego Marię Bernardoniego. Ostateczny kształt kościołowi nadał w " +
                        "latach 1605-1619 Giovanni Trevano, będący autorem projektów fasady, kopuły i wystroju wnętrza. ",
                new Point(50.0569298,19.9379164)
                );

        poitraIPawla.setQuest(kingsWay);
        poitraIPawla.addParent(stWojciech);

        QuestPoint stAndrzej = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Kościół św. Andrzeja w Krakowie",
                "Kościół zbudowano w latach 1079–1098, z fundacji palatyna Sieciecha. " +
                        "Był główną świątynią osady Okół. Znajdował się początkowo pod patronatem benedyktynów " +
                        "z opactwa w Sieciechowie, a potem z Tyńca. Być może początkowo nosił wezwanie św. Idziego. " +
                        "Według Długosza broniła się tutaj ludność Okołu przed Mongołami w 1241. W 1243 " +
                        "Konrad Mazowiecki podczas walk o tron krakowski otoczył kościół fosą i wałem. " +
                        "Ponowny najazd tatarski z 1260 spowodował prawdopodobnie częściowe zniszczenie świątyni.\n" +
                        "W 1320 kościół otrzymały klaryski, dotąd mające swoją siedzibę w Grodzisku pod Skałą. " +
                        "Wybudowano wówczas dla nowych lokatorek zabudowania klasztorne z fundacji króla " +
                        "Władysława Łokietka. Kościół kilkakrotnie padał ofiarą pożarów, nie spowodowało to " +
                        "jednak poważniejszych zmian w wyglądzie zewnętrznym (poza dodaniem barokowych hełmów " +
                        "wież i portalu od strony północnej), ale w XVIII w. dokonano gruntownej barokizacji " +
                        "wnętrza (prace prowadzić tu mieli Baltazar Fontana – dekoracja stiukowa i polichromia, " +
                        "oraz być może Franciszek Placidi – ołtarz). ",
                new Point(50.0567455,19.9380371));

        stAndrzej.setQuest(kingsWay);
        stAndrzej.addParent(poitraIPawla);

        QuestPoint stIdzi = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Kościół św. Idziego w Krakowie",
                "Według tradycji został on wybudowany w końcu XI w. przez księcia Władysława Hermana " +
                        "jako dziękczynienie po urodzeniu się jego syna Bolesława Krzywoustego, które mieli zawdzięczać " +
                        "modłom i darom zanoszonym do prowansalskiego opactwa St. Gilles. Pod obecnym kościołem " +
                        "gotyckim nie odnaleziono jednak żadnych reliktów romańskich, a trudno oczekiwać, iż świątynia " +
                        "fundowana z takich powodów, byłaby drewniana. Z tego powodu część badaczy skłania się ku " +
                        "przypuszczeniu, że pierwotnie wezwanie św. Idziego nosił dzisiejszy kościół św. Andrzeja, " +
                        "a pierwszym budynkiem na tym miejscu była świątynia gotycka. ",
                new Point(50.0552094,19.9379017));
        stIdzi.setQuest(kingsWay);
        stIdzi.addParent(stAndrzej);

        // End branch

        QuestPoint wawel = new QuestPoint(QuestPointStatus.UNVISITED_INVISIBLE,
                "Zamek Królewski na Wawelu",
                "Zamek był na przestrzeni wieków wielokrotnie rozbudowywany i odnawiany. " +
                        "Liczne pożary, grabieże i przemarsze obcych wojsk, połączone z niszczeniem " +
                        "rezydencji powodowały, iż obiekt wielokrotnie odbudowywano w nowych stylach " +
                        "architektonicznych oraz remontowano jego szatę zewnętrzną, a także przekształcano i " +
                        "zmieniano wygląd i wyposażenie wnętrz. ",
                new Point(50.0520527,19.9370058));
        wawel.setQuest(kingsWay);
        wawel.addParent(stIdzi);
        wawel.addParent(wsd);

        questRepository.save(kingsWay);
        Quest quest = ((List<Quest>)questRepository.findAll()).get(0);
        questStartPointRepository.save(barbakan);
        questPointRepository.saveAll(Arrays.asList(bramaFloreanska, kosciolMariacki, sukiennice,
                stAnne, uj, stFranciszek, wsd, stWojciech, poitraIPawla, stAndrzej, stIdzi,
                wawel));

    }
}
